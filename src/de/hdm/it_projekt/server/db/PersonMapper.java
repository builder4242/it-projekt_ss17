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

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Unternehmen;

/**
 * Mapper-Klasse, die <code>Person</code>-Objekte auf eine relationale Datenbank
 * abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung gestellt, mit
 * deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und geloescht werden
 * koennen. Das Mapping ist bidirektional. D.h., Objekte koennen in
 * DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * 
 * @author Tugba Bulat
 */
public class PersonMapper {

	/**
	 * Die Klasse PersonMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static PersonMapper personMapper = null;

	/***
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen
	 */
	protected PersonMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>PersonMapper.personMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>PersonMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> PersonMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>PersonMapper</code>-Objekt.
	 */
	public static PersonMapper personMapper() {
		if (personMapper == null) {
			personMapper = new PersonMapper();
		}

		return personMapper;
	}

	
	/**
	 * Diese Methode ermoeglicht es eine Person in der Datenbank anzulegen.
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public Person insert(Person p) throws Exception {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		PreparedStatement preStmt = null;

		try {
			String sql = "INSERT INTO `Person`(`ID`) VALUES (NULL)";

			preStmt = con.prepareStatement(sql);
			preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
		return p;
	}

	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param p - das Objekt, das in die Datenbank geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Person update(Person p) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Person " + "SET vorname=\"" + Person.getVorname() + "SET name=\"" + Organisationseinheit.getName() + "\" " + "SET email=\""
					+ Organisationseinheit.getEmail() + "\" " + "SET strasse=\"" + Organisationseinheit.getStrasse()
					+ "\" " + "SET plz=\"" + Organisationseinheit.getPLZ() + "\" " + "SET ort=\""
					+ Organisationseinheit.getOrt() + "\" " + "SET tel=\"" + Organisationseinheit.getTel() + "\" "
					+ "SET googleID=\"" + Organisationseinheit.getGoogleID() + "\" " + "WHERE ID="
					+ Organisationseinheit.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Person p) zu wahren,
		// geben wir p zurueck.
		return p;
	}

	
	/**
	 * Loeschen der Daten eines <code>Person</code>-Objekts aus der Datenbank.
	 * 
	 * @param p - das Objekt, das aus der Datenbank geloescht werden soll
	 */
	public void delete(Person p) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Person " + "WHERE ID=" + Organisationseinheit.getID());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Auslesen aller Personen.
	 * 
	 * @return
	 */
	public Vector<Person> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Person> result = new Vector<Person>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT vorname " + "FROM Person" + "SELECT ID, name, email, strasse, plz, ort, tel, googleID "
			+ "FROM Organisationseinheit" + " ORDER BY ID");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Person-Objekt erstellt.

			while (rs.next()) {
				Person p = new Person();
				p.setVorname(rs.getString("vorname"));
				p.setID(rs.getInt("ID"));
				p.setName(rs.getString("name"));
				p.setEmail(rs.getString("email"));
				p.setStrasse(rs.getString("strasse"));
				p.setPlz(rs.getInt("plz"));
				p.setOrt(rs.getString("ort"));
				p.setTel(rs.getString("tel"));
				p.setGoogleID(rs.getString("googleID"));
				
				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(p);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

}
