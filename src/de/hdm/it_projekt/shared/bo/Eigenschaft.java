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
	 * Rueckgabe des Eigenschaftsnamens
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
	 * Setzen Rueckgabe des Eigenschaftswertes
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
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.name + " " + this.wert;
		  }
}
