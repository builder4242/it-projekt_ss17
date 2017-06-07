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

import de.hdm.it_projekt.shared.bo.BusinessObject;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Ausschreibung;


public class BewerbungAusschreibungTreeView implements TreeViewModel{



	
	private BewerbungForm bewerbungForm;
	private AusschreibungForm ausschreibungForm;

	private Bewerbung selectedBewerbung = null;
	private Ausschreibung selectedAusschreibung = null;

	private ProjektAdministrationAsync projektVerwaltung = null; //warten auf anlage Async
	private ListDataProvider<Bewerbung> BewerbungDataProvider = null;
	
	private Map<Bewerbung, ListDataProvider<Ausschreibung>> AusschreibungDataProviders = null;

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
			if (bo instanceof Bewerbung) {
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
	 * "selectedBewerbung" bzw. das "selectedAusschreibung" gesetzt.
	 */
	private class SelectionChangeEventHandler implements
			SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Bewerbung) {
				setSelectedBewerbung((Bewerbung) selection);
			} else if (selection instanceof Ausschreibung) {
				setSelectedAusschreibung((Ausschreibung) selection);
			}
		}
	}

	/*
	 * Im Konstruktor werden die für den Kunden- und Kontobaum wichtigen lokalen
	 * Variaben initialisiert.
	 */
	public void BewerbungAusschreibungsTreeViewModel() {
		projektVerwaltung = ClientsideSettings.getProjektVerwaltung();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEventHandler());
		AusschreibungDataProviders = new HashMap<Bewerbung, ListDataProvider<Ausschreibung>>();
	}

	void setBewerbungForm(Bewerbung cf) {
		bewerbungForm = cf;
	}

	void setAusschreibungForm(AusschreibungForm af) {
		ausschreibungForm = af;
	}

	Bewerbung getSelectedBewerbung() {
		return selectedBewerbung;
	}

	void setSelectedBewerbung(Bewerbung c) {
		selectedBewerbung = c;
		bewerbungForm.setSelected(c);
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
			projektVerwaltung.getBewerbungById(a.getOwnerID(),
					new AsyncCallback<Bewerbung>() {
						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(Bewerbung bewerbung) {
							selectedBewerbung = bewerbung;
							bewerbungForm.setSelected(bewerbung);
						}
					});
		}
	}

	/*
	 * Wenn ein Kunde neu erzeugt wurde, wird er selektiert.
	 */
	void addBewerbung(Bewerbung bewerbung) {
		BewerbungDataProvider.getList().add(bewerbung);
		selectionModel.setSelected(bewerbung, true);
	}

	void updateBewerbung(Bewerbung bewerbung) {
		List<Bewerbung> bewerbungList = BewerbungDataProvider.getList();
		int i = 0;
		for (Bewerbung c : bewerbungList) {
			if (c.getId() == bewerbung.getId()) {
				bewerbungList.set(i, bewerbung);
				break;
			} else {
				i++;
			}
		}
		BewerbungDataProvider.refresh();
	}

	void removeBewerbung(Bewerbung bewerbung) {
		BewerbungDataProvider.getList().remove(bewerbung);
		AusschreibungDataProviders.remove(bewerbung);
	}

	void addAusschreibungOfBewerbung(Ausschreibung ausschreibung, Bewerbung bewerbung) {
		// falls es noch keinen Ausschreibung Provider für diesen Bewerbung gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!AusschreibungDataProviders.containsKey(bewerbung)) {
			return;
		}
		ListDataProvider<Ausschreibung> AusschreibungsProvider = AusschreibungDataProviders
				.get(bewerbung);
		if (!AusschreibungsProvider.getList().contains(ausschreibung)) {
			AusschreibungsProvider.getList().add(ausschreibung);
		}
		selectionModel.setSelected(ausschreibung, true);
	}

	void removeAusschreibungOfBewerbung(Ausschreibung ausschreibung, Bewerbung bewerbung) {
		// falls es keinen Ausschreibung Provider für diesen Bewerbung gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!AusschreibungDataProviders.containsKey(bewerbung)) {
			return;
		}
		AusschreibungDataProviders.get(bewerbung).getList().remove(ausschreibung);
		selectionModel.setSelected(bewerbung, true);
	}

	/*
	 * Ein Konto in der Baumstruktur soll ersetzt werden durch ein Konto mit derselben id.
	 * Dies ist sinnvoll, wenn sich die Eigenschaften eines Kontos geändert haben und in der
	 * Baumstruktur noch ein "veraltetes" Kontoobjekt enthalten ist.
	 */
	void updateAusschreibung(Ausschreibung a) {
		projektVerwaltung.getBewerbungById(a.getOwnerID(),
				new UpdateAusschreibungCallback(a));
	}

	private class UpdateAusschreibungCallback implements AsyncCallback<Bewerbung> {

		Ausschreibung ausschreibung = null;

		UpdateAusschreibungCallback(Ausschreibung a) {
			ausschreibung = a;
		}

		@Override
		public void onFailure(Throwable t) {
		}

		@Override
		public void onSuccess(Bewerbung bewerbung) {
			List<Ausschreibung> ausschreibungList = AusschreibungDataProviders.get(bewerbung)
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
			// Erzeugen eines ListDataproviders für Bewerbungdaten
			BewerbungDataProvider = new ListDataProvider<Bewerbung>();
			projektVerwaltung
					.getAllBewerbungs(new AsyncCallback<Vector<Bewerbung>>() {
						@Override
						public void onFailure(Throwable t) {
						}

						@Override
						public void onSuccess(Vector<Bewerbung> Bewerbungs) {
							for (Bewerbung c : Bewerbungs) {
								BewerbungDataProvider.getList().add(c);
							}
						}
					});

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Bewerbung>(BewerbungDataProvider,
					new BewerbungCell(), selectionModel, null);
		}

		if (value instanceof Bewerbung) {
			// Erzeugen eines ListDataproviders für Ausschreibung-Daten
			final ListDataProvider<Ausschreibung> AusschreibungsProvider = new ListDataProvider<Ausschreibung>();
			AusschreibungDataProviders.put((Bewerbung) value, AusschreibungsProvider);

			projektVerwaltung.getAusschreibungsOf((Bewerbung) value,
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

