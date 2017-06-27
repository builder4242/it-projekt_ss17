package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;

public class BeteiligungForm extends Showcase {

	Beteiligung btToDisplay = null;
	ProjektBeteiligungListView pblv = null;

	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");
	Label formTitel = new Label();
	
	IntegerBox pTageIb = new IntegerBox();
	DateBox startDb = new DateBox();
	DateBox endDb = new DateBox();
	Label beteiligterLb = new Label();

	public BeteiligungForm(boolean ausschreibender) {

		formTitel.setText("Beteiligung");
		formTitel.setStyleName("h1");
		this.add(formTitel);

		Grid form = new Grid(4, 2);
		form.addStyleName("myprojekt-formlabel");
		this.add(form);

		form.setWidget(0, 0, new Label("Beteiligter"));
		form.setWidget(0, 1, beteiligterLb);
		beteiligterLb.setStyleName("myproject-textfield");

		form.setWidget(1, 0, new Label("Personentage"));
		form.setWidget(1, 1, pTageIb);
		pTageIb.setStyleName("myproject-textfield");

		form.setWidget(2, 0, new Label("Startdatum"));
		form.setWidget(2, 1, startDb);
		startDb.setStyleName("myproject-textfield");
		startDb.setFormat(new DateBox.DefaultFormat(fmt));

		form.setWidget(3, 0, new Label("Enddatum"));
		form.setWidget(3, 1, endDb);
		endDb.setStyleName("myproject-textfield");
		endDb.setFormat(new DateBox.DefaultFormat(fmt));

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

		if(ausschreibender == false) {
			pTageIb.setEnabled(false);
			startDb.setEnabled(false);
			endDb.setEnabled(false);
			buttonsPanel.setVisible(false);
		}
	}

	void setSelected(Beteiligung bt) {

		if (bt != null) {
			btToDisplay = bt;
			pTageIb.setValue(btToDisplay.getPersonentage());
			startDb.setValue(btToDisplay.getStartdatum());
			endDb.setValue(btToDisplay.getEnddatum());
			
			pa.getBeteiligterFor(btToDisplay, new AsyncCallback<Organisationseinheit>() {

				@Override
				public void onSuccess(Organisationseinheit result) {
					if (result != null) {
						if (result instanceof Person) {
							Person p = (Person) result;
							beteiligterLb.setText(p.getName() + ", " + p.getVorname());
						} else {
							beteiligterLb.setText(result.getName());
						}
					}
				}

				@Override
				public void onFailure(Throwable caught) {
				}
			});
		} else {
			pTageIb.setValue(null);
			startDb.setValue(null);
			endDb.setValue(null);
			beteiligterLb.setText("");
		}
	}

	void setProjektBeteiligungListView(ProjektBeteiligungListView pblv) {
		this.pblv = pblv;
	}

	private class ChangeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (btToDisplay != null) {
				btToDisplay.setPersonentage(pTageIb.getValue());
				btToDisplay.setStartdatum(startDb.getValue());
				btToDisplay.setEnddatum(endDb.getValue());

				pa.save(btToDisplay, new SaveCallback());
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
			pblv.updateBeteiligung(btToDisplay);
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (btToDisplay != null) {
				pa.delete(btToDisplay, new DeleteBeteiligungCallback(btToDisplay));
			} else {
				Window.alert("Es wurde nichts ausgewählt.");
			}
		}
	}

	class DeleteBeteiligungCallback implements AsyncCallback<Void> {

		Beteiligung beteiligung = null;
		
		public DeleteBeteiligungCallback(Beteiligung bt) {
			beteiligung = bt;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			if (beteiligung != null) {
				setSelected(null);
				pblv.removeBeteiligung(beteiligung);
			}
		}
	}
}
