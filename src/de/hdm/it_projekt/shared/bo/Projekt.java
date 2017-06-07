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
	
	/*Beginn Fremdschluesseldefinitionen*/
	/**
	 * Fremnschluesselbeziehung zu Projektmarktplatz
	 */
	private int projektMarktplatzId = 0;
	/**
	 * Fremdschluesselbeziehung zu Projektbetreiber
	 */
	private int projektbetreiberId = 0;
	/**
	 * Fremdschluesselbeziehung zu Projektleiter
	 */
	private int projektleiterId = 0;
	/*Ende Fremdschluesseldefinitionen*/

	/**
	 * Rueckgabe des Namens
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
	 * R�ckgabe des Startdatums
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
	 * Rueckgabe des Enddatums
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
	 * Rueckgabe der Beschreibung
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
	
	/*Beginn Fremdschluessel Getter und Setter*/
	/**
	 * @return the projektMarktplatzId
	 */
	public int getProjektMarktplatzId() {
		return projektMarktplatzId;
	}

	/**
	 * @param projektMarktplatzId the projektMarktplatzId to set
	 */
	public void setProjektMarktplatzId(int projektMarktplatzId) {
		this.projektMarktplatzId = projektMarktplatzId;
	}

	/**
	 * @return the projektbetreiberId
	 */
	public int getProjektbetreiberId() {
		return projektbetreiberId;
	}

	/**
	 * @param projektbetreiberId the projektbetreiberId to set
	 */
	public void setProjektbetreiberId(int projektbetreiberId) {
		this.projektbetreiberId = projektbetreiberId;
	}

	/**
	 * @return the projektleiterId
	 */
	public int getProjektleiterId() {
		return projektleiterId;
	}

	/**
	 * @param projektleiterId the projektleiterId to set
	 */
	public void setProjektleiterId(int projektleiterId) {
		this.projektleiterId = projektleiterId;
	}
	/*Ende Fremdschluessel Getter und Setter*/
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.beschreibung + " " + this.name + " " + this.enddatum + " " + this.startdatum;
	  }
	
	
}
