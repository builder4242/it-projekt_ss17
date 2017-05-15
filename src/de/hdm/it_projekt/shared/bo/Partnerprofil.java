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
	 *  Letzte Änderung am Projekt
	 */
	private Date aenderungsdatum;
	
	/**
	 * 
	 */
	private Vector<Eigenschaft> eigenschaften = null;
	
	public Partnerprofil () {
		
		/**
		 * Prüfen ob der Vector Eigenschaften
		 */
		if(eigenschaften == null) {
			this.eigenschaften = new Vector<Eigenschaft>();
		}
	}

	/**
	 * Rückgabe des Erstelldatums
	 * @return the erstelldatum
	 */
	public Date getErstelldatum() {
		return erstelldatum;
	}

	/**
	 * Setzen des Erstelldatums
	 * @param erstelldatum the erstelldatum to set
	 */
	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
	}

	/**
	 * Rückgabe des Änderungsdatums
	 * @return the aenderungsdatum
	 */
	public Date getAenderungsdatum() {
		return aenderungsdatum;
	}

	/**
	 * Setzen des Änderungsdatums
	 * @param aenderungsdatum the aenderungsdatum to set
	 */
	public void setAenderungsdatum(Date aenderungsdatum) {
		this.aenderungsdatum = aenderungsdatum;
	}

	/**
	 * Rückgabe des Vectors Eigenschaften
	 * @return the eigenschaften
	 */
	public Vector<Eigenschaft> getEigenschaften() {
		return eigenschaften;
	}

	/**
	 * Eien weitere Eigenschaft hinzufügen
	 * @param e
	 */
	public void addEigenschaft(Eigenschaft e) {
		this.eigenschaften.add(e);
	}
	
	/**
	 * Eine Eigenschaft löschen
	 * @param Eigenschaft e
	 * @return true wenn das Objekt erfolgreich gelöscht wurde.
	 */
	public boolean deleteEigenschaft(Eigenschaft e) {
		return this.eigenschaften.remove(e);
	}
	
}
