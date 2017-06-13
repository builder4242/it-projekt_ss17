/**
 * 
 */
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.client.GUI_in_dev.GUI_alt.ProjektOrganisationseinheitTreeView;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;

/**
 * @author Daniel Fleps
 *
 */
public class NewOrganisationseinheitForm extends Showcase {

	Organisationseinheit organisationseinheitToDisplay = null;

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	TextBox firstNameTextBox = new TextBox();
	TextBox lastNameTextBox = new TextBox();
	TextBox emailTextBox = new TextBox();
	TextBox telTextBox = new TextBox();
	TextBox strasseTextBox = new TextBox();
	TextBox plzTextBox = new TextBox();
	TextBox ortTextBox = new TextBox();
	TextBox googleIdTextBox = new TextBox();
	ListBox o = new ListBox();

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public NewOrganisationseinheitForm() {
		Window.alert("Es ist ein Fehler beim anlegen des Benutzerkontos aufgetreten.");
	}
	
	
	public NewOrganisationseinheitForm(String googleId) {
		
		googleIdTextBox.setText(googleId);
		googleIdTextBox.setEnabled(false);
		
		Grid organisationseinheitGrid = new Grid(9, 2);
		this.add(organisationseinheitGrid);

		
		o.insertItem("Person", "P", 1);
		o.insertItem("Unternehmen",  "U", 2);
		o.insertItem("Team", "T", 3);
		
		o.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				
				if(o.getSelectedValue() != "P")
					firstNameTextBox.setEnabled(false);
				else
					firstNameTextBox.setEnabled(true);
			}
			
		});
		
		organisationseinheitGrid.setWidget(0, 0, new Label("Auswahl"));
		organisationseinheitGrid.setWidget(0, 1, o);

		Label firstNameLabel = new Label("Vorname");
		organisationseinheitGrid.setWidget(1, 0, firstNameLabel);
		organisationseinheitGrid.setWidget(1, 1, firstNameTextBox);

		Label lastNameLabel = new Label("Nachname");
		organisationseinheitGrid.setWidget(2, 0, lastNameLabel);
		organisationseinheitGrid.setWidget(2, 1, lastNameTextBox);

		Label emailLabel = new Label("E-Mail");
		organisationseinheitGrid.setWidget(3, 0, emailLabel);
		organisationseinheitGrid.setWidget(3, 1, emailTextBox);

		Label telLabel = new Label("Telefon");
		organisationseinheitGrid.setWidget(4, 0, telLabel);
		organisationseinheitGrid.setWidget(4, 1, telTextBox);

		Label strasseLabel = new Label("Strasse");
		organisationseinheitGrid.setWidget(5, 0, strasseLabel);
		organisationseinheitGrid.setWidget(5, 1, strasseTextBox);

		Label plzLabel = new Label("PLZ");
		organisationseinheitGrid.setWidget(6, 0, plzLabel);
		organisationseinheitGrid.setWidget(6, 1, plzTextBox);

		Label ortLabel = new Label("Ort");
		organisationseinheitGrid.setWidget(7, 0, ortLabel);
		organisationseinheitGrid.setWidget(7, 1, ortTextBox);
		
		Label googleIdLabel = new Label("GoogleID");
		organisationseinheitGrid.setWidget(8, 0, googleIdLabel);
		organisationseinheitGrid.setWidget(8, 1, googleIdTextBox);

		HorizontalPanel customerButtonsPanel = new HorizontalPanel();
		this.add(customerButtonsPanel);

		Button newButton = new Button("Neu");
		newButton.addClickHandler(new NewClickHandler());
		customerButtonsPanel.add(newButton);

	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			if(o.getSelectedValue() == "P")
				pa.createPerson(lastNameTextBox.getText(), 
				firstNameTextBox.getText(), 
				emailTextBox.getText(), 
				strasseTextBox.getText(), 
				Integer.parseInt(plzTextBox.getText()),
				ortTextBox.getText(), 
				telTextBox.getText(), 
				googleIdTextBox.getText(),
				new CreatePersonCallback());

		}

	}
	
	private class CreatePersonCallback implements AsyncCallback<Person> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Person result) {
			
			Window.alert("Person angelegt");
			
		}
		
	}

}
