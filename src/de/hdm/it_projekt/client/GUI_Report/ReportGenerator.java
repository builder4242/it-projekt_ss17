package de.hdm.it_projekt.client.GUI_Report;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.server.report.*;
import de.hdm.it_projekt.shared.ReportGeneratorAsync;
import de.hdm.it_projekt.shared.bo.*;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.Row;
import de.hdm.it_projekt.shared.report.SimpleReport;

public class ReportGenerator implements EntryPoint {

	 HorizontalPanel hPanel = null;
	 VerticalPanel menuPanel = null;
	 VerticalPanel contentPanel = null;

	public void onModuleLoad() { 

		/**
		 * Definition der Panels
		 */
		hPanel = new HorizontalPanel();
		menuPanel = new VerticalPanel();
		contentPanel = new VerticalPanel();
		
		hPanel.add(menuPanel);
		hPanel.add(contentPanel);

		/**
		 * Button Definitionen
		 */
		Button alleAusschreibungenButton = new Button("Zeige Alle Ausschreibungen");
		alleAusschreibungenButton.setStyleName("myprojekt-reportbutton");
		alleAusschreibungenButton.addClickHandler(new ChangeClickhandler());

		Button passendeAusschreibungenButton = new Button("Zeige passende Ausschreibungen");
		passendeAusschreibungenButton.setStyleName("myprojekt-reportbutton");
		passendeAusschreibungenButton.addClickHandler(new ChangeClickhandler());

		Button bewerbungAufAusschreibungenButton = new Button("Zeige Bewerbungen für Ausschreibung");
		bewerbungAufAusschreibungenButton.setStyleName("myprojekt-reportbutton");
		bewerbungAufAusschreibungenButton.addClickHandler(new ChangeClickhandler());

		Button ausschreibungenAufBewerbungButton = new Button("Zeige Ausschreibung zur Bewerbung");
		ausschreibungenAufBewerbungButton.setStyleName("myprojekt-reportbutton");
		ausschreibungenAufBewerbungButton.addClickHandler(new ChangeClickhandler());

		Button fanOutButton = new Button("Zeige Fan Out");
		fanOutButton.setStyleName("myprojekt-reportbutton");
		fanOutButton.addClickHandler(new ChangeClickhandler());

		Button fanInButton = new Button("Zeige Fan In");
		fanInButton.setStyleName("myprojekt-reportbutton");
		fanInButton.addClickHandler(new ChangeClickhandler());

		Button zugehoerigeProjekteButton = new Button("Zeige zugehoerige Projekte");
		zugehoerigeProjekteButton.setStyleName("myprojekt-reportbutton");
		zugehoerigeProjekteButton.addClickHandler(new ChangeClickhandler());

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

		/**
		 * Ueberschirft fuer den Rpeort Generator
		 */
		Label ueberschriftLabel = new Label("Report Generator");
		ueberschriftLabel.setStyleName("h1");

		/**
		 * Zusammenfuegen des Panels
		 */
		RootPanel.get("menu").add(ueberschriftLabel);
		RootPanel.get("content").add(hPanel);

	}

	private class ChangeClickhandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			
			
			contentPanel.clear();
			//contentPanel.add(new Label("test"));
			contentPanel.add(new AlleAusschreibungenReportForm());
			
		}
	}

}
