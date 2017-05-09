package de.hdm.it_projekt.shared.bo;

import java.util.Date;

/**
 * Klasse Bewerbung (Unterklasse von BusinessObject)
 * @author Sid Heiland
 *
 */
public class Bewerbung extends BusinessObject {
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Erstelldatum der Bewerbung
	 */
	private Date erstelldatum;
	
	/**
	 * Text der Bewerbung
	 */
	private String bewerbungstext;
	
	/**
	 * Bewertung der Bewerbung 
	 */
	private Bewertung bewertung = null;

	/**
	 * Auslesen des Erstelldatums
	 * @return erstelldatum
	 */
	public Date getErstelldatum() {
		return erstelldatum;
	}
	
	/**
	 * Setzen des Erstelldatums
	 * @param erstelldatum
	 */
	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
	}
	
	/**
	 * Auslesen des Bewerbungstext
	 * @return bewerbungstext
	 */
	public String getBewerbungstext() {
		return bewerbungstext;
	}
	
	/**
	 * Setzen des Bewebrungstext
	 * @param bewerbungstext
	 */
	public void setBewerbungstext(String bewerbungstext) {
		this.bewerbungstext = bewerbungstext;
	}
	
	/**
	 *  Rückgabe der Bewertung
	 * @return the bewertung
	 */
	public Bewertung getBewertung() {
		return bewertung;
	}

	/**
	 *  Setzen der Bewertung
	 * @param bewertung the bewertung to set
	 */
	public void setBewertung(Bewertung bewertung) {
		this.bewertung = bewertung;
	}
}
