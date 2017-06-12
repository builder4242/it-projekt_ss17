package de.hdm.it_projekt.client.GUI;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.shared.bo.LoginInfo;

public class LoginPanel extends Showcase {

	private LoginInfo loginInfo = null;
	
	protected LoginPanel(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}
	
	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Login";
	}

	@Override
	protected void run() {

		
		VerticalPanel loginPanel = new VerticalPanel();
		Label loginLabel = new Label("Bitte einloggen...");
		Anchor signInLink = new Anchor("Sign In");
				
		// Assemble login panel
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(loginPanel);
		
	}

}
