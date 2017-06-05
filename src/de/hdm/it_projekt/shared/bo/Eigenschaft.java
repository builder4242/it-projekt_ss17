package de.hdm.it_projekt.shared.bo;

/**
 * 
 * @author Daniel Miedtank
 *
 */
public class Eigenschaft extends BusinessObject {


	private static final long serialVersionUID = 1L;

	/**
	 * Eigenschaftsname
	 */
	private String name;
	
	/**
	 * Eigenschaftswert
	 */
	private float wert;

	/**
	 * Default Konstruktor
	 */
	public Eigenschaft() {
		
	}
		
	/**
	 * Rückgabe des Eigenschaftsnamens
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzen des Eigenschaftsnamens
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setzen Rückgabe des Eigenschaftswertes
	 * @return Wert
	 */
	public float getWert() {
		return wert;
	}

	/**
	 * Setzen des Eigenschaftswertes
	 * @param f the wert to set
	 */
	public void setWert(float f) {
		this.wert = f;
	}
}
