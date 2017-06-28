package de.hdm.it_projekt.shared.report;

import java.util.Vector;

import de.hdm.it_projekt.client.GUI_Report.ReportGenerator;
import de.hdm.it_projekt.server.db.AusschreibungMapper;
import de.hdm.it_projekt.shared.bo.Ausschreibung;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

/**
 * 
 * <p>Abfrage von s�mtlichen Ausschreibungen, die dem Partnerprofil des Benutzers entsprechend.</p>
 * 
 * @author Daniel Miedtank
 *
 */
public class AlleAusschreibungenReport extends SimpleReport {

	private static final long serialVersionUID = 1L;

	private Vector<Ausschreibung> ausschreibungen = null;
	
	public AlleAusschreibungenReport(Vector<Ausschreibung> as) {
		super("Alle Ausschreibungen Report");
		this.ausschreibungen = as;
		
		run();
	}
	
	private void run() {
		for(Ausschreibung as : ausschreibungen) {
			this.addRow(as.getBezeichnung());
		}
	}
}
