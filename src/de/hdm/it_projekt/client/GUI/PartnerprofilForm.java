package de.hdm.it_projekt.client.GUI;

/** Die Klasse PartnerprofilForm dient dem Aufbau und der interaktion mit 
 * dem Formular "Partnerprofil" unter "Eigenes Profil verwalten" in der GUI
 * die Klasse stellt zwei Textfelder sowei drei Buttons bereit. 
 * Desweiteren gibt es Labeles für das Anlegedatum sowie das Datum der letzten
 * Änderung, diese werden automatisch gesetzt.
 * Die Anordung wird über ein Grid gelöst. Die drei ClickHandler für die drei Buttons regeln,
 * was beim Drücken eines Buttons passiert.
 *  Die Optik von Lables, Textfeltern und Buttons wird durch das Einbinden von 
 * CSS umgestzt.   */ 

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

public class PartnerprofilForm extends Showcase {

	Partnerprofil ppToDisplay = null;
	PartnerprofilTreeViewModel pptvm = null;

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	Label formTitel = new Label();
	DateBox erstellDb = new DateBox();
	DateBox aenderungDb = new DateBox();

	public PartnerprofilForm(boolean ausschreibender) {

		formTitel.setText("Partnerprofil");
		formTitel.setStyleName("h1");
		this.add(formTitel);

		Grid form = new Grid(2, 2);
		form.addStyleName("myprojekt-formlabel");
		this.add(form);

		form.setWidget(0, 0, new Label("Erstellt"));
		form.setWidget(0, 1, erstellDb);
		erstellDb.setStyleName("myproject-textfield");
		erstellDb.setFormat(new DateBox.DefaultFormat(fmt));
		erstellDb.setEnabled(false);

		form.setWidget(1, 0, new Label("letzte Änderung:"));
		form.setWidget(1, 1, aenderungDb);
		aenderungDb.setStyleName("myproject-textfield");
		aenderungDb.setFormat(new DateBox.DefaultFormat(fmt));
		aenderungDb.setEnabled(false);
		
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		this.add(buttonsPanel);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		buttonsPanel.add(deleteButton);

		Button newButton = new Button("Neu");
		newButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(new NewClickHandler());
		buttonsPanel.add(newButton);
		buttonsPanel.addStyleName("myprojekt-buttonspanel");
		
		if(ausschreibender == false) {
			buttonsPanel.setVisible(false);
		}

	}

	void setSelected(Partnerprofil pp) {

		if (pp != null) {
			this.ppToDisplay = pp;
			erstellDb.setValue(ppToDisplay.getErstelldatum());
			aenderungDb.setValue(ppToDisplay.getAenderungsdatum());
		} else {
			erstellDb.setValue(null);
			aenderungDb.setValue(null);
		}
	}

	void setPartnerprofilTreeViewModel(PartnerprofilTreeViewModel pptvm) {
		this.pptvm = pptvm;
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (ppToDisplay != null) {
				pa.delete(ppToDisplay, new DeletePartnerprofilCallback(ppToDisplay, pptvm.getSelectedAusschreibung()));
			} else {
				Window.alert("Es wurde nichts ausgewählt.");
			}
		}
	}

	class DeletePartnerprofilCallback implements AsyncCallback<Void> {

		Partnerprofil partnerprofil = null;
		Ausschreibung ausschreibung = null;

		public DeletePartnerprofilCallback(Partnerprofil pp, Ausschreibung as) {
			partnerprofil = pp;
			ausschreibung = as;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			if (partnerprofil != null && ausschreibung != null) {
				setSelected(null);
				pptvm.removePartnerprofilForAusschreibung(ppToDisplay, ausschreibung);
			}
		}
	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (pptvm.getSelectedAusschreibung().getPartnerprofilId() != 0)
				Window.alert("Es existiert schon ein Partnerprofil.");
			else
				pa.createPartnerprofilFor(pptvm.getSelectedAusschreibung(), new CreatePartnerprofilCallback());
		}
	}

	class CreatePartnerprofilCallback implements AsyncCallback<Partnerprofil> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Anlegen fehlgeschlagen");
		}

		@Override
		public void onSuccess(Partnerprofil partnerprofil) {
			pptvm.addPartnerprofilForAusschreibung(partnerprofil);
		}
	}
}
