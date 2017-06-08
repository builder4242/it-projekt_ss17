/**
 * 
 */
package de.hdm.it_projekt.client;
/**
 * Import der benoetigten Pakete
 */
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Sid Heiland
 *
 */
public interface LoginServiceAsync {
	public void login(String requestUri, AsyncCallback<LoginInfo> async);

}
