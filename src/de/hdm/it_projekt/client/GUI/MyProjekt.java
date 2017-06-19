package de.hdm.it_projekt.client.GUI;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.it_projekt.shared.LoginService;
import de.hdm.it_projekt.shared.LoginServiceAsync;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.LoginInfo;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.client.ClientsideSettings;

public class MyProjekt implements EntryPoint {

	/**
	 * Begin Attribute fuer Login
	 */
	protected static LoginInfo loginInfo = null;
	protected static ProjektMarktplatz cpm = null;

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
		abmeldungButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.assign(loginInfo.getLogoutUrl());
			}
		});
		
		/* Menüleiste */ 
		Button marktplaetzeButton = new Button("Marktplätze"); 
		marktplaetzeButton.setStyleName("myprojekt-menubutton");
		Button projekteButton = new Button("Projekte"); 
		projekteButton.setStyleName("myprojekt-menubutton");
		Button profilButton = new Button("Profil"); 
		profilButton.setStyleName("myprojekt-menubutton");
		profilButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				RootPanel.get("content").clear();
				RootPanel.get("contetn").add(new PartnerprofilForm());
				
			}
		});
		
		Button bewerbungButton = new Button("Bewerbungn"); 
		bewerbungButton.setStyleName("myprojekt-menubutton");
						
		menu.add(marktplaetzeButton);
		menu.add(projekteButton);
		menu.add(profilButton);
		menu.add(bewerbungButton);

		
		if (loginInfo.getCurrentUser() == null) {
			pa.findByGoogleId(loginInfo, new AsyncCallback<Person>() {

				@Override
				public void onSuccess(Person result) {

					Showcase showcase;

					if (result != null) {
						loginInfo.setCurrentUser(result);
						loginHeader.add(new Label(loginInfo.toString()));
						loginHeader.add(abmeldungButton);
						showcase = new Marktuebersicht();
					} else {

						showcase = new NewPersonForm(loginInfo.getEmailAddress());
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
