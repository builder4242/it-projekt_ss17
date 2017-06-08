package de.hdm.it_projekt.client;

import java.util.Vector;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.it_projekt.client.GUI.MarktplatzUebersicht;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class MyProjekt implements EntryPoint {


	public void onModuleLoad() {
		

		ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();



		final Label ausgabe = new Label();
		ausgabe.setText("Hallo<br>");

		final HorizontalPanel mainPanel = new HorizontalPanel();
		
		pa.getAlleProjektMarktplaetze(new Marktplaetze(ausgabe));
		
		final MarktplatzUebersicht menu = new MarktplatzUebersicht();
		
		//menu.onInitialize().asWidget()
		/* main Panel */ 
	    mainPanel.add(ausgabe); 
		
		
		/*Panel zur HTML Seite hinzufügen */ 
		 RootPanel.get("content").add(mainPanel);
		 
	}

	class Marktplaetze implements AsyncCallback<Vector<ProjektMarktplatz>> {

		private Label a;

		public Marktplaetze(Label a) {
			this.a = a;
		}

		@Override
		public void onFailure(Throwable caught) {

			a.setText("Fehler bei getAlleMarktplätze." + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<ProjektMarktplatz> result) {

			String t = "";
			for (ProjektMarktplatz pm : result) {
				t += pm.toString();
			}
			
			a.setText(t);			
		}
	}
}
