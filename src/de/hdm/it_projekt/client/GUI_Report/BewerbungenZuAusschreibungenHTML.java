package de.hdm.it_projekt.client.GUI_Report;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ReportGeneratorAsync;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.report.AlleAusschreibungenReport;
import de.hdm.it_projekt.shared.report.BewerbungenZuAusschreibungenReport;
import de.hdm.it_projekt.shared.report.HTMLReportWriter;


public class BewerbungenZuAusschreibungenHTML extends Showcase {

	private Organisationseinheit o = null;
	
	public BewerbungenZuAusschreibungenHTML(Organisationseinheit o) {
		this.o = o;
	}
	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {

		if (o != null) {
			rp.createBewerbungenZuAusschreibungenReport(o, new BewerbungenZuAusschreibungenReportCallback(this));
		}
	}

	class BewerbungenZuAusschreibungenReportCallback implements AsyncCallback<BewerbungenZuAusschreibungenReport> {
		private Showcase showcase = null;

		public BewerbungenZuAusschreibungenReportCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(BewerbungenZuAusschreibungenReport report) {
			if (report != null) {
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(report);
				this.showcase.append(writer.getReportText());
			}
		}
	}
}
