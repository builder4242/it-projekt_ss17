/**
 * 
 */
package de.hdm.it_projekt.server.db;

/**
 * Mapper-Klasse, die <code>Team</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte
 * koennen in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * 
 * @author Tugba Bulat
 */
public class TeamMapper {

	
	/**
	 * Die Klasse TeamMapper wird nur einmal instantiiert.
	 * Man spricht hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für saemtliche eventuellen Instanzen dieser Klasse vorhanden.
	 * Sie speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static TeamMapper teamMapper = null;

	
	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected TeamMapper() {

	}

	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>TeamMapper.teamMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>TeamMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> TeamMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>TeamMapper</code>-Objekt.
	 */
	public static TeamMapper teamMapper() {
		if (teamMapper == null) {
			teamMapper = new TeamMapper();
		}

		return teamMapper;
	}

}
