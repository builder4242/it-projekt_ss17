
package de.hdm.it_projekt.server.db;

/**
 * Anlehnung an @author Thies
 * @author ElifY
 * 
 * Mapper-Klasse, die <code>Bewertung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 */
public class BewertungMapper {

	/**
	   * Die Klasse BewertungMapper wird nur einmal instantiiert. Man spricht
	   * hierbei von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   */
	  private static BewertungMapper bewertungMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen. 
	   */
	  protected BewertungMapper() {
	  }

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>BewertungMapper.bewertungMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von <code>BewertungMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> BewertungMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>BewertungMapper</code>-Objekt.
	   */
	  public static BewertungMapper bewertungMapper() {
	    if (bewertungMapper == null) {
	      bewertungMapper = new BewertungMapper();
	    }

	    return bewertungMapper;
	  }

}

