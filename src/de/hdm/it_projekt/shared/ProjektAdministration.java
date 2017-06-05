package de.hdm.it_projekt.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.it_projekt.shared.bo.*;

@RemoteServiceRelativePath("projektadministration")
public interface ProjektAdministration extends RemoteService {

	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusätzlich zum No Argument Constructor der implementierenden
	 * Klasse {@link ProjektAdministrationImpl} notwendig. Bitte diese Methode
	 * direkt nach der Instantiierung aufrufen.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	public ProjektMarktplatz createProjektMarktplatz(String bezeichnung) throws IllegalArgumentException;

	public Projekt createProjektFor(ProjektMarktplatz marktplatz, String name, Date startdatum, Date enddatum,
			String beschreibung) throws IllegalArgumentException;

	public Ausschreibung createAusschreibungFor(Projekt projekt, String bezeichnung, Date bewerbungsfrist,
			String ausschreibungstext, Partnerprofil profil) throws IllegalArgumentException;

	public Partnerprofil createPartnerprofilFor(Ausschreibung ausschreibung) throws IllegalArgumentException;
	
	public Partnerprofil createPartnerprofilFor(Organisationseinheit organisation) throws IllegalArgumentException;
	
	public Eigenschaft createEigenschaftFor(Ausschreibung ausschreibung, String name, String value)
			throws IllegalArgumentException;

	public Person createPerson(String name, String vorname, String email, String strasse, int plz, String ort,
			String tel) throws IllegalArgumentException;

	public Unternehmen createUnternehmen(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException;

	public Team createTeam(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException;

	public Bewerbung createBewerbungFor(Projekt projekt, Organisationseinheit organisation)
			throws IllegalArgumentException;

	
	 
}
