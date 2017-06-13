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
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;

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
	 * 
	 */
	public AusschreibungMapper getAlleAusschreibungen() {
		// TODO Auto-generated method stub
		asMapper.findAll();
		return asMapper;
	}

	public AusschreibungMapper getAusschreibungenForPartnerprofil(Partnerprofil pp) {

		asMapper.getByPartnerprofil(pp);

		return asMapper;

	}

	public BewerbungMapper getBewerbungenOnAusschreibung(Ausschreibung a) {
		
		bwMapper.getByAusschreibung(a);

		return bwMapper;

	}
	
	public BewerbungMapper getBewerbungToAusschreibung (Organisationseinheit o){
		bwMapper.getByOrganisationseinheit(o);
		
		return bwMapper;
		
	}
	 

}
