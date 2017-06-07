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
import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;

public class AusschreibungBewerbungTreeView implements TreeViewModel {

	private AusschreibungForm ausschreibungForm;
	private BewerbungForm bewerbungForm;
	private Ausschreibung selectedAusschreibung = null;
	private Bewerbung selectedBewerbung = null;
	private ProjektAdministrationAsync projektVerwaltung = null;
	private ListDataProvider<Ausschreibung> AusschreibungDataProvider = null;
	private Map<Ausschreibung, ListDataProvider<Bewerbung>> BewerbungDataProviders = null;

	/**
	 * Bildet BusinessObjects auf eindeutige Zahlenobjekte ab, die als Schlüssel
	 * für Baumknoten dienen. Dadurch werden im Selektionsmodell alle Objekte
	 * mit derselben id selektiert, wenn eines davon selektiert wird. Der
	 * Schlüssel für Kundenobjekte ist eine positive, der für Kontenobjekte eine
	 * negative Zahl, die sich jeweils aus der id des Objektes ergibt. Dadurch
	 * können Kunden- von Kontenobjekten unterschieden werden, auch wenn sie
	 * dieselbe id haben.
	 */

	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {
		@Override
		public Integer getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			}
			if (bo instanceof Ausschreibung) {
				return new Integer(bo.getId());
			} else {
				return new Integer(-bo.getId());
			}
		}
	};

	/**
	 * Diese Implementierung des TreeViewModels sorgt für die Verwaltung des
	 * Kunden- und Kontenbaumes.
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
	 * "selectedAusschreibung" bzw. das "selectedBewerbung" gesetzt.
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Ausschreibung) {
				setSelectedAusschreibung((Ausschreibung) selection);
			} else if (selection instanceof Bewerbung) {
				setSelectedBewerbung((Bewerbung) selection);
			}
		}
	}

	/*
	 * Im Konstruktor werden die für den Kunden- und Kontobaum wichtigen lokalen
	 * Variaben initialisiert.
	 */
	public void AusschreibungBewerbungsTreeViewModel() {
		projektVerwaltung = ClientsideSettings.getProjektAdministration();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		BewerbungDataProviders = new HashMap<Ausschreibung, ListDataProvider<Bewerbung>>();
	}

	void setAusschreibungForm(AusschreibungForm af) {
		ausschreibungForm = af;
	}

	void setBewerbungForm(BewerbungForm bf) {
		bewerbungForm = bf;
	}

	Ausschreibung getSelectedAusschreibung() {
		return selectedAusschreibung;
	}

	void setSelectedAusschreibung(Ausschreibung c) {
		selectedAusschreibung = c;
		ausschreibungForm.setSelected(c);
		selectedBewerbung = null;
		bewerbungForm.setSelected(null);
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
		bewerbungForm.setSelected(a);

		if (a != null) {
			projektVerwaltung.getAusschreibungById(a.getAusschreibungId(), new AsyncCallback<Ausschreibung>() {
				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Ausschreibung ausschreibung) {
					selectedAusschreibung = ausschreibung;
					ausschreibungForm.setSelected(ausschreibung);
				}
			});
		}
	}

	/*
	 * Wenn ein Kunde neu erzeugt wurde, wird er selektiert.
	 */
	void addAusschreibung(Ausschreibung ausschreibung) {
		AusschreibungDataProvider.getList().add(ausschreibung);
		selectionModel.setSelected(ausschreibung, true);
	}

	void updateAusschreibung(Ausschreibung ausschreibung) {
		List<Ausschreibung> ausschreibungList = AusschreibungDataProvider.getList();
		int i = 0;
		for (Ausschreibung c : ausschreibungList) {
			if (c.getId() == ausschreibung.getId()) {
				ausschreibungList.set(i, ausschreibung);
				break;
			} else {
				i++;
			}
		}
		AusschreibungDataProvider.refresh();
	}

	void removeAusschreibung(Ausschreibung ausschreibung) {
		AusschreibungDataProvider.getList().remove(ausschreibung);
		BewerbungDataProviders.remove(ausschreibung);
	}

	void addBewerbungOfAusschreibung(Bewerbung bewerbung, Ausschreibung ausschreibung) {
		// falls es noch keinen Bewerbung Provider für diesen Ausschreibung
		// gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!BewerbungDataProviders.containsKey(ausschreibung)) {
			return;
		}
		ListDataProvider<Bewerbung> BewerbungsProvider = BewerbungDataProviders.get(ausschreibung);
		if (!BewerbungsProvider.getList().contains(bewerbung)) {
			BewerbungsProvider.getList().add(bewerbung);
		}
		selectionModel.setSelected(bewerbung, true);
	}

	void removeBewerbungOfAusschreibung(Bewerbung bewerbung, Ausschreibung ausschreibung) {
		// falls es keinen Bewerbung Provider für diesen Ausschreibung gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!BewerbungDataProviders.containsKey(ausschreibung)) {
			return;
		}
		BewerbungDataProviders.get(ausschreibung).getList().remove(bewerbung);
		selectionModel.setSelected(ausschreibung, true);
	}

	/*
	 * Ein Konto in der Baumstruktur soll ersetzt werden durch ein Konto mit
	 * derselben id. Dies ist sinnvoll, wenn sich die Eigenschaften eines Kontos
	 * geändert haben und in der Baumstruktur noch ein "veraltetes" Kontoobjekt
	 * enthalten ist.
	 */
	void updateBewerbung(Bewerbung a) {
		projektVerwaltung.getAusschreibungById(a.getOrganisationseinheitId(), new UpdateBewerbungCallback(a));
	}

	private class UpdateBewerbungCallback implements AsyncCallback<Ausschreibung> {

		Bewerbung bewerbung = null;

		UpdateBewerbungCallback(Bewerbung a) {
			bewerbung = a;
		}

		@Override
		public void onFailure(Throwable t) {
		}

		@Override
		public void onSuccess(Ausschreibung ausschreibung) {
			List<Bewerbung> bewerbungList = BewerbungDataProviders.get(ausschreibung).getList();
			for (int i = 0; i < bewerbungList.size(); i++) {
				if (bewerbung.getId() == bewerbungList.get(i).getId()) {
					bewerbungList.set(i, bewerbung);
					break;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.view.client.TreeViewModel#getNodeInfo(java.lang.Object)
	 */
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		// TODO Auto-generated method stub
		return null;
	
	if (value.equals("Root")) {
		// Erzeugen eines ListDataproviders für Ausschreibungdaten
		AusschreibungDataProvider = new ListDataProvider<Ausschreibung>();
		projektVerwaltung
				.getAllAusschreibung(new AsyncCallback<Vector<Ausschreibung>>() {
					@Override
					public void onFailure(Throwable t) {}
					@Override
					public void onSuccess(Vector<Ausschreibung> Ausschreibungs) {
						for (Ausschreibung c : Ausschreibungs) {
							AusschreibungDataProvider.getList().add(c);
						}
					}
				});

		// Return a node info that pairs the data with a cell.
		return new DefaultNodeInfo<Ausschreibung>(AusschreibungDataProvider,
				new AusschreibungCell(), selectionModel, null);
	}

	if (value instanceof Ausschreibung) {
		// Erzeugen eines ListDataproviders für Bewerbung-Daten
		final ListDataProvider<Bewerbung> BewerbungsProvider = new ListDataProvider<Bewerbung>();
		BewerbungDataProviders.put((Ausschreibung) value, BewerbungsProvider);

		projektVerwaltung.getBewerbungsOf((Ausschreibung) value,
				new AsyncCallback<Vector<Bewerbung>>() {

	public void onFailure(Throwable t) {
	}

	public void onSuccess(Vector<Bewerbung> bewerbungs) {
						for (Bewerbung a : bewerbungs) {
							BewerbungProvider.getList().add(a);}
	// Return a node info that pairs the data with a cell.
							return new DefaultNodeInfo<Bewerbung>(bewerbungsProvider,new BewerbungCell(),selectionModel,null);
							}return null;

	}}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		// value is of type Bewerbung
		return (value instanceof Bewerbung);
	}

}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.view.client.TreeViewModel#isLeaf(java.lang.Object)
	 */
	@Override
	public boolean isLeaf(Object value) {
		// TODO Auto-generated method stub
		return false;
	}
}

/*
 * // Get the NodeInfo that provides the children of the specified value.
 * 
 * @Override public <T> NodeInfo<?> getNodeInfo(T value) {
 * 
 * 
 * @Override public <T> NodeInfo<?> getNodeInfo(T value) { // TODO
 * Auto-generated method stub return null; }
 * 
 * @Override public boolean isLeaf(Object value) { // TODO Auto-generated method
 * stub return false; }
 * 
 * }
 */
