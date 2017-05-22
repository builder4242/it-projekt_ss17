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
	private static String name;
	
	/**
	 *  Startdatum des Projektes
	 */
	private static Date startdatum;
	
	/**
	 * Enddatum des Projektes
	 */
	private static Date enddatum;
	
	/**
	 * Erweiterte Beschreibung des Projektes
	 */
	private static String beschreibung;

	/**
	 * Rückgabe des Namens
	 * @return the name
	 */
	public static String getName() {
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
	public static Date getStartdatum() {
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
	public static Date getEnddatum() {
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
	public static String getBeschreibung() {
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
