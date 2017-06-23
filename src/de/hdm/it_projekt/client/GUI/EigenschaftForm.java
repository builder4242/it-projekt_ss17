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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.it_projekt.shared.bo.Eigenschaft;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

public class EigenschaftForm extends Showcase {

	Eigenschaft eToDisplay = null;
	ProjektTreeViewModel ptvm = null;

	Label formTitel = new Label();
	TextBox nameTb = new TextBox();
	TextBox wertTb = new TextBox();

	public EigenschaftForm() {

		formTitel.setText("Eigenschaft");
		formTitel.setStyleName("h1");
		this.add(formTitel);

		Grid form = new Grid(2, 2);
		form.addStyleName("myprojekt-formlabel");
		this.add(form);

		form.setWidget(0, 0, new Label("Name"));
		form.setWidget(0, 1, nameTb);
		nameTb.setStyleName("myproject-textfield");

		form.setWidget(1, 0, new Label("Wert"));
		form.setWidget(1, 1, wertTb);
		wertTb.setStyleName("myproject-textfield");

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

	void setSelected(Eigenschaft e) {

		if (e != null) {
			this.eToDisplay = e;
			nameTb.setText(eToDisplay.getName());
			wertTb.setText(eToDisplay.getWert());
		} else {
			nameTb.setText("");
			wertTb.setText("");
		}
	}
	
	void setProjektTreeViewModel(ProjektTreeViewModel ptvm) {
		this.ptvm = ptvm;
	}

	private class ChangeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (eToDisplay != null) {
				eToDisplay.setName(nameTb.getText());
				eToDisplay.setWert(wertTb.getText());

				pa.save(eToDisplay, new SaveEigenschaftCallback());
			} else
				Window.alert("Es wurde nichts ausgewählt");
		}
	}
	
	class SaveEigenschaftCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");

		}

		@Override
		public void onSuccess(Void result) {
			ptvm.updateEigenschaft(eToDisplay);						
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(eToDisplay != null)
				pa.delete(eToDisplay, new DeleteEigenschaftCallback(eToDisplay, ptvm.getSelectedPartnerprofil()));
			else
				Window.alert("Es wurde nichts ausgewählt.");
		}
	}
	
	class DeleteEigenschaftCallback implements AsyncCallback<Void> {

		Eigenschaft eigenschaft = null;
		Partnerprofil partnerprofil = null;
		
		public DeleteEigenschaftCallback(Eigenschaft e, Partnerprofil pp) {
			eigenschaft = e;
			partnerprofil = pp;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");					
		}

		@Override
		public void onSuccess(Void result) {

			if(eigenschaft != null && partnerprofil != null) {
				setSelected(null);
				ptvm.removeEigenschaftForPartnerprofil(eigenschaft, partnerprofil);
			}
		}
	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			pa.createEigenschaftFor(ptvm.getSelectedPartnerprofil(), nameTb.getText(), wertTb.getText(), new CreateEigenschaftCallback(ptvm.getSelectedPartnerprofil()));
		}
	}
	
	class CreateEigenschaftCallback implements AsyncCallback<Eigenschaft> {

		Partnerprofil partnerprofil = null;
		
		public CreateEigenschaftCallback(Partnerprofil pp) {
			partnerprofil = pp;
		}
		
		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Anlegen fehlgeschlagen");
		}

		@Override
		public void onSuccess(Eigenschaft eigenschaft) {
			ptvm.addEigenschaftForPartnerprofil(eigenschaft, partnerprofil);
		}
	}
}
