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

import de.hdm.it_projekt.shared.bo.Projekt;

public class ProjektForm extends Showcase {

	Projekt prToDisplay = null;
	ProjektTreeViewModel ptvm = null;

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");
	
	Label formTitel = new Label();
	TextBox nameTb = new TextBox();
	DateBox startDb = new DateBox();
	DateBox endDb = new DateBox();
	TextBox beschreibungTb = new TextBox();

	public ProjektForm() {
		
		formTitel.setText("Projekt");
		this.add(formTitel);
		
		Grid form = new Grid(4, 2);
		this.add(form);

		form.setWidget(0, 0, new Label("Name"));
		form.setWidget(0, 1, nameTb);

		form.setWidget(1, 0, new Label("Startdatum"));
		form.setWidget(1, 1, startDb);
		startDb.setFormat(new DateBox.DefaultFormat(fmt));
		
		form.setWidget(2, 0, new Label("Enddatum"));
		form.setWidget(2, 1, endDb);
		endDb.setFormat(new DateBox.DefaultFormat(fmt));

		form.setWidget(3, 0, new Label("Beschreibung"));
		form.setWidget(3, 1, beschreibungTb);

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		this.add(buttonsPanel);

		Button changeButton = new Button("Ändern");
		changeButton.addClickHandler(new ChangeClickHandler());
		buttonsPanel.add(changeButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.addClickHandler(new DeleteClickHandler());
		buttonsPanel.add(deleteButton);

		Button newButton = new Button("Neu");
		newButton.addClickHandler(new NewClickHandler());
		buttonsPanel.add(newButton);

	}

	void setSelected(Projekt pr) {

		if (pr != null) {
			this.prToDisplay = pr;
			nameTb.setText(pr.getName());
			startDb.setValue(pr.getStartdatum());
			endDb.setValue(pr.getEnddatum());
			beschreibungTb.setText(pr.getBeschreibung());
		} else {
			nameTb.setText("");
			startDb.setValue(null);
			endDb.setValue(null);
			beschreibungTb.setText("");
		}
	}

	void setProjektTreeViewModel(ProjektTreeViewModel ptvm) {
		this.ptvm = ptvm;
	}

	private class ChangeClickHandler implements ClickHandler {

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

			if (prToDisplay != null) {
				pa.delete(prToDisplay, new DeleteProjektCallback(prToDisplay));
			} else {
				Window.alert("Es wurde nichts ausgewählt.");
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

		@Override
		public void onClick(ClickEvent event) {

			pa.createProjektFor(MyProjekt.cpm, nameTb.getText(), startDb.getValue(), endDb.getValue(), beschreibungTb.getText(), new CreateProjektCallback());
		}
	}
	
	class CreateProjektCallback implements AsyncCallback<Projekt> {

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
