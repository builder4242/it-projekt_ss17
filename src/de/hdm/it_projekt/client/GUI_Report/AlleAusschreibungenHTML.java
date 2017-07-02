package de.hdm.it_projekt.client.GUI_Report;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.HTMLReportWriter;

/**
 * 
 * @author Anlehnung an Thies
 *
 */
public class AlleAusschreibungenHTML extends Showcase {

	private ProjektMarktplatz pm = null;
	
	public AlleAusschreibungenHTML(ProjektMarktplatz cpm) {
		this.pm = cpm;
	}
	
	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {

		if (this.pm != null) {
			rp.createAlleAusschreibungenReport(this.pm, new AlleAusschreibungenReportCallback(this));
		}
	}

	class AlleAusschreibungenReportCallback implements AsyncCallback<AlleAusschreibungenReport> {
		private Showcase showcase = null;

		public AlleAusschreibungenReportCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(AlleAusschreibungenReport report) {
			if (report != null) {
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(report);
				this.showcase.append(writer.getReportText());
			}
		}
	}
}
