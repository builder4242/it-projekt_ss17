package de.hdm.it_projekt.shared.bo;

import java.util.Date;
import java.util.Vector;

/**
 * 
 * @author Daniel Miedtank
 *
 */
public class Partnerprofil extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Datum an dem das Projekt erstellt wurde.
	 */
	private Date erstelldatum;

	/**
	 * Letzte Änderung am Projekt
	 */
	private Date aenderungsdatum;

	/**
	 * @return the erstelldatum
	 */
	public Date getErstelldatum() {
		return erstelldatum;
	}

	/**
	 * @param erstelldatum
	 *            the erstelldatum to set
	 */
	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
	}

	/**
	 * @return the aenderungsdatum
	 */
	public Date getAenderungsdatum() {
		return aenderungsdatum;
	}

	/**
	 * @param aenderungsdatum
	 *            the aenderungsdatum to set
	 */
	public void setAenderungsdatum(Date aenderungsdatum) {
		this.aenderungsdatum = aenderungsdatum;
	}

	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode
	 * die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
		return super.toString() + " " + this.aenderungsdatum + " " + this.erstelldatum;
	}

}
