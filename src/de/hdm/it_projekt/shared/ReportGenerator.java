package de.hdm.it_projekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	public void init() throws IllegalArgumentException;

	
	public String getAlleAusschreibungenReport(ProjektMarktplatz pm) throws IllegalArgumentException;
	
	public String getMatchingAusschreibungenReport(Organisationseinheit o) throws IllegalArgumentException;
	
	public String getBewerbungenAufAusschreibungReport(Organisationseinheit o) throws IllegalArgumentException;
	
	public String getBewerbungenReport(Organisationseinheit o) throws IllegalArgumentException;
	
	public String getProjektverflechtungenReport(Organisationseinheit o) throws IllegalArgumentException;
	
	
	public Vector<Ausschreibung> getAlleAusschreibungenFor(ProjektMarktplatz pm) throws IllegalArgumentException;
	
	public Vector<Ausschreibung> getMatchingAusschreibungenFor(Organisationseinheit o) throws IllegalArgumentException;
	
	public Vector<Ausschreibung> getAusschreibungFor(Organisationseinheit o) throws IllegalArgumentException;
	
	public Vector<Bewerbung> getBewerbungenFor(Ausschreibung as) throws IllegalArgumentException;
	
	public Vector<Bewerbung> getBewerbungenFor(Organisationseinheit o) throws IllegalArgumentException;
	
	public Vector<Organisationseinheit> getBewerberForAusschreibenden(Organisationseinheit o) throws IllegalArgumentException;
	
			
	
}
