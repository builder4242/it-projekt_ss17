package de.hdm.it_projekt.server.report;

/**
 * 
 * To be Done
 * createMatchingAusschreibungenReport - Methode die Vektor zurück gibt in Projektadministartion
 * createbAusschreibungZuBewerbungReport - methode für das auslesen einer ausschreibung auf die sich beworben wurde
 * createbAusschreibungZuBewerbungReport - fertig machen 
 * Abfrage von Projektverflechtungen (Teilnahmen und weitere Einreichungen/Bewerbungen) eines Bewerbers durch den Ausschreibenden. 
 * fan out - warten wie status von bewerbung ausgelesen werden kann 
 * fan in - methode auslesen der Ausschreibungen nach oe fehlt
 * 
 *
 * Durchführung einer Fan-in/Fan-out-Analyse: Zu allen Teilnehmern kann jeweils die Anzahl von Bewerbungen 
 * (laufende, abgelehnte, ange- nommene) (eine Art Fan-out) und deren Anzahl von Ausschreibungen (erfolgreich besetzte, ab- gebrochene, laufende, also Fan-in) 
 * tabellarisch aufgeführt werden.(Fan out gibt ab 1: n, fan in nimmt auf n:1 
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
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AlleBewerbungenReport;
import de.hdm.it_projekt.shared.report.AnzahlAusschreibungenReport;
import de.hdm.it_projekt.shared.report.AnzahlBewerbungenReport;
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

	/**
	 * Report für alle Ausschreibungen die im Projekt vorhanden sind
	 */
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

	/**
	 * Report über alle Ausschreibungen die zum eigenen Partnerprofil passen.
	 * 
	 * @param pp
	 * @return
	 * @throws IllegalArgumentException
	 */

	public AlleAusschreibungenReport createMatchingAusschreibungenReport(Partnerprofil pp)
			throws IllegalArgumentException {
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
		Vector<Ausschreibung> ausschreibung = this.administration.getAusschreibungBy(pp);

		for (Ausschreibung a : ausschreibung) {
			Row ausschreibungRow = new Row();
			ausschreibungRow.addColumn(new Column(String.valueOf(this.administration.getAusschreibungBy(pp))));
			result.addRow(ausschreibungRow);
		}
		return result;
	}

	/**
	 * Report über alle Bewerbungen auf eigene Ausschreibung
	 * 
	 * @param as
	 * @return
	 * @throws IllegalArgumentException
	 */
	public AlleBewerbungenReport createBewerbungenAufAusschreibungReport(Ausschreibung as)
			throws IllegalArgumentException {
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

	/**
	 * Abfrage der eigenen Bewerbungen und den zu- gehörigen Ausschreibungen
	 * 
	 * @param bw
	 * @return
	 * @throws IllegalArgumentException
	 */
	public AlleBewerbungenReport createbAusschreibungZuBewerbungReport(Bewerbung bw) throws IllegalArgumentException {
		if (this.getProjektAdministration() == null)
			return null;
		AlleBewerbungenReport result = new AlleBewerbungenReport();
		result.setTitle("Ausschreibung zu Bewerbung");
		result.setCreated(new Date());

		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Ausschreibung zu Bewerbung"));
		Row headline = new Row();

		headline.addColumn(new Column("Ausschreibung"));
		headline.addColumn(new Column("Bewerbung"));

		result.addRow(headline);

		Vector<Ausschreibung> ausschreibung = this.administration.getAusschreibungBy(bw);

		for (Ausschreibung a : ausschreibung) {
			Row ausschreibungRow = new Row();
			ausschreibungRow.addColumn(new Column(String.valueOf(bw)));
			ausschreibungRow.addColumn(new Column(String.valueOf(this.administration.getAusschreibungBy(bw))));
			result.addRow(ausschreibungRow);
		}
		return result;
	}

	/**
	 * Durchführung einer Fan-out-Analyse: Zu allen Teilnehmern kann
	 * jeweils die Anzahl von Bewerbungen (laufende, abgelehnte, ange- nommene)
	 * (eine Art Fan-out) 
	 * 
	 * @param oe
	 * @return
	 */

	public AnzahlBewerbungenReport fanOutReport(Organisationseinheit oe) throws IllegalArgumentException {
		if (this.getProjektAdministration() == null)
			return null;
		AnzahlBewerbungenReport result = new AnzahlBewerbungenReport();
		result.setTitle("Fan Out Analyse");
		result.setCreated(new Date());

		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Abgelehnte Bewerbungen"));
		Row headline = new Row();

		headline.addColumn(new Column("Ausschreibung"));
		headline.addColumn(new Column("Laufende"));
		
		result.addRow(headline);

		Vector<Beteiligung> laufende = this.administration.getBeteiligungenFor(oe);

		for (Beteiligung b : laufende) {
			Row beteiligungRow = new Row();
			beteiligungRow.addColumn(new Column(String.valueOf(oe)));
			beteiligungRow.addColumn(new Column(String.valueOf(this.administration.getBeteiligungenFor(oe))));
			result.addRow(beteiligungRow);
		}
		headline = null;
		
		headline.addColumn(new Column("Ausschreibung"));
		headline.addColumn(new Column("Abgelehnt"));
		result.addRow(headline);

		Vector<Beteiligung> abgelehnt = this.administration.getBeteiligungenFor(oe);

		for (Beteiligung b : abgelehnt) {
			Row beteiligungRow = new Row();
			beteiligungRow.addColumn(new Column(String.valueOf(oe)));
			beteiligungRow.addColumn(new Column(String.valueOf(this.administration.getBeteiligungenFor(oe))));
			result.addRow(beteiligungRow);
		}
		headline = null;
		
		headline.addColumn(new Column("Ausschreibung"));

		result.addRow(headline);

		Vector<Beteiligung> beteiligung = this.administration.getBeteiligungenFor(oe);

		for (Beteiligung b : beteiligung) {
			Row beteiligungRow = new Row();
			beteiligungRow.addColumn(new Column(String.valueOf(oe)));
			beteiligungRow.addColumn(new Column(String.valueOf(this.administration.getBeteiligungenFor(oe))));
			result.addRow(beteiligungRow);
		}

		
		return result;
	}
	/**
	 * und deren Anzahl von Ausschreibungen (erfolgreich
	 * besetzte, ab- gebrochene, laufende, also Fan-in) tabellarisch aufgeführt
	 * werden.(Fan out gibt ab 1: n, fan in nimmt auf n:1 FAn in analyse
	 * 
	 */

	public AnzahlAusschreibungenReport fanInAnalyse(Projekt pr) throws IllegalArgumentException  {

		if (this.getProjektAdministration() == null)
			return null;
		AnzahlAusschreibungenReport result = new AnzahlAusschreibungenReport();
		result.setTitle("Fan In Analyse");
		result.setCreated(new Date());
		
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Laufende Ausschreibungen:"));
		Row headline = new Row();
		//laufende Ausschreibungen anzeigen
		headline.addColumn(new Column ("Ausschreibung"));
		headline.addColumn(new Column ("Status"));
		result.addRow(headline);
	
		Vector<Ausschreibung> laufende = this.administration.getAusschreibungFor(pr);
				
				for(Ausschreibung a : laufende){
					Row laufendeRow = new Row();
					laufendeRow.addColumn(new Column(String.valueOf(pr)));
					laufendeRow.addColumn(new Column(String.valueOf(this.administration.getAusschreibungFor(pr))));
				}
		// erfolgreiche Ausschreibungen anzeigen
		header.addSubParagraph(new SimpleParagraph("Erfolgreiche Ausschreibungen:"));
		headline.addColumn(new Column("Ausschreibung"));
		headline.addColumn(new Column("Erfolgreich"));
		result.addRow(headline);
		
		Vector<Beteiligung> erfolgreich =  this.administration.getBeteiligungenFor(pr);
				for ( Beteiligung b : erfolgreich){
					Row erfolgreichRow = new Row();
					erfolgreichRow.addColumn(new Column( String.valueOf(pr)));
					erfolgreichRow.addColumn(new Column(String.valueOf(this.administration.getBeteiligungenFor(pr))));
				}
		// abgebrochene Ausschreibungen anzeigen 
		header.addSubParagraph( new SimpleParagraph("Abgebrochene Ausschreibungen:"));
		headline.addColumn(new Column("Ausschreibung"));
		headline.addColumn(new Column("Abgebrochene"));
		result.addRow(headline);
		
		Vector<Ausschreibung> abgebrochen = this.administration
				
			for (Ausschreibung a : abgebrochen){
				Row abgebrochenRow = new Row();
				abgebrochenRow.addColumn(new Column (String.valueOf(pr)));
				abgebrochenRow.addColumn(new Column(String.valueOf(this.administration.)));// ergänzen!!!
				result.addRow(headline);
			}
		
		return result;

	}

	/* (non-Javadoc)
	 * @see de.hdm.it_projekt.shared.ReportGenerator#createAlleBewerbungenReport(de.hdm.it_projekt.shared.bo.Ausschreibung)
	 */
	@Override
	public AlleBewerbungenReport createAlleBewerbungenReport(Ausschreibung as) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public 
}
