/**
 * 
 */
package de.hdm.it_projekt.server.db;

/**
 * Mapper-Klasse, die <code>Beteiligung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * @author ElifY
 */
public class BeteiligungMapper {

  /**
   * Die Klasse BeteiligungMapper wird nur einmal instantiiert. Man spricht hierbei
   * von einem sogenannten <b>Singleton</b>.
   * <p>
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   */
  private static BeteiligungMapper beteiligungMapper = null;

  /**
   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
   * Instanzen dieser Klasse zu erzeugen.
   */
  protected BeteiligungMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>BeteiligungMapper.beteiligungMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
   * Instanz von <code>BeteiligungMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> BeteiligungMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>BeteiligungMapper</code>-Objekt.
   */
  public static BeteiligungMapper beteiligungMapper() {
    if (beteiligungMapper == null) {
      beteiligungMapper = new BeteiligungMapper();
    }

    return beteiligungMapper;
  }
}
