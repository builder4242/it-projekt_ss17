package de.hdm.it_projekt.shared.bo;

import java.io.Serializable;

/**
 * Klasse Login Info 
 * @author Sid Heiland
 *
 */
public class LoginInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Beginn Attributdefinition 
	 */
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;
	private String nickname;
	
	private Organisationseinheit currentUser;
	/**
	 * Ende Attributdefinition 
	 */
	
	public LoginInfo() {
		this.currentUser = null;
	}
	
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @return the loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * @param loginUrl the loginUrl to set
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * @return the logoutUrl
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * @param logoutUrl the logoutUrl to set
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @param loggedIn the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	/**
	 * @return the currentUser
	 */
	public Organisationseinheit getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(Organisationseinheit currentUser) {
		this.currentUser = currentUser;
	}

	public String toString() {
		if(this.currentUser == null)
			return this.getNickname() + " (" + this.getEmailAddress() + ")";
		else
			return this.currentUser.getName() + " (" + this.currentUser.getEmail() + ")";
	}
	

}
