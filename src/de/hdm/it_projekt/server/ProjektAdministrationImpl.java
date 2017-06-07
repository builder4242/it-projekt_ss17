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
		return this.pmMapper.findAll();
	}

	@Override
	public ProjektMarktplatz getProjektMarktplatzById(int id) throws IllegalArgumentException {
		return this.pmMapper.findById(id);
	}

	@Override
	public Vector<Projekt> getAlleProjekteFor(ProjektMarktplatz pm) throws IllegalArgumentException {
		return this.prMapper.getByProjektmarktplatz(pm);
	}

	@Override
	public Vector<Projekt> getProjektByName(String name) throws IllegalArgumentException {
		return this.prMapper.findByName(name);
	}

	@Override
	public Projekt getProjektById(int id) throws IllegalArgumentException {
		return this.prMapper.findById(id);
	}

	@Override
	public Vector<Ausschreibung> getAusschreibungFor(Projekt pr) throws IllegalArgumentException {
		return this.asMapper.getByProjekt(pr);
	}

	@Override
	public Ausschreibung getAusschreibungById(int id) throws IllegalArgumentException {
		return this.asMapper.findById(id);
	}

	@Override
	public Partnerprofil getPartnerprofilById(int id) throws IllegalArgumentException {
		return this.ppMapper.findById(id);
	}

	@Override
	public Vector<Eigenschaft> getEigenschaftenFor(Partnerprofil pr) throws IllegalArgumentException {
		return null;
	}

	@Override
	public Vector<Bewerbung> getBewerbungFor(Ausschreibung as) throws IllegalArgumentException {
		return this.bwMapper.getByAusschreibung(as);
	}

	@Override
	public Bewertung getBewertungFor(Bewerbung bw) throws IllegalArgumentException {
		return this.bwtMapper.getByBewerbung(bw);
	}

	@Override
	public Vector<Beteiligung> getBeteiligungenFor(Projekt pr) throws IllegalArgumentException {
		return this.btMapper.getByProjekt(pr);
	}

	@Override
	public Vector<Beteiligung> getBeteiligungenFor(Organisationseinheit o) throws IllegalArgumentException {
		return this.btMapper.getByOrganisationseinheit(o);
	}

	@Override
	public ProjektMarktplatz createProjektMarktplatz(String bez) throws IllegalArgumentException {
		ProjektMarktplatz pm = new ProjektMarktplatz();
		pm.setBezeichnung(bez);

		pm.setId(1);

		return this.pmMapper.insert(pm);
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

		if (or instanceof Person)
			this.save((Person) or);

		if (or instanceof Unternehmen)
			this.save((Unternehmen) or);

		if (or instanceof Team)
			this.save((Team) or);

		return npp;
	}

	@Override
	public Eigenschaft createEigenschaftFor(Partnerprofil pp, String name, String value)
			throws IllegalArgumentException {

		Eigenschaft e = new Eigenschaft();

		e.setName(name);
		e.setWert(value);
		e.setPartnerprofilId(pp.getId());

		e.setId(1);

		return this.eMapper.insert(e);

	}

	@Override
	public Person createPerson(String name, String vorname, String email, String strasse, int plz, String ort,
			String tel) throws IllegalArgumentException {

		Person p = new Person();
		p.setName(name);
		p.setVorname(vorname);
		p.setEmail(email);
		p.setStrasse(strasse);
		p.setPlz(plz);
		p.setOrt(ort);
		p.setTel(tel);

		p.setId(1);

		return this.pMapper.insert(p);
	}

	@Override
	public Unternehmen createUnternehmen(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException {

		Unternehmen u = new Unternehmen();
		u.setName(name);
		u.setEmail(email);
		u.setStrasse(strasse);
		u.setPlz(plz);
		u.setOrt(ort);
		u.setTel(tel);

		u.setId(1);

		return this.uMapper.insert(u);
	}

	@Override
	public Team createTeam(String name, String email, String strasse, int plz, String ort, String tel)
			throws IllegalArgumentException {
		Team t = new Team();

		t.setName(name);
		t.setEmail(email);
		t.setStrasse(strasse);
		t.setPlz(plz);
		t.setOrt(ort);
		t.setTel(tel);

		t.setId(1);

		return this.tMapper.insert(t);
	}

	@Override
	public Bewerbung bewerben(Ausschreibung as, Organisationseinheit organisation, String bewerbungstext)
			throws IllegalArgumentException {

		Bewerbung bw = new Bewerbung();

		bw.setAusschreibungId(as.getId());
		bw.setOrganisationseinheitId(organisation.getId());
		bw.setErstelldatum(new Date());
		bw.setBewerbungstext(bewerbungstext);

		bw.setId(1);

		return this.bwMapper.insert(bw);

	}

	@Override
	public Bewertung bewerten(Bewerbung bw, String stellungnahme, float wert) throws IllegalArgumentException {

		Bewertung bwt = new Bewertung();

		bwt.setBewerbungId(bw.getId());
		bwt.setErstelldatum(new Date());
		bwt.setStellungnahme(stellungnahme);
		bwt.setWert(wert);

		bwt.setId(1);

		return this.bwtMapper.insert(bwt);

	}

	@Override
	public Beteiligung beteiligen(Projekt pr, Organisationseinheit or, int personentage, Date startdatum, Date enddatum)
			throws IllegalArgumentException {

		Beteiligung bt = new Beteiligung();

		bt.setProjektId(pr.getId());
		bt.setOrganisationseinheitId(or.getId());
		bt.setPersonentage(personentage);
		bt.setStartdatum(startdatum);
		bt.setEnddatum(enddatum);

		bt.setId(1);

		return this.btMapper.insert(bt);

	}

	@Override
	public void projektmarktplatzBeitreten(ProjektMarktplatz pm, Organisationseinheit o)
			throws IllegalArgumentException {

	}

	@Override
	public void save(ProjektMarktplatz pm) throws IllegalArgumentException {

		this.pmMapper.update(pm);

	}

	@Override
	public void save(Projekt pr) throws IllegalArgumentException {

		this.prMapper.update(pr);

	}

	@Override
	public void save(Ausschreibung as) throws IllegalArgumentException {

		this.asMapper.update(as);

	}

	@Override
	public void save(Partnerprofil pp) throws IllegalArgumentException {

		this.ppMapper.update(pp);
	}

	@Override
	public void save(Eigenschaft e) throws IllegalArgumentException {

		this.eMapper.update(e);
	}

	@Override
	public void save(Bewerbung bw) throws IllegalArgumentException {

		this.bwMapper.update(bw);
	}

	@Override
	public void save(Bewertung bwt) throws IllegalArgumentException {

		this.bwtMapper.update(bwt);
	}

	@Override
	public void save(Beteiligung bt) throws IllegalArgumentException {

		this.btMapper.update(bt);
	}

	@Override
	public void save(Person ps) throws IllegalArgumentException {

		this.pMapper.update(ps);
	}

	@Override
	public void save(Unternehmen u) throws IllegalArgumentException {

		this.uMapper.update(u);
	}

	@Override
	public void save(Team t) throws IllegalArgumentException {

		this.tMapper.update(t);
	}

	@Override
	public void delete(ProjektMarktplatz pm) throws IllegalArgumentException {

		this.pmMapper.delete(pm);
	}

	@Override
	public void delete(Projekt pr) throws IllegalArgumentException {

		this.prMapper.delete(pr);

	}

	@Override
	public void delete(Ausschreibung as) throws IllegalArgumentException {

		this.asMapper.delete(as);
	}

	@Override
	public void delete(Partnerprofil pp) throws IllegalArgumentException {

		this.ppMapper.delete(pp);
	}

	@Override
	public void delete(Eigenschaft e) throws IllegalArgumentException {

		this.eMapper.delete(e);
	}

	@Override
	public void delete(Bewerbung bw) throws IllegalArgumentException {

		this.bwMapper.delete(bw);

	}

	@Override
	public void delete(Bewertung bwt) throws IllegalArgumentException {

		this.bwtMapper.delete(bwt);
	}

	@Override
	public void delete(Beteiligung bt) throws IllegalArgumentException {

		this.btMapper.delete(bt);
	}

	@Override
	public void delete(Person ps) throws IllegalArgumentException {

		this.pMapper.update(ps);
	}

	@Override
	public void delete(Unternehmen u) throws IllegalArgumentException {

		this.uMapper.delete(u);
	}

	@Override
	public void delete(Team t) throws IllegalArgumentException {

		this.tMapper.delete(t);
	}
}
