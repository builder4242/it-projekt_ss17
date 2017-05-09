package de.hdm.it_projekt.shared.bo;

import java.util.Date;

/**
 * 
 * @author Daniel Miedtank
 *
 */
public class Projekt extends BusinessObject {


	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Name des Projektes
	 */
	private String name;
	
	/**
	 *  Startdatum des Projektes
	 */
	private Date startdatum;
	
	/**
	 * Enddatum des Projektes
	 */
	private Date enddatum;
	
	/**
	 * Erweiterte Beschreibung des Projektes
	 */
	private String beschreibung;

	/**
	 * Rückgabe des Namens
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzen des Namens
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Rückgabe des Startdatums
	 * @return the startdatum
	 */
	public Date getStartdatum() {
		return startdatum;
	}

	/**
	 * Setzen des Startdatums
	 * @param startdatum the startdatum to set
	 */
	public void setStartdatum(Date startdatum) {
		this.startdatum = startdatum;
	}

	/**
	 * Rückgabe des Enddatums
	 * @return the enddatum
	 */
	public Date getEnddatum() {
		return enddatum;
	}

	/**
	 * Setzen des Enddatums
	 * @param enddatum the enddatum to set
	 */
	public void setEnddatum(Date enddatum) {
		this.enddatum = enddatum;
	}

	/**
	 * Rückgabe der Beschreibung
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * Setzen der Beschreibung
	 * @param beschreibung the beschreibung to set
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
	
}
