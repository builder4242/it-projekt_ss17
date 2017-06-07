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
	
	
	/*Beginn Fremdschluesseldefinitionen*/
	/**
	 * Fremdschluesselbeziehung zur Ausschreibung
	 */
	private int ausschreibungId = 0;
	/**
	 * Fremdschluesselbeziehung zur Organisationseinheit
	 */
	private int organisationseinheitId = 0;
	  /*Ende Fremdschluesseldefinitionen*/

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
	
	/*Beginn Fremdschluessel Getter und Setter*/
	/**
	 * @return the ausschreibungId
	 */
	public int getAusschreibungId() {
		return ausschreibungId;
	}

	/**
	 * @param ausschreibungId the ausschreibungId to set
	 */
	public void setAusschreibungId(int ausschreibungId) {
		this.ausschreibungId = ausschreibungId;
	}

	/**
	 * @return the organisationseinheitId
	 */
	public int getOrganisationseinheitId() {
		return organisationseinheitId;
	}

	/**
	 * @param organisationseinheitId the organisationseinheitId to set
	 */
	public void setOrganisationseinheitId(int organisationseinheitId) {
		this.organisationseinheitId = organisationseinheitId;
	}
	
	  /*Ende Fremdschluessel Getter und Setter*/
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.erstelldatum + " " + this.bewerbungstext;
	  }
}
