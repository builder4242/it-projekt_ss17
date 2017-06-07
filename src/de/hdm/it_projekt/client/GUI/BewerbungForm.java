package de.hdm.it_projekt.client.GUI;
/**
 * To be Done:
 * Bankverwaltung
 * ClientsideSettings
 * remove/addbewerbung
 * 
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

import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
/**
* Formular für die Darstellung der gewählten Bewerbung
* 
* @author Julian Reimenthal
*/
public class BewerbungForm extends VerticalPanel {


		BankAdministrationAsync bankVerwaltung = ClientsideSettings // waiting for Classes 
				.getBankVerwaltung();
		Bewerbung bewerbungToDisplay = null;
		AusschreibungBewerbungTreeView catvm = null;

		/*
		 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
		 */
		DateBox erstelldatumTextBox = new DateBox();
		TextBox bewerbungstextTextBox = new TextBox();
		Label idValueLabel = new Label();

		/*
		 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in
		 * einem Raster angeordnet, dessen Größe sich aus dem Platzbedarf
		 * der enthaltenen Widgets bestimmt.
		 */
		public BewerbungForm() {
			Grid bewerbungGrid = new Grid(3, 2);
			this.add(bewerbungGrid);

			Label idLabel = new Label("ID");
			bewerbungGrid.setWidget(0, 0, idLabel);
			bewerbungGrid.setWidget(0, 1, idValueLabel);

			Label bewerbungsdatumLabel = new Label("Erstelldatum");
			bewerbungGrid.setWidget(1, 0, bewerbungsdatumLabel);
			bewerbungGrid.setWidget(1, 1, bewerbungsdatumLabel);
			
			Label bewerbungstextLabel = new Label("Bewerbungstext");
			bewerbungGrid.setWidget(2, 0, bewerbungstextLabel);
			bewerbungGrid.setWidget(2, 1, bewerbungstextLabel);
			
			HorizontalPanel bewerbungButtonsPanel = new HorizontalPanel();
			this.add(bewerbungButtonsPanel);

			Button changeButton = new Button("Ändern");
			changeButton.addClickHandler(new ChangeClickHandler());
			bewerbungButtonsPanel.add(changeButton);

			Button searchButton = new Button("Suchen");
			bewerbungButtonsPanel.add(searchButton);

			Button deleteButton = new Button("Löschen");
			deleteButton.addClickHandler(new DeleteClickHandler());
			bewerbungButtonsPanel.add(deleteButton);

			Button newButton = new Button("Neu");
			newButton.addClickHandler(new NewClickHandler());
			bewerbungButtonsPanel.add(newButton);
		}

		/*
		 * Click Handlers.
		 */

		/**
		 * Die Änderung einer Bewerbung.
		 * 
		 */
		private class ChangeClickHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				if (bewerbungToDisplay != null) {
					bewerbungToDisplay.setBewerbungstext(bewerbungstextTextBox.getText());
					bewerbungToDisplay.setErstelldatum(erstelldatumTextBox.getValue());
					//bewerbungToDisplay.setEigenschaften(bezeichnungTextBox.getValue());
					bankVerwaltung.save(bewerbungToDisplay, new SaveCallback());
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
				catvm.updateBewerbung(bewerbungToDisplay);
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
				if (bewerbungToDisplay == null) {
					Window.alert("kein Kunde ausgewählt");
				} else {
					bankVerwaltung.delete(bewerbungToDisplay,
							new deleteBewerbungCallback(bewerbungToDisplay));
				}
			}
		}

		class deleteBewerbungCallback implements AsyncCallback<Void> {

			Bewerbung bewerbung = null;

			deleteBewerbungCallback(Bewerbung c) {
				bewerbung = c;
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Löschen des Kunden ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Void result) {
				if (bewerbung != null) {
					setSelected(null);
					catvm.removeBewerbung(bewerbung);
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
				String bewerbungstext = bewerbungstextTextBox.getText();
				Partnerprofil p1 = new Partnerprofil();
				p1.setEigenschaften((Eigenschaft).setName(partnerprofileigenschaftTextBox.getValue()));
				
				bankVerwaltung.createBewerbung(firstName, lastName,
						new CreateBewerbungCallback());
			}
		}

		class CreateBewerbungCallback implements AsyncCallback<Bewerbung> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Anlegen eines neuen Kunden ist fehlgeschlagen!");
			}

			@Override
			public void onSuccess(Bewerbung bewerbung) {
				if (bewerbung != null) {
					// Das erfolgreiche Hinzufügen eines Kunden wird an den Kunden- und
					// Kontenbaum propagiert.
					catvm.addBewerbung(bewerbung);
				}
			}
		}

		// catvm setter
		void setCatvm(ProjektBewerbungTreeView catvm) {
			this.catvm = catvm;
		}

		/*
		 * Wenn der anzuzeigende Kunde gesetzt bzw. gelöscht wird, werden die
		 * zugehörenden Textfelder mit den Informationen aus dem Kundenobjekt
		 * gefüllt bzw. gelöscht.
		 */
		void setSelected(Bewerbung c) {
			if (c != null) {
				bewerbungToDisplay = c;
				bewerbungstextTextBox.setText(bewerbungToDisplay.getBewerbungstext());
				erstelldatumTextBox.setTitle(bewerbungToDisplay.getErstelldatum().toString());
				idValueLabel.setText(Integer.toString(bewerbungToDisplay.getId()));
			} else {
				bewerbungstextTextBox.setText("");
				erstelldatumTextBox.setTitle("");
				idValueLabel.setText("");
			}
		}
		
	
	}
