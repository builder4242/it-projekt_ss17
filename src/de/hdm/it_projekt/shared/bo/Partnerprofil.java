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
		 * Pruefen ob der Vector Eigenschaften
		 */
		if(eigenschaften == null) {
			this.eigenschaften = new Vector<Eigenschaft>();
		}
	}


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
	 * @return the aenderungsdatum
	 */
	public Date getAenderungsdatum() {
		return aenderungsdatum;
	}



	/**
	 * @param aenderungsdatum the aenderungsdatum to set
	 */
	public void setAenderungsdatum(Date aenderungsdatum) {
		this.aenderungsdatum = aenderungsdatum;
	}



	/**
	 * @param eigenschaften the eigenschaften to set
	 */
	public void setEigenschaften(Vector<Eigenschaft> eigenschaften) {
		this.eigenschaften = eigenschaften;
	}



	/**
	 * Rueckgabe des Vectors Eigenschaften
	 * @return the eigenschaften
	 */
	public Vector<Eigenschaft> getEigenschaften() {
		return eigenschaften;
	}

	/**
	 * Eien weitere Eigenschaft hinzufuegen
	 * @param e
	 */
	public void addEigenschaft(Eigenschaft e) {
		this.eigenschaften.add(e);
	}
	
	/**
	 * Eine Eigenschaft loeschen
	 * @param Eigenschaft e
	 * @return true wenn das Objekt erfolgreich geloescht wurde.
	 */
	public boolean deleteEigenschaft(Eigenschaft e) {
		return this.eigenschaften.remove(e);
	}	
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.aenderungsdatum + " " + this.eigenschaften + " " + this.erstelldatum;
	  }

}
