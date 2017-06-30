/** Die Klasse ProjektForm dient dem Aufbau und der interaktion mit 
 * dem Formular "Projekt" unter "Meine Ausschreibungen" in der GUI
 * die Klasse stellt ein Textfeld, eine Text Area sowei drei Buttons bereit. 
 * Desweiteren gibt es ein Label für den Projektleiter das automatisch gesetzt
 * wird, da dieser automatisch gesetzt wird. Die Anordung wird über ein Grid gelöst. 
 * Die drei ClickHandler für die drei Buttons regeln, was beim Drücken eines Buttons 
 * passiert. Die Optik von Lables, Textfeltern und Buttons wird durch das Einbinden von 
 * CSS umgestzt.   */
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;

public class ProjektForm extends Showcase {

	private Projekt prToDisplay = null;
	private ProjektTreeViewModel ptvm = null;

	private DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	private Label formTitel = new Label();
	/** Label deklarieren */
	private TextBox nameTb = new TextBox();
	/** TextBox deklarieren */
	private DateBox startDb = new DateBox();
	/** DateBox (Date picker) deklarieren */
	private DateBox endDb = new DateBox();
	/** DateBox (Date picker) deklarieren */

	private Label projektLeiterL = new Label("nicht angelegt");
	/** Label deklarieren und Text hinzugefügt */
	private TextArea beschreibungTb = new TextArea();
	/** TextArea deklariert */

	private HorizontalPanel buttonsOPanel = new HorizontalPanel();

	public ProjektForm(boolean ausschreibender) {

		formTitel.setText("Projekt"); /** Form Titel festgelegt */
		formTitel.setStyleName("h1"); /** CSS verknüpft */
		this.add(formTitel);

		Grid form = new Grid(5,
				2); /** Gird zum anordnen der Elemente erstellen */
		form.addStyleName("myprojekt-formlabel"); /** CSS auf Grid anwenden */
		this.add(form);

		/** Formular wird im Grid aufgebaut */
		form.setWidget(0, 0, new Label("Name"));
		form.setWidget(0, 1, nameTb);
		nameTb.setStyleName("myproject-textfield");

		form.setWidget(1, 0, new Label("Startdatum"));
		form.setWidget(1, 1, startDb);
		startDb.setStyleName("myproject-textfield");
		startDb.setFormat(new DateBox.DefaultFormat(fmt));

		form.setWidget(2, 0, new Label("Enddatum"));
		form.setWidget(2, 1, endDb);
		endDb.setStyleName("myproject-textfield");
		endDb.setFormat(new DateBox.DefaultFormat(fmt));

		form.setWidget(3, 0, new Label("Beschreibung"));
		form.setWidget(3, 1, beschreibungTb);
		beschreibungTb.setStyleName("myprojekt-textarea");

		form.setWidget(4, 0, new Label("Projektleiter"));
		form.setWidget(4, 1, projektLeiterL);
		projektLeiterL.setStyleName("myprojekt-formlabel2");

		HorizontalPanel buttonsPanel = new HorizontalPanel(); /**
																 * neues
																 * HorizontalPanel
																 * für Buttons
																 */
		this.add(buttonsPanel);
		buttonsPanel.addStyleName("myprojekt-buttonspanel");

		Button changeButton = new Button("Ändern");
		changeButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		changeButton.addClickHandler(
				new ChangeClickHandler()); /**
											 * Click Handler für Button erstellen
											 */
		buttonsPanel.add(changeButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(
				new DeleteClickHandler()); /**
											 * Click Handler für Button erstellen
											 */
		buttonsPanel.add(deleteButton);

		Button newButton = new Button("Anlegen");
		newButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(
				new NewClickHandler()); /**
										 * Click Handler für Button erstellen
										 */
		buttonsPanel.add(newButton);

		this.add(buttonsOPanel);
		buttonsOPanel.setVisible(false);

		Button newAusschreibung = new Button("Ausschreibung anlegen");
		newAusschreibung.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newAusschreibung.addClickHandler(
				new NewAusschreibungClickHandler()); /**
														 * Click Handler für Button
														 * erstellen
														 */
		buttonsOPanel.add(newAusschreibung);

		Button showBeteiligung = new Button("Beteiligungen ansehen");
		showBeteiligung.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		showBeteiligung.addClickHandler(
				new ShowBeteiligungenClickHandler()); /**
														 * Click Handler für
														 * Button erstellen
														 */
		buttonsOPanel.add(showBeteiligung);

		if (ausschreibender == false) {
			nameTb.setEnabled(false);
			startDb.setEnabled(false);
			endDb.setEnabled(false);
			beschreibungTb.setEnabled(false);
			buttonsPanel.setVisible(false);
			newAusschreibung.setVisible(false);
		}
	}

	void setSelected(Projekt pr) {

		if (pr != null) {
			this.prToDisplay = pr;
			nameTb.setText(pr.getName());
			startDb.setValue(pr.getStartdatum());
			endDb.setValue(pr.getEnddatum());
			beschreibungTb.setText(pr.getBeschreibung());
			buttonsOPanel.setVisible(true);

			pa.getOrganisationseinheitById(prToDisplay.getProjektleiterId(), new AsyncCallback<Organisationseinheit>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Organisationseinheit result) {
					if (result instanceof Person)
						projektLeiterL.setText(result.getName() + ", " + ((Person) result).getVorname());
					else
						projektLeiterL.setText(result.getName());
				}
			});

		} else {
			nameTb.setText("");
			startDb.setValue(null);
			endDb.setValue(null);
			beschreibungTb.setText("");
			projektLeiterL.setText("");
			buttonsOPanel.setVisible(false);
		}
	}

	void setProjektTreeViewModel(ProjektTreeViewModel ptvm) {
		this.ptvm = ptvm;
	}

	private class NewAusschreibungClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ptvm.projektForm.setVisible(false);
			ptvm.ausschreibungForm.setVisible(true);
		}

	}

	private class ShowBeteiligungenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ptvm.projektForm.setVisible(false);
			ptvm.showBeteiligungForm();
		}

	}

	private class ChangeClickHandler implements ClickHandler {

		/**
		 * Wird beim Klick auf den Ändern Button ausgeführt und ändert die Daten
		 * im TreeView und in der Datenbank. Dafür werden Create-, Set- und
		 * Get-Methoden verwendet, sowie AsyncCallbacks erstellt.
		 */
		@Override
		public void onClick(ClickEvent event) {

			if (prToDisplay != null) {
				prToDisplay.setName(nameTb.getText());
				prToDisplay.setStartdatum(startDb.getValue());
				prToDisplay.setEnddatum(endDb.getValue());
				prToDisplay.setBeschreibung(beschreibungTb.getText());

				pa.save(prToDisplay, new SaveCallback());
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
			ptvm.updateProjekt(prToDisplay);
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			/** wird beim Klick auf den Button "Löschen" ausgeführt */
			if (prToDisplay != null) {
				pa.delete(prToDisplay, new DeleteProjektCallback(prToDisplay));
			} else {
				Window.alert(
						"Es wurde nichts ausgewählt."); /**
														 * Fehlermeldung wird
														 * als Popup ausgegen
														 */
			}
		}
	}

	class DeleteProjektCallback implements AsyncCallback<Void> {

		Projekt projekt = null;

		public DeleteProjektCallback(Projekt pr) {
			this.projekt = pr;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			if (projekt != null) {
				setSelected(null);
				ptvm.removeProjekt(prToDisplay);
			}
		}
	}

	private class NewClickHandler implements ClickHandler {

		/** wird beim Klick auf den Button "Neu" ausgeführt */
		@Override
		public void onClick(ClickEvent event) {

			pa.createProjektFor(MyProjekt.cpm, nameTb.getText(), startDb.getValue(), endDb.getValue(),
					beschreibungTb.getText(), MyProjekt.loginInfo.getCurrentUser(), new CreateProjektCallback());
		}
	}

	class CreateProjektCallback implements AsyncCallback<Projekt> {

		/** wird beim Klick auf den Button "Anlegen" ausgeführt */
		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Anlegen fehlgeschlagen");

		}

		@Override
		public void onSuccess(Projekt projekt) {
			ptvm.addProjekt(projekt);

		}
	}
}
