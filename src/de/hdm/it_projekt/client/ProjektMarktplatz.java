package de.hdm.it_projekt.client;

import java.util.Vector;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.it_projekt.server.db.*;


public class ProjektMarktplatz implements EntryPoint {


	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		String Ergebnis; 
		ProjektMarktplatz markt = null; 
		
		TextBox testTextBox = new TextBox();
		
		Button TestButton = new Button("Test"); 
		
		VerticalPanel mainPanel = new VerticalPanel();
		HorizontalPanel addPanel = new HorizontalPanel();
		Label testLabel = new Label();
		
		/*Mapper Buttons*/ 
		Button projektmarktplatzButton = new Button("Daten Projektmarktplatz"); 
		
		
		
		
		/*Aufbau des Textfeldes inkl. Button */  
		addPanel.add(testTextBox);
	    addPanel.add(TestButton);
	    mainPanel.add(testLabel);
	
	
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
	
	}
		
		

}
