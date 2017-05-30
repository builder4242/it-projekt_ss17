/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Team;

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

	
	/**
	 * Die Methode ermoeglicht es Team-Objekte in der Datenbank anzulegen. 
	 * 
	 * @param t - das in der Datenbank anzulegende Objekt
	 * @return
	 * @throws Exception
	 */
	public Team insert(Team t) throws Exception {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		PreparedStatement preStmt = null;

		try {
			String sql = "INSERT INTO `Team`(`ID`) VALUES (NULL)";

			preStmt = con.prepareStatement(sql);
			preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
		return t;
	}

	
	/**
	 * Wiederholtes Schreiben eines Team-Objekts in die Datenbank.
	 * 
	 * @param t - das Objekt, das in die Datenbank geschrieben werden soll
	 * @return
	 */
	public Team update(Team t) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Team " + "SET name=\"" + Organisationseinheit.getName() + "\" " + "SET email=\""
					+ Organisationseinheit.getEmail() + "\" " + "SET strasse=\"" + Organisationseinheit.getStrasse()
					+ "\" " + "SET plz=\"" + Organisationseinheit.getPLZ() + "\" " + "SET ort=\""
					+ Organisationseinheit.getOrt() + "\" " + "SET tel=\"" + Organisationseinheit.getTel() + "\" "
					+ "SET googleID=\"" + Organisationseinheit.getGoogleID() + "\" " + "WHERE ID="
					+ Organisationseinheit.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Team t) zu wahren,
		// geben wir t zurueck.
		return t;
	}
	
	
	/**
	 * Loeschen der Daten eines Team-Objekts aus der Datenbank.
	 * 
	 * @param t - das zu loeschende Objekt
	 */
	public void delete (Team t){

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Team " + "WHERE ID=" + Organisationseinheit.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
