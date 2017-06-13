package de.hdm.it_projekt.shared;
/** 
 * Import der benoetigten Klassen
 */
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.it_projekt.shared.bo.LoginInfo;

/**
 * @author Sid Heiland
 *
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	public LoginInfo login(String requestUri);

}
