package de.hdm.it_projekt.client.GUI_Report;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.*;

public class ReportGenerator implements EntryPoint {

	/**
	 * Begin Attribute fuer Login
	 */
	protected static LoginInfo loginInfo = null;
	protected static ProjektMarktplatz cpm = null;

	HorizontalPanel hPanel = null;
	VerticalPanel menuPanel = null;
	VerticalPanel contentPanel = null;

	
	public void onModuleLoad() {

		/**
		 * Ueberschirft fuer den Rpeort Generator
		 */
		Label ueberschriftLabel = new Label("Report Generator");
		ueberschriftLabel.setStyleName("h1");
		RootPanel.get("menu").add(ueberschriftLabel);

		/**
		 * Definition der Panels
		 */
		hPanel = new HorizontalPanel();
		menuPanel = new VerticalPanel();
		contentPanel = new VerticalPanel();

		hPanel.add(menuPanel);
		hPanel.add(contentPanel);


		RootPanel.get("content").clear();
		RootPanel.get("content").add(hPanel);
		
		
		/**
		 * Button Definitionen
		 */
		Button alleAusschreibungenButton = new Button("Zeige Alle Ausschreibungen");
		alleAusschreibungenButton.setStyleName("myprojekt-reportbutton");
		alleAusschreibungenButton.addClickHandler(new AlleAusschreibungenClickhandler());

		Button passendeAusschreibungenButton = new Button("Zeige zu mir passende Ausschreibungen");
		passendeAusschreibungenButton.setStyleName("myprojekt-reportbutton");

		Button bewerbungAufAusschreibungenButton = new Button("Zeige Bewerbungen für meine Ausschreibungen");
		bewerbungAufAusschreibungenButton.setStyleName("myprojekt-reportbutton");

		Button ausschreibungenAufBewerbungButton = new Button("Zeige meine Bewerbungen");
		ausschreibungenAufBewerbungButton.setStyleName("myprojekt-reportbutton");

		Button fanOutButton = new Button("Zeige Fan Out");
		fanOutButton.setStyleName("myprojekt-reportbutton");

		Button fanInButton = new Button("Zeige Fan In");
		fanInButton.setStyleName("myprojekt-reportbutton");

		Button zugehoerigeProjekteButton = new Button("Zeige Projektverflechtungen meiner Bewerber");
		zugehoerigeProjekteButton.setStyleName("myprojekt-reportbutton");

		/**
		 * Hinzufuegen der Buttons zum vertikel Panel
		 */
		menuPanel.add(alleAusschreibungenButton);
		menuPanel.add(passendeAusschreibungenButton);
		menuPanel.add(bewerbungAufAusschreibungenButton);
		menuPanel.add(ausschreibungenAufBewerbungButton);
		menuPanel.add(fanOutButton);
		menuPanel.add(fanInButton);
		menuPanel.add(zugehoerigeProjekteButton);
		
		
		if(cpm == null) {
			contentPanel.add(new ReportMarktuebersicht(this));
			menuPanel.setVisible(false);
		} 
	}

	private class AlleAusschreibungenClickhandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			contentPanel.clear();
			contentPanel.add(new AlleAusschreibungenReportForm(cpm));

		}
	}

}
