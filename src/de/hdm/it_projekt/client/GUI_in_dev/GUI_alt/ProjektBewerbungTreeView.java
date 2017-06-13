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

import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.BusinessObject;
import de.hdm.it_projekt.shared.bo.Projekt;




public class ProjektBewerbungTreeView implements TreeViewModel {

	
	private ProjektForm projektForm;
	private BewerbungForm bewerberForm;

	private Projekt selectedProjekt = null;
	private Bewerbung selectedBewerbung = null;

	private ProjektAdministrationAsync projektVerwaltung = null;
	private ListDataProvider<Projekt> ProjektDataProvider = null;
	
	private Map<Projekt, ListDataProvider<Bewerbung>> BewerbungDataProviders = null;

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
	 * "selectedProjekt" bzw. das "selectedBewerbung" gesetzt.
	 */
	private class SelectionChangeEventHandler implements
			SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Projekt) {
				setSelectedProjekt((Projekt) selection);
			} else if (selection instanceof Bewerbung) {
				setSelectedBewerbung((Bewerbung) selection);
			}
		}
	}

	/*
	 * Im Konstruktor werden die für den Kunden- und Kontobaum wichtigen lokalen
	 * Variaben initialisiert.
	 */
	public ProjektBewerbungsTreeViewModel() {
		projektVerwaltung = ClientsideSettings.getProjektVerwaltung();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEventHandler());
		BewerbungDataProviders = new HashMap<Projekt, ListDataProvider<Bewerbung>>();
	}

	void setProjektForm(ProjektForm cf) {
		projektForm = cf;
	}

	void setBewerbungForm(BewerbungForm af) {
		bewerberForm = af;
	}

	Projekt getSelectedProjekt() {
		return selectedProjekt;
	}

	void setSelectedProjekt(Projekt c) {
		selectedProjekt = c;
		projektForm.setSelected(c);
		selectedBewerbung = null;
		bewerberForm.setSelected(null);
	}

	Bewerbung getSelectedBewerbung() {
		return selectedBewerbung;
	}

	/*
	 * Wenn ein Konto ausgewählt wird, wird auch der ausgewählte Kunde
	 * angepasst.
	 */
	void setSelectedBewerbung(Bewerbung a) {
		selectedBewerbung = a;
		bewerberForm.setSelected(a);

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
		BewerbungDataProviders.remove(projekt);
	}

	void addBewerbungOfProjekt(Bewerbung bewerber, Projekt projekt) {
		// falls es noch keinen Bewerbung Provider für diesen Projekt gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!BewerbungDataProviders.containsKey(projekt)) {
			return;
		}
		ListDataProvider<Bewerbung> BewerbungsProvider = BewerbungDataProviders
				.get(projekt);
		if (!BewerbungsProvider.getList().contains(bewerber)) {
			BewerbungsProvider.getList().add(bewerber);
		}
		selectionModel.setSelected(bewerber, true);
	}

	void removeBewerbungOfProjekt(Bewerbung bewerber, Projekt projekt) {
		// falls es keinen Bewerbung Provider für diesen Projekt gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!BewerbungDataProviders.containsKey(projekt)) {
			return;
		}
		BewerbungDataProviders.get(projekt).getList().remove(bewerber);
		selectionModel.setSelected(projekt, true);
	}

	/*
	 * Ein Konto in der Baumstruktur soll ersetzt werden durch ein Konto mit derselben id.
	 * Dies ist sinnvoll, wenn sich die Eigenschaften eines Kontos geändert haben und in der
	 * Baumstruktur noch ein "veraltetes" Kontoobjekt enthalten ist.
	 */
	void updateBewerbung(Bewerbung a) {
		projektVerwaltung.getProjektById(a.getOwnerID(),
				new UpdateBewerbungCallback(a));
	}

	private class UpdateBewerbungCallback implements AsyncCallback<Projekt> {

		Bewerbung bewerber = null;

		UpdateBewerbungCallback(Bewerbung a) {
			bewerber = a;
		}

		@Override
		public void onFailure(Throwable t) {
		}

		@Override
		public void onSuccess(Projekt projekt) {
			List<Bewerbung> bewerberList = BewerbungDataProviders.get(projekt)
					.getList();
			for (int i=0; i<bewerberList.size(); i++) {
				if (bewerber.getId() == bewerberList.get(i).getId()) {
					bewerberList.set(i, bewerber);
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
			// Erzeugen eines ListDataproviders für Bewerbung-Daten
			final ListDataProvider<Bewerbung> BewerbungsProvider = new ListDataProvider<Bewerbung>();
			BewerbungDataProviders.put((Projekt) value, BewerbungsProvider);

			projektVerwaltung.getBewerbungsOf((Projekt) value,
					new AsyncCallback<Vector<Bewerbung>>() {
					
						public void onFailure(Throwable t) {
						}

						public void onSuccess(Vector<Bewerbung> bewerbers) {
							for (Bewerbung a : bewerbers) {
								BewerbungProvider.getList().add(a);
							}
						}
					

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Bewerbung>(bewerbersProvider,
					new BewerbungCell(), selectionModel, null);
			}
		return null;
	}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		// value is of type Bewerbung
		return (value instanceof Bewerbung);
	}

}
