package de.hdm.it_projekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AlleBewerbungenReport;
import de.hdm.it_projekt.shared.report.AnzahlAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AnzahlBewerbungenReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> initReportGeneratorCallback);

	void createAlleAusschreibungenReport(Projekt p, AsyncCallback<AlleAusschreibungenReport> callback);
	
	void createAlleBewerbungenReport(Ausschreibung as, AsyncCallback<AlleBewerbungenReport> callback);
	
	void createBewerbungenAufAusschreibungReport(Ausschreibung as, AsyncCallback<AlleBewerbungenReport> callback);
	
	void createbAusschreibungZuBewerbungReport(Bewerbung bw, AsyncCallback <AlleBewerbungenReport> callback);
	
	void fanOutReport(Organisationseinheit oe, AsyncCallback <AnzahlBewerbungenReport> callback);
	
	void  fanInAnalyse(Projekt pr, AsyncCallback <AnzahlAusschreibungenReport> callback);
}
