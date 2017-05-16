/**
 * 
 */
package de.hdm.it_projekt.server.db;

	/**
	 * Mapper-Klasse, die <code>Projektmarktplatz</code>-Objekte auf eine relationale
	 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
	 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
	 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
	 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
	 * <p>
	 * 
	 * Anlehnung an @author Thies
	 * @author Tugba
	 *
	 */
public class ProjektmarktplatzMapper {

	/**
	 * Die Klasse ProjektmarktplatzMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	  private static ProjektmarktplatzMapper projektmarktplatzMapper = null;
	  
	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	   * Instanzen dieser Klasse zu erzeugen.
	   * 
	   */
	  protected ProjektmarktplatzMapper() {
	  }
	  
	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>ProjektmarktplatzMapper.projektmarktplatzMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von <code>ProjektmarktplatzMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> ProjektmarktplatzMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>ProjektmarktplatzMapper</code>-Objekt.
	   * @see projektmarktplatzMapper
	   */
	  public static ProjektmarktplatzMapper projektmarktplatzMapper() {
	    if (projektmarktplatzMapper == null) {
	    	projektmarktplatzMapper = new ProjektmarktplatzMapper();
	    }

	    return projektmarktplatzMapper;
	  }
	
}
