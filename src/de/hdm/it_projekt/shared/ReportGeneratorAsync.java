package de.hdm.it_projekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> initReportGeneratorCallback);

	void getAlleAusschreibungenFor(ProjektMarktplatz pm, AsyncCallback<Vector<Ausschreibung>> callback);

	void getAlleAusschreibungenReport(ProjektMarktplatz pm, AsyncCallback<String> callback);

	void getAusschreibungFor(Organisationseinheit o, AsyncCallback<Vector<Ausschreibung>> callback);

	void getBewerberForAusschreibenden(Organisationseinheit o, AsyncCallback<Vector<Organisationseinheit>> callback);

	void getBewerbungenAufAusschreibungReport(Organisationseinheit o, AsyncCallback<String> callback);

	void getBewerbungenFor(Ausschreibung as, AsyncCallback<Vector<Bewerbung>> callback);

	void getBewerbungenFor(Organisationseinheit o, AsyncCallback<Vector<Bewerbung>> callback);

	void getBewerbungenReport(Organisationseinheit o, AsyncCallback<String> callback);

	void getMatchingAusschreibungenFor(Organisationseinheit o, AsyncCallback<Vector<Ausschreibung>> callback);

	void getMatchingAusschreibungenReport(Organisationseinheit o, AsyncCallback<String> callback);

	void getProjektverflechtungenReport(Organisationseinheit o, AsyncCallback<String> callback);

}
