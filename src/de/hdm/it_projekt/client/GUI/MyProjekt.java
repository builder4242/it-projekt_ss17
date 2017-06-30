/** Die Klasse MyProjekt stellt den EntryPoint dar, hier beginnt der Aufbau der GUI. 
 * Neben der Implementierung des Logins ist hier vor allem die Menüleiste zu finden,
 * die sich aus vier Buttons zusammensetzt und der Navigation zwischen den Einzelnen 
 * Seiten der GUI dient. Desweiteren ist hier der Abmeldebutton für den Logout zu finden. 
 * Die Optik wird durch das Einbinden von CSS angepasst. 
 */
package de.hdm.it_projekt.client.GUI;



import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.it_projekt.shared.LoginService;
import de.hdm.it_projekt.shared.LoginServiceAsync;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.LoginInfo;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.client.ClientsideSettings;

public class MyProjekt implements EntryPoint {

	/**
	 * Begin Attribute fuer Login
	 */
	static LoginInfo loginInfo = null;
	static ProjektMarktplatz cpm = null;


	/* Ende Attribute fuer Login */

	/**
	 * Methode welche beim Seitenaufruf geladen wird und prueft ob User
	 * eingeloggt ist. Falls ja wird Methode loadMyProjekt() aufgerufen, falls
	 * nicht die Methode loadLogin()
	 */
	public void onModuleLoad() {
		// Check login status using login service
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					loadMyProjekt();
				} else {
					LoginPanel loginPanel = new LoginPanel(loginInfo);
					loginPanel.run();
				}
			}
		});
	}

	/**
	 * Mit der Methode laodMyProjekt() wird der eigentliche Seiteninhalt geladen
	 */
	private void loadMyProjekt() {

		final ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

		final HorizontalPanel loginHeader = new HorizontalPanel();

		final HorizontalPanel menu = new HorizontalPanel();

		RootPanel.get("userinfo").add(loginHeader);
		RootPanel.get("menu").add(menu);

		final Button abmeldungButton = new Button("Abmelden");
		abmeldungButton.setStyleName(
				"myprojekt-abmeldebutton"); /**
											 * Verknüft CSS Klasse auf Button
											 */
		abmeldungButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.assign(loginInfo.getLogoutUrl());
			}
		});

		/* Menüleiste */
		Button marktplaetzeButton = new Button("Projektmarktplätze");
		marktplaetzeButton.setStyleName("myprojekt-menubutton");
		marktplaetzeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				RootPanel.get("content").clear();
				RootPanel.get("content").add(new Marktuebersicht());

			}
		});

		Button projekteButton = new Button("Meine Ausschreibungen");
		projekteButton.setStyleName("myprojekt-menubutton");
		projekteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (cpm != null) {
					HorizontalPanel hPanel = new HorizontalPanel();

					VerticalPanel treePanel = new VerticalPanel();
					hPanel.add(treePanel);

					VerticalPanel contentPanel = new VerticalPanel();
					hPanel.add(contentPanel);

					ProjektTreeViewModel ptvm = new ProjektTreeViewModel(true);
					CellTree.Resources projektTreeRessource = GWT.create(CellTree.Resources.class);
					CellTree cellTree = new CellTree(ptvm, "Root", projektTreeRessource);
					cellTree.setAnimationEnabled(true);
					treePanel.add(cellTree);

					ProjektForm projektForm = new ProjektForm(true);
					projektForm.setProjektTreeViewModel(ptvm);
					contentPanel.add(projektForm);
					ptvm.setProjektForm(projektForm);

					AusschreibungForm ausschreibungForm = new AusschreibungForm(true);
					ausschreibungForm.setProjektTreeViewModel(ptvm);
					contentPanel.add(ausschreibungForm);
					ptvm.setAusschreibungForm(ausschreibungForm);

					BewerbungForm bewerbungForm = new BewerbungForm(true);
					bewerbungForm.setProjektTreeViewModel(ptvm);
					contentPanel.add(bewerbungForm);
					ptvm.setBewerbungForm(bewerbungForm);

					BewertungForm bewertungForm = new BewertungForm(true);
					bewertungForm.setProjektTreeViewModel(ptvm);
					contentPanel.add(bewertungForm);
					ptvm.setBewertungForm(bewertungForm);

					VerticalPanel rightPanel = new VerticalPanel();
					ptvm.setRightPanel(rightPanel);
					hPanel.add(rightPanel);

					RootPanel.get("content").clear();
					RootPanel.get("content").add(hPanel);
				} else {
					RootPanel.get("content").clear();
					RootPanel.get("content").add(new Label("Es wurde kein Projektmarktplatz ausgewählt."));
				}

			}
		});

		Button bewerbungButton = new Button("Meine Bewerbungen");
		bewerbungButton.setStyleName("myprojekt-menubutton");
		bewerbungButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (cpm != null && loginInfo.getCurrentUser().getPartnerprofilId() != 0) {

					HorizontalPanel hPanel = new HorizontalPanel();

					VerticalPanel treePanel = new VerticalPanel();
					hPanel.add(treePanel);

					VerticalPanel contentPanel = new VerticalPanel();
					hPanel.add(contentPanel);

					ProjektTreeViewModel ptvm = new ProjektTreeViewModel(false);
					CellTree.Resources projektTreeRessource = GWT.create(CellTree.Resources.class);
					CellTree cellTree = new CellTree(ptvm, "Root", projektTreeRessource);
					cellTree.setAnimationEnabled(true);
					treePanel.add(cellTree);

					ProjektForm projektForm = new ProjektForm(false);
					projektForm.setProjektTreeViewModel(ptvm);
					contentPanel.add(projektForm);
					ptvm.setProjektForm(projektForm);

					AusschreibungForm ausschreibungForm = new AusschreibungForm(false);
					ausschreibungForm.setProjektTreeViewModel(ptvm);
					contentPanel.add(ausschreibungForm);
					ptvm.setAusschreibungForm(ausschreibungForm);

					BewerbungForm bewerbungForm = new BewerbungForm(false);
					bewerbungForm.setProjektTreeViewModel(ptvm);
					contentPanel.add(bewerbungForm);
					ptvm.setBewerbungForm(bewerbungForm);

					BewertungForm bewertungForm = new BewertungForm(false);
					bewertungForm.setProjektTreeViewModel(ptvm);
					contentPanel.add(bewertungForm);
					ptvm.setBewertungForm(bewertungForm);

					VerticalPanel rightPanel = new VerticalPanel();
					ptvm.setRightPanel(rightPanel);
					hPanel.add(rightPanel);

					RootPanel.get("content").clear();
					RootPanel.get("content").add(hPanel);
				} else {
					RootPanel.get("content").clear();
					if(cpm == null)
						RootPanel.get("content").add(new Label("Sie haben keinen Projektmarktplatz ausgewählt."));
					
					if(loginInfo.getCurrentUser().getPartnerprofilId() == 0)
						RootPanel.get("content").add(new Label("Sie besitzen noch kein Partnerprofil."));
						
				}

			}
		});

		Button profilButton = new Button("Eigenes Profil verwalten");
		profilButton.setStyleName("myprojekt-menubutton");
		profilButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				RootPanel.get("content").clear();
				RootPanel.get("content").add(new OPartnerprofilForm());

			}
		});
		
		Button reportGeneratorButton = new Button("Report Generator");
		reportGeneratorButton.setStyleName("myprojekt-reportmenubutton");
		reportGeneratorButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.assign(GWT.getHostPageBaseURL() + "reportgenerator.html");

			}
		});

		menu.add(marktplaetzeButton);
		menu.add(projekteButton);
		menu.add(bewerbungButton);
		menu.add(profilButton);
		menu.add(reportGeneratorButton);

		if (loginInfo.getCurrentUser() == null) {
			pa.findByGoogleId(loginInfo, new AsyncCallback<Person>() {

				@Override
				public void onSuccess(Person result) {

					Showcase showcase;

					if (result != null) {
						loginInfo.setCurrentUser(result);
						Label LoginInfoLabel = new Label(loginInfo.toString()); 
						LoginInfoLabel.setStyleName("myprojekt-loginlabel");
						loginHeader.add(LoginInfoLabel);
						loginHeader.add(abmeldungButton);
						showcase = new Marktuebersicht();
					} else {

						showcase = new NewPersonForm(loginInfo.getEmailAddress(), menu);
						menu.setVisible(false);
					}

					RootPanel.get("content").clear();
					RootPanel.get("content").add(showcase);

				}

				@Override
				public void onFailure(Throwable caught) {

					Window.alert("Es ist ein Datenbankfehler aufgetreten.");
					LoginPanel loginPanel = new LoginPanel(loginInfo);
					loginPanel.run();
				}
			});
		}
	}
}
