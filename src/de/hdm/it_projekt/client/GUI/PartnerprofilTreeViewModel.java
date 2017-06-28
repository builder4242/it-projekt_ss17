package de.hdm.it_projekt.client.GUI;

/** Die Klasse PartnerprofilTreeViewModel dient der Darstellung von Informationen über das 
 * Partnerprofil.  */ 

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
import de.hdm.it_projekt.client.GUI.Cell.EigenschaftCell;
import de.hdm.it_projekt.client.GUI.Cell.PartnerprofilCell;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.*;

public class PartnerprofilTreeViewModel implements TreeViewModel {

	ProjektAdministrationAsync pa = null;

	PartnerprofilForm partnerprofilForm = null;
	EigenschaftForm eigenschaftForm = null;

	private Ausschreibung selectedAusschreibung = null;
	private Partnerprofil selectedPartnerprofil = null;
	private Eigenschaft selectedEigenschaft = null;

	private ListDataProvider<Partnerprofil> partnerprofilDataProviders = null;
	private Map<Partnerprofil, ListDataProvider<Eigenschaft>> eigenschaftDataProviders = null;

	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {
		@Override
		public String getKey(BusinessObject bo) {
			if (bo == null)
				return null;
			if (bo instanceof Partnerprofil)
				return "PP" + Integer.toString(bo.getId());
			if (bo instanceof Eigenschaft)
				return "ES" + Integer.toString(bo.getId());
			else
				return null;
		}
	};

	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObject> selectionModel = null;

	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {

			BusinessObject selection = selectionModel.getSelectedObject();

			if (selection instanceof Partnerprofil)
				setSelectedPartnerprofil((Partnerprofil) selection);
			if (selection instanceof Eigenschaft)
				setSelectedEigenschaft((Eigenschaft) selection);
		}
	}

	public PartnerprofilTreeViewModel(Ausschreibung as) {

		selectedAusschreibung = as;
		pa = ClientsideSettings.getProjektAdministration();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		
		eigenschaftDataProviders = new HashMap<Partnerprofil, ListDataProvider<Eigenschaft>>();
	}

	public void setPartnerprofilForm(PartnerprofilForm pf) {
		partnerprofilForm = pf;
	}

	public void setEigenschaftForm(EigenschaftForm ef) {
		eigenschaftForm = ef;
	}
	
	Ausschreibung getSelectedAusschreibung() {
		return selectedAusschreibung;
	}

	void setSelectedPartnerprofil(Partnerprofil pp) {

		selectedPartnerprofil = pp;
		partnerprofilForm.setSelected(pp);

		selectedEigenschaft = null;
		eigenschaftForm.setSelected(null);
	}

	Partnerprofil getSelectedPartnerprofil() {
		return selectedPartnerprofil;
	}

	void setSelectedEigenschaft(Eigenschaft e) {
		selectedEigenschaft = e;
		eigenschaftForm.setSelected(e);

		pa.getPartnerprofilById(e.getPartnerprofilId(), new AsyncCallback<Partnerprofil>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Partnerprofil partnerprofil) {
				selectedPartnerprofil = partnerprofil;
				partnerprofilForm.setSelected(partnerprofil);
			}
		});
	}

	Eigenschaft getSelectedEigenschaft() {
		return selectedEigenschaft;
	}


	void addPartnerprofilForAusschreibung(Partnerprofil pp) {

		partnerprofilDataProviders.getList().add(pp);
		partnerprofilDataProviders.refresh();
		selectionModel.setSelected(pp, true);
	}

	void updatePartnerprofil(Partnerprofil pp) {

		List<Partnerprofil> partnerprofilProvider = partnerprofilDataProviders.getList();
		partnerprofilProvider.set(partnerprofilProvider.indexOf(pp), pp);
		partnerprofilDataProviders.refresh();

	}

	void removePartnerprofilForAusschreibung(Partnerprofil pp, Ausschreibung as) {

		if (!partnerprofilDataProviders.getList().contains(pp))
			return;

		partnerprofilDataProviders.getList().remove(pp);
	}

	void addEigenschaftForPartnerprofil(Eigenschaft e, Partnerprofil pp) {

		if (!eigenschaftDataProviders.containsKey(pp))
			return;

		ListDataProvider<Eigenschaft> eigenschaftProvider = eigenschaftDataProviders.get(pp);

		if (!eigenschaftProvider.getList().contains(e))
			eigenschaftProvider.getList().add(e);
		
		updatePartnerprofil(pp);

		selectionModel.setSelected(e, true);
	}

	void updateEigenschaft(Eigenschaft e) {
		pa.getPartnerprofilById(e.getPartnerprofilId(), new UpdateEigenschaftCallback(e));
	}

	class UpdateEigenschaftCallback implements AsyncCallback<Partnerprofil> {

		Eigenschaft eigenschaft = null;

		public UpdateEigenschaftCallback(Eigenschaft e) {
			eigenschaft = e;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Partnerprofil partnerprofil) {
			
			if(eigenschaft != null && partnerprofil != null) {
				List<Eigenschaft> eigenschaftList = eigenschaftDataProviders.get(partnerprofil).getList();
				
				if(!eigenschaftList.contains(eigenschaft)) 
					eigenschaftList.set(eigenschaftList.indexOf(eigenschaft), eigenschaft);
				
				updatePartnerprofil(partnerprofil);
				
				selectionModel.setSelected(eigenschaft, true);
			}

		}
	}

	void removeEigenschaftForPartnerprofil(Eigenschaft e, Partnerprofil pp) {

		if (!eigenschaftDataProviders.containsKey(pp))
			return;

		eigenschaftDataProviders.get(pp).getList().remove(e);
		eigenschaftDataProviders.get(pp).refresh();
		updatePartnerprofil(pp);
		selectionModel.setSelected(pp, true);
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value.equals("Root")) {

			partnerprofilDataProviders = new ListDataProvider<Partnerprofil>();
			
			pa.getPartnerprofilFor(selectedAusschreibung, new AsyncCallback<Partnerprofil>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Partnerprofil partnerprofil) {
					partnerprofilDataProviders.getList().add(partnerprofil);
					
					selectionModel.setSelected(partnerprofil, true);
				}
			});

			return new DefaultNodeInfo<Partnerprofil>(partnerprofilDataProviders, new PartnerprofilCell(), selectionModel, null);
		}

		if (value instanceof Partnerprofil) {

			final ListDataProvider<Eigenschaft> eigenschaftProvider = new ListDataProvider<Eigenschaft>();
			eigenschaftDataProviders.put((Partnerprofil) value, eigenschaftProvider);

			pa.getEigenschaftenFor((Partnerprofil) value, new AsyncCallback<Vector<Eigenschaft>>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Vector<Eigenschaft> eigenschaften) {

					for (Eigenschaft e : eigenschaften) {
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
