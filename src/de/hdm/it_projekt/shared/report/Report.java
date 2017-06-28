package de.hdm.it_projekt.shared.report;

import java.io.Serializable;

import de.hdm.it_projekt.server.report.ReportGeneratorImpl;
import de.hdm.it_projekt.shared.ReportGenerator;

public abstract class Report implements Serializable {


	private static final long serialVersionUID = 1L;

	ReportGeneratorImpl rp = null;
			
	private String titel = "";

	public Report(String t) {
		titel = t;
		rp = new ReportGeneratorImpl();
	}
	
	/**
	 * HTML-Header-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getHeader() {
		StringBuffer result = new StringBuffer();

		result.append("<html><head><title></title></head><body>");
		return result.toString();
	}
	
	public String getTitel() {
		return titel;
	}

	/**
	 * HTML-Trailer-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getTrailer() {
		return "</body></html>";
	}

	public abstract String runHTMLReport();

}
