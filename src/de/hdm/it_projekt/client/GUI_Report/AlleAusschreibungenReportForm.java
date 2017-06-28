package de.hdm.it_projekt.client.GUI_Report;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class AlleAusschreibungenReportForm extends ReportShowcase {
	 

	private HTML report = null;
	
	public AlleAusschreibungenReportForm(ProjektMarktplatz pm) {
		
		report = new HTML();
		this.add(report);
		rp.getAlleAusschreibungenReport(pm, new ReportCallback());
		
	}
	
	private class ReportCallback implements AsyncCallback<String> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(String result) {
			report.setText(result);			
		}
		
	}
	
}
