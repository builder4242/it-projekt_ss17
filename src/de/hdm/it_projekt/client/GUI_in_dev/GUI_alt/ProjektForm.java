package de.hdm.it_projekt.client.GUI_in_dev.GUI_alt;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Projekt;
/**
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Julian Reimenthal
 * 
 * To be done:
 * Projekt erstellung benötigt einen projektmarktpplatz um ertsellt zu werden. Methode zum auslesen? 
 */
public class ProjektForm extends VerticalPanel {


		ProjektAdministrationAsync projektVerwaltung = ClientsideSettings
				.getProjektAdministration();
		Projekt projektToDisplay = null;
		ProjektOrganisationseinheitTreeView catvm = null;

		/*
		 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
		 */
		TextBox nameTextBox = new TextBox();
		DateBox startdatumDateBox = new DateBox();
		DateBox enddatumDateBox = new DateBox();
		TextBox lastNameTextBox = new TextBox();
		Label idValueLabel = new Label();

		/*
		 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in
		 * einem Raster angeordnet, dessen Größe sich aus dem Platzbedarf
		 * der enthaltenen Widgets bestimmt.
		 */
		public ProjektForm() {
			Grid projektGrid = new Grid(3, 2);
			this.add(projektGrid);

			Label idLabel = new Label("ID");
			projektGrid.setWidget(0, 0, idLabel);
			projektGrid.setWidget(0, 1, idValueLabel);

			Label firstNameLabel = new Label("Vorname");
			projektGrid.setWidget(1, 0, firstNameLabel);
			projektGrid.setWidget(1, 1, firstNameTextBox);

			Label lastNameLabel = new Label("Nachname");
			projektGrid.setWidget(2, 0, lastNameLabel);
			projektGrid.setWidget(2, 1, lastNameTextBox);

			HorizontalPanel projektButtonsPanel = new HorizontalPanel();
			this.add(projektButtonsPanel);

			Button changeButton = new Button("Ändern");
			changeButton.addClickHandler(new ChangeClickHandler());
			projektButtonsPanel.add(changeButton);

			Button searchButton = new Button("Suchen");
			projektButtonsPanel.add(searchButton);

			Button deleteButton = new Button("Löschen");
			deleteButton.addClickHandler(new DeleteClickHandler());
			projektButtonsPanel.add(deleteButton);

			Button newButton = new Button("Neu");
			newButton.addClickHandler(new NewClickHandler());
			projektButtonsPanel.add(newButton);
		}

		/*
		 * Click Handlers.
		 */

		/**
		 * Die Änderung einer Projekt bezieht sich auf den
		 * Namen, im Fall dass es sich um eine Person handelt zusätzlich auf den Vorname.
		 *  Es erfolgt der Aufruf der Service-Methode "save".
		 * 
		 */
		private class ChangeClickHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				if (projektToDisplay != null) {
					projektToDisplay.setName(firstNameTextBox.getText());
					projektToDisplay.setName(lastNameTextBox.getText());
					projektVerwaltung.save(projektToDisplay, new SaveCallback());
				} else {
					Window.alert("kein Kunde ausgewählt");
				}
			}
		}

		private class SaveCallback implements AsyncCallback<Void> {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Die Namensänderung ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Void result) {
				// Die Änderung wird zum Kunden- und Kontenbaum propagiert.
				catvm.updateProjekt(projektToDisplay);
			}
		}

		/**
		 * Das erfolgreiche Löschen eines Kunden führt zur Aktualisierung des
		 * Kunden- und Kontenbaumes.
		 * 
		 */
		private class DeleteClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				if (projektToDisplay == null) {
					Window.alert("kein Kunde ausgewählt");
				} else {
					projektVerwaltung.delete(projektToDisplay,
							new deleteProjektCallback(projektToDisplay));
				}
			}
		}

		class deleteProjektCallback implements AsyncCallback<Void> {

			Projekt projekt = null;

			deleteProjektCallback(Projekt c) {
				projekt = c;
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Löschen des Kunden ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Void result) {
				if (projekt != null) {
					setSelected(null);
					catvm.removeProjekt(projekt);
				}
			}
		}

		/**
		 * Auch wenn ein neuer Kunde hinzukommt, muss der Kunden- und Kontenbaum
		 * aktualisiert werden.
		 * 
		 */
		private class NewClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				String name = nameTextBox.getText();
				Date startdatum= startdatumDateBox.getValue() 
				Date enddatum= enddatumDateBox.getValue()
				String lastName = lastNameTextBox.getText();
				projektVerwaltung.createProjektFor(null, name, null, null, lastName,
						new CreateProjektCallback());
			}
		}

		class CreateProjektCallback implements AsyncCallback<Projekt> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Anlegen eines neuen Kunden ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Projekt projekt) {
				if (projekt != null) {
					// Das erfolgreiche Hinzufügen eines Kunden wird an den Kunden- und
					// Kontenbaum propagiert.
					catvm.addProjekt(projekt);
				}
			}
		}

		// catvm setter
		void setCatvm(ProjektOrganisationseinheitTreeView catvm) {
			this.catvm = catvm;
		}

		/*
		 * Wenn der anzuzeigende Kunde gesetzt bzw. gelöscht wird, werden die
		 * zugehörenden Textfelder mit den Informationen aus dem Kundenobjekt
		 * gefüllt bzw. gelöscht.
		 */
		void setSelected(Projekt c) {
			if (c != null) {
				projektToDisplay = c;
				firstNameTextBox.setText(projektToDisplay.getFirstName());
				lastNameTextBox.setText(projektToDisplay.getName());
				idValueLabel.setText(Integer.toString(projektToDisplay.getId()));
			} else {
				firstNameTextBox.setText("");
				lastNameTextBox.setText("");
				idValueLabel.setText("");
			}
		}

	}

