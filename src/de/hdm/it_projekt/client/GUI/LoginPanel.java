package de.hdm.it_projekt.client.GUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.shared.bo.LoginInfo;

public class LoginPanel extends Showcase {

	private LoginInfo loginInfo = null;
	
	protected LoginPanel(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	
	protected void run() {

		final Button loginButton = new Button("Login");
		loginButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(loginInfo.getLoginUrl());				
			}
		});
		
		RootPanel.get("userinfo").add(loginButton);
		
		
		VerticalPanel loginPanel = new VerticalPanel();
		Label greeting = new Label();
		
		greeting.setText("Hallo hier kommt eine Begrüßungstext mit Beschreibung des Logins.....");
		
		// Assemble login panel
		RootPanel.get("content").clear();
		RootPanel.get("content").add(greeting);
		
	}

}