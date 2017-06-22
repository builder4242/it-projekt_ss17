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

import de.hdm.it_projekt.server.ProjektAdministrationImpl;
import de.hdm.it_projekt.server.db.*;
import de.hdm.it_projekt.shared.ProjektAdministration;
import de.hdm.it_projekt.shared.ReportGenerator;
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AlleBewerbungenReport;
import de.hdm.it_projekt.shared.report.Column;
import de.hdm.it_projekt.shared.report.CompositeParagraph;
import de.hdm.it_projekt.shared.report.Row;
import de.hdm.it_projekt.shared.report.SimpleParagraph;

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

	private ProjektAdministration administration = null;

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

		ProjektAdministrationImpl a = new ProjektAdministrationImpl();
		a.init();
		this.administration = a;
	}

	protected ProjektAdministration getProjektAdministration() {
		return this.administration;
	}

	public AlleAusschreibungenReport createAlleAusschreibungenReport(Projekt p) throws IllegalArgumentException {

		if (this.getProjektAdministration() == null)
			return null;
		AlleAusschreibungenReport result = new AlleAusschreibungenReport();
		result.setTitle("Alle Ausschreibungen des Projekts");
		result.setCreated(new Date());

		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph(p.getName()));
		Row headline = new Row();

		headline.addColumn(new Column("Ausschreibung"));

		result.addRow(headline);
		Vector<Ausschreibung> ausschreibung = this.administration.getAusschreibungFor(p);

		for (Ausschreibung a : ausschreibung) {
			Row ausschreibungRow = new Row();
			ausschreibungRow.addColumn(new Column(String.valueOf(this.administration.getAusschreibungFor(p))));
			result.addRow(ausschreibungRow);
		}
		return result;
	}
	
	public AlleAusschreibungenReport createMatchingAusschreibungenReport(Partnerprofil pp) throws IllegalArgumentException
	{
		if (this.getProjektAdministration() == null)
			return null;
		AlleAusschreibungenReport result = new AlleAusschreibungenReport();
		result.setTitle("Alle Ausschreibungen passend zum Partnerprofil");
		result.setCreated(new Date());

		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Profil ID:" + pp.getId()));
		Row headline = new Row();

		headline.addColumn(new Column("Ausschreibung"));

		result.addRow(headline);
		Vector<Ausschreibung> ausschreibung = this.administration.getAusschreibungby(pp);

		for (Ausschreibung a : ausschreibung) {
			Row ausschreibungRow = new Row();
			ausschreibungRow.addColumn(new Column(String.valueOf(this.administration.getAusschreibungby(p))));
			result.addRow(ausschreibungRow);
		}
		return result;
	}
	
	public AlleBewerbungenReport createBewerbungenAufAusschreibungReport(Ausschreibung as) throws IllegalArgumentException
	{
		if (this.getProjektAdministration() == null)
			return null;
		AlleBewerbungenReport result = new AlleBewerbungenReport();
		result.setTitle("Alle Bewerbungen auf Ausschreibungen");
		result.setCreated(new Date());

		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Bewerbungen auf Aussreibung"));
		Row headline = new Row();

		headline.addColumn(new Column("Ausschreibung"));
		headline.addColumn(new Column("Bewerbung"));

		result.addRow(headline);
		
		Vector<Bewerbung> bewerbung = this.administration.getBewerbungFor(as);

		for (Bewerbung b : bewerbung) {
			Row bewerbungRow = new Row();
			bewerbungRow.addColumn(new Column(String.valueOf(as)));
			bewerbungRow.addColumn(new Column(String.valueOf(this.administration.getBewerbungFor(as))));
			result.addRow(bewerbungRow);
		}
		return result;
	}
	
}
