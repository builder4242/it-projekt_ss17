package de.hdm.it_projekt.client.GUI_alt;

/*
 * To be Done:
 * callback in create fehlt
 */
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

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
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.server.ProjektAdministrationImpl;

/**
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Julian Reimenthal
 */
public class AusschreibungForm extends VerticalPanel {

	ProjektAdministrationAsync projektVerwaltung = ClientsideSettings // waiting
																		// for
																		// Classes
			.getProjektAdministration();
	Ausschreibung ausschreibungToDisplay = null;
	ProjektAusschreibungTreeView catvm = null;

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	TextBox bezeichnungTextBox = new TextBox();
	DateBox bewerbungsfristTextBox = new DateBox();
	TextBox ausschreibungstextTextBox = new TextBox();
	TextBox partnerprofilTextBox = new TextBox();
	TextBox projektTextBox = new TextBox();
	Label idValueLabel = new Label();

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public AusschreibungForm() {
		Grid ausschreibungGrid = new Grid(6, 2);
		this.add(ausschreibungGrid);

		Label idLabel = new Label("ID");
		ausschreibungGrid.setWidget(0, 0, idLabel);
		ausschreibungGrid.setWidget(0, 1, idValueLabel);

		Label bezeichnungLabel = new Label("Bezeichnung");
		ausschreibungGrid.setWidget(1, 0, bezeichnungLabel);
		ausschreibungGrid.setWidget(1, 1, bezeichnungLabel);

		Label bewerbungsfristLabel = new Label("Bewerbungsfrist");
		ausschreibungGrid.setWidget(2, 0, bewerbungsfristLabel);
		ausschreibungGrid.setWidget(2, 1, bewerbungsfristLabel);

		Label ausschreibungstextLabel = new Label("Ausschreibungstext");
		ausschreibungGrid.setWidget(3, 0, ausschreibungstextLabel);
		ausschreibungGrid.setWidget(3, 1, ausschreibungstextLabel);

		Label partnerprofilLabel = new Label("Partnerprofil");
		ausschreibungGrid.setWidget(4, 0, partnerprofilLabel);
		ausschreibungGrid.setWidget(4, 1, partnerprofilLabel);

		Label projektLabel = new Label("Projekt");
		ausschreibungGrid.setWidget(5, 0, projektLabel);
		ausschreibungGrid.setWidget(5, 1, projektLabel);

		HorizontalPanel ausschreibungButtonsPanel = new HorizontalPanel();
		this.add(ausschreibungButtonsPanel);

		Button changeButton = new Button("Ändern");
		changeButton.addClickHandler(new ChangeClickHandler());
		ausschreibungButtonsPanel.add(changeButton);

		Button searchButton = new Button("Suchen");
		ausschreibungButtonsPanel.add(searchButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.addClickHandler(new DeleteClickHandler());
		ausschreibungButtonsPanel.add(deleteButton);

		Button newButton = new Button("Neu");
		newButton.addClickHandler(new NewClickHandler());
		ausschreibungButtonsPanel.add(newButton);
	}

	/*
	 * Click Handlers.
	 */

	/**
	 * Die Änderung einer Ausschreibung.
	 * 
	 */
	private class ChangeClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (ausschreibungToDisplay != null) {
				ausschreibungToDisplay.setAusschreibungstext(ausschreibungstextTextBox.getText());
				ausschreibungToDisplay.setBezeichnung(bezeichnungTextBox.getText());
				ausschreibungToDisplay.setBewerbungsfrist(bewerbungsfristTextBox.getValue());

				/**
				 * Konvertierung von String der Projekt ID zu Int
				 */

				int i = Integer.parseInt(bezeichnungTextBox.getText());
				ausschreibungToDisplay.setProjektId(i);

				/**
				 * Konvertierung von String der Partnerprofil ID zu Int
				 */

				int s = Integer.parseInt(partnerprofilTextBox.getText());
				ausschreibungToDisplay.setPartnerprofilId(i);

				projektVerwaltung.save(ausschreibungToDisplay, new SaveCallback());

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
			catvm.updateAusschreibung(ausschreibungToDisplay);
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
			if (ausschreibungToDisplay == null) {
				Window.alert("kein Kunde ausgewählt");
			} else {
				projektVerwaltung.delete(ausschreibungToDisplay,
						new deleteAusschreibungCallback(ausschreibungToDisplay));
			}
		}
	}

	class deleteAusschreibungCallback implements AsyncCallback<Void> {

		Ausschreibung ausschreibung = null;

		deleteAusschreibungCallback(Ausschreibung c) {
			ausschreibung = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Das Löschen des Kunden ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Void result) {
			if (ausschreibung != null) {
				setSelected(null);
				catvm.removeAusschreibung(ausschreibung);
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
			String ausschreibungstext = ausschreibungstextTextBox.getText();
			Projekt pr = projektVerwaltung.getProjektByName(projektTextBox.getText(), callback);
			Date bewerbungsfrist = bewerbungsfristTextBox.getValue();
			int i = Integer.parseInt(partnerprofilTextBox.getText());
			Partnerprofil profil = projektVerwaltung.getPartnerprofilById(i, callback);

			projektVerwaltung.createAusschreibungFor(pr, bezeichnung, bewerbungsfrist, ausschreibungstext, profil,
					new CreateAusschreibungCallback());
		}
	}

	class CreateAusschreibungCallback implements AsyncCallback<Ausschreibung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Das Anlegen eines neuen Kunden ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Ausschreibung ausschreibung) {
			if (ausschreibung != null) {
				// Das erfolgreiche Hinzufügen eines Kunden wird an den Kunden-
				// und
				// Kontenbaum propagiert.
				catvm.updateAusschreibung(ausschreibung);
			}
		}
	}

	// catvm setter
	void setCatvm(ProjektAusschreibungTreeView catvm) {
		this.catvm = catvm;
	}

	/*
	 * Wenn der anzuzeigende Kunde gesetzt bzw. gelöscht wird, werden die
	 * zugehörenden Textfelder mit den Informationen aus dem Kundenobjekt
	 * gefüllt bzw. gelöscht.
	 */
	void setSelected(Ausschreibung c) {
		if (c != null) {
			ausschreibungToDisplay = c;
			bezeichnungTextBox.setText(ausschreibungToDisplay.getBezeichnung());
			ausschreibungstextTextBox.setText(ausschreibungToDisplay.getAusschreibungstext());
			String s = projektVerwaltung.getProjektById(id, callback).toString();
			projektTextBox.setText(s);
			
			idValueLabel.setText(Integer.toString(ausschreibungToDisplay.getId()));
		} else {
			bezeichnungTextBox.setText("");
			ausschreibungstextTextBox.setText("");
			idValueLabel.setText("");
		}
	}
	
	Projekt pr = projektVerwaltung.getProjektByName(projektTextBox.getText(), callback);
	Date bewerbungsfrist = bewerbungsfristTextBox.getValue();
	int i = Integer.parseInt(partnerprofilTextBox.getText());
	Partnerprofil profil = projektVerwaltung.getPartnerprofilById(i, callback);

}
