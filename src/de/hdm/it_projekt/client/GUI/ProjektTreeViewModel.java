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
import de.hdm.it_projekt.client.GUI.Cell.EigenschaftCell;
import de.hdm.it_projekt.client.GUI.Cell.PartnerprofilCell;
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
	private Map<Ausschreibung, ListDataProvider<Partnerprofil>> partnerprofilDataProviders = null;
	private Map<Partnerprofil, ListDataProvider<Eigenschaft>> eigenschaftDataProviders = null;

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
		partnerprofilDataProviders = new HashMap<Ausschreibung, ListDataProvider<Partnerprofil>>();
		eigenschaftDataProviders = new HashMap<Partnerprofil, ListDataProvider<Eigenschaft>>();
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
		
		if(value instanceof Ausschreibung) {
			
			final ListDataProvider<Partnerprofil> partnerprofilProvider = new ListDataProvider<Partnerprofil>();
			partnerprofilDataProviders.put((Ausschreibung) value, partnerprofilProvider);
			
			pa.getPartnerprofilById(((Ausschreibung) value).getPartnerprofilId(), new AsyncCallback<Partnerprofil>() {

				@Override
				public void onFailure(Throwable caught) {					
				}

				@Override
				public void onSuccess(Partnerprofil partnerprofil) {
					partnerprofilProvider.getList().add(partnerprofil);
				}
			});
			
			return new DefaultNodeInfo<Partnerprofil>(partnerprofilProvider, new PartnerprofilCell(), selectionModel, null);
			
		}
		
		if(value instanceof Partnerprofil) {
			
			final ListDataProvider<Eigenschaft> eigenschaftProvider = new ListDataProvider<Eigenschaft>();
			eigenschaftDataProviders.put((Partnerprofil) value, eigenschaftProvider);
			
			pa.getEigenschaftenFor((Partnerprofil) value, new AsyncCallback<Vector<Eigenschaft>>() {

				@Override
				public void onFailure(Throwable caught) {					
				}

				@Override
				public void onSuccess(Vector<Eigenschaft> eigenschaften) {

					for(Eigenschaft e : eigenschaften) {
						eigenschaftProvider.getList().add(e);
					}
				}
			});
			
			
			return new DefaultNodeInfo<Eigenschaft>(eigenschaftProvider, new EigenschaftCell(), selectionModel, null);
		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {

		return (value instanceof Eigenschaft);
	}

}
