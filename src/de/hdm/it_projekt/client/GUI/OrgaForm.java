/**
 * 
 */
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Team;
import de.hdm.it_projekt.shared.bo.Unternehmen;

/**
 * @author Daniel Fleps
 *
 */
public class OrgaForm extends Showcase {

	private Organisationseinheit oToDisplay = null;

	HorizontalPanel hPanel = null;
	VerticalPanel oPanel = null;
	VerticalPanel pPanel = null;
	
	Label firstNameLabel = new Label("Vorname");
	Label lastNameLabel = new Label("Nachname");
	Label typL = new Label();
	private TextBox firstNameTextBox = new TextBox();
	private TextBox lastNameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	private TextBox telTextBox = new TextBox();
	private TextBox strasseTextBox = new TextBox();
	private TextBox plzTextBox = new TextBox();
	private TextBox ortTextBox = new TextBox();

	public OrgaForm(Organisationseinheit o) {

		oToDisplay = o;
		hPanel = new HorizontalPanel();
		oPanel = new VerticalPanel();
		pPanel = new VerticalPanel();

		this.add(hPanel);
		hPanel.add(oPanel);
		
		hPanel.add(pPanel);
		
		Label textL = new Label("Hier können Sie ihr Benutzerprofil bearbeiten.");
		
		oPanel.add(textL);
		oPanel.addStyleName("myprojekt-formlabel");

		Grid organisationseinheitGrid = new Grid(9, 2);
		oPanel.add(organisationseinheitGrid);

		organisationseinheitGrid.setWidget(0, 0, new Label("Sie sind angemeldet als"));
		organisationseinheitGrid.setWidget(0, 1, typL);

		organisationseinheitGrid.setWidget(1, 0, firstNameLabel);
		organisationseinheitGrid.setWidget(1, 1, firstNameTextBox);

		organisationseinheitGrid.setWidget(2, 0, lastNameLabel);
		organisationseinheitGrid.setWidget(2, 1, lastNameTextBox);

		Label emailLabel = new Label("E-Mail");
		organisationseinheitGrid.setWidget(3, 0, emailLabel);
		organisationseinheitGrid.setWidget(3, 1, emailTextBox);
		emailTextBox.setEnabled(false);

		Label telLabel = new Label("Telefon");
		organisationseinheitGrid.setWidget(4, 0, telLabel);
		organisationseinheitGrid.setWidget(4, 1, telTextBox);

		Label strasseLabel = new Label("Strasse");
		organisationseinheitGrid.setWidget(5, 0, strasseLabel);
		organisationseinheitGrid.setWidget(5, 1, strasseTextBox);

		Label plzLabel = new Label("PLZ");
		organisationseinheitGrid.setWidget(6, 0, plzLabel);
		organisationseinheitGrid.setWidget(6, 1, plzTextBox);

		Label ortLabel = new Label("Ort");
		organisationseinheitGrid.setWidget(7, 0, ortLabel);
		organisationseinheitGrid.setWidget(7, 1, ortTextBox);

		HorizontalPanel customerButtonsPanel = new HorizontalPanel();
		oPanel.add(customerButtonsPanel);

		Button changeButton = new Button("Ändern");
		changeButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		changeButton.addClickHandler(new ChangeClickHandler());
		customerButtonsPanel.add(changeButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.setStyleName(
				"myprojekt-formbutton"); /** Verknüft CSS Klasse auf Button */
		deleteButton.addClickHandler(new DeleteClickHandler());
		customerButtonsPanel.add(deleteButton);
		
		setSelected();
		partnerprofilForm();
	}

	public void setSelected() {
		if(oToDisplay != null) {
			if(oToDisplay instanceof Person) {
				firstNameLabel.setVisible(true);
				firstNameTextBox.setVisible(true);
				lastNameLabel.setText("Nachname");
				typL.setText("Person");
				firstNameTextBox.setText(((Person) oToDisplay).getVorname());
			}
			if(oToDisplay instanceof Unternehmen) {
				firstNameLabel.setVisible(false);
				firstNameTextBox.setVisible(false);
				lastNameLabel.setText("Name");
				typL.setText("Unternehmen");
			}
			if(oToDisplay instanceof Team) {
				firstNameLabel.setVisible(false);
				firstNameTextBox.setVisible(false);
				lastNameLabel.setText("Name");
				typL.setText("Team");
			}
			
			lastNameTextBox.setText(oToDisplay.getName());
			emailTextBox.setText(oToDisplay.getEmail());
			telTextBox.setText(oToDisplay.getTel());
			strasseTextBox.setText(oToDisplay.getStrasse());
			plzTextBox.setText(Integer.toString(oToDisplay.getPlz()));
			ortTextBox.setText(oToDisplay.getOrt());
			
		}
	}

	private class ChangeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (oToDisplay != null) {

				oToDisplay.setName(lastNameTextBox.getText());
				oToDisplay.setTel(telTextBox.getText());
				oToDisplay.setStrasse(strasseTextBox.getText());
				oToDisplay.setPlz(Integer.parseInt(plzTextBox.getText()));
				oToDisplay.setOrt(ortTextBox.getText());

				if(oToDisplay instanceof Person) {
					((Person) oToDisplay).setVorname(firstNameTextBox.getText());
					pa.save((Person) oToDisplay, new SavePersonCallback());					
				}
				if(oToDisplay instanceof Unternehmen) {
					pa.save((Unternehmen) oToDisplay, new SaveUnternehmenCallback());
				}
				if(oToDisplay instanceof Team) {
					pa.save((Team) oToDisplay,  new SaveTeamCallback());
				}

			} else
				Window.alert("Es ist ein Fehler aufgetreten.");
		}

	}

	class SavePersonCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Änderungen gespeichert.");
		}
	}
	
	class SaveTeamCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Änderungen gespeichert.");
		}
	}
	
	class SaveUnternehmenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Änderungen gespeichert.");
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if(oToDisplay instanceof Person) {
				pa.delete((Person) oToDisplay, new DeletePersonCallback());					
			}
			if(oToDisplay instanceof Unternehmen) {
				pa.delete((Unternehmen) oToDisplay, new DeleteUnternehmenCallback());
			}
			if(oToDisplay instanceof Team) {
				pa.delete((Team) oToDisplay,  new DeleteTeamCallback());
			}
		}
	}
	
	class DeletePersonCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			deleteSession();
		}
	}
	
	class DeleteTeamCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			deleteSession();		}
	}
	
	class DeleteUnternehmenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
		}

		@Override
		public void onSuccess(Void result) {
			deleteSession();		}
	}
	
	public void deleteSession() {
		
		MyProjekt.cpm = null;
				
		Window.alert("Ihr konnto wurde gelöscht.");
		
		Window.Location.assign(MyProjekt.loginInfo.getLogoutUrl());
		
	}
	
	void partnerprofilForm() {
		
		if(MyProjekt.loginInfo.getCurrentUser().getPartnerprofilId() == 0) {
			
			pPanel.add(new Label("Es existiert noch kein Partnerprofil zu diesem Benutzer."));
			Button newProfilButton = new Button("Partnerprofil anlegen.");
			pPanel.add(newProfilButton);
			newProfilButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					pa.createPartnerprofilFor(MyProjekt.loginInfo.getCurrentUser(), new CreatePPCallback());
					
				}				
			});	
			
		} else {
			pPanel.add(new OPartnerprofilForm());
		}
	}
	
	private class CreatePPCallback implements AsyncCallback<Partnerprofil> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Es ist ein Fehler aufgetreten.");
			
		}

		@Override
		public void onSuccess(Partnerprofil result) {
			MyProjekt.loginInfo.getCurrentUser().setPartnerprofilId(result.getId());
			pPanel.clear();
			pPanel.add(new OPartnerprofilForm());
		}
		
	}
}
