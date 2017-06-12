package de.hdm.it_projekt.client.GUI;

import java.util.Vector;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class Marktuebersicht extends Showcase {

	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Marktübersicht";
	}

	@Override
	protected void run() {

		final ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();


		final VerticalPanel projekte = new VerticalPanel();
		final Label ausgabe = new Label();

		final ListBox marktplaetze = new ListBox();


		marktplaetze.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
 
				pa.getProjektMarktplatzById(Integer.parseInt(marktplaetze.getSelectedValue()),
						new AsyncCallback<ProjektMarktplatz>() {

							@Override
							public void onFailure(Throwable caught) {
								projekte.add(new Label("Fehler im Projektmarktplatz."));
							}

							@Override
							public void onSuccess(ProjektMarktplatz pm_result) {

								pa.getAlleProjekteFor(pm_result, new AsyncCallback<Vector<Projekt>>() {

									@Override
									public void onFailure(Throwable caught) {

										projekte.add(new Label("Fehler bei den Projekte."));
									}

									public void onSuccess(Vector<Projekt> pr_result) {

										projekte.clear();
										projekte.add(new Label("Projekte:"));
										for (Projekt p : pr_result) {
											projekte.add(new Label(p.toString()));
										}

									}

								});

							}
						});
			}
		});

		ausgabe.setText("Bitte wählen Sie einen Marktplatz aus:");

		pa.getAlleProjektMarktplaetze(new AsyncCallback<Vector<ProjektMarktplatz>>() {

			@Override
			public void onFailure(Throwable caught) {

				ausgabe.setText(caught.getMessage());
			}

			@Override
			public void onSuccess(Vector<ProjektMarktplatz> result) {

				marktplaetze.setVisibleItemCount(result.size());
				for (ProjektMarktplatz pm : result) {
					marktplaetze.addItem(pm.getBezeichnung(), Integer.toString(pm.getId()));
				}
			}
		});

		this.add(ausgabe);
		this.add(marktplaetze);
		this.add(projekte);
		
	}

}
