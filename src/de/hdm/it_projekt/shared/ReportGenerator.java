package de.hdm.it_projekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AlleBewerbungenReport;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	public void init() throws IllegalArgumentException;

	/**
	 * @param as
	 * @return 
	 */
		public abstract AlleAusschreibungenReport createAlleAusschreibungenReport(Projekt p) 
				throws IllegalArgumentException;
		public abstract AlleBewerbungenReport createAlleBewerbungenReport(Ausschreibung as) throws IllegalArgumentException; 
	}

