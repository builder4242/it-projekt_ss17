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

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.BusinessObject;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Projekt;


public class PartnerprofilProjektTreeView implements TreeViewModel{



	
	private PartnerprofilForm partnerprofilForm;
	private ProjektForm projektForm;

	private Partnerprofil selectedPartnerprofil = null;
	private Projekt selectedProjekt = null;

	private ProjektAdministrationAsync projektVerwaltung = null; //warten auf anlage Async
	private ListDataProvider<Partnerprofil> PartnerprofilDataProvider = null;
	
	private Map<Partnerprofil, ListDataProvider<Projekt>> ProjektDataProviders = null;

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
			if (bo instanceof Partnerprofil) {
				return new Integer(bo.getId());
			} else {
				return new Integer(-bo.getId());
			}
		}
	};

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
	 * "selectedPartnerprofil" bzw. das "selectedProjekt" gesetzt.
	 */
	private class SelectionChangeEventHandler implements
			SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Partnerprofil) {
				setSelectedPartnerprofil((Partnerprofil) selection);
			} else if (selection instanceof Projekt) {
				setSelectedProjekt((Projekt) selection);
			}
		}
	}

	/*
	 * Im Konstruktor werden die für den Kunden- und Kontobaum wichtigen lokalen
	 * Variaben initialisiert.
	 */
	public void PartnerprofilProjektsTreeViewModel() {
		projektVerwaltung = ClientsideSettings.getProjektAdminstration();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEventHandler());
		ProjektDataProviders = new HashMap<Partnerprofil, ListDataProvider<Projekt>>();
	}

	void setPartnerprofilForm(Partnerprofil cf) {
		partnerprofilForm = cf;
	}

	void setProjektForm(ProjektForm af) {
		projektForm = af;
	}

	Partnerprofil getSelectedPartnerprofil() {
		return selectedPartnerprofil;
	}

	void setSelectedPartnerprofil(Partnerprofil c) {
		selectedPartnerprofil = c;
		partnerprofilForm.setSelected(c);
		selectedProjekt = null;
		projektForm.setSelected(null);
	}

	Projekt getSelectedProjekt() {
		return selectedProjekt;
	}

	/*
	 * Wenn ein Konto ausgewählt wird, wird auch der ausgewählte Kunde
	 * angepasst.
	 */
	void setSelectedProjekt(Projekt a) {
		selectedProjekt = a;
		projektForm.setSelected(a);

		if (a != null) {
			projektVerwaltung.getPartnerprofilById(a.getProjektleiterId(),
					new AsyncCallback<Partnerprofil>() {
						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(Partnerprofil partnerprofil) {
							selectedPartnerprofil = partnerprofil;
							partnerprofilForm.setSelected(partnerprofil);
						}
					});
		}
	}

	/*
	 * Wenn ein Kunde neu erzeugt wurde, wird er selektiert.
	 */
	void addPartnerprofil(Partnerprofil partnerprofil) {
		PartnerprofilDataProvider.getList().add(partnerprofil);
		selectionModel.setSelected(partnerprofil, true);
	}

	void updatePartnerprofil(Partnerprofil partnerprofil) {
		List<Partnerprofil> partnerprofilList = PartnerprofilDataProvider.getList();
		int i = 0;
		for (Partnerprofil c : partnerprofilList) {
			if (c.getId() == partnerprofil.getId()) {
				partnerprofilList.set(i, partnerprofil);
				break;
			} else {
				i++;
			}
		}
		PartnerprofilDataProvider.refresh();
	}

	void removePartnerprofil(Partnerprofil partnerprofil) {
		PartnerprofilDataProvider.getList().remove(partnerprofil);
		ProjektDataProviders.remove(partnerprofil);
	}

	void addProjektOfPartnerprofil(Projekt projekt, Partnerprofil partnerprofil) {
		// falls es noch keinen Projekt Provider für diesen Partnerprofil gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!ProjektDataProviders.containsKey(partnerprofil)) {
			return;
		}
		ListDataProvider<Projekt> ProjektsProvider = ProjektDataProviders
				.get(partnerprofil);
		if (!ProjektsProvider.getList().contains(projekt)) {
			ProjektsProvider.getList().add(projekt);
		}
		selectionModel.setSelected(projekt, true);
	}

	void removeProjektOfPartnerprofil(Projekt projekt, Partnerprofil partnerprofil) {
		// falls es keinen Projekt Provider für diesen Partnerprofil gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!ProjektDataProviders.containsKey(partnerprofil)) {
			return;
		}
		ProjektDataProviders.get(partnerprofil).getList().remove(projekt);
		selectionModel.setSelected(partnerprofil, true);
	}

	/*
	 * Ein Konto in der Baumstruktur soll ersetzt werden durch ein Konto mit derselben id.
	 * Dies ist sinnvoll, wenn sich die Eigenschaften eines Kontos geändert haben und in der
	 * Baumstruktur noch ein "veraltetes" Kontoobjekt enthalten ist.
	 */
	void updateProjekt(Projekt a) {
		projektVerwaltung.getPartnerprofilById(a.getProjektleiterId(),
				new UpdateProjektCallback(a));
	}

	private class UpdateProjektCallback implements AsyncCallback<Partnerprofil> {

		Projekt projekt = null;

		UpdateProjektCallback(Projekt a) {
			projekt = a;
		}

		@Override
		public void onFailure(Throwable t) {
		}

		@Override
		public void onSuccess(Partnerprofil partnerprofil) {
			List<Projekt> projektList = ProjektDataProviders.get(partnerprofil)
					.getList();
			for (int i=0; i<projektList.size(); i++) {
				if (projekt.getId() == projektList.get(i).getId()) {
					projektList.set(i, projekt);
					break;
				}
			}
		}
	}

	// Get the NodeInfo that provides the children of the specified value.
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value.equals("Root")) {
			// Erzeugen eines ListDataproviders für Partnerprofildaten
			PartnerprofilDataProvider = new ListDataProvider<Partnerprofil>();
			projektVerwaltung
				.getBeteiligungenFor(selectedProjekt, new AsyncCallback<Vector<Beteiligung>>(){
						@Override
						public void onFailure(Throwable t) {
						}

						@Override
						public void onSuccess(Vector<Beteiligung> result) {
							// TODO Auto-generated method stub
							for (Beteiligung c : result) {
								PartnerprofilDataProvider.getList().add(c);
							}
						}
					});

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Partnerprofil>(PartnerprofilDataProvider,
					new PartnerprofilCell(), selectionModel, null);
		}

		if (value instanceof Partnerprofil) {
			// Erzeugen eines ListDataproviders für Projekt-Daten
			final ListDataProvider<Projekt> ProjektsProvider = new ListDataProvider<Projekt>();
			ProjektDataProviders.put((Partnerprofil) value, ProjektsProvider);

			projektVerwaltung.getProjektsOf((Partnerprofil) value,
					new AsyncCallback<Vector<Projekt>>() {
					
						public void onFailure(Throwable t) {
						}

						public void onSuccess(Vector<Projekt> projekts) {
							for (Projekt a : projekts) {
								ProjektProvider.getList().add(a);
							}
						}
					

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Projekt>(projektsProvider,
					new ProjektCell(), selectionModel, null);
			}
		return null;
	}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		// value is of type Projekt
		return (value instanceof Projekt);
	}

}


	@Override
	public boolean isLeaf(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

}

