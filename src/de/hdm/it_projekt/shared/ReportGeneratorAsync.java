package de.hdm.it_projekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> initReportGeneratorCallback);

	void createAlleAusschreibungenReport(ProjektMarktplatz pm, AsyncCallback<AlleAusschreibungenReport> callback);

	void getAusschreibungFor(Organisationseinheit o, AsyncCallback<Vector<Ausschreibung>> callback);

	void getBewerberForAusschreibenden(Organisationseinheit o, AsyncCallback<Vector<Organisationseinheit>> callback);

	void getBewerbungenFor(Ausschreibung as, AsyncCallback<Vector<Bewerbung>> callback);

	void getAlleAusschreibungenFor(ProjektMarktplatz pm, AsyncCallback<Vector<Ausschreibung>> callback);

	void getMatchingAusschreibungenFor(Organisationseinheit o, AsyncCallback<Vector<Ausschreibung>> callback);

	void getBewerbungenFrom(Organisationseinheit o, AsyncCallback<Vector<Bewerbung>> callback);


}
