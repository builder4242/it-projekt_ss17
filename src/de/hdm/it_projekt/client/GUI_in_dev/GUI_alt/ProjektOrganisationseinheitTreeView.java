package de.hdm.it_projekt.client.GUI_in_dev.GUI_alt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.BusinessObject;
import de.hdm.it_projekt.shared.bo.Projekt;




public class ProjektOrganisationseinheitTreeView implements TreeViewModel {

	
	private ProjektForm projektForm;
	private OrganisationseinheitForm organisationseinheitForm;

	private Projekt selectedProjekt = null;
	private Organisationseinheit selectedOrganisationseinheit = null;

	private ProjektAdministrationAsync projektVerwaltung = null;
	private ListDataProvider<Projekt> ProjektDataProvider = null;
	
	private Map<Projekt, ListDataProvider<Organisationseinheit>> OrganisationseinheitDataProviders = null;

	/**
	 * Bildet BusinessObjects auf eindeutige Zahlenobjekte ab, die als Schlüssel
	 * für Baumknoten dienen. Dadurch werden im Selektionsmodell alle Objekte
	 * mit derselben id selektiert, wenn eines davon selektiert wird. Der
	 * Schlüssel für Kundenobjekte ist eine positive, der für Kontenobjekte eine
	 * negative Zahl, die sich jeweils aus der id des Objektes ergibt. Dadurch
	 * können Kunden- von Kontenobjekten unterschieden werden, auch wenn sie
	 * dieselbe id haben.
	 */
	
	private class BusinessObjectKeyProvider implements
			ProvidesKey<BusinessObject> {
		@Override
		public Integer getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			}
			if (bo instanceof Projekt) {
				return new Integer(bo.getId());
			} else {
				return new Integer(-bo.getId());
			}
		}
	};

	
	
	
	
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLeaf(Object value) {
		// TODO Auto-generated method stub
		return false;
	}





/**
 * Diese Implementierung des TreeViewModels sorgt für die Verwaltung des Kunden-
 * und Kontenbaumes.
 * 
 * @author Christian Rathke
 * 
 */

	/*
	 * In dieser Map merken wir uns die ListDataProviders für die Kontolisten
	 * der im Kunden- und Kontobaum expandierten Kundenknoten.
	 */
	
	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObject> selectionModel = null;

	/**
	 * Nested Class für die Reaktion auf Selektionsereignisse. Als Folge einer
	 * Baumknotenauswahl wird je nach Typ des Business-Objekts der
	 * "selectedProjekt" bzw. das "selectedOrganisationseinheit" gesetzt.
	 */
	private class SelectionChangeEventHandler implements
			SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Projekt) {
				setSelectedProjekt((Projekt) selection);
			} else if (selection instanceof Organisationseinheit) {
				setSelectedOrganisationseinheit((Organisationseinheit) selection);
			}
		}
	}

	/*
	 * Im Konstruktor werden die für den Kunden- und Kontobaum wichtigen lokalen
	 * Variaben initialisiert.
	 */
	public ProjektOrganisationseinheitsTreeViewModel() {
		projektVerwaltung = ClientsideSettings.getProjektVerwaltung();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEventHandler());
		OrganisationseinheitDataProviders = new HashMap<Projekt, ListDataProvider<Organisationseinheit>>();
	}

	void setProjektForm(ProjektForm cf) {
		projektForm = cf;
	}

	void setOrganisationseinheitForm(OrganisationseinheitForm af) {
		organisationseinheitForm = af;
	}

	Projekt getSelectedProjekt() {
		return selectedProjekt;
	}

	void setSelectedProjekt(Projekt c) {
		selectedProjekt = c;
		projektForm.setSelected(c);
		selectedOrganisationseinheit = null;
		organisationseinheitForm.setSelected(null);
	}

	Organisationseinheit getSelectedOrganisationseinheit() {
		return selectedOrganisationseinheit;
	}

	/*
	 * Wenn ein Konto ausgewählt wird, wird auch der ausgewählte Kunde
	 * angepasst.
	 */
	void setSelectedOrganisationseinheit(Organisationseinheit a) {
		selectedOrganisationseinheit = a;
		organisationseinheitForm.setSelected(a);

		if (a != null) {
			projektVerwaltung.getProjektById(a.getId(),
					new AsyncCallback<Projekt>() {
						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(Projekt projekt) {
							selectedProjekt = projekt;
							projektForm.setSelected(projekt);
						}
					});
		}
	}

	/*
	 * Wenn ein Kunde neu erzeugt wurde, wird er selektiert.
	 */
	void addProjekt(Projekt projekt) {
		ProjektDataProvider.getList().add(projekt);
		selectionModel.setSelected(projekt, true);
	}

	void updateProjekt(Projekt projekt) {
		List<Projekt> projektList = ProjektDataProvider.getList();
		int i = 0;
		for (Projekt c : projektList) {
			if (c.getId() == projekt.getId()) {
				projektList.set(i, projekt);
				break;
			} else {
				i++;
			}
		}
		ProjektDataProvider.refresh();
	}

	void removeProjekt(Projekt projekt) {
		ProjektDataProvider.getList().remove(projekt);
		OrganisationseinheitDataProviders.remove(projekt);
	}

	void addOrganisationseinheitOfProjekt(Organisationseinheit organisationseinheit, Projekt projekt) {
		// falls es noch keinen Organisationseinheit Provider für diesen Projekt gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!OrganisationseinheitDataProviders.containsKey(projekt)) {
			return;
		}
		ListDataProvider<Organisationseinheit> OrganisationseinheitsProvider = OrganisationseinheitDataProviders
				.get(projekt);
		if (!OrganisationseinheitsProvider.getList().contains(organisationseinheit)) {
			OrganisationseinheitsProvider.getList().add(organisationseinheit);
		}
		selectionModel.setSelected(organisationseinheit, true);
	}

	void removeOrganisationseinheitOfProjekt(Organisationseinheit organisationseinheit, Projekt projekt) {
		// falls es keinen Organisationseinheit Provider für diesen Projekt gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!OrganisationseinheitDataProviders.containsKey(projekt)) {
			return;
		}
		OrganisationseinheitDataProviders.get(projekt).getList().remove(organisationseinheit);
		selectionModel.setSelected(projekt, true);
	}

	/*
	 * Ein Konto in der Baumstruktur soll ersetzt werden durch ein Konto mit derselben id.
	 * Dies ist sinnvoll, wenn sich die Eigenschaften eines Kontos geändert haben und in der
	 * Baumstruktur noch ein "veraltetes" Kontoobjekt enthalten ist.
	 */
	void updateOrganisationseinheit(Organisationseinheit a) {
		projektVerwaltung.getProjektById(a.getId(),
				new UpdateOrganisationseinheitCallback(a));
	}

	private class UpdateOrganisationseinheitCallback implements AsyncCallback<Projekt> {

		Organisationseinheit organisationseinheit = null;

		UpdateOrganisationseinheitCallback(Organisationseinheit a) {
			organisationseinheit = a;
		}

		@Override
		public void onFailure(Throwable t) {
		}

		@Override
		public void onSuccess(Projekt projekt) {
			List<Organisationseinheit> organisationseinheitList = OrganisationseinheitDataProviders.get(projekt)
					.getList();
			for (int i=0; i<organisationseinheitList.size(); i++) {
				if (organisationseinheit.getId() == organisationseinheitList.get(i).getId()) {
					organisationseinheitList.set(i, organisationseinheit);
					break;
				}
			}
		}
	}

	// Get the NodeInfo that provides the children of the specified value.
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value.equals("Root")) {
			// Erzeugen eines ListDataproviders für Projektdaten
			ProjektDataProvider = new ListDataProvider<Projekt>();
			projektVerwaltung
					.getAllProjekts(new AsyncCallback<Vector<Projekt>>() {
						@Override
						public void onFailure(Throwable t) {
						}

						@Override
						public void onSuccess(Vector<Projekt> Projekts) {
							for (Projekt c : Projekts) {
								ProjektDataProvider.getList().add(c);
							}
						}
					});

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Projekt>(ProjektDataProvider,
					new ProjektCell(), selectionModel, null);
		}

		if (value instanceof Projekt) {
			// Erzeugen eines ListDataproviders für Organisationseinheit-Daten
			final ListDataProvider<Organisationseinheit> OrganisationseinheitsProvider = new ListDataProvider<Organisationseinheit>();
			OrganisationseinheitDataProviders.put((Projekt) value, OrganisationseinheitsProvider);

			projektVerwaltung.getOrganisationseinheitsOf((Projekt) value,
					new AsyncCallback<Vector<Organisationseinheit>>() {
					
						public void onFailure(Throwable t) {
						}

						public void onSuccess(Vector<Organisationseinheit> organisationseinheits) {
							for (Organisationseinheit a : organisationseinheits) {
								OrganisationseinheitProvider.getList().add(a);
							}
						}
					

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Organisationseinheit>(organisationseinheitsProvider,
					new OrganisationseinheitCell(), selectionModel, null);
			}
		return null;
	}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		// value is of type Organisationseinheit
		return (value instanceof Organisationseinheit);
	}

}
