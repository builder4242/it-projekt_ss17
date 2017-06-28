package de.hdm.it_projekt.shared.report;

import java.util.Vector;

public class SimpleReport extends Report {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Vector<String> rows = null;
	private String tHead = "";
	
	public SimpleReport(String t) {
		super(t);
		rows = new Vector<String>();
	}
	
	public void setTHead(String t) {
		this.tHead = t;
	}
	
	public void addRow(String row) {
		rows.add(row);
	}

	private String generateContent() {
		
		StringBuffer html = new StringBuffer();
		
		html.append("<h1>" + this.getTitel() + "</h1>");
		html.append("<table border=1><tr><th>" + this.tHead + "</th></tr>");
		
		for(String r : this.rows) {
			html.append("<tr><td>" + r + "</td></tr>");
		}
		
		html.append("</table>");
		
		return html.toString();
	}
	
	@Override
	public String runHTMLReport() {
		return getHeader() + generateContent() + getTrailer();
	}
}
