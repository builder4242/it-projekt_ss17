package de.hdm.it_projekt.shared.bo;

import java.util.Date;

/**
 * Klasse Beteiligung (Unterklasse von BusinessObject)
 * @author Sid Heiland
 *
 */
public class Beteiligung extends BusinessObject {
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/*Beginn Fremdschluesseldefinitionen*/
	private int projektId = 0;
	private int organisationseinheitId = 0;
	
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
	 * Personentage der Beteiligung
	 */
	private int personentage;
	
	/**
	 * Startdatum der Beteiligung
	 */
	private Date startdatum;
	
	/**
	 * Enddatum der Beteiligung
	 */
	private Date enddatum;
	
	/**
	 * Auslesen der Personentage 
	 * @return personentage
	 */
	public int getPersonentage() {
		return personentage;
	}
	
	/**
	 * Setzen der Personentage
	 * @param personentage
	 */
	public void setPersonentage(int personentage) {
		this.personentage = personentage;
	}
	
	/**
	 * Auslesen des Startdatum
	 * @return startdatum
	 */
	public Date getStartdatum() {
		return startdatum;
	}
	
	/**
	 * Setzen des Startdatum
	 * @param startdatum
	 */
	public void setStartdatum(Date startdatum) {
		this.startdatum = startdatum;
	}
	
	/**
	 * Auslesen des Enddatum
	 * @return enddatum
	 */
	public Date getEnddatum() {
		return enddatum;
	}
	
	/**
	 * Setzen des Enddatum
	 * @param enddatum
	 */
	public void setEnddatum(Date enddatum) {
		this.enddatum = enddatum;
	}
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die
	 * spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.personentage + " " + this.startdatum + " " + this.enddatum;
	  }
	

}
