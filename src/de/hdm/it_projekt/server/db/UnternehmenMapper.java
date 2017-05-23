/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.client.ProjektMarktplatz;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Unternehmen;

/**
 * Mapper-Klasse, die <code>Unternehmen</code>-Objekte auf eine relationale
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
public class UnternehmenMapper {

	/**
	 * Die Klasse UnternehmenMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static UnternehmenMapper unternehmenMapper = null;

	/***
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen
	 */
	protected UnternehmenMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>UnternehmenMapper.unternehmenMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>UnternehmenMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> UnternehmenMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>UnternehmenMapper</code>-Objekt.
	 */
	public static UnternehmenMapper unternehmenMapper() {
		if (unternehmenMapper == null) {
			unternehmenMapper = new UnternehmenMapper();
		}

		return unternehmenMapper;
	}

	/**
	 * Diese Methode ermoeglicht es ein Unternehmen in der Datenbank anzulegen.
	 * 
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Unternehmen insert(Unternehmen u) throws Exception {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		PreparedStatement preStmt = null;

		try {
			String sql = "INSERT INTO `Unternehmen`(`ID`) VALUES (NULL)";

			preStmt = con.prepareStatement(sql);
			preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
		return u;
	}

	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank. 
	 * @param u - das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Unternehmen update(Unternehmen u) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Unternehmen " + "SET name=\"" + Organisationseinheit.getName() + "\" " + "SET email=\""
					+ Organisationseinheit.getEmail() + "\" " + "SET strasse=\"" + Organisationseinheit.getStrasse()
					+ "\" " + "SET plz=\"" + Organisationseinheit.getPLZ() + "\" " + "SET ort=\""
					+ Organisationseinheit.getOrt() + "\" " + "SET tel=\"" + Organisationseinheit.getTel() + "\" "
					+ "SET googleID=\"" + Organisationseinheit.getGoogleID() + "\" " + "WHERE ID="
					+ Organisationseinheit.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Unternehmen u) zu wahren,
		// geben wir u zurueck.
		return u;
	}

	
	/**
	 * Loeschen der Daten eines <code>Unternehmen</code>-Objekts aus der Datenbank.
	 * 
	 * @param u - das Objekt, das aus der DB geloescht werden soll
	 */
	public void delete(Unternehmen u) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Unternehmen " + "WHERE ID=" + Organisationseinheit.getID());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Vector<Unternehmen> findByName(String name){
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Unternehmen> result = new Vector<Unternehmen>();

		try {

		// Leeres SQL-Statement (JDBC) anlegen
		Statement stmt = con.createStatement();

		// Statement ausfuellen und als Query an die DB schicken
		ResultSet rs = stmt.executeQuery("SELECT ID, name, email, strasse, plz, ort, tel, googleID FROM Unternehmen" + "WHERE name="
		+ name + " ORDER BY name");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Unternehmen-Objekt erstellt.
			while (rs.next()) {
	
			// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
			// Ergebnis-Objekts
			Unternehmen u = new Unternehmen();
			u.setID(rs.getInt("ID"));
			u.setName(rs.getString("name"));
			u.setEmail(rs.getString("email"));
			u.setStrasse(rs.getString("strasse"));
			u.setPlz(rs.getInt("plz"));
			u.setOrt(rs.getString("ort"));
			u.setTel(rs.getString("tel"));
			u.setGoogleID(rs.getString("googleID"));
	
			// Hinzufuegen des neuen Objekts zum Ergebnisvektor
			result.addElement(u);
			}
		}
		catch (SQLException e2) {
		e2.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}
		
}
