/**
 * 
 */
package de.hdm.it_projekt.shared;
/**
 * Import der benoetigten Pakete
 */
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.bo.LoginInfo;

/**
 * @author Sid Heiland
 *
 */
public interface LoginServiceAsync {
	public void login(String requestUri, AsyncCallback<LoginInfo> async);

}
