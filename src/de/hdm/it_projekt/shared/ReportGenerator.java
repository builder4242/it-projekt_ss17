package de.hdm.it_projekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	public void init() throws IllegalArgumentException;
	
	public AlleAusschreibungenReport createAlleAusschreibungenReport(ProjektMarktplatz pm) throws IllegalArgumentException;
		
	public Vector<Ausschreibung> getAlleAusschreibungenFor(ProjektMarktplatz pm) throws IllegalArgumentException;
	
	public Vector<Ausschreibung> getMatchingAusschreibungenFor(Organisationseinheit o) throws IllegalArgumentException;
	
	public Vector<Ausschreibung> getAusschreibungFor(Organisationseinheit o) throws IllegalArgumentException;
	
	public Vector<Bewerbung> getBewerbungenFor(Ausschreibung as) throws IllegalArgumentException;
	
	public Vector<Bewerbung> getBewerbungenFrom(Organisationseinheit o) throws IllegalArgumentException;
	
	public Vector<Organisationseinheit> getBewerberForAusschreibenden(Organisationseinheit o) throws IllegalArgumentException;
	
			
	
}
