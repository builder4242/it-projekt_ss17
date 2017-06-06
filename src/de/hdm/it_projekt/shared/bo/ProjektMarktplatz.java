package de.hdm.it_projekt.shared.bo;

/***
	 * 
	 * @author Tugba Bulat
	 *
	 */
public class ProjektMarktplatz extends BusinessObject {


	private static final long serialVersionUID = 1L;

	private String bezeichnung;

	/**
	 * 
	 * @return
	 */
	public String getBezeichnung() {
		return Bezeichnung;
	}

	/**
	 * 
	 * @param bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnungbezeichnung = bezeichnung;
	}
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.bezeichnung;
	  }

}
