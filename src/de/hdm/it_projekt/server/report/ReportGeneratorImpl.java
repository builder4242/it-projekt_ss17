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

		ProjektAdministrationImpl pa = new ProjektAdministrationImpl();
		pa.init();
		this.administration = pa;
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
	public AlleBewerbungenReport createAusschreibungZuBewerbungReport(Bewerbung bw) throws IllegalArgumentException {
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
	 * Durchführung einer Fan-out-Analyse: Zu allen Teilnehmern kann jeweils
	 * die Anzahl von Bewerbungen (laufende, abgelehnte, ange- nommene) (eine
	 * Art Fan-out)
	 * 
	 * @param oe
	 * @return
	 */

	@SuppressWarnings("null")
	public AnzahlBewerbungenReport fanOutReport(Organisationseinheit oe) throws IllegalArgumentException {
		if (this.getProjektAdministration() == null)
			return null;
		AnzahlBewerbungenReport result = new AnzahlBewerbungenReport();

		 result.setTitle("Fan Out Analyse");
		 result.setCreated(new Date());
		
		 CompositeParagraph header = new CompositeParagraph();
		 header.addSubParagraph(new SimpleParagraph("Anzahl laufender, abgelehnter sowie angenommener Bewerbungen"));
		 Row headline = new Row();
		
		int j=0;
		 /**
		 *Laufende ausschreibungen zählen 
		 */
		headline.addColumn(new Column ("Anzahl laufender Bewerbungen:"));
			 
			
		Vector<Bewerbung> laufende = this.administration.
					
			for(int a; a< laufende.size();a++){
					
					j++;
				}
			headline.addColumn(new Column ("Anzahl: "+j));
			result.addRow(headline);
			j=0;
		 /**
		 *Laufende ausschreibungen zählen 
		 */
			
		headline.addColumn(new Column ("Anzahl abgelehnter Bewerbungen:"));
					 
					
		Vector<Bewerbung> abgelehnt = this.administration
							
			for(int a; a< laufende.size();a++){
								
					j++;
				}
				
			headline.addColumn(new Column ("Anzahl: "+j));
			result.addRow(headline);
			j=0;
				
			 /**
			 *Laufende ausschreibungen zählen 
			 */
			headline.addColumn(new Column ("Anzahl erfolgreicher Bewerbungen:"));
						 
						
			Vector<Beteiligung> angenommen  = this.administration.getBeteiligungenFor(oe);
								
				for(int a; a< laufende.size();a++){
									
						j++;
					}
					
			headline.addColumn(new Column ("Anzahl: "+j));
			result.addRow(headline);
		return result;
	}

	/**
	 * und deren Anzahl von Ausschreibungen (erfolgreich
	 * besetzte, ab- gebrochene, laufende, also Fan-in) tabellarisch aufgeführt
	 * werden.(Fan out gibt ab 1: n, fan in nimmt auf n:1 FAn in analyse
	 * 
	 */

	public AnzahlAusschreibungenReport fanInAnalyse(Projekt pr) throws IllegalArgumentException  {

		int i= 0;
		
		if (this.getProjektAdministration() == null)
			return null;
		
		AnzahlAusschreibungenReport result = new AnzahlAusschreibungenReport();
		
		result.setTitle("Fan In Analyse");
		
		result.setCreated(new Date());
		
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Laufende Ausschreibungen:"));
		
		Row headline = new Row();
		
		/**
		 *Laufende ausschreibungen zählen 
		 */
		headline.addColumn(new Column ("Anzahl:"));
		 
		
		Vector<Ausschreibung> laufende = this.administration.getAusschreibungFor(pr);
				
				for(int a=0; a< laufende.size();a++){
					
					i++;
				}
				headline.addColumn(new Column ("Anzahl: "+i));
				result.addRow(headline);
				i=0;
				
				
				/**
				 *Laufende erfolgreiche zählen 
				 */
		header.addSubParagraph(new SimpleParagraph("Erfolgreiche Ausschreibungen:"));
		
		
		Vector<Beteiligung> erfolgreich =  this.administration.getBeteiligungenFor(pr);
				
		for (int a=0; a< erfolgreich.size();a++){
					
					i++;
				}
				headline.addColumn(new Column ("Anzahl: "+i));
				result.addRow(headline);
				i=0;
				
				/**
				 *Laufende abgebrochene zählen 
				 */
		header.addSubParagraph( new SimpleParagraph("Abgebrochene Ausschreibungen:"));
	
		Vector<Ausschreibung> abgebrochen = this.administration.getAusschreibungFor(pr);
				
				for(Ausschreibung a: abgebrochen){
					if (a.getBewerbungsfrist().before(result.getCreated())) 
						i++;
					else{
						
					}
				}
		headline.addColumn(new Column ("Anzahl: "+i));
		result.addRow(headline);
		

		return result;

	}

	/**
	 * 
	 * Report der alle Projektverflechtungen einer OE ausgibt
	 * 
	 * @param oe
	 * @return
	 * @throws IllegalArgumentException
	 */
	public AlleBewerbungenReport createProjektverflechtungenReport(Organisationseinheit oe)
			throws IllegalArgumentException {
		if (this.getProjektAdministration() == null)
			return null;
		AlleBewerbungenReport result = new AlleBewerbungenReport();
		result.setTitle("Projektverflechtungen");
		result.setCreated(new Date());

		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Projektverflechtungen der Organisationseinheit " + oe.getName()));
		Row headline = new Row();
		headline.addColumn(new Column("Organistaionseinheit"));
		headline.addColumn(new Column("Beteiligung an Projekt"));

		result.addRow(headline);

		Vector<Beteiligung> beteiligung = this.administration.getBeteiligungenFor(oe);

		for (Beteiligung b : beteiligung) {
			Row beteiligungRow = new Row();
			beteiligungRow.addColumn(new Column(oe.getName().toString()));
			beteiligungRow.addColumn(new Column(String.valueOf(this.administration.getBeteiligungenFor(oe))));
		}

		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hdm.it_projekt.shared.ReportGenerator#createAlleBewerbungenReport(de.
	 * hdm.it_projekt.shared.bo.Ausschreibung)
	 */
	@Override
	public AlleBewerbungenReport createAlleBewerbungenReport(Ausschreibung as) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hdm.it_projekt.shared.ReportGenerator#
	 * createbAusschreibungZuBewerbungReport(de.hdm.it_projekt.shared.bo.
	 * Bewerbung)
	 */
	@Override
	public AlleBewerbungenReport createbAusschreibungZuBewerbungReport(Bewerbung bw) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
}
