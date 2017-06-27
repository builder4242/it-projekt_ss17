package de.hdm.it_projekt.client.GUI_Report;


import com.google.gwt.core.client.*; 
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.server.report.*;


public class ReportGenerator implements EntryPoint {

	
	
	public void onModuleLoad() {

		/**
		 * Definition der Panels
		 */
		final VerticalPanel vertPanelNav = new VerticalPanel();
		

		
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
		vertPanelNav.add(alleAusschreibungenButton);
		vertPanelNav.add(passendeAusschreibungenButton);
		vertPanelNav.add(bewerbungAufAusschreibungenButton);
		vertPanelNav.add(ausschreibungenAufBewerbungButton);
		vertPanelNav.add(fanOutButton);
		vertPanelNav.add(fanInButton);
		vertPanelNav.add(zugehoerigeProjekteButton);

		/**
		 * Ueberschirft fuer den Rpeort Generator
		 */
		Label ueberschriftLabel = new Label("Report Generator");
		ueberschriftLabel.setStyleName("h1");
		
		/**
		 * Zusammenfuegen des Panels
		 */
		RootPanel.get("content").add(ueberschriftLabel);
		RootPanel.get("content").add(vertPanelNav);
		
	}
		
	private class ChangeClickhandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			ListBox lb1 = new ListBox();
			lb1.addItem("test");
			lb1.addItem("test2");
			lb1.addItem("test2");
			lb1.addItem("test2");
			lb1.addItem("test2");
			lb1.addItem("test2");
			lb1.addItem("test2");
			lb1.addItem("test2");
			lb1.setVisibleItemCount(5);
			final VerticalPanel vertPanelAuswahl = new VerticalPanel();
			vertPanelAuswahl.add(lb1);
			RootPanel.get("content").add(vertPanelAuswahl);
					//ReportGeneratorImpl.createAlleAusschreibungenReport().toString();
		
			
			//Window.alert("tbd");
		}
	}	

}
