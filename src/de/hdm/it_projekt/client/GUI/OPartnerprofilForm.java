/** Die Klasse OPartnerprofilForm dient dem Aufbau des Infotextes (Labels) auf der Seite
 * "Eigens Profil verwalten" in der GUI.
 * Desweiteren gibt es Labeles für das Anlegedatum sowie das Datum der letzten
 * Änderung, diese werden automatisch gesetzt, in dem sie sich ihren Wert über Get-Methoden abfragen.
 * Die Optik der Lables, wird durch das Einbinden von CSS angepasst.   */
package de.hdm.it_projekt.client.GUI;

import java.util.List;
import java.util.Vector;

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
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

public class OPartnerprofilForm extends Showcase {

	private Organisationseinheit o = null;
	private Partnerprofil pp = null;

	final boolean editable;
	
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

	final CellList<Eigenschaft> eCl = new CellList<Eigenschaft>(new EigenschaftCell(), KEY_PROVIDER);

	final SingleSelectionModel<Eigenschaft> eSelectionModel = new SingleSelectionModel<Eigenschaft>(KEY_PROVIDER);
	
	static ListDataProvider<Eigenschaft> eDataProvider = new ListDataProvider<Eigenschaft>();

	final static List<Eigenschaft> eL = eDataProvider.getList();

	public OPartnerprofilForm(Organisationseinheit o, final boolean editable) {

		this.o = o;
		this.editable = editable;

		eL.clear();

		p.add(leftcol);
		p.add(content);

		final Button deleteButton = new Button("Partnerprofil löschen");
		deleteButton.setStyleName("myprojekt-formbutton");
		deleteButton.addClickHandler(new DeleteClickHandler());

		if (this.editable == false) {
			deleteButton.setVisible(false);
		} else {
			
		}

		pa.getPartnerprofilById(o.getPartnerprofilId(), new AsyncCallback<Partnerprofil>() {

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("Es ist leider ein Fehler aufgetreten.");

			}

			@Override
			public void onSuccess(Partnerprofil result) {

				if (result != null) {
					pp = result;

					Label PPLb = new Label("Partnerprofil");
					Label ALb = new Label("angelegt: " + pp.getErstelldatum());
					Label LALb = new Label("letzte Änderung: " + pp.getAenderungsdatum());

					PPLb.setStyleName("h1");
					ALb.setStyleName("myprojekt-opartnerprofil");
					LALb.setStyleName("myprojekt-opartnerprofil");

					leftcol.add(PPLb);
					leftcol.add(ALb);
					leftcol.add(LALb);
					leftcol.add(getEigenschaftCelllist());
					leftcol.addStyleName("Eigenschaft-Cell");
					leftcol.add(deleteButton);
					if (editable)
						content.add(new OEigenschaftForm(pp));
				}

			}
		});

		this.add(p);

	}

	private Widget getEigenschaftCelllist() {

		if(editable)
			eCl.setSelectionModel(eSelectionModel);
		
		eCl.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		eSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {

				OEigenschaftForm eForm = new OEigenschaftForm(pp);
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

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			pa.delete(pp, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Es ist ein Fehler beim löschen aufgetreten.");
				}

				@Override
				public void onSuccess(Void result) {
					MyProjekt.loginInfo.getCurrentUser().setPartnerprofilId(0);
					RootPanel.get("content").clear();
					RootPanel.get("content").add(new OrgaForm(MyProjekt.loginInfo.getCurrentUser()));
				}
			});
		}

	}
}
