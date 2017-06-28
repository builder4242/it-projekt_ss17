package de.hdm.it_projekt.client.GUI;

/** Die Klasse LoginPanel dient dem Aufbau der Startseite für nicht eingeloggte User. Der User wird 
 * durch einen Text begrüßt und hat die Möglichkeit über den Anmeldebutton zum Login bzw. zur Registrierung
 * zu gelangem. Das Design wird durch einbinden von CSS angepasst.  */

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
		loginButton.setStyleName("myprojekt-loginbutton");
		loginButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(loginInfo.getLoginUrl());				
			}
		});
		
		RootPanel.get("userinfo").add(loginButton);
		
		
		VerticalPanel loginPanel = new VerticalPanel();
		Label greeting = new Label();
		
		greeting.setText("Willkommen bei getLinked, der profesionellen Plattform für Projekte aller Art. Melden Sie sich an"
				+ " und entdecken Sie tausende von spannenden Projekten oder suchen Sie ganz einfach Mitarbeiter für Ihr eigenes Projekt."
				+ " Mit getLinked wird jedes Projekt zu einem Erfolg!");
		
		// Assemble login panel
		RootPanel.get("content").clear();
		RootPanel.get("content").add(greeting);
		
	}

}