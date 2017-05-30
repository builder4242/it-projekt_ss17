package de.hdm.it_projekt.shared.bo;

/***
	 * 
	 * @author Tugba Bulat
	 *
	 */
public class ProjektMarktplatz extends BusinessObject {


	private static final long serialVersionUID = 1L;

	private String Bezeichnung;

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
		Bezeichnung = bezeichnung;
	}
	

}
