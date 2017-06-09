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
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class MyProjekt implements EntryPoint {

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte eilogge... Ahnma!.");
	private Anchor signInLink = new Anchor("Sign In");

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

	private void loadLogin() {
		// Assemble login panel
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("stockList").add(loginPanel);
	}

	private void loadMyProjekt() {

		ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

		final HorizontalPanel header = new HorizontalPanel();
		final HorizontalPanel menu = new HorizontalPanel();
		final HorizontalPanel content = new HorizontalPanel();

		final Label headline = new Label("MyProjekt");
		header.add(headline);

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

		RootPanel.get("header").add(header);
		RootPanel.get("menu").add(menu);
		RootPanel.get("content").add(content);

	}

}
