package de.hdm.it_projekt.client.GUI;

import java.util.Vector;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class Marktuebersicht extends Showcase {

	final Label ausgabe = new Label();

	final ListBox memberPmLb = new ListBox();
	final ListBox nonmemberPmLb = new ListBox();

	Vector<ProjektMarktplatz> memberPm = new Vector<ProjektMarktplatz>();

	public Marktuebersicht() {

		memberPmLb.addChangeHandler(new SelectChangeHandler());

		ausgabe.setText("Bitte wählen Sie einen Marktplatz aus:");

/*
		pa.getProjektMarktplaetzeByOrganisation(MyProjekt.cu, new AsyncCallback<Vector<ProjektMarktplatz>>() {

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("Es ist ein Fehler aufgetreten");
			}

			@Override
			public void onSuccess(Vector<ProjektMarktplatz> result) {

				memberPm = result;

				memberPmLb.setVisibleItemCount(result.size() + 1);

				for (ProjektMarktplatz pm : result) {
					memberPmLb.addItem(pm.getBezeichnung(), Integer.toString(pm.getId()));
				}

				
			}
		});*/
		
		pa.getAlleProjektMarktplaetze(new AsyncCallback<Vector<ProjektMarktplatz>>() {

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("Fehler beim Abruf der Projekt Marktplätze");
			}

			@Override
			public void onSuccess(Vector<ProjektMarktplatz> result) {

				for (ProjektMarktplatz pm : result) {
					nonmemberPmLb.addItem(pm.getBezeichnung(), Integer.toString(pm.getId()));
					for (ProjektMarktplatz mpm : memberPm) {
						
							
					}
				}

			}
		});

		this.add(ausgabe);
		this.add(memberPmLb);
		this.add(nonmemberPmLb);

	}

	private class SelectChangeHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {

			pa.getProjektMarktplatzById(Integer.parseInt(memberPmLb.getSelectedValue()),
					new AsyncCallback<ProjektMarktplatz>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler bei der Auswahl im Projektmarktplatz.");
						}

						@Override
						public void onSuccess(ProjektMarktplatz result) {

						}
					});
		}
	}

}
