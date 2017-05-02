package de.hdm.it_projekt.shared.bo;

/**
 * Klasse Bewertung (Unterklasse von BusinessObject)
 * @author Sid Heiland
 *
 */
public class Bewertung extends BusinessObject {
	
	
	/**
	 * Stellungnahme der Bewertung
	 */
	private String stellungnahme;
	
	/**
	 * Wert der Bewertung
	 */
	private float wert;
	
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

}
