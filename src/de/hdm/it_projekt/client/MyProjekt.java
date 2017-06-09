package de.hdm.it_projekt.client;

import java.util.Vector;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Anchor;

import de.hdm.it_projekt.client.GUI_in_dev.MarktplatzUebersicht;
import de.hdm.it_projekt.shared.LoginService;
import de.hdm.it_projekt.shared.LoginServiceAsync;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.LoginInfo;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class MyProjekt implements EntryPoint {

	/**
	 * Begin Attribute fuer Login
	 */
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte einloggen...");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sing Out");
	/*Ende Attribute fuer Login */

	/**
	 * Methode welche beim Seitenaufruf geladen wird und prueft ob User eingeloggt ist. Falls ja wird Methode loadMyProjekt() aufgerufen,
	 * falls nicht die Methode loadLogin()
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
					loadLogin();
				}
			}
		});
	}

	/**
	 * Methode stellt Login bereit
	 */
	private void loadLogin() {
		// Assemble login panel
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("content").add(loginPanel);
	}

	/**
	 * Mit der Methode laodMyProjekt() wird der eigentliche Seiteninhalt geladen
	 */
	private void loadMyProjekt() {
		
		signOutLink.setHref(loginInfo.getLogoutUrl());

		ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

		final HorizontalPanel header = new HorizontalPanel();
		final HorizontalPanel menu = new HorizontalPanel();
		final HorizontalPanel content = new HorizontalPanel();

		final Label menulabel = new Label("hier sollte das Menü stehen !");
		menu.add(menulabel);

		final Label ausgabe = new Label();

		pa.getAlleProjektMarktplaetze(new AsyncCallback<Vector<ProjektMarktplatz>>() {

			@Override
			public void onFailure(Throwable caught) {

				ausgabe.setText(caught.getMessage());
			}

			@Override
			public void onSuccess(Vector<ProjektMarktplatz> result) {

				String t = "";
				for (ProjektMarktplatz pm : result) {
					t += pm.toString();
				}

				ausgabe.setText(t);
			}
		});

		content.add(ausgabe);
		
		RootPanel.get("signout").add(signOutLink);
		RootPanel.get("menu").add(menu);
		RootPanel.get("content").add(content);

	}

}
