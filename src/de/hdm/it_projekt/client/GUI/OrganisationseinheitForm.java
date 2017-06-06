package de.hdm.it_projekt.client.GUI;

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

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
/**
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Julian Reimenthal
 */
public class OrganisationseinheitForm extends VerticalPanel {


		BankAdministrationAsync bankVerwaltung = ClientsideSettings // waiting 
				.getBankVerwaltung();
		Organisationseinheit organisationseinheitToDisplay = null;
		ProjektOrganisationseinheitTreeView catvm = null;

		/*
		 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
		 */
		TextBox firstNameTextBox = new TextBox();
		TextBox lastNameTextBox = new TextBox();
		Label idValueLabel = new Label();

		/*
		 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in
		 * einem Raster angeordnet, dessen Größe sich aus dem Platzbedarf
		 * der enthaltenen Widgets bestimmt.
		 */
		public OrganisationseinheitForm() {
			Grid organisationseinheitGrid = new Grid(3, 2);
			this.add(organisationseinheitGrid);

			Label idLabel = new Label("ID");
			organisationseinheitGrid.setWidget(0, 0, idLabel);
			organisationseinheitGrid.setWidget(0, 1, idValueLabel);

			Label firstNameLabel = new Label("Vorname");
			organisationseinheitGrid.setWidget(1, 0, firstNameLabel);
			organisationseinheitGrid.setWidget(1, 1, firstNameTextBox);

			Label lastNameLabel = new Label("Nachname");
			organisationseinheitGrid.setWidget(2, 0, lastNameLabel);
			organisationseinheitGrid.setWidget(2, 1, lastNameTextBox);

			HorizontalPanel organisationseinheitButtonsPanel = new HorizontalPanel();
			this.add(organisationseinheitButtonsPanel);

			Button changeButton = new Button("Ändern");
			changeButton.addClickHandler(new ChangeClickHandler());
			organisationseinheitButtonsPanel.add(changeButton);

			Button searchButton = new Button("Suchen");
			organisationseinheitButtonsPanel.add(searchButton);

			Button deleteButton = new Button("Löschen");
			deleteButton.addClickHandler(new DeleteClickHandler());
			organisationseinheitButtonsPanel.add(deleteButton);

			Button newButton = new Button("Neu");
			newButton.addClickHandler(new NewClickHandler());
			organisationseinheitButtonsPanel.add(newButton);
		}

		/*
		 * Click Handlers.
		 */

		/**
		 * Die Änderung einer Organisationseinheit bezieht sich auf den
		 * Namen, im Fall dass es sich um eine Person handelt zusätzlich auf den Vorname.
		 *  Es erfolgt der Aufruf der Service-Methode "save".
		 * 
		 */
		private class ChangeClickHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				if (organisationseinheitToDisplay != null) {
					organisationseinheitToDisplay.setName(firstNameTextBox.getText());
					organisationseinheitToDisplay.setLastName(lastNameTextBox.getText());
					bankVerwaltung.save(organisationseinheitToDisplay, new SaveCallback());
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
				catvm.updateOrganisationseinheit(organisationseinheitToDisplay);
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
				if (organisationseinheitToDisplay == null) {
					Window.alert("kein Kunde ausgewählt");
				} else {
					bankVerwaltung.delete(organisationseinheitToDisplay,
							new deleteOrganisationseinheitCallback(organisationseinheitToDisplay));
				}
			}
		}

		class deleteOrganisationseinheitCallback implements AsyncCallback<Void> {

			Organisationseinheit organisationseinheit = null;

			deleteOrganisationseinheitCallback(Organisationseinheit c) {
				organisationseinheit = c;
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Löschen des Kunden ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Void result) {
				if (organisationseinheit != null) {
					setSelected(null);
					catvm.removeOrganisationseinheit(organisationseinheit);
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
				String firstName = firstNameTextBox.getText();
				String lastName = lastNameTextBox.getText();
				bankVerwaltung.createOrganisationseinheit(firstName, lastName,
						new CreateOrganisationseinheitCallback());
			}
		}

		class CreateOrganisationseinheitCallback implements AsyncCallback<Organisationseinheit> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Anlegen eines neuen Kunden ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Organisationseinheit organisationseinheit) {
				if (organisationseinheit != null) {
					// Das erfolgreiche Hinzufügen eines Kunden wird an den Kunden- und
					// Kontenbaum propagiert.
					catvm.addOrganisationseinheit(organisationseinheit);
				}
			}
		}

		// catvm setter
		void setCatvm(OrganisationseinheitAccountsTreeViewModel catvm) {
			this.catvm = catvm;
		}

		/*
		 * Wenn der anzuzeigende Kunde gesetzt bzw. gelöscht wird, werden die
		 * zugehörenden Textfelder mit den Informationen aus dem Kundenobjekt
		 * gefüllt bzw. gelöscht.
		 */
		void setSelected(Organisationseinheit c) {
			if (c != null) {
				organisationseinheitToDisplay = c;
				firstNameTextBox.setText(organisationseinheitToDisplay.getFirstName());
				lastNameTextBox.setText(organisationseinheitToDisplay.getName());
				idValueLabel.setText(Integer.toString(organisationseinheitToDisplay.getId()));
			} else {
				firstNameTextBox.setText("");
				lastNameTextBox.setText("");
				idValueLabel.setText("");
			}
		}

	}

}
