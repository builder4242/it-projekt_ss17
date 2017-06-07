package de.hdm.it_projekt.client.GUI;

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

import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.BusinessObject;
import de.hdm.it_projekt.shared.bo.Projekt;




public class ProjektAusschreibungTreeView implements TreeViewModel {

	
	private ProjektForm projektForm;
	private AusschreibungForm ausschreibungForm;

	private Projekt selectedProjekt = null;
	private Ausschreibung selectedAusschreibung = null;

	private ProjektAdministrationAsync projektVerwaltung = null;
	private ListDataProvider<Projekt> ProjektDataProvider = null;
	
	private Map<Projekt, ListDataProvider<Ausschreibung>> AusschreibungDataProviders = null;

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
	 * "selectedProjekt" bzw. das "selectedAusschreibung" gesetzt.
	 */
	private class SelectionChangeEventHandler implements
			SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Projekt) {
				setSelectedProjekt((Projekt) selection);
			} else if (selection instanceof Ausschreibung) {
				setSelectedAusschreibung((Ausschreibung) selection);
			}
		}
	}

	/*
	 * Im Konstruktor werden die für den Kunden- und Kontobaum wichtigen lokalen
	 * Variaben initialisiert.
	 */
	public ProjektAusschreibungsTreeViewModel() {
		projektVerwaltung = ClientsideSettings.getProjektVerwaltung();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEventHandler());
		AusschreibungDataProviders = new HashMap<Projekt, ListDataProvider<Ausschreibung>>();
	}

	void setProjektForm(ProjektForm cf) {
		projektForm = cf;
	}

	void setAusschreibungForm(AusschreibungForm af) {
		ausschreibungForm = af;
	}

	Projekt getSelectedProjekt() {
		return selectedProjekt;
	}

	void setSelectedProjekt(Projekt c) {
		selectedProjekt = c;
		projektForm.setSelected(c);
		selectedAusschreibung = null;
		ausschreibungForm.setSelected(null);
	}

	Ausschreibung getSelectedAusschreibung() {
		return selectedAusschreibung;
	}

	/*
	 * Wenn ein Konto ausgewählt wird, wird auch der ausgewählte Kunde
	 * angepasst.
	 */
	void setSelectedAusschreibung(Ausschreibung a) {
		selectedAusschreibung = a;
		ausschreibungForm.setSelected(a);

		if (a != null) {
			projektVerwaltung.getProjektById(a.getOwnerID(),
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
		AusschreibungDataProviders.remove(projekt);
	}

	void addAusschreibungOfProjekt(Ausschreibung ausschreibung, Projekt projekt) {
		// falls es noch keinen Ausschreibung Provider für diesen Projekt gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!AusschreibungDataProviders.containsKey(projekt)) {
			return;
		}
		ListDataProvider<Ausschreibung> AusschreibungsProvider = AusschreibungDataProviders
				.get(projekt);
		if (!AusschreibungsProvider.getList().contains(ausschreibung)) {
			AusschreibungsProvider.getList().add(ausschreibung);
		}
		selectionModel.setSelected(ausschreibung, true);
	}

	void removeAusschreibungOfProjekt(Ausschreibung ausschreibung, Projekt projekt) {
		// falls es keinen Ausschreibung Provider für diesen Projekt gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!AusschreibungDataProviders.containsKey(projekt)) {
			return;
		}
		AusschreibungDataProviders.get(projekt).getList().remove(ausschreibung);
		selectionModel.setSelected(projekt, true);
	}

	/*
	 * Ein Konto in der Baumstruktur soll ersetzt werden durch ein Konto mit derselben id.
	 * Dies ist sinnvoll, wenn sich die Eigenschaften eines Kontos geändert haben und in der
	 * Baumstruktur noch ein "veraltetes" Kontoobjekt enthalten ist.
	 */
	void updateAusschreibung(Ausschreibung a) {
		projektVerwaltung.getProjektById(a.getOwnerID(),
				new UpdateAusschreibungCallback(a));
	}

	private class UpdateAusschreibungCallback implements AsyncCallback<Projekt> {

		Ausschreibung ausschreibung = null;

		UpdateAusschreibungCallback(Ausschreibung a) {
			ausschreibung = a;
		}

		@Override
		public void onFailure(Throwable t) {
		}

		@Override
		public void onSuccess(Projekt projekt) {
			List<Ausschreibung> ausschreibungList = AusschreibungDataProviders.get(projekt)
					.getList();
			for (int i=0; i<ausschreibungList.size(); i++) {
				if (ausschreibung.getId() == ausschreibungList.get(i).getId()) {
					ausschreibungList.set(i, ausschreibung);
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
			// Erzeugen eines ListDataproviders für Ausschreibung-Daten
			final ListDataProvider<Ausschreibung> AusschreibungsProvider = new ListDataProvider<Ausschreibung>();
			AusschreibungDataProviders.put((Projekt) value, AusschreibungsProvider);

			projektVerwaltung.getAusschreibungsOf((Projekt) value,
					new AsyncCallback<Vector<Ausschreibung>>() {
					
						public void onFailure(Throwable t) {
						}

						public void onSuccess(Vector<Ausschreibung> ausschreibungs) {
							for (Ausschreibung a : ausschreibungs) {
								AusschreibungProvider.getList().add(a);
							}
						}
					

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Ausschreibung>(ausschreibungsProvider,
					new AusschreibungCell(), selectionModel, null);
			}
		return null;
	}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		// value is of type Ausschreibung
		return (value instanceof Ausschreibung);
	}

}