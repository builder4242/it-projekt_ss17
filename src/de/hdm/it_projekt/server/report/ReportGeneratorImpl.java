package de.hdm.it_projekt.server.report;

/**
 * 
 * To be Done
 * createMatchingAusschreibungenReport - Methode die Vektor zurück gibt in Projektadministartion
 * fan in analyse methode zum auslesen abgebrochener Ausschreibungen
 */
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.it_projekt.server.ProjektAdministrationImpl;
import de.hdm.it_projekt.server.db.*;
import de.hdm.it_projekt.shared.ProjektAdministration;
import de.hdm.it_projekt.shared.ReportGenerator;
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AlleBewerbungenReport;
import java.util.Date;
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

	private ProjektAdministration pa = null;

	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

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

		ProjektAdministrationImpl pa = new ProjektAdministrationImpl();
		pa.init();
	}

	@Override
	public String getAlleAusschreibungenReport(ProjektMarktplatz pm) throws IllegalArgumentException {
		
		AlleAusschreibungenReport report = new AlleAusschreibungenReport(asMapper.getByProjektmarktplatz(pm));
		
		return report.runHTMLReport();
	}

	@Override
	public String getMatchingAusschreibungenReport(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBewerbungenAufAusschreibungReport(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBewerbungenReport(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProjektverflechtungenReport(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Ausschreibung> getAlleAusschreibungenFor(ProjektMarktplatz pm) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return asMapper.getByProjektmarktplatz(pm);
	}

	@Override
	public Vector<Ausschreibung> getMatchingAusschreibungenFor(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Ausschreibung> getAusschreibungFor(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Bewerbung> getBewerbungenFor(Ausschreibung as) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Bewerbung> getBewerbungenFor(Organisationseinheit o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Organisationseinheit> getBewerberForAusschreibenden(Organisationseinheit o)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}



}
