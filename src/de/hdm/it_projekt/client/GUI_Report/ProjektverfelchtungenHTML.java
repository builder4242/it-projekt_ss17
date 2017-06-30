package de.hdm.it_projekt.client.GUI_Report;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.HTMLReportWriter;
import de.hdm.it_projekt.shared.report.ProjektverflechtungenReport;


public class ProjektverfelchtungenHTML extends Showcase {

	private Organisationseinheit o = null;
	private ProjektMarktplatz cpm = null;
	
	public ProjektverfelchtungenHTML(Organisationseinheit o, ProjektMarktplatz pm) {
		this.o = o;
		this.cpm = pm;
	}
	
	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {

		if (this.o != null && cpm != null) {
			rp.createProjektverflechtungenReport(this.o, this.cpm, new ProjektverflechtungenReportCallback(this));
		}
	}

	class ProjektverflechtungenReportCallback implements AsyncCallback<ProjektverflechtungenReport> {
		private Showcase showcase = null;

		public ProjektverflechtungenReportCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(ProjektverflechtungenReport report) {
			if (report != null) {
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(report);
				this.showcase.append(writer.getReportText());
			}
		}
	}
}
