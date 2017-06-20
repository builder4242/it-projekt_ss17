package de.hdm.it_projekt.client.GUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.client.GUI.Cell.AusschreibungCell;
import de.hdm.it_projekt.client.GUI.Cell.ProjektCell;
import de.hdm.it_projekt.client.GUI.Cell.ProjektleiterCell;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.*;

public class ProjektTreeViewModel implements TreeViewModel {

	ProjektAdministrationAsync pa = null;

	private Projekt selectedProjekt = null;
	private Ausschreibung selectedAusschreibung = null;
	private Partnerprofil selectedPartnerprofil = null;
	private Eigenschaft selectedEigenschaft = null;

	private ListDataProvider<Projekt> projektDataProvider = null;
	private Map<Projekt, ListDataProvider<Ausschreibung>> ausschreibungDataProviders = null;
	private Map<Projekt, ListDataProvider<Person>> projektleiterDataProviders = null;

	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {
		@Override
		public Integer getKey(BusinessObject bo) {
			if (bo == null)
				return null;
			else
				return bo.getId();
		}
	};

	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObject> selectionModel = null;

	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {

			Window.alert(selectionModel.getSelectedObject().toString());

		}
	}

	public ProjektTreeViewModel() {

		pa = ClientsideSettings.getProjektAdministration();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		ausschreibungDataProviders = new HashMap<Projekt, ListDataProvider<Ausschreibung>>();
		projektleiterDataProviders = new HashMap<Projekt, ListDataProvider<Person>>();
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value.equals("Root")) {

			projektDataProvider = new ListDataProvider<Projekt>();

			pa.getAlleProjekteFor(MyProjekt.cpm, new AsyncCallback<Vector<Projekt>>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Vector<Projekt> projekte) {
					for (Projekt pr : projekte) {
						projektDataProvider.getList().add(pr);
					}
				}
			});
			
			return new DefaultNodeInfo<Projekt>(projektDataProvider, new ProjektCell(), selectionModel, null);
		}
		
		if(value instanceof Projekt) {
			
			final ListDataProvider<Ausschreibung> ausschreibungsProvider = new ListDataProvider<Ausschreibung>();
			ausschreibungDataProviders.put((Projekt) value, ausschreibungsProvider);
			
			pa.getAusschreibungFor((Projekt) value, new AsyncCallback<Vector<Ausschreibung>>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Vector<Ausschreibung> ausschreibungen) {
					for(Ausschreibung a : ausschreibungen) {
						ausschreibungsProvider.getList().add(a);
					}					
				}
			});
			
			return new DefaultNodeInfo<Ausschreibung>(ausschreibungsProvider, new AusschreibungCell(), selectionModel, null);
		}
		
		if(value instanceof Projekt) {
			
			final ListDataProvider<Person> projektleiterProvider = new ListDataProvider<Person>();
			projektleiterDataProviders.put((Projekt) value, projektleiterProvider);
			
			pa.getProjektleiterFor((Projekt) value, new AsyncCallback<Person>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Person projektleiter) {
					projektleiterProvider.getList().add(projektleiter);					
				}
			});
			
			return new DefaultNodeInfo<Person>(projektleiterProvider, new ProjektleiterCell(), selectionModel, null);
			
		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {

		return (value instanceof Ausschreibung);
	}

}
