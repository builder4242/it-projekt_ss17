/** Die Klasse NewPersonForm wird benötigt um ein Profil an zu legen, wenn sich ein Nutzer zum
 *  ersten mal einloggt. Die Klasse stellt 7 TextBoxes für Informationen wie Name und Adresse bereit. 
 *  Über den Button Neu, mit zugehörigem ClickHandler, werden die Daten aus den Textfelden in die Datenbank
 *  gespeichert und der Nutzer wird zur Marktplatzübersicht weitergeleitet. Die Optik wird druch das 
 *  einbinden von CSS angepasst.   */
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.it_projekt.shared.bo.LoginInfo;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Team;
import de.hdm.it_projekt.shared.bo.Unternehmen;

/**
 * @author Daniel Fleps
 *
 */
public class NewPersonForm extends Showcase {

	private Widget menu = null;
	static LoginInfo loginInfo = null;
	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private Label firstNameLabel = new Label("Vorname");
	private Label lastNameLabel = new Label("Nachname");
	private TextBox firstNameTextBox = new TextBox();
	private TextBox lastNameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	private TextBox telTextBox = new TextBox();
	private TextBox strasseTextBox = new TextBox();
	private TextBox plzTextBox = new TextBox();
	private TextBox ortTextBox = new TextBox();
	private ListBox auswahlBox = new ListBox();

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public NewPersonForm() {
		Window.alert("Es ist ein Fehler beim anlegen des Benutzerkontos aufgetreten.");
	}

	public NewPersonForm(String googleId, Widget m) {

		menu = m;

		auswahlBox.addItem("Person", "P");
		auswahlBox.addItem("Unternehmen", "U");
		auswahlBox.addItem("Team", "T");
		auswahlBox.addChangeHandler(new AuswahlChangeHandler());

		this.add(new Label(
				"Sie besitzen noch kein Benutzerprofil bei uns, bitte geben Sie in nachfolgendem Formular Ihre Daten ein."));
		this.addStyleName("myprojekt-formlabel");

		Grid organisationseinheitGrid = new Grid(9, 2);
		this.add(organisationseinheitGrid);

		emailTextBox.setText(googleId);
		emailTextBox.setEnabled(false);

		Label auswahlLabel = new Label("Typ");
		organisationseinheitGrid.setWidget(0, 0, auswahlLabel);
		organisationseinheitGrid.setWidget(0, 1, auswahlBox);

		organisationseinheitGrid.setWidget(1, 0, firstNameLabel);
		organisationseinheitGrid.setWidget(1, 1, firstNameTextBox);

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

		Button newButton = new Button("Anlegen");
		newButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(new NewClickHandler());
		customerButtonsPanel.add(newButton);

		Button abmeldungButton = new Button("Abmelden");
		abmeldungButton.setStyleName("myprojekt-abmeldebutton");
		abmeldungButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.assign(loginInfo.getLogoutUrl());
			}
		});

	}

	private class AuswahlChangeHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {

			if (auswahlBox.getSelectedValue() == "P") {
				firstNameLabel.setVisible(true);
				firstNameTextBox.setVisible(true);
				lastNameLabel.setText("Nachname");
			} else {
				firstNameLabel.setVisible(false);
				firstNameTextBox.setVisible(false);
				lastNameLabel.setText("Name");
			}
		}
	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (auswahlBox.getSelectedValue() == "P") {
				pa.createPerson(lastNameTextBox.getText(), firstNameTextBox.getText(), emailTextBox.getText(),
						strasseTextBox.getText(), Integer.parseInt(plzTextBox.getText()), ortTextBox.getText(),
						telTextBox.getText(), new CreatePersonCallback());
			}

			if(auswahlBox.getSelectedValue() == "T") {
				pa.createTeam(lastNameTextBox.getText(), emailTextBox.getText(),
						strasseTextBox.getText(), Integer.parseInt(plzTextBox.getText()), ortTextBox.getText(),
						telTextBox.getText(), new CreateTeamCallback());			
			}

			if(auswahlBox.getSelectedValue() == "U") {
				pa.createUnternehmen(lastNameTextBox.getText(), emailTextBox.getText(),
						strasseTextBox.getText(), Integer.parseInt(plzTextBox.getText()), ortTextBox.getText(),
						telTextBox.getText(), new CreateUnternehmenCallback());
			}
		}
	}

	private class CreatePersonCallback implements AsyncCallback<Person> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Person result) {

			if (result == null) {
				Window.alert("Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut.");
			} else {

				setLoggedIn(result);
			}
		}
	}
	
	private class CreateTeamCallback implements AsyncCallback<Team> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Team result) {

			if (result == null) {
				Window.alert("Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut.");
			} else {
				setLoggedIn(result);
			}
		}
	}
	
	private class CreateUnternehmenCallback implements AsyncCallback<Unternehmen> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Unternehmen result) {

			if (result == null) {
				Window.alert("Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut.");
			} else {
				setLoggedIn(result);
			}
		}
	}
	
	private void setLoggedIn(Organisationseinheit o) {
		
		Window.alert("Ihr Konto wurde erfolgreich angelegt.");

		MyProjekt.loginInfo.setCurrentUser(o);

		Label infoLabel = new Label(MyProjekt.loginInfo.toString());
		infoLabel.addStyleName("myprojekt-loginlabel");
		RootPanel.get("userinfo").add(infoLabel);

		Showcase showcase = new Marktuebersicht();
		RootPanel.get("content").clear();
		RootPanel.get("content").add(showcase);
		menu.setVisible(true);
	}
}
