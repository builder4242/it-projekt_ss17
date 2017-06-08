package de.hdm.it_projekt.shared.bo;

/***
 * 
 * @author Tugba Bulat
 *
 */
public class ProjektMarktplatz extends BusinessObject {

	
	private String bezeichnung;

	/**
	 * 
	 * @return
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * 
	 * @param bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode
	 * die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
		return super.toString() + " " + this.bezeichnung;
	}
}
