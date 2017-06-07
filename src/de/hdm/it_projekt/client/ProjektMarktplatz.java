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

	public String Ergebnis; 
	private ProjektMarktplatz markt = null; 
	
	private TextBox testTextBox = new TextBox();
	
	private Button TestButton = new Button("Test"); 
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private Label testLabel = new Label();
	
	/*Mapper Buttons*/ 
	private Button projektmarktplatzButton = new Button("Daten Projektmarktplatz"); 
	
	
	
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		/*Aufbau des Textfeldes inkl. Button */  
		addPanel.add(testTextBox);
	    addPanel.add(TestButton);
	    mainPanel.add(testLabel);
	
	
		/* main Panel */ 
		mainPanel.add(addPanel); 
		
		
		/*Panel zur HTML Seite hinzufügen */ 
		 RootPanel.get().add(mainPanel);
		 
		 
		 
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
