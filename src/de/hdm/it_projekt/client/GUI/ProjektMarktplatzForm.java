/** Die Klasse NewProjektMarktplatzForm dient dem Aufbau und der Interaktion mit dem Formular zum Anlegen 
 * eines neuen Marktplatzes auf der Startseite "Projektmarktplätze" der GUI. Es wird eine TextBox für die 
 * Bezeichung einens neuen Marktplatzes sowie ein Button mit zugeörigem ClickHandler zum Anlegen bereitgestellt.
 * Die Optik wird durch das Einbinden von CSS angepasst. 
 * */
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class ProjektMarktplatzForm extends Showcase {

	private ProjektMarktplatz pmToDisplay = null;

	private Marktuebersicht pmForm = null;
	private Label formTitel = new Label();
	/** Label deklarieren */
	private TextBox bezeichnungTextBox = new TextBox();

	
	public ProjektMarktplatzForm() {

		formTitel.setText("ProjektMarktplatz"); /** Form Titel festgelegt */
		formTitel.setStyleName("h1"); /** CSS verknüpft */
		this.add(formTitel);

		HorizontalPanel formP = new HorizontalPanel();
		this.add(formP);
		Label pmLabel = new Label("Bezeichnung");
		pmLabel.setStyleName("myprojekt-bezichungmarktplatzlabel");
		formP.add(pmLabel);
		formP.add(bezeichnungTextBox);
		bezeichnungTextBox.setStyleName("myproject-anlegentextfield");

		Button changeButton = new Button("Speichern");
		changeButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		changeButton
				.addClickHandler(new ChangeClickHandler()); /**
															 * Click Handler für
															 * Button erstellen
															 */
		this.add(changeButton);

	}

	void setSelected(ProjektMarktplatz pm) {
		if (pm != null) {
			this.pmToDisplay = pm;
			bezeichnungTextBox.setText(pm.getBezeichnung());
		} else {
			pmToDisplay = null;
			bezeichnungTextBox.setText("");
		}
	}

	void setMarktplatzForm(Marktuebersicht mF) {
		this.pmForm = mF;
	}

	private class ChangeClickHandler implements ClickHandler {

		/**
		 * Wird beim Klick auf den Ändern Button ausgeführt und ändert die Daten im TreeView und in der Datenbank.
		 * Dafür werden Create-, Set- und Get-Methoden verwendet, sowie AsyncCallbacks erstellt. 
		 */
		@Override
		public void onClick(ClickEvent event) {

			if (pmToDisplay == null) {
				pa.createProjektMarktplatz(bezeichnungTextBox.getText(), MyProjekt.loginInfo.getCurrentUser().getId(),
						new CreatePmCallback());
			} else {
				pmToDisplay.setBezeichnung(bezeichnungTextBox.getText());
				pa.save(pmToDisplay, new SavePmCallback());
			}
		}
	}

	private class CreatePmCallback implements AsyncCallback<ProjektMarktplatz> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(ProjektMarktplatz result) {

			RootPanel.get("content").clear();
			RootPanel.get("content").add(new Marktuebersicht());
		}
	}

	private class SavePmCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");

		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("content").clear();
			RootPanel.get("content").add(new Marktuebersicht());
		}

	}
}
