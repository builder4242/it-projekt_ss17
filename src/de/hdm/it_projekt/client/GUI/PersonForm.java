/**
 * 
 */
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.it_projekt.client.GUI.AusschreibungForm.DeleteAusschreibungCallback;
import de.hdm.it_projekt.client.GUI.AusschreibungForm.SaveCallback;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;

/**
 * @author Daniel Fleps
 *
 */
public class PersonForm extends Showcase {
	
	private Organisationseinheit organisationseinheitToDisplay = null;
	private Widget menu = null;
	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private Person asToDisplay = null;
	
	private TextBox firstNameTextBox = new TextBox();
	private TextBox lastNameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	private TextBox telTextBox = new TextBox();
	private TextBox strasseTextBox = new TextBox();
	private TextBox plzTextBox = new TextBox();
	private TextBox ortTextBox = new TextBox();

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public PersonForm() {
		Window.alert("Es ist ein Fehler beim anlegen des Benutzerkontos aufgetreten.");
	}

	public PersonForm(String googleId, Widget m) {

		menu = m;
		this.add(new Label(
				"Sie besitzen noch kein Benutzerprofil bei uns, bitte geben Sie in nachfolgendem Formular Ihre Daten ein."));
		this.addStyleName("myprojekt-formlabel");
		
		Grid organisationseinheitGrid = new Grid(9, 2);
		this.add(organisationseinheitGrid);

		emailTextBox.setText(googleId);
		emailTextBox.setEnabled(false);

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

		HorizontalPanel customerButtonsPanel = new HorizontalPanel();
		this.add(customerButtonsPanel);


		Button newButton = new Button("Neu");
		newButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(new NewClickHandler());
		customerButtonsPanel.add(newButton);
		customerButtonsPanel.add(newButton);
		
		Button changeButton = new Button("Ändern");
		changeButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		changeButton.addClickHandler(new ChangeClickHandler());
		customerButtonsPanel.add(changeButton);
		
		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		customerButtonsPanel.add(deleteButton);

	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			pa.createPerson(lastNameTextBox.getText(), firstNameTextBox.getText(), emailTextBox.getText(),
					strasseTextBox.getText(), Integer.parseInt(plzTextBox.getText()), ortTextBox.getText(),
					telTextBox.getText(), new CreatePersonCallback());
		}
	}
	
	private class ChangeClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			if (asToDisplay != null) {
				asToDisplay.setVorname(firstNameTextBox.getText());
				asToDisplay.setName(lastNameTextBox.getText());
				asToDisplay.setTel(telTextBox.getText());
				asToDisplay.setStrasse(strasseTextBox.getText());
				asToDisplay.setPlz(Integer.parseInt(plzTextBox.getText()));   
				asToDisplay.setOrt(ortTextBox.getText());
				
				

				pa.save(asToDisplay, new SaveCallback());
				Window.alert("Änderungen gespeichert.");
			} else
				Window.alert("Es wurde nichts ausgewählt");
		}
		
		
	}
	
	class SaveCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			// ptvm.updateAusschreibung(asToDisplay);
		}
	}
	
private class DeleteClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			
			
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

			MyProjekt.loginInfo.setCurrentUser(result);

			RootPanel.get("userinfo").add(new Label(MyProjekt.loginInfo.toString()));
			
			Showcase showcase = new Marktuebersicht();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(showcase);
			menu.setVisible(true);
			

		}

	}

}
