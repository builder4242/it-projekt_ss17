package de.hdm.it_projekt.client;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProjektMarktplatz implements EntryPoint {

	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		
		
		private TextBox testTextBox = new TextBox();
		private Button addWertButton = new Button("Submit");
		private VerticalPanel mainPanel = new VerticalPanel();
		private HorizontalPanel addPanel = new HorizontalPanel();
		
		
		public void onModuleLoad() {
			
			/*Aufbau des Textfeldes inkl. Button */  
			addPanel.add(testTextBox);
		    addPanel.add(addWertButton);
			}
		
			/* main Panel */ 
			mainPanel.add(addPanel); 
			
			
			/*Panel zur HTML Seite hinzufügen */ 
			 RootPanel.get().add(mainPanel);
		
		
	}

}
