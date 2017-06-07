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
	
	/*Beginn Fremdschluesseldefinitionen*/
	private int ausschreibungId = 0;
	private int organisationseinheitId = 0;
	
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
	
	/*Ende Fremdschluesseldefinitionen*/

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
	 *  R�ckgabe der Bewertung
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
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.erstelldatum + " " + this.bewerbungstext;
	  }
}
