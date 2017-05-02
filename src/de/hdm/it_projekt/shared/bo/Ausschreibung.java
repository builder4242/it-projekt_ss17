package de.hdm.it_projekt.shared.bo;

import java.util.*;

public class Ausschreibung extends BusinessObject {

	private String bezeichnung;
	private Date bewerbungsfrist;
	private String ausschreibungstext;
	
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public Date getBewerbungsfrist() {
		return bewerbungsfrist;
	}
	public void setBewerbungsfrist(Date bewerbungsfrist) {
		this.bewerbungsfrist = bewerbungsfrist;
	}
	public String getAusschreibungstext() {
		return ausschreibungstext;
	}
	public void setAusschreibungstext(String ausschreibungstext) {
		this.ausschreibungstext = ausschreibungstext;
	}
	
	
}
