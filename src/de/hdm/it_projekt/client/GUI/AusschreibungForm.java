/** Die Klasse AusschreibungForm dient dem Aufbau und der Interaktion mit dem Formular "Ausschreibung
 * auf der Seite "Meine Ausschreibungen" in der GUI. Die Klasse beinhaltet eine TextBox für die Bezeichung
 * des Projekts, eine DateBox für die Bewerbungsfrist, sowie eine TextArea für den Ausschreibungstext.
 * Desweiteren gibt es drei Buttons mit jeweiligen ClickHandlern zum Ändern, Löschen und Anlegen. Die Optik würd 
 * über Einbinden von CSS angepasst.  */ 
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
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Projekt;

public class AusschreibungForm extends Showcase {

	private Ausschreibung asToDisplay = null;
	private ProjektTreeViewModel ptvm = null;

	private DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	private Label formTitel = new Label();				/** Label für die Formularüberschrift */ 
	private TextBox bezeichnungTb = new TextBox();		/** TextBox für die Bezeichung */ 
	private DateBox fristDb = new DateBox();			/** DateBox für die Bewerbungsfrist */ 
	private TextArea astextgTb = new TextArea();		/** TextArea für den Ausschreibungstext */ 

	public AusschreibungForm(boolean ausschreibender) {

		formTitel.setText("Ausschreibung");     /** Text für Überschrift setzen */ 
		formTitel.setStyleName("h1");			/** CSS Klasse h1 auf Überschrift anwenden */ 
		this.add(formTitel);					/** Überschrift zum Panel hinzufügen */ 

		Grid form = new Grid(3, 2);						/** Grid zur Anordung erstellen */ 
		form.addStyleName("myprojekt-formlabel");		/** CSS Klasse anwenden */ 
		this.add(form);									/** Überschrift zum Panel hinzufügen */ 

		form.setWidget(0, 0, new Label("Bezeichnung"));		/** Nues Label erstellen und positionieren */ 
		form.setWidget(0, 1, bezeichnungTb);				/** Textbox im Grid positionieren */ 
		bezeichnungTb.setStyleName("myproject-textfield");	/** CSS Klasse auf Textfeld anwenden */ 

		form.setWidget(1, 0, new Label("Bewerbungsfrist"));	/** Neues Label erstellen und positionieren */ 
		form.setWidget(1, 1, fristDb);						/** DateBox im Grif positionieren */ 
		fristDb.setStyleName("myproject-textfield");		/** CSS Klasse auf DateBox anwenden */ 
		fristDb.setFormat(new DateBox.DefaultFormat(fmt));	/** Format für DateBox festlegen */ 

		form.setWidget(2, 0, new Label("Ausschreibungstext"));	/** Neues Label erstellen und positionieren */ 
		form.setWidget(2, 1, astextgTb);						/** TextBox im Grid platzieren */ 
		astextgTb.setStyleName("myprojekt-textarea");			/** CSS Klasse auf TextBox anwenden */ 


		HorizontalPanel buttonsPanel = new HorizontalPanel();	/** HorizontalPanel für Buttons anlegen */ 

		

		this.add(buttonsPanel);
		buttonsPanel.addStyleName("myprojekt-buttonspanel");



		Button changeButton = new Button("Ändern");
		changeButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		changeButton.addClickHandler(new ChangeClickHandler());
		buttonsPanel.add(changeButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		buttonsPanel.add(deleteButton);

		Button newButton = new Button("Anlegen");
		newButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(new NewClickHandler());
		buttonsPanel.add(newButton);

		HorizontalPanel buttonOPanel = new HorizontalPanel();
		this.add(buttonOPanel);
		Button newBewerbungButton = new Button();
		newBewerbungButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newBewerbungButton.addClickHandler(new NewBewerbungClickHandler());
		buttonOPanel.add(newBewerbungButton);
		
		Button showPartnerprofilButton = new Button();
		showPartnerprofilButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		showPartnerprofilButton.addClickHandler(new showPartnerprofilClickHandler());
		buttonOPanel.add(showPartnerprofilButton);


		if(ausschreibender == false) {
			bezeichnungTb.setEnabled(false);
			fristDb.setEnabled(false);
			astextgTb.setEnabled(false);
			buttonsPanel.setVisible(false);
			showPartnerprofilButton.setText("Partnerprofil anzeigen");
			newBewerbungButton.setText("Bewerbung anlegen");
			this.add(newBewerbungButton);
		} else {
			showPartnerprofilButton.setText("Partnerprofil verwalten");
			newBewerbungButton.setText("Bewerbung anzeigen");
		}
	}

	void setSelected(Ausschreibung as) {

		if (as != null) {
			asToDisplay = as;
			bezeichnungTb.setText(as.getBezeichnung());
			fristDb.setValue(as.getBewerbungsfrist());
			astextgTb.setText(as.getAusschreibungstext());
		} else {
			bezeichnungTb.setText("");
			fristDb.setValue(null);
			astextgTb.setText("");
		}
	}

	void setProjektTreeViewModel(ProjektTreeViewModel ptvm) {
		this.ptvm = ptvm;
	}
	
	private class NewBewerbungClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ptvm.ausschreibungForm.setVisible(false);
			ptvm.bewerbungForm.setVisible(true);
		}		
	}
	
	private class showPartnerprofilClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ptvm.ausschreibungForm.setVisible(false);
			ptvm.showPartnerprofilTree();
		}
		
	}

	private class ChangeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (asToDisplay != null) {
				asToDisplay.setBezeichnung(bezeichnungTb.getText());
				asToDisplay.setBewerbungsfrist(fristDb.getValue());
				asToDisplay.setAusschreibungstext(astextgTb.getText());

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
			ptvm.updateAusschreibung(asToDisplay);
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (asToDisplay != null) {
				pa.delete(asToDisplay, new DeleteAusschreibungCallback(asToDisplay, ptvm.getSelectedProjekt()));
			} else {
				Window.alert("Es wurde nichts ausgewählt.");
			}
		}
	}

	class DeleteAusschreibungCallback implements AsyncCallback<Void> {

		Ausschreibung ausschreibung = null;
		Projekt projekt = null;
		
		public DeleteAusschreibungCallback(Ausschreibung as, Projekt pr) {
			ausschreibung = as;
			projekt = pr;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			if (ausschreibung != null && projekt != null) {
				setSelected(null);
				ptvm.removeAusschreibungForProjekt(ausschreibung, projekt);
			}
		}
	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Projekt selectedProjekt = ptvm.getSelectedProjekt();
			pa.createAusschreibungFor(selectedProjekt, bezeichnungTb.getText(), fristDb.getValue(), astextgTb.getText(), new CreateAusschreibungCallback(selectedProjekt));
		}
	}

	class CreateAusschreibungCallback implements AsyncCallback<Ausschreibung> {

		Projekt projekt = null;
		
		public CreateAusschreibungCallback(Projekt pr) {
			projekt = pr;
		}
		
		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Anlegen fehlgeschlagen");
		}

		@Override
		public void onSuccess(Ausschreibung ausschreibung) {
			if(ausschreibung != null && projekt != null)
				ptvm.addAusschreibungForProjekt(ausschreibung, projekt);

		}
	}
}
