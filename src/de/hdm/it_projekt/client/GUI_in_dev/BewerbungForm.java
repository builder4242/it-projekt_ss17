/**
 * 
 */
package de.hdm.it_projekt.client.GUI_in_dev;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;



/**
 * @author Daniel Fleps
 *
 */
public class BewerbungForm {

	
	DateBox erstelldatumTextBox = new DateBox();
	TextArea bewerbungstextTextArea = new TextArea();
	TextBox auschreibungTextBox = new TextBox();
	TextBox organisationseinheitTextBox = new TextBox();
	Label idValueLabel = new Label();
	
	//Clickhandler to be DONE 
	
	public BewerbungForm(){
		
		
			Grid bewerbungGrid = new Grid(5, 2);
			//this.add(bewerbungGrid);

			Label idLabel = new Label("ID");
			bewerbungGrid.setWidget(0, 0, idLabel);
			bewerbungGrid.setWidget(0, 1, idValueLabel);

			Label bewerbungsdatumLabel = new Label("Erstelldatum");
			bewerbungGrid.setWidget(1, 0, bewerbungsdatumLabel);
			bewerbungGrid.setWidget(1, 1, bewerbungsdatumLabel);
			
			Label bewerbungstextLabel = new Label("Bewerbungstext");
			bewerbungGrid.setWidget(2, 0, bewerbungstextLabel);
			bewerbungGrid.setWidget(2, 1, bewerbungstextLabel);
			
			Label auschreibungLabel = new Label("Ausschreibung");
			bewerbungGrid.setWidget(2, 0, auschreibungLabel);
			bewerbungGrid.setWidget(2, 1, auschreibungLabel);
			
			Label organisationseinheitLabel = new Label("Organisationseinheit");
			bewerbungGrid.setWidget(2, 0, organisationseinheitLabel);
			bewerbungGrid.setWidget(2, 1, organisationseinheitLabel);
			
			HorizontalPanel bewerbungButtonsPanel = new HorizontalPanel();
			//this.add(bewerbungButtonsPanel);

			Button changeButton = new Button("Ändern");
			//changeButton.addClickHandler(new ChangeClickHandler());
			bewerbungButtonsPanel.add(changeButton);

			Button searchButton = new Button("Suchen");
			bewerbungButtonsPanel.add(searchButton);

			Button deleteButton = new Button("Löschen");
			//deleteButton.addClickHandler(new DeleteClickHandler());
			bewerbungButtonsPanel.add(deleteButton);

			Button newButton = new Button("Neu");
			//newButton.addClickHandler(new NewClickHandler());
			bewerbungButtonsPanel.add(newButton);
	
		}
}
