package de.hdm.it_projekt.server;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.it_projekt.server.db.*;
import de.hdm.it_projekt.shared.ProjektAdministration;
import de.hdm.it_projekt.shared.bo.*;

@SuppressWarnings("serial")
public class ProjektAdministrationImpl extends RemoteServiceServlet implements ProjektAdministration {

	private ProjektMarktplatz pm = null;

	private AusschreibungMapper asMapper = null;
	private BeteiligungMapper btMapper = null;
	private BewerbungMapper bwMapper = null;
	private BewertungMapper bwtMapper = null;
	private EigenschaftMapper eMapper = null;
	private PartnerprofilMapper ppMapper = null;
	private PersonMapper pMapper = null;
	private ProjektMapper prMapper = null;
	private ProjektMarktplatzMapper pmMapper = null;
	private TeamMapper tMapper = null;
	private UnternehmenMapper uMapper = null;

	public ProjektAdministrationImpl() throws IllegalArgumentException {

	}

	@Override
	public void init() throws IllegalArgumentException {

		this.asMapper = AusschreibungMapper.ausschreibungMapper();
		this.btMapper = BeteiligungMapper.beteiligungMapper();
		this.bwMapper = BewerbungMapper.bewerbungMapper();
		this.bwtMapper = BewertungMapper.bewertungMapper();
		this.eMapper = EigenschaftMapper.eigenschaftMapper();
		this.ppMapper = PartnerprofilMapper.partnerprofilMapper();
		this.pMapper = PersonMapper.personMapper();
		this.prMapper = ProjektMapper.projektMapper();
		this.pmMapper = ProjektMarktplatzMapper.projektMarktplatzMapper();
		this.tMapper = TeamMapper.teamMapper();
		this.uMapper = UnternehmenMapper.unternehmenMapper();
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
		ProjektMarktplatz pm = new ProjektMarktplatz();
		pm.setBezeichnung(bez);

		pm.setId(1);

		return this.pmMapper.insert(pm);
		;
	}

	@Override
	public Projekt createProjektFor(ProjektMarktplatz pm, String name, Date startdatum, Date enddatum,
			String beschreibung) throws IllegalArgumentException {

		Projekt pr = new Projekt();
		pr.setName(name);
		pr.setStartdatum(startdatum);
		pr.setEnddatum(enddatum);
		pr.setBeschreibung(beschreibung);
		pr.setProjektMarktplatzId(pm.getId());

		pr.setId(1);

		return this.prMapper.insert(pr);
		;
	}

	@Override
	public Ausschreibung createAusschreibungFor(Projekt pr, String bezeichnung, Date bewerbungsfrist,
			String ausschreibungstext, Partnerprofil profil) throws IllegalArgumentException {

		Ausschreibung as = new Ausschreibung();
		as.setBezeichnung(bezeichnung);
		as.setBewerbungsfrist(bewerbungsfrist);
		as.setAusschreibungstext(ausschreibungstext);
		as.setPartnerprofilId(profil.getId());
		as.setProjektId(pr.getId());

		as.setId(1);

		return this.asMapper.insert(as);
		;
	}

	@Override
	public Partnerprofil createPartnerprofilFor(Ausschreibung as) throws IllegalArgumentException {

		Partnerprofil pp = new Partnerprofil();
		pp.setErstelldatum(new Date());
		pp.setAenderungsdatum(new Date());

		pp.setId(1);

		Partnerprofil npp = this.ppMapper.insert(pp);

		as.setPartnerprofilId(npp.getId());

		this.save(as);

		return npp;
	}

	@Override
	public Partnerprofil createPartnerprofilFor(Organisationseinheit or) throws IllegalArgumentException {

		Partnerprofil pp = new Partnerprofil();
		pp.setErstelldatum(new Date());
		pp.setAenderungsdatum(new Date());

		pp.setId(1);

		Partnerprofil npp = this.ppMapper.insert(pp);

		or.setPartnerprofilId(npp.getId());

		if(or instanceof Person)
			this.save((Person)or);
		
		if(or instanceof Unternehmen)
			this.save((Unternehmen)or);
		
		if(or instanceof Team)
			this.save((Team)or);

		return npp;
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
