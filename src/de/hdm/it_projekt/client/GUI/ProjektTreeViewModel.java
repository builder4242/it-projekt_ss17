package de.hdm.it_projekt.client.GUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.client.GUI.Cell.AusschreibungCell;
import de.hdm.it_projekt.client.GUI.Cell.BewerbungCell;
import de.hdm.it_projekt.client.GUI.Cell.BewertungCell;
import de.hdm.it_projekt.client.GUI.Cell.ProjektCell;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.*;

public class ProjektTreeViewModel implements TreeViewModel {

	ProjektAdministrationAsync pa = null;

	ProjektForm projektForm = null;
	AusschreibungForm ausschreibungForm = null;
	BewerbungForm bewerbungForm = null;
	BewertungForm bewertungForm = null;
	VerticalPanel rightPanel = null;

	private Projekt selectedProjekt = null;
	private Ausschreibung selectedAusschreibung = null;
	private Bewerbung selectedBewerbung = null;
	private Bewertung selectedBewertung = null;

	private ListDataProvider<Projekt> projektDataProvider = null;
	private Map<Projekt, ListDataProvider<Ausschreibung>> ausschreibungDataProviders = null;
	private Map<Ausschreibung, ListDataProvider<Bewerbung>> bewerbungDataProviders = null;
	private Map<Bewerbung, ListDataProvider<Bewertung>> bewertungDataProviders = null;

	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {
		@Override
		public String getKey(BusinessObject bo) {
			if (bo == null)
				return null;
			if (bo instanceof Projekt)
				return "PR" + Integer.toString(bo.getId());
			if (bo instanceof Ausschreibung)
				return "AS" + Integer.toString(bo.getId());
			if (bo instanceof Bewerbung)
				return "BW" + Integer.toString(bo.getId());
			if (bo instanceof Bewertung)
				return "BT" + Integer.toString(bo.getId());
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
			if (selection instanceof Bewerbung)
				setSelectedBewerbung((Bewerbung) selection);
			if (selection instanceof Bewertung)
				setSelectedBewertung((Bewertung) selection);
		}
	}

	public ProjektTreeViewModel() {

		pa = ClientsideSettings.getProjektAdministration();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());

		ausschreibungDataProviders = new HashMap<Projekt, ListDataProvider<Ausschreibung>>();
		bewerbungDataProviders = new HashMap<Ausschreibung, ListDataProvider<Bewerbung>>();
		bewertungDataProviders = new HashMap<Bewerbung, ListDataProvider<Bewertung>>();
	}

	public void setProjektForm(ProjektForm pf) {
		projektForm = pf;
	}

	public void setAusschreibungForm(AusschreibungForm af) {
		ausschreibungForm = af;
	}

	public void setBewerbungForm(BewerbungForm bwf) {
		bewerbungForm = bwf;
	}

	public void setBewertungForm(BewertungForm bwtf) {
		bewertungForm = bwtf;
	}
	public void setRightPanel(VerticalPanel rP) {
		rightPanel = rP;
	}

	void setSelectedProjekt(Projekt pr) {
		selectedProjekt = pr;
		projektForm.setSelected(pr);

		selectedAusschreibung = null;
		ausschreibungForm.setSelected(null);

		selectedBewerbung = null;
		bewerbungForm.setSelected(null);

		selectedBewertung = null;
		bewertungForm.setSelected(null);
		
		HorizontalPanel hP = new HorizontalPanel();		
		
		BeteiligungForm beteiligungForm = new BeteiligungForm();		
		ProjektBeteiligungListView pblv = new ProjektBeteiligungListView(selectedProjekt);
		
		beteiligungForm.setProjektBeteiligungListView(pblv);
		pblv.setBeteiligungForm(beteiligungForm);
		
		hP.add(pblv.getBeteiligungenCellList());
		hP.add(beteiligungForm);
		
		rightPanel.clear();
		rightPanel.add(hP);
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

		selectedBewerbung = null;
		bewerbungForm.setSelected(null);

		selectedBewertung = null;
		bewertungForm.setSelected(null);
		
		
		PartnerprofilTreeViewModel pptvm = new PartnerprofilTreeViewModel(selectedAusschreibung);
		CellTree.Resources partnerprofilTreeRessource = GWT.create(CellTree.Resources.class);
		CellTree cellTree = new CellTree(pptvm, "Root", partnerprofilTreeRessource);
		cellTree.setAnimationEnabled(true);
		
		HorizontalPanel hP = new HorizontalPanel();
		hP.add(cellTree);
		
		VerticalPanel vP = new VerticalPanel();
		hP.add(vP);
		
		PartnerprofilForm partnerprofilForm = new PartnerprofilForm();
		partnerprofilForm.setPartnerprofilTreeViewModel(pptvm);
		pptvm.setPartnerprofilForm(partnerprofilForm);
		vP.add(partnerprofilForm);
		
		EigenschaftForm eigenschaftForm = new EigenschaftForm();
		eigenschaftForm.setPartnerprofilTreeViewModel(pptvm);
		pptvm.setEigenschaftForm(eigenschaftForm);
		vP.add(eigenschaftForm);
		
		rightPanel.clear();
		rightPanel.add(hP);

	}

	Ausschreibung getSelectedAusschreibung() {
		return selectedAusschreibung;
	}

	void setSelectedBewerbung(Bewerbung bw) {

		selectedBewerbung = bw;
		bewerbungForm.setSelected(bw);

		pa.getAusschreibungBy(bw, new AsyncCallback<Ausschreibung>() {

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

		selectedBewertung = null;
		bewertungForm.setSelected(null);
	}

	Bewerbung getSelectedBewerbung() {
		return selectedBewerbung;
	}

	void setSelectedBewertung(Bewertung bwt) {
		selectedBewertung = bwt;
		bewertungForm.setSelected(bwt);

		pa.getBewerbungById(bwt.getBewerbungId(), new AsyncCallback<Bewerbung>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Bewerbung bewerbung) {
				selectedBewerbung = bewerbung;
				bewerbungForm.setSelected(bewerbung);

				pa.getAusschreibungBy(selectedBewerbung, new AsyncCallback<Ausschreibung>() {

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

	Bewertung getSelectedBewertung() {
		return selectedBewertung;
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
		bewerbungDataProviders.remove(pr);
		bewertungDataProviders.remove(pr);
	}

	void addAusschreibungForProjekt(Ausschreibung as, Projekt pr) {
		if (!ausschreibungDataProviders.containsKey(pr))
			return;

		ListDataProvider<Ausschreibung> ausschreibungProvider = ausschreibungDataProviders.get(pr);

		if (!ausschreibungProvider.getList().contains(as))
			ausschreibungProvider.getList().add(as);

		setSelectedAusschreibung(as);
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
			return;
		}

		ausschreibungDataProviders.get(pr).getList().remove(as);
		ausschreibungDataProviders.get(pr).refresh();
		selectionModel.setSelected(pr, true);
	}

	void removeBewerbungForAusschreibung(Bewerbung bw, Ausschreibung as) {

		if (!bewerbungDataProviders.containsKey(as))
			return;

		bewerbungDataProviders.get(as).getList().remove(bw);
		selectionModel.setSelected(as, true);
	}

	void addBewertungForBewerbung(Bewertung bwt, Bewerbung bw) {

		if (!bewertungDataProviders.containsKey(bw))
			return;

		ListDataProvider<Bewertung> bewertungtProvider = bewertungDataProviders.get(bw);

		if (!bewertungtProvider.getList().contains(bwt))
			bewertungtProvider.getList().add(bwt);

		selectionModel.setSelected(bwt, true);
	}

	void updateBewertung(Bewertung bwt) {
		pa.getBewerbungById(bwt.getBewerbungId(), new UpdateBewertungCallback(bwt));
	}

	class UpdateBewertungCallback implements AsyncCallback<Bewerbung> {

		Bewertung bewertung = null;

		public UpdateBewertungCallback(Bewertung bwt) {
			bewertung = bwt;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Bewerbung bewerbung) {

			if (bewertung != null && bewerbung != null) {
				List<Bewertung> bewertungList = bewertungDataProviders.get(bewerbung).getList();

				if (!bewertungList.contains(bewertung))
					bewertungList.set(bewertungList.indexOf(bewertung), bewertung);

				selectionModel.setSelected(bewertung, true);
			}

		}
	}

	void removeBewertungForBewerbung(Bewertung bwt, Bewerbung bw) {

		if (!bewertungDataProviders.containsKey(bw))
			return;

		bewertungDataProviders.get(bw).getList().remove(bwt);
		bewertungDataProviders.get(bw).refresh();

		selectionModel.setSelected(bw, true);
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

			final ListDataProvider<Bewerbung> bewerbungProvider = new ListDataProvider<Bewerbung>();
			bewerbungDataProviders.put((Ausschreibung) value, bewerbungProvider);

			pa.getBewerbungBy(((Ausschreibung) value), new AsyncCallback<Vector<Bewerbung>>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Vector<Bewerbung> bewerbungen) {
					for (Bewerbung bw : bewerbungen) {
						bewerbungProvider.getList().add(bw);
					}
				}
			});

			return new DefaultNodeInfo<Bewerbung>(bewerbungProvider, new BewerbungCell(), selectionModel, null);

		}

		if (value instanceof Bewerbung) {

			final ListDataProvider<Bewertung> bewertungProvider = new ListDataProvider<Bewertung>();
			bewertungDataProviders.put((Bewerbung) value, bewertungProvider);

			pa.getBewertungFor((Bewerbung) value, new AsyncCallback<Bewertung>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Bewertung bwt) {

					bewertungProvider.getList().add(bwt);
				}
			});

			return new DefaultNodeInfo<Bewertung>(bewertungProvider, new BewertungCell(), selectionModel, null);
		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {

		return (value instanceof Bewertung);
	}
}
