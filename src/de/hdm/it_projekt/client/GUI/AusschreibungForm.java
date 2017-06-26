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

	Ausschreibung asToDisplay = null;
	ProjektTreeViewModel ptvm = null;

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	Label formTitel = new Label();
	TextBox bezeichnungTb = new TextBox();
	DateBox fristDb = new DateBox();
	TextArea astextgTb = new TextArea();

	public AusschreibungForm() {

		formTitel.setText("Ausschreibung");
		formTitel.setStyleName("h1");
		this.add(formTitel);

		Grid form = new Grid(3, 2);
		form.addStyleName("myprojekt-formlabel");
		this.add(form);

		form.setWidget(0, 0, new Label("Bezeichnung"));
		form.setWidget(0, 1, bezeichnungTb);
		bezeichnungTb.setStyleName("myproject-textfield");

		form.setWidget(1, 0, new Label("Bewerbungsfrist"));
		form.setWidget(1, 1, fristDb);
		fristDb.setStyleName("myproject-textfield");
		fristDb.setFormat(new DateBox.DefaultFormat(fmt));

		form.setWidget(2, 0, new Label("Ausschreibungstext"));
		form.setWidget(2, 1, astextgTb);
		astextgTb.setStyleName("myprojekt-textarea");

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		this.add(buttonsPanel);

		Button changeButton = new Button("Ändern");
		changeButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		changeButton.addClickHandler(new ChangeClickHandler());
		buttonsPanel.add(changeButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		buttonsPanel.add(deleteButton);

		Button newButton = new Button("Neu");
		newButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(new NewClickHandler());
		buttonsPanel.add(newButton);
		buttonsPanel.addStyleName("myprojekt-buttonspanel");

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
