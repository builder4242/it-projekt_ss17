package de.hdm.it_projekt.server.report;

/**
 * 
 * To be Done
 * getBewerbungToAusschreibung - Ausschreibungen auslesen
 * Methode zum auslesen an welchen Projekten eines Marktplatzes eine OE beteiligt ist 
 * Fan-In/Fan-Out Analyse
 * 
 */
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.it_projekt.server.db.*;
import de.hdm.it_projekt.shared.ReportGenerator;
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;

import java.util.Vector;

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

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

	/**
	 * @return
	 * Abfrage von allen Ausschreibugen
	 */
	public Vector<Ausschreibung> getAlleAusschreibungen() {

		return asMapper.findAll();
	}
	/**
	 * 
	 * Ausschreibung zu Partnerprofil suchen
	 * @param pp
	 * @return
	 */

	public Vector<Ausschreibung> getAusschreibungenForPartnerprofil(Partnerprofil pp) {

		return asMapper.getByPartnerprofil(pp);
	}

	/**
	 * Abfragen aller Bewerbungen auf Ausschreibungen des Benutzers 
	 * @param oe
	 * @return
	 */
	public Vector<Ausschreibung> getBewerbungenOnAusschreibung(Organisationseinheit oe) {

		return null;

	}
	/**
	 * Abfrage der eigenen Bewerbungen und den zugehörigen Ausschreibungen des Benutzers
	 * @param o
	 * @return
	 */

	public Vector<Ausschreibung> getBewerbungToAusschreibung(Organisationseinheit o) {

		Vector<Bewerbung> bwV = bwMapper.getByOrganisationseinheit(o);
		Vector<Ausschreibung> asV = new Vector<Ausschreibung>();

		for (Bewerbung bw : bwV) {
			asV.addAll(asMapper.findByBewerbung(bw));
		}

		return asV;
	}
	
	/**
	 * Abfrage von Projektverflechtungen (Teilnahmen und weitere Einreichungen/Bewerbungen)
	 * eines Bewerbers durch den Ausschreibenden.
	 * @param o
	 * @return
	 */

	public Vector<Projekt> getProjektVerflechtungen(Organisationseinheit o) {
		
		Vector<Bewerbung> bwV = bwMapper.getByOrganisationseinheit(o);
		Vector<Projekt> prV = prMapper.findByTeilnehmer(o);
		
		return prV;

	}

}
