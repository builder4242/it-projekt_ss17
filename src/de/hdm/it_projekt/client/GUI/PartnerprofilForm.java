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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;

import de.hdm.it_projekt.shared.bo.Partnerprofil;

public class PartnerprofilForm extends Showcase {

	Partnerprofil ppToDisplay = null;
	ProjektTreeViewModel ptvm = null;

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	Label formTitel = new Label();
	DateBox erstellDb = new DateBox();
	DateBox aenderungDb = new DateBox();

	public PartnerprofilForm() {

		formTitel.setText("Partnerprofil");
		this.add(formTitel);

		Grid form = new Grid(2, 2);
		this.add(form);

		form.setWidget(0, 0, new Label("Erstellt"));
		form.setWidget(0, 1, erstellDb);
		erstellDb.setFormat(new DateBox.DefaultFormat(fmt));
		erstellDb.setEnabled(false);

		form.setWidget(1, 0, new Label("letzte Änderung:"));
		form.setWidget(1, 1, aenderungDb);
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

	void setProjektTreeViewModel(ProjektTreeViewModel ptvm) {
		this.ptvm = ptvm;
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (ppToDisplay != null) {
				pa.delete(ppToDisplay, new DeletePartnerprofilCallback(ppToDisplay));
			} else {
				Window.alert("Es wurde nichts ausgewählt.");
			}
		}
	}

	class DeletePartnerprofilCallback implements AsyncCallback<Void> {

		Partnerprofil partnerprofil = null;

		public DeletePartnerprofilCallback(Partnerprofil pp) {
			this.partnerprofil = pp;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			if (partnerprofil != null) {
				setSelected(null);
				ptvm.removePartnerprofil(ppToDisplay);
			}
		}
	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (ptvm.getSelectedAusschreibung().getPartnerprofilId() != 0)
				Window.alert("Es existiert schon ein Partnerprofil.");
			else
				pa.createPartnerprofilFor(ptvm.getSelectedAusschreibung(), new CreatePartnerprofilCallback());
		}
	}

	class CreatePartnerprofilCallback implements AsyncCallback<Partnerprofil> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Anlegen fehlgeschlagen");

		}

		@Override
		public void onSuccess(Partnerprofil partnerprofil) {
			ptvm.addPartnerprofil(partnerprofil);

		}
	}
}
