package de.hdm.it_projekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AlleBewerbungenReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> initReportGeneratorCallback);

	void createAlleAusschreibungenReport(Projekt p, AsyncCallback<AlleAusschreibungenReport> callback);
	
	void createAlleBewerbungenReport(Ausschreibung as, AsyncCallback<AlleBewerbungenReport> callback);
}
