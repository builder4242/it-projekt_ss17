package de.hdm.it_projekt.client.GUI;

/** Die Klasse OPartnerprofilForm dient dem Aufbau des Infotextes (Labels) auf der Seite
 * "Eigens Profil verwalten" in der GUI.
 * Desweiteren gibt es Labeles für das Anlegedatum sowie das Datum der letzten
 * Änderung, diese werden automatisch gesetzt, in dem sie sich ihren Wert über Get-Methoden abfragen.
 * Die Optik der Lables, wird durch das Einbinden von CSS angepasst.   */ 

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.it_projekt.client.GUI.Cell.EigenschaftCell;
import de.hdm.it_projekt.shared.bo.Eigenschaft;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

public class OPartnerprofilForm extends Showcase {

	static Partnerprofil pp = null;

	final HorizontalPanel p = new HorizontalPanel();
	final VerticalPanel leftcol = new VerticalPanel();
	final VerticalPanel content = new VerticalPanel();

	final ProvidesKey<Eigenschaft> KEY_PROVIDER = new ProvidesKey<Eigenschaft>() {

		@Override
		public Object getKey(Eigenschaft item) {
			// TODO Auto-generated method stub
			return item.getId();
		}
	};

	Cell<Eigenschaft> eCell = new EigenschaftCell();

	final CellList<Eigenschaft> eCl = new CellList<Eigenschaft>(eCell, KEY_PROVIDER);

	final SingleSelectionModel<Eigenschaft> eSelectionModel = new SingleSelectionModel<Eigenschaft>(KEY_PROVIDER);

	static ListDataProvider<Eigenschaft> eDataProvider = new ListDataProvider<Eigenschaft>();

	final static List<Eigenschaft> eL = eDataProvider.getList();

	public OPartnerprofilForm() {

		eL.clear();
		
		p.add(leftcol);
		p.add(content);

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

						} else {
							pa.createPartnerprofilFor(MyProjekt.loginInfo.getCurrentUser(),
									new createPartnerprofilCallback());
						}
						leftcol.add(new Label("Partnerprofil"));
						leftcol.add(new Label("angelegt: " + pp.getErstelldatum()));
						leftcol.add(new Label("letzte Änderung " + pp.getAenderungsdatum()));
						leftcol.add(new Label(""));
						leftcol.add(getEigenschaftCelllist());
						leftcol.addStyleName("myprojekt-opartnerprofil");
						content.add(new OEigenschaftForm());
					}
				});

		this.add(p);

	}

	private Widget getEigenschaftCelllist() {

		eCl.setSelectionModel(eSelectionModel);
		eCl.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		eSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {

				OEigenschaftForm eForm = new OEigenschaftForm();
				eForm.setSelected(eSelectionModel.getSelectedObject());

				content.clear();
				content.add(eForm);
			}
		});

		eDataProvider.addDataDisplay(eCl);

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
			MyProjekt.loginInfo.getCurrentUser().setPartnerprofilId(result.getId());
		}
	}
}
