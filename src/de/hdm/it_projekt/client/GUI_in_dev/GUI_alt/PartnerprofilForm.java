package de.hdm.it_projekt.client.GUI_in_dev.GUI_alt;

/**
 * To be Done:
 * Projektverwaltung
 * ClientsideSettings
 * remove/addpartnerprofil
 * Clickhandler anpassen
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
import de.hdm.it_projekt.shared.bo.Partnerprofil;
/**
* Formular für die Darstellung der gewählten Partnerprofil
* 
* @author Julian Reimenthal
*/
public class PartnerprofilForm extends VerticalPanel {


		ProjektAdministrationAsync projektVerwaltung = ClientsideSettings // waiting for Classes 
				.getProjektAdministration();
		Partnerprofil partnerprofilToDisplay = null;
		PartnerprofilProjektTreeView catvm = null;

		/*
		 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
		 */
		DateBox erstelldatumTextBox = new DateBox();
		DateBox aenderungsdatumTextBox =new DateBox();
		TextBox eigenschaftenTextBox = new TextBox();
		Label idValueLabel = new Label();

		/*
		 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in
		 * einem Raster angeordnet, dessen Größe sich aus dem Platzbedarf
		 * der enthaltenen Widgets bestimmt.
		 */
		public PartnerprofilForm() {
			Grid partnerprofilGrid = new Grid(4, 2);
			this.add(partnerprofilGrid);

			Label idLabel = new Label("ID");
			partnerprofilGrid.setWidget(0, 0, idLabel);
			partnerprofilGrid.setWidget(0, 1, idValueLabel);

			Label partnerprofilsErstelldatumLabel = new Label("Erstelldatum");
			partnerprofilGrid.setWidget(1, 0, partnerprofilsErstelldatumLabel);
			partnerprofilGrid.setWidget(1, 1, partnerprofilsErstelldatumLabel);
			
			Label partnerprofilAenderungsdatumLabel = new Label("Änderungsdatum");
			partnerprofilGrid.setWidget(2, 0, partnerprofilAenderungsdatumLabel);
			partnerprofilGrid.setWidget(2, 1, partnerprofilAenderungsdatumLabel);
			
			Label partnerprofilEigenschaftenLabel = new Label("Eigenschaften");
			partnerprofilGrid.setWidget(2, 0, partnerprofilEigenschaftenLabel);
			partnerprofilGrid.setWidget(2, 1, partnerprofilEigenschaftenLabel);
			
			HorizontalPanel partnerprofilButtonsPanel = new HorizontalPanel();
			this.add(partnerprofilButtonsPanel);

			Button changeButton = new Button("Ändern");
			changeButton.addClickHandler(new ChangeClickHandler());
			partnerprofilButtonsPanel.add(changeButton);

			Button searchButton = new Button("Suchen");
			partnerprofilButtonsPanel.add(searchButton);

			Button deleteButton = new Button("Löschen");
			deleteButton.addClickHandler(new DeleteClickHandler());
			partnerprofilButtonsPanel.add(deleteButton);

			Button newButton = new Button("Neu");
			newButton.addClickHandler(new NewClickHandler());
			partnerprofilButtonsPanel.add(newButton);
		}

		/*
		 * Click Handlers.
		 */

		/**
		 * Die Änderung einer Partnerprofil.
		 * 
		 */
		private class ChangeClickHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				if (partnerprofilToDisplay != null) {
					partnerprofilToDisplay.setAenderungsdatum(aenderungsdatumTextBox.getValue());
					partnerprofilToDisplay.setErstelldatum(erstelldatumTextBox.getValue());
					//partnerprofilToDisplay.setEigenschaften(bezeichnungTextBox.getValue());
					projektVerwaltung.save(partnerprofilToDisplay, new SaveCallback());
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
				catvm.updatePartnerprofil(partnerprofilToDisplay);
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
				if (partnerprofilToDisplay == null) {
					Window.alert("kein Kunde ausgewählt");
				} else {
					projektVerwaltung.delete(partnerprofilToDisplay,
							new deletePartnerprofilCallback(partnerprofilToDisplay));
				}
			}
		}

		class deletePartnerprofilCallback implements AsyncCallback<Void> {

			Partnerprofil partnerprofil = null;

			deletePartnerprofilCallback(Partnerprofil c) {
				partnerprofil = c;
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Löschen des Kunden ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Void result) {
				if (partnerprofil != null) {
					setSelected(null);
					catvm.removePartnerprofil(partnerprofil);
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
				String bezeichnung = bezeichnungTextBox.getText();
				String partnerprofilstext = partnerprofilstextTextBox.getText();
				Partnerprofil p1 = new Partnerprofil();
				p1.setEigenschaften((Eigenschaft).setName(partnerprofileigenschaftTextBox.getValue()));
				
				projektVerwaltung.createPartnerprofil(firstName, lastName,
						new CreatePartnerprofilCallback());
			}
		}

		class CreatePartnerprofilCallback implements AsyncCallback<Partnerprofil> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Anlegen eines neuen Kunden ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Partnerprofil partnerprofil) {
				if (partnerprofil != null) {
					// Das erfolgreiche Hinzufügen eines Kunden wird an den Kunden- und
					// Kontenbaum propagiert.
					catvm.addPartnerprofil(partnerprofil);
				}
			}
		}

		// catvm setter
		void setCatvm(PartnerprofilProjektTreeView catvm) {
			this.catvm = catvm;
		}

		/*
		 * Wenn der anzuzeigende Kunde gesetzt bzw. gelöscht wird, werden die
		 * zugehörenden Textfelder mit den Informationen aus dem Kundenobjekt
		 * gefüllt bzw. gelöscht.
		 */
		void setSelected(Partnerprofil c) {
			if (c != null) {
				partnerprofilToDisplay = c;
				partnerprofilstextTextBox.setText(partnerprofilToDisplay.getPartnerprofilstext());
				erstelldatumTextBox.setTitle(partnerprofilToDisplay.getErstelldatum().toString());
				idValueLabel.setText(Integer.toString(partnerprofilToDisplay.getId()));
			} else {
				partnerprofilstextTextBox.setText("");
				erstelldatumTextBox.setTitle("");
				idValueLabel.setText("");
			}
		}
		
	
	}
