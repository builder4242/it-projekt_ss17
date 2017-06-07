package de.hdm.it_projekt.shared.bo;

/**
 * 
 * @author Daniel Miedtank
 *
 */
public class Eigenschaft extends BusinessObject {


	private static final long serialVersionUID = 1L;
	
	/*Beginn Fremdschluesseldefinitionen*/
	/**
	 * Fremdschluesselbeziehung zu Partnerprofil
	 */
	private int partnerprofilId = 0;
	/*Ende Fremdschluesseldefinitionen*/

	/**
	 * Eigenschaftsname
	 */
	private String name;
	
	/**
	 * Eigenschaftswert
	 */
	private String wert;
		
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
	public String getWert() {
		return wert;
	}

	/**
	 * Setzen des Eigenschaftswertes
	 * @param f the wert to set
	 */
	public void setWert(String f) {
		this.wert = f;
	}
	
	/*Beginn Fremdschluessel Getter und Setter*/
	/**
	 * @return the partnerprofilId
	 */
	public int getPartnerprofilId() {
		return partnerprofilId;
	}

	/**
	 * @param partnerprofilId the partnerprofilId to set
	 */
	public void setPartnerprofilId(int partnerprofilId) {
		this.partnerprofilId = partnerprofilId;
	}
	/*Ende Fremdschluessel Getter und Setter*/
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.name + " " + this.wert;
		  }
}
