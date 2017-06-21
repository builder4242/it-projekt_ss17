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
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.*;

public class ProjektTreeViewModel implements TreeViewModel {

	ProjektAdministrationAsync pa = null;

	ProjektForm projektForm = null;
	AusschreibungForm ausschreibungForm = null;
	PartnerprofilForm partnerprofilForm = null;
	EigenschaftForm eigenschaftForm = null;

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
		public String getKey(BusinessObject bo) {
			if (bo == null)
				return null;
			if (bo instanceof Projekt)
				return "PR" + Integer.toString(bo.getId());
			if (bo instanceof Ausschreibung)
				return "AS" + Integer.toString(bo.getId());
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

			if (selection instanceof Projekt)
				setSelectedProjekt((Projekt) selection);
			if (selection instanceof Ausschreibung)
				setSelectedAusschreibung((Ausschreibung) selection);
			if (selection instanceof Partnerprofil)
				setSelectedPartnerprofil((Partnerprofil) selection);
			if (selection instanceof Eigenschaft)
				setSelectedEigenschaft((Eigenschaft) selection);
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

	public void setProjektForm(ProjektForm pf) {
		projektForm = pf;
	}

	public void setAusschreibungForm(AusschreibungForm af) {
		ausschreibungForm = af;
	}

	public void setPartnerprofilForm(PartnerprofilForm pf) {
		partnerprofilForm = pf;
	}

	public void setEigenschaftForm(EigenschaftForm ef) {
		eigenschaftForm = ef;
	}

	void setSelectedProjekt(Projekt pr) {
		selectedProjekt = pr;
		projektForm.setSelected(pr);

		selectedAusschreibung = null;
		ausschreibungForm.setSelected(null);

		selectedPartnerprofil = null;
		partnerprofilForm.setSelected(null);

		selectedEigenschaft = null;
		eigenschaftForm.setSelected(null);

	}

	Projekt getSelectedProjekt() {
		return selectedProjekt;
	}

	void setSelectedAusschreibung(Ausschreibung as) {
		selectedAusschreibung = as;
		ausschreibungForm.setSelected(as);

		pa.getProjektById(as.getProjektId(), new AsyncCallback<Projekt>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Projekt projekt) {
				selectedProjekt = projekt;
				projektForm.setSelected(projekt);

			}
		});

		selectedPartnerprofil = null;
		partnerprofilForm.setSelected(null);

		selectedEigenschaft = null;
		eigenschaftForm.setSelected(null);

	}

	Ausschreibung getSelectedAusschreibung() {
		return selectedAusschreibung;
	}

	void setSelectedPartnerprofil(Partnerprofil pp) {

		selectedPartnerprofil = pp;
		partnerprofilForm.setSelected(pp);

		pa.getAusschreibungby(pp, new AsyncCallback<Ausschreibung>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Ausschreibung ausschreibung) {
				selectedAusschreibung = ausschreibung;
				ausschreibungForm.setSelected(ausschreibung);

				pa.getProjektById(selectedAusschreibung.getProjektId(), new AsyncCallback<Projekt>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Projekt projekt) {
						selectedProjekt = projekt;
						projektForm.setSelected(projekt);

					}
				});
			}
		});

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

				pa.getAusschreibungby(selectedPartnerprofil, new AsyncCallback<Ausschreibung>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Ausschreibung ausschreibung) {
						selectedAusschreibung = ausschreibung;
						ausschreibungForm.setSelected(ausschreibung);

						pa.getProjektById(selectedAusschreibung.getProjektId(), new AsyncCallback<Projekt>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Projekt projekt) {
								selectedProjekt = projekt;
								projektForm.setSelected(projekt);

							}
						});
					}
				});
			}
		});
	}

	Eigenschaft getSelectedEigenschaft() {
		return selectedEigenschaft;
	}

	void addProjekt(Projekt pr) {
		projektDataProvider.getList().add(pr);
		selectionModel.setSelected(pr, true);
	}

	void updateProjekt(Projekt pr) {
		List<Projekt> projektList = projektDataProvider.getList();

		projektList.set(projektList.indexOf(pr), pr);

		projektDataProvider.refresh();
	}

	void removeProjekt(Projekt pr) {
		projektDataProvider.getList().remove(pr);
		ausschreibungDataProviders.remove(pr);
		partnerprofilDataProviders.remove(pr);
		eigenschaftDataProviders.remove(pr);
	}

	void addAusschreibungForProjekt(Ausschreibung as, Projekt pr) {
		if (!ausschreibungDataProviders.containsKey(pr))
			return;

		ListDataProvider<Ausschreibung> ausschreibungProvider = ausschreibungDataProviders.get(pr);

		if (!ausschreibungProvider.getList().contains(as))
			ausschreibungProvider.getList().add(as);

		selectionModel.setSelected(as, true);
	}

	void updateAusschreibung(Ausschreibung as) {
		pa.getProjektById(as.getProjektId(), new UpdateAusschreibungCallback(as));
	}

	class UpdateAusschreibungCallback implements AsyncCallback<Projekt> {

		Ausschreibung ausschreibung = null;

		public UpdateAusschreibungCallback(Ausschreibung as) {
			ausschreibung = as;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Projekt projekt) {

			if (ausschreibung != null && projekt != null) {
				List<Ausschreibung> ausschreibungList = ausschreibungDataProviders.get(projekt).getList();
				ausschreibungList.set(ausschreibungList.indexOf(ausschreibung), ausschreibung);
				ausschreibungDataProviders.get(projekt).refresh();
				selectionModel.setSelected(ausschreibung, true);
			}
		}
	}

	void removeAusschreibungForProjekt(Ausschreibung as, Projekt pr) {
		
		if (!ausschreibungDataProviders.containsKey(pr)) {
			Window.alert("hier ");
			return;
		}

		ausschreibungDataProviders.get(pr).getList().remove(as);
		selectionModel.setSelected(pr, true);
	}

	void addPartnerprofil(Partnerprofil pp) {

	}

	void updatePartnerprofil(Partnerprofil pp) {

	}

	void removePartnerprofil(Partnerprofil pp) {

	}

	void addEigenschaft(Eigenschaft e) {

	}

	void updateEigenschaft(Eigenschaft e) {

	}

	void removeEigenschaft(Eigenschaft e) {

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

		if (value instanceof Projekt) {

			final ListDataProvider<Ausschreibung> ausschreibungsProvider = new ListDataProvider<Ausschreibung>();
			ausschreibungDataProviders.put((Projekt) value, ausschreibungsProvider);

			pa.getAusschreibungFor((Projekt) value, new AsyncCallback<Vector<Ausschreibung>>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Vector<Ausschreibung> ausschreibungen) {
					for (Ausschreibung a : ausschreibungen) {
						ausschreibungsProvider.getList().add(a);
					}
				}
			});

			return new DefaultNodeInfo<Ausschreibung>(ausschreibungsProvider, new AusschreibungCell(), selectionModel,
					null);
		}

		if (value instanceof Ausschreibung) {

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

			return new DefaultNodeInfo<Partnerprofil>(partnerprofilProvider, new PartnerprofilCell(), selectionModel,
					null);

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
