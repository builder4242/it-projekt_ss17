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

import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class MyProjekt implements EntryPoint {

	private final ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

	public void onModuleLoad() {
		// TODO Auto-generated method stub




		String ausgabe = null;

		final HorizontalPanel mainPanel = new HorizontalPanel();
		
		pa.getAlleProjektMarktplaetze(new Marktplaetze(ausgabe));
		final Label label = new Label(ausgabe);
	
		/* main Panel */ 
	    mainPanel.add(label); 
		
		
		/*Panel zur HTML Seite hinzufügen */ 
		 RootPanel.get("content").add(mainPanel);
		 
		 
		 

	}

	class Marktplaetze implements AsyncCallback<Vector<ProjektMarktplatz>> {

		private String a = "";

		public Marktplaetze(String a) {
			this.a = a;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<ProjektMarktplatz> result) {

			for (ProjektMarktplatz pm : result) {
				a += pm.toString();
			}

		}
	}
}
