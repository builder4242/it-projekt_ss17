package de.hdm.it_projekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	public void init() throws IllegalArgumentException;
}
