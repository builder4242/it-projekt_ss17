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

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;

/**
 * @author Daniel Fleps
 *
 */
public class NewPersonForm extends Showcase {

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

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public NewPersonForm() {
		Window.alert("Es ist ein Fehler beim anlegen des Benutzerkontos aufgetreten.");
	}

	public NewPersonForm(String googleId) {

		this.add(new Label(
				"Sie besitzen noch kein Benutzerprofil bei uns, bitte geben Sie in nachfolgendem Formular Ihre Daten ein."));

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

	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			pa.createPerson(lastNameTextBox.getText(), firstNameTextBox.getText(), emailTextBox.getText(),
					strasseTextBox.getText(), Integer.parseInt(plzTextBox.getText()), ortTextBox.getText(),
					telTextBox.getText(), new CreatePersonCallback());
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

		}

	}
}
