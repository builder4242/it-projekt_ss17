package de.hdm.it_projekt.shared.bo;

import java.util.Date;

/**
 * Klasse Ausschreibung (Unterklasse von BusinessObject)
 * 
 * @author Sid Heiland
 *
 */
public class Ausschreibung extends BusinessObject {
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Bezeichnung der Ausschreibung
	 */
	private String bezeichnung;

	/**
	 * Bewerbungsfrist der Ausschreibung
	 */
	private Date bewerbungsfrist;

	/**
	 * Text der Ausschreibung
	 */
	private String ausschreibungstext;

	/**
	 * Partnerprofil zur Ausschreibung
	 */
	private int partnerprofilId;

	/**
	 * Auslesen der Bezeichnung der Ausschreibung
	 * 
	 * @return bezeichnung
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * Setzen der Bezeichnung der Ausschreibung
	 * 
	 * @param bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	/**
	 * Auslesen der Bewerbungsfrist der Ausschreibung
	 * 
	 * @return bewerbungsfrist
	 */
	public Date getBewerbungsfrist() {
		return bewerbungsfrist;
	}

	/**
	 * Setzen der Bewerbungsfrist der Ausschreibung
	 * 
	 * @param bewerbungsfrist
	 */
	public void setBewerbungsfrist(Date bewerbungsfrist) {
		this.bewerbungsfrist = bewerbungsfrist;
	}

	/**
	 * Auslesen des Ausschreibungstext der Ausschreibung
	 * 
	 * @return ausschreibungstext
	 */
	public String getAusschreibungstext() {
		return ausschreibungstext;
	}

	/**
	 * Setzen des Ausschreibungstext der Ausschreibung
	 * 
	 * @param ausschreibungstext
	 */
	public void setAusschreibungstext(String ausschreibungstext) {
		this.ausschreibungstext = ausschreibungstext;
	}
		
	/**
	 * @return the partnerprofil
	 */
	public int getPartnerprofilId() {
		return partnerprofilId;
	}

	/**
	 * @param partnerprofil the partnerprofil to set
	 */
	public void setPartnerprofilId(int id) {
		this.partnerprofilId = id;
	}
	
/**
 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die
 * spezifischen Attribute dieser Klasse aus
 */
	public String toString() {
	    return super.toString() + " " + this.bezeichnung + " " + this.bewerbungsfrist + " " + this.ausschreibungstext;
	  }

}
