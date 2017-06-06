package de.hdm.it_projekt.server;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.it_projekt.shared.ProjektAdministration;
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Bewertung;
import de.hdm.it_projekt.shared.bo.Eigenschaft;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.bo.Team;
import de.hdm.it_projekt.shared.bo.Unternehmen;

@SuppressWarnings("serial")
public class ProjektAdministrationImpl extends RemoteServiceServlet implements ProjektAdministration {

	@Override
	public void init() throws IllegalArgumentException {

	}

	@Override
	public Vector<ProjektMarktplatz> getAlleProjektMarktplaetze() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjektMarktplatz getProjektMarktplatzById(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Projekt> getAlleProjekteFor(ProjektMarktplatz pm) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Projekt> getProjektByName(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projekt getProjektById(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Ausschreibung> getAusschreibungFor(Projekt pr) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ausschreibung getAusschreibungById(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partnerprofil getPartnerprofilById(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Eigenschaft> getEigenschaftenFor(Partnerprofil pr) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Bewerbung> getBewerbungFor(Ausschreibung as) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bewertung getBewertungFor(Bewerbung bw) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Beteiligung> getBeteiligungenFor(Projekt pr) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Beteiligung> getBeteiligungenFor(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjektMarktplatz createProjektMarktplatz(String bez) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projekt createProjektFor(ProjektMarktplatz mp, String name, Date startdatum, Date enddatum,
			String beschreibung) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ausschreibung createAusschreibungFor(Projekt pr, String bezeichnung, Date bewerbungsfrist,
			String ausschreibungstext, Partnerprofil profil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partnerprofil createPartnerprofilFor(Ausschreibung as) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partnerprofil createPartnerprofilFor(Organisationseinheit or) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Eigenschaft createEigenschaftFor(Ausschreibung as, String name, String value)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person createPerson(String name, String vorname, String email, String strasse, int plz, String ort,
			String tel) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unternehmen createUnternehmen(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team createTeam(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bewerbung bewerben(Projekt pr, Organisationseinheit organisation) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bewertung bewerten(Bewerbung bw, String stellungsnahme, float wert) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Beteiligung beteiligen(Projekt pr, Organisationseinheit or, int personentage, Date startdatum, Date enddatum)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void projektmarktplatzBeitreten(ProjektMarktplatz pm, Organisationseinheit o)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(ProjektMarktplatz pm) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Projekt pr) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Ausschreibung as) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Partnerprofil pr) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Eigenschaft e) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Bewerbung bw) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Bewertung bwt) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Beteiligung bt) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Person ps) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Unternehmen u) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Team t) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(ProjektMarktplatz pm) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Projekt pr) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Ausschreibung as) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Partnerprofil pr) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Eigenschaft e) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Bewerbung bw) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Bewertung bwt) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Beteiligung bt) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Person ps) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Unternehmen u) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Team t) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}
}
