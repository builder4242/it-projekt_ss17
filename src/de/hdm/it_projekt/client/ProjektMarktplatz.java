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


public class ProjektMarktplatz implements EntryPoint {

	private final ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

	public void onModuleLoad() {
		// TODO Auto-generated method stub


		String Ergebnis;
		ProjektMarktplatz markt = null;


		
		Button TestButton = new Button("Test"); 
		
		Button sidButton = new Button("Sid"); 
		

		final String ausgabe;

		/*Mapper Buttons*/ 
		Button projektmarktplatzButton = new Button("Daten Projektmarktplatz"); 
		
		
		Hyperlink link = new Hyperlink("Ausschreibung", "LauffaehigeTestKlasse");
		
		/*Aufbau des Textfeldes inkl. Button */  
		addPanel.add(testLabel);
		addPanel.add(link);
		addPanel.add(testTextBox);
	    addPanel.add(TestButton);
	    mainPanel.add(testLabel);
	    addPanel.add(projektmarktplatzButton);
	    addPanel.add(sidButton);
	
	
		/* main Panel */ 
		mainPanel.add(addPanel); 
		
		
		/*Panel zur HTML Seite hinzufügen */ 
		 RootPanel.get("content").add(mainPanel);
		 
		 
		 
		 /* Click Handler Test */ 
		 
		TestButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		          Window.alert("Test erfolgreich");
		        
		        	}
		      });
		
			/* Mapper Test */ 
		projektmarktplatzButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				Window.alert("ausstehend"); 
			}
		}); 
		
		sidButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				Window.alert("Hallo ich bin ein Sid"); 
				
			}
		}); 
		
		
		//TEST 
		//TEST DER MENU BAR 
		
		

	
	}
	
		Label testLabel = new Label(ausgabe);

		/* Mapper Buttons */
		Button projektmarktplatzButton = new Button("Daten Projektmarktplatz");

		/* Aufbau des Textfeldes inkl. Button */
		addPanel.add(testTextBox);
		addPanel.add(TestButton);
		mainPanel.add(testLabel);

		/* main Panel */
		mainPanel.add(addPanel);

		/* Panel zur HTML Seite hinzufügen */
		RootPanel.get("content").add(mainPanel);

		/* Click Handler Test */

		TestButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert("Test erfolgreich");

			
		});

		/* Mapper Test */
		projektmarktplatzButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert("ausstehend");
			}
		});
		


	}
	class Marktplaetze implements AsyncCallback<Vector<de.hdm.it_projekt.shared.bo.ProjektMarktplatz>> {

		private String a = "";
		public Marktplaetze (String a) {
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
