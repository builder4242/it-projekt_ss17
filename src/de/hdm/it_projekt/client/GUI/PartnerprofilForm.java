package de.hdm.it_projekt.client.GUI;

import java.util.List;
import java.util.Vector;

import org.apache.bcel.classfile.PMGClass;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.it_projekt.client.GUI.Cell.EigenschaftCell;
import de.hdm.it_projekt.shared.bo.Eigenschaft;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

public class PartnerprofilForm extends Showcase {

	Partnerprofil pp = null;

	final VerticalPanel vP = new VerticalPanel();

	final ProvidesKey<Eigenschaft> KEY_PROVIDER = new ProvidesKey<Eigenschaft>() {

		@Override
		public Object getKey(Eigenschaft item) {
			// TODO Auto-generated method stub
			return item.getId();
		}

	};

	public PartnerprofilForm() {

		pa.getPartnerprofilById(MyProjekt.loginInfo.getCurrentUser().getPartnerprofilId(),
				new AsyncCallback<Partnerprofil>() {

					@Override
					public void onFailure(Throwable caught) {

						Window.alert("Es ist leider ein Fehler aufgetreten.");

					}

					@Override
					public void onSuccess(Partnerprofil result) {

						if (result != null) {
							pp = result;
							vP.add(new Label("Partnerprofil zugewiesen " + pp.toString()));

						} else {
							pa.createPartnerprofilFor(MyProjekt.loginInfo.getCurrentUser(),
									new createPartnerprofilCallback());
						}
						// vP.add(getEigenschaftCelllist());
					}
				});

		vP.add(new Label("PP ID: " + Integer.toString(MyProjekt.loginInfo.getCurrentUser().getPartnerprofilId())));
		this.add(vP);

	}

	private Widget getEigenschaftCelllist() {

		Cell<Eigenschaft> eCell = new EigenschaftCell();

		final CellList<Eigenschaft> eCl = new CellList<Eigenschaft>(eCell, KEY_PROVIDER);

		final SingleSelectionModel<Eigenschaft> eSelectionModel = new SingleSelectionModel<Eigenschaft>(KEY_PROVIDER);
		eCl.setSelectionModel(eSelectionModel);
		eCl.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		eSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// TODO Auto-generated method stub

			}
		});

		ListDataProvider<Eigenschaft> eDataProvider = new ListDataProvider<Eigenschaft>();
		eDataProvider.addDataDisplay(eCl);

		final List<Eigenschaft> eL = eDataProvider.getList();

		pa.getEigenschaftenFor(pp, new AsyncCallback<Vector<Eigenschaft>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Es ist ein Fehler aufgetreten.");

			}

			@Override
			public void onSuccess(Vector<Eigenschaft> result) {

				for (Eigenschaft e : result) {
					eL.add(e);
				}

			}
		});

		return eCl;

	}

	private class createPartnerprofilCallback implements AsyncCallback<Partnerprofil> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten");
		}

		@Override
		public void onSuccess(Partnerprofil result) {

			pp = result;
			vP.add(new Label("partnerprofil angelegt" + pp.toString()));

		}
	}
}
