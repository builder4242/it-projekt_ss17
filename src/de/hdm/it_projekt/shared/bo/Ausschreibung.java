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
	
	/*Beginn Fremdschluesseldefinitionen*/
	
	private int projektId = 0;
	private int partnerprofilId = 0;
	

	/**
	 * @return the projektId
	 */
	public int getProjektId() {
		return projektId;
	}

	/**
	 * @param projektId the projektId to set
	 */
	public void setProjektId(int projektId) {
		this.projektId = projektId;
	}

	/**
	 * @return the partnerprofilId
	 */
	public int getPartnerprofilId() {
		return partnerprofilId;
	}

	/**
	 * @param partnerprofilId the partnerprofilId to set
	 */
	public void setPartnerprofilId(int partnerprofilId) {
		this.partnerprofilId = partnerprofilId;
	}
	
	/*Ende Fremdschluesseldefinitionen*/


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
 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die
 * spezifischen Attribute dieser Klasse aus
 */
	public String toString() {
	    return super.toString() + " " + this.bezeichnung + " " + this.bewerbungsfrist + " " + this.ausschreibungstext;
	  }

}
