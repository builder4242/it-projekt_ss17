/** Die Klasse BewerbungForm dient dem Aufbau und der Interaktion mit dem Formular "Bewerber"
 * auf der Seite "Meine Ausschreibungen" der GUI. Die Klasse stellt eine DateBox
 * für das Erstelldatum, ein Label für den Bewerber sowie eine TextArea für den Ausschreibungstext
 * bereit. Über den Button "Löschen" mit zugehörigem ClickHandler kann eine Bewerbung gelöscht werden.
 * Die Optik wird über das Einbinden von CSS angepasst. */
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
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;

public class BewerbungForm extends Showcase {

	private Bewerbung bwToDisplay = null;
	private ProjektTreeViewModel ptvm = null;

	private DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");

	private boolean ausschreibender; 
	private Button newButton = new Button("Bewerben");
	private Label formTitel = new Label();
	private DateBox erstellDb = new DateBox();
	private TextArea textTb = new TextArea();
	private Label bewerberLb = new Label("nicht angelegt");

	public BewerbungForm(boolean ausschreibender) {

		this.ausschreibender = ausschreibender;
		
		formTitel.setText("Bewerbung");
		formTitel.setStyleName("h1");
		this.add(formTitel);

		Grid form = new Grid(3, 2);
		form.addStyleName("myprojekt-formlabel");
		this.add(form);

		form.setWidget(0, 0, new Label("Erstelldatum"));
		form.setWidget(0, 1, erstellDb);
		erstellDb.setFormat(new DateBox.DefaultFormat(fmt));
		erstellDb.setStyleName("myproject-textfield");
		erstellDb.setEnabled(false);

		form.setWidget(1, 0, new Label("Bewerber"));
		form.setWidget(1, 1, bewerberLb);
		bewerberLb.setStyleName("myprojekt-formlabel2"); 

		form.setWidget(2, 0, new Label("Bewerbungstext"));
		form.setWidget(2, 1, textTb);
		textTb.setStyleName("myproject-textfield");
		textTb.setEnabled(false);

		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.addStyleName("myprojekt-buttonspanel");
		this.add(buttonsPanel);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		buttonsPanel.add(deleteButton);
		
				
		newButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		newButton.addClickHandler(new NewClickHandler());
		buttonsPanel.add(newButton);

		Button bewertenButton = new Button("Bewertung anlegen");
		bewertenButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		bewertenButton.addClickHandler(new BewertenClickHandler());
		buttonsPanel.add(bewertenButton);
		
		Button partnerprofilButton = new Button("Partnerprofil anzeigen");
		bewertenButton.setStyleName("myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		bewertenButton.addClickHandler(new PartnerprofilClickHandler());
		this.add(bewertenButton);
		
		if(ausschreibender == false) {
			textTb.setEnabled(true);
			bewerberLb.setText(MyProjekt.loginInfo.getCurrentUser().getName());	
			bewertenButton.setVisible(false);
			partnerprofilButton.setVisible(false);
		} else {
			newButton.setVisible(false);
		}
	}

	void setSelected(Bewerbung bw) {

		if (bw != null) {
			this.bwToDisplay = bw;
			erstellDb.setValue(bwToDisplay.getErstelldatum());
			textTb.setText(bwToDisplay.getBewerbungstext());
			newButton.setVisible(false);
			pa.getBewerberFor(bwToDisplay, new AsyncCallback<Organisationseinheit>() {

				@Override
				public void onSuccess(Organisationseinheit result) {
					if (result != null) {
						if (result instanceof Person) {
							Person p = (Person) result;
							bewerberLb.setText(p.getName() + ", " + p.getVorname());
						} else {
							bewerberLb.setText(result.getName());
						}
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});

		} else {
			erstellDb.setValue(null);
			textTb.setText("");
			bewerberLb.setText("");
			
			if(ausschreibender == false)
				newButton.setVisible(true);
		}
	}

	void setProjektTreeViewModel(ProjektTreeViewModel ptvm) {
		this.ptvm = ptvm;
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(bwToDisplay != null)
				pa.delete(bwToDisplay, new DeleteCallback(bwToDisplay, ptvm.getSelectedAusschreibung()));
			else
				Window.alert("Es wurde nichts ausgewählt.");
		}
	}
	
	private class BewertenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ptvm.bewerbungForm.setVisible(false);
			ptvm.bewertungForm.setVisible(true);
		}
		
	}
	
	class DeleteCallback implements AsyncCallback<Void> {

		Ausschreibung ausschreibung = null;
		Bewerbung bewerbung = null;
		
		public DeleteCallback(Bewerbung bw, Ausschreibung as) {
			ausschreibung = as;
			bewerbung = bw;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			if(bewerbung != null && ausschreibung != null)
				ptvm.removeBewerbungForAusschreibung(bewerbung, ausschreibung);			
		}		
	}
	
	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			Ausschreibung as = ptvm.getSelectedAusschreibung();
			
			if(as != null) {
				pa.createBewerbungFor(as, MyProjekt.loginInfo.getCurrentUser(), textTb.getText(), new CreateBewerbungCallback(as));
			} else {
				Window.alert("Es wurde keine Ausschreibung ausgewählt.");
			}
		}
	}
	
	private class CreateBewerbungCallback implements AsyncCallback<Bewerbung> {

		Ausschreibung ausschreibung = null;
		
		public CreateBewerbungCallback(Ausschreibung as) {
			this.ausschreibung = as;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Bewerbung bewerbung) {

			if(ausschreibung != null && bewerbung != null)
				ptvm.addBewerbungForAusschreibung(ausschreibung, bewerbung);
			
		}		
	}
	
	private class PartnerprofilClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			
		}
		
	}
}
