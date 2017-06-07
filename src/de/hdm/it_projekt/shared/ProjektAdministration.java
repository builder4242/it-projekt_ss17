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

	public Vector<ProjektMarktplatz> getAlleProjektMarktplaetze() throws IllegalArgumentException;

	public ProjektMarktplatz getProjektMarktplatzById(int id) throws IllegalArgumentException;

	public Vector<Projekt> getAlleProjekte() throws IllegalArgumentException;

	public Vector<Projekt> getProjektByName(String name) throws IllegalArgumentException;

	public Projekt getProjektById(int id) throws IllegalArgumentException;

	public Vector<Ausschreibung> getAusschreibungFor(Projekt pr) throws IllegalArgumentException;

	public Ausschreibung getAusschreibungById(int id) throws IllegalArgumentException;

	public Partnerprofil getPartnerprofilById(int id) throws IllegalArgumentException;

	public Vector<Eigenschaft> getEigenschaftenFor(Partnerprofil pr) throws IllegalArgumentException;

	public Vector<Bewerbung> getBewerbungFor(Ausschreibung as) throws IllegalArgumentException;

	public Bewertung getBewertungFor(Bewerbung bw) throws IllegalArgumentException;

	public Vector<Beteiligung> getBeteiligungenFor(Projekt pr) throws IllegalArgumentException;

	public Vector<Beteiligung> getBeteiligungenFor(Organisationseinheit o) throws IllegalArgumentException;

	/**
	 * 
	 * @param bez
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ProjektMarktplatz createProjektMarktplatz(String bez) throws IllegalArgumentException;

	public Projekt createProjektFor(ProjektMarktplatz mp, String name, Date startdatum, Date enddatum,
			String beschreibung) throws IllegalArgumentException;

	public Ausschreibung createAusschreibungFor(Projekt pr, String bezeichnung, Date bewerbungsfrist,
			String ausschreibungstext, Partnerprofil profil) throws IllegalArgumentException;

	public Partnerprofil createPartnerprofilFor(Ausschreibung as) throws IllegalArgumentException;

	public Partnerprofil createPartnerprofilFor(Organisationseinheit or) throws IllegalArgumentException;

	public Eigenschaft createEigenschaftFor(Ausschreibung as, String name, String value)
			throws IllegalArgumentException;

	public Person createPerson(String name, String vorname, String email, String strasse, int plz, String ort,
			String tel) throws IllegalArgumentException;

	public Unternehmen createUnternehmen(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException;

	public Team createTeam(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException;

	public Bewerbung bewerben(Projekt pr, Organisationseinheit organisation) throws IllegalArgumentException;

	public Bewertung bewerten(Bewerbung bw, String stellungsnahme, float wert) throws IllegalArgumentException;

	public Beteiligung beteiligen(Projekt pr, Organisationseinheit or, int personentage, Date startdatum, Date enddatum)
			throws IllegalArgumentException;

	public void projektmarktplatzBeitreten(ProjektMarktplatz pm, Organisationseinheit o)
			throws IllegalArgumentException;

	public void save(ProjektMarktplatz pm) throws IllegalArgumentException;

	public void save(Projekt pr) throws IllegalArgumentException;

	public void save(Ausschreibung as) throws IllegalArgumentException;

	public void save(Partnerprofil pr) throws IllegalArgumentException;

	public void save(Eigenschaft e) throws IllegalArgumentException;

	public void save(Bewerbung bw) throws IllegalArgumentException;

	public void save(Bewertung bwt) throws IllegalArgumentException;

	public void save(Beteiligung bt) throws IllegalArgumentException;

	public void save(Person ps) throws IllegalArgumentException;

	public void save(Unternehmen u) throws IllegalArgumentException;

	public void save(Team t) throws IllegalArgumentException;

	public void delete(ProjektMarktplatz pm) throws IllegalArgumentException;

	public void delete(Projekt pr) throws IllegalArgumentException;

	public void delete(Ausschreibung as) throws IllegalArgumentException;

	public void delete(Partnerprofil pr) throws IllegalArgumentException;

	public void delete(Eigenschaft e) throws IllegalArgumentException;

	public void delete(Bewerbung bw) throws IllegalArgumentException;

	public void delete(Bewertung bwt) throws IllegalArgumentException;

	public void delete(Beteiligung bt) throws IllegalArgumentException;

	public void delete(Person ps) throws IllegalArgumentException;

	public void delete(Unternehmen u) throws IllegalArgumentException;

	public void delete(Team t) throws IllegalArgumentException;

}
