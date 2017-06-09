package de.hdm.it_projekt.client.GUI_alt;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LauffaehigeTestKlasse {
	
	TextBox testTextBox = new TextBox();
	
	Button TestButton = new Button("Test"); 
	
	Button sidButton = new Button("Sid"); 
	
	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel addPanel = new HorizontalPanel();
	Label testLabel = new Label("Verlinkte Seite");
	
	public void LauffaehigeTestklasse(){
	/*Aufbau des Textfeldes inkl. Button */  
	addPanel.add(testLabel);

	addPanel.add(testTextBox);
    addPanel.add(TestButton);
    mainPanel.add(testLabel);
   
    addPanel.add(sidButton);


	/* main Panel */ 
	mainPanel.add(addPanel); 
	
	
	/*Panel zur HTML Seite hinzufügen */ 
	 RootPanel.get("content").add(mainPanel);
	}
}
