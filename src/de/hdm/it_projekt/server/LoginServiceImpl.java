package de.hdm.it_projekt.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import de.hdm.it_projekt.shared.LoginService;
import de.hdm.it_projekt.shared.bo.LoginInfo;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 *  Implementierungsklasse fuer den Login Service
 *  
 * @author Sid Heiland
 *
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Methode fuer UserLogin
	 */
	public LoginInfo login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();
		
		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
			
		}
		return loginInfo;
	}

}
