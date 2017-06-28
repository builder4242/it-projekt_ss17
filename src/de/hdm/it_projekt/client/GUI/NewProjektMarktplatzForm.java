package de.hdm.it_projekt.client.GUI;

/** Die Klasse NewProjektMarktplatzForm dient dem Aufbau und der Interaktion mit dem Formular zum Anlegen 
 * eines neuen Marktplatzes auf der Startseite "Projektmarktplätze" der GUI. Es wird eine TextBox für die 
 * Bezeichung einens neuen Marktplatzes sowie ein Button mit zugeörigem ClickHandler zum Anlegen bereitgestellt.
 * Die Optik wird durch das Einbinden von CSS angepasst. */ 

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class NewProjektMarktplatzForm extends Showcase {

	
	private TextBox bezeichnungTextBox = new TextBox();
	
	
	public NewProjektMarktplatzForm() {
		
		VerticalPanel formVp = new VerticalPanel();
		this.add(formVp);
		
		Label beschreibungLabel = new Label("Hier können Sie einen neuen Projektmarktplatz anlegen: ");
		beschreibungLabel.setStyleName("h2_margin-top");
		formVp.add(beschreibungLabel);
		
		HorizontalPanel insertHp = new HorizontalPanel();
		formVp.add(insertHp);
		
		
		Label pmLabel = new Label("Bezeichnung");
		pmLabel.setStyleName("myprojekt-bezichungmarktplatzlabel");
		insertHp.add(pmLabel);
		insertHp.add(bezeichnungTextBox);
		bezeichnungTextBox.setStyleName("myproject-anlegentextfield");
	
		
		Button newBtn = new Button("anlegen");
		newBtn.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		insertHp.add(newBtn);
		
		newBtn.addClickHandler(new NewClickHandler());
		
	}
	
	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			pa.createProjektMarktplatz(bezeichnungTextBox.getText(),new AsyncCallback<ProjektMarktplatz>() {
				
				@Override
				public void onSuccess(ProjektMarktplatz result) {

					RootPanel.get("content").clear();
					RootPanel.get("content").add(new Marktuebersicht());
				}
				
				@Override
				public void onFailure(Throwable caught) {

					Window.alert("Es ist ein Fehler aufgetreten.");
					
				}
			});
		}
		
	}
	
	
}
