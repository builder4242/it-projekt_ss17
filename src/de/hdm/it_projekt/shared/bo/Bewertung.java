package de.hdm.it_projekt.shared.bo;
import java.util.Date;

/**
 * Klasse Bewertung (Unterklasse von BusinessObject)
 * @author Sid Heiland
 *
 */
public class Bewertung extends BusinessObject {
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stellungnahme der Bewertung
	 */
	private String stellungnahme;
	
	/**
	 * Wert der Bewertung
	 */
	private float wert;
	
	/**
	 * Erstelldatum der Bewertung
	 */
	private Date erstelldatum;
	
	/*Beginn Fremdschluesseldefinitionen*/
	/**
	 * Fremdschluesselbeziehung zu Bewerbung
	 */
	private int bewerbungId = 0;
	/*Ende Fremdschluesseldefinitionen*/
	
	/**
	 * @return the erstelldatum
	 */
	public Date getErstelldatum() {
		return erstelldatum;
	}

	/**
	 * @param erstelldatum the erstelldatum to set
	 */
	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
	}

	/**
	 * Auslesen der Stellungnahme
	 * @return stellungnahme
	 */
	public String getStellungnahme() {
		return stellungnahme;
	}
	
	/**
	 * Setzen der Stellungnahme
	 * @param stellungnahme
	 */
	public void setStellungnahme(String stellungnahme) {
		this.stellungnahme = stellungnahme;
	}
	
	/**
	 * Auslesen des Wertes
	 * @return
	 */
	public float getWert() {
		return wert;
	}
	
	/**
	 * Setzen des Wertes der Bewertung
	 * @param wert
	 */
	public void setWert(float wert) {
		this.wert = wert;
	}
	
	/*Beginn Fremdschluessel Getter und Setter*/
	/**
	 * @return the bewerbungId
	 */
	public int getBewerbungId() {
		return bewerbungId;
	}

	/**
	 * @param bewerbungId the bewerbungId to set
	 */
	public void setBewerbungId(int bewerbungId) {
		this.bewerbungId = bewerbungId;
	}	
	 /*Ende Fremdschluessel Getter und Setter*/
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.stellungnahme + " " + this.wert;
	}


}
