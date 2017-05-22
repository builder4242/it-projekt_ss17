/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import de.hdm.it_projekt.client.ProjektMarktplatz;

/**
	 * Mapper-Klasse, die <code>Projektmarktplatz</code>-Objekte auf eine relationale
	 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
	 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
	 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
	 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
	 * <p>
	 * 
	 * Anlehnung an @author Thies
	 * @author Tugba Bulat
	 *
	 */
public class ProjektMarktplatzMapper {

	/**
	 * Die Klasse ProjektMarktplatzMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	  private static ProjektMarktplatzMapper projektMarktplatzMapper = null;
	  
	  
	  
	  /**
	   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	   * Instanzen dieser Klasse zu erzeugen.
	   * 
	   */
	  protected ProjektMarktplatzMapper() {
	  }
	  
	  
	  
	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>ProjektMarktplatzMapper.projektmarktplatzMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
	   * Instanz von <code>ProjektMarktplatzMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> ProjektMarktplatzMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>ProjektMarktplatzMapper</code>-Objekt.
	   * @see projektMarktplatzMapper
	   */
	  public static ProjektMarktplatzMapper projektMarktplatzMapper() {
	    if (projektMarktplatzMapper == null) {
	    	projektMarktplatzMapper = new ProjektMarktplatzMapper();
	    }

	    return projektMarktplatzMapper;
	  }
	  
	  
	  
		/** 
		 * Diese Methode ermoeglicht es einen Projektmarktplatz in der Datenbank anzulegen.
		 * 
		 * @param projektMarktplatz
		 * @return
		 * @throws Exception
		 */
		public Vector <ProjektMarktplatz> insert(Vector<ProjektMarktplatz> pm)
				throws Exception {
			// DB-Verbindung herstellen
			Connection con = DBConnection.connection();
			PreparedStatement preStmt = null;

			try {
				String sql = "INSERT INTO `Projektmarktplatz`(`ID`) VALUES (NULL)";
				
				preStmt = con.prepareStatement(sql);
				preStmt.executeUpdate();
				preStmt.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
			}
			return pm;
		}
  
		
		
		/***
		 * Wiederholtes Schreiben eines Objekts in die Datenbank.
		 * 
		 * @param pm - das Objekt, das in die DB geschrieben werden soll
		 * @return das als Parameter übergebene Objekt
		 */
		public Vector <ProjektMarktplatz> update(Vector<ProjektMarktplatz> pm){
		    
			// DB-Verbindung herstellen
			Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("UPDATE Projektmarktplatz " + "SET bezeichnung=\""
		          + ProjektMarktplatz.getBezeichnung() + "\" " + "WHERE id=" + ProjektMarktplatz.getID());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Um Analogie zu insert(Vector<ProjektMarktplatz> pm) zu wahren, geben wir pm zurück
		    return pm;
		  }
		
		
		
		/***
		 * 
		 * @param pm - das aus der DB zu loeschende "Objekt"
		 */
		public void delete(ProjektMarktplatz pm){ 
			
			// DB-Verbindung herstellen
			Connection con = DBConnection.connection();
			Statement stmt = null;
			
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM Projektmarktplatz " + "WHERE ID=" + ProjektMarktplatz.getID());
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}


		/***
		 * Auslesen aller ProjektMarktplaetze.
		 * 
		 * @return Ein Vektor mit Projektmarktplatz-Objekten, die saemtliche
		 *         Projektmarktplaetze repraesentieren. Bei evtl. Exceptions wird eine
		 *         partiell gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
		 */
		public Vector<ProjektMarktplatz> findAll() {
	
			// DB-Verbindung herstellen
			Connection con = DBConnection.connection();
	
			// Ergebnisvektor vorbereiten
			Vector<ProjektMarktplatz> result = new Vector<ProjektMarktplatz>();
	
			try {
	
				// Leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
	
				ResultSet rs = stmt.executeQuery("SELECT ID, bezeichnung " + "FROM Projektmarktplatz" + " ORDER BY ID");
	
				// Fuer jeden Eintrag im Suchergebnis wird nun ein
				// Projektmarktplatz-Objekt erstellt.
	
				while (rs.next()) {
					ProjektMarktplatz pm = new ProjektMarktplatz();
					pm.setID(rs.getInt("ID"));
					pm.setBezeichnung(rs.getString("bezeichnung"));
	
					// Hinzufuegen des neuen Objekts zum Ergebnisvektor
					result.addElement(pm);
				}
			}
			catch (SQLException e4) {
				e4.printStackTrace();
			}
	
			// Ergebnisvektor zurueckgeben
			return result;
		}

		
		/***
		 * Suchen eines Projektmarktplatzes mit vorgegebener ID.
		 * Da diese eindeutig ist, wird genau ein Objekt zurueckgegeben.
		 * @param ID - Primaerschluesselattribut in DB
		 * @return Projektmarktplatz-Objekt, das dem uebergebenen Schluessel entspricht, null
	 *         bei nicht vorhandenem DB-Tupel.
		 */
		public Vector<ProjektMarktplatz> findByID (int ID){
			// DB-Verbindung herstellen
			Connection con = DBConnection.connection();
			
			try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT ID, bezeichnung FROM Projektmarktplatz" +
			"WHERE id=" + ID + " ORDER BY ID");

				/*
				 * Da ID der Primaerschluessel ist, kann maximal nur ein Tupel
				 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
				 */
				if (rs.next()) {
					// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
					// Ergebnis-Objekts
	
			        ProjektMarktplatz pm = new  ProjektMarktplatz();
			        pm.setID(rs.getInt("ID"));
			        pm.setBezeichnung(rs.getString("bezeichnung"));
	
			        return pm;
				}
			}
			catch (SQLException e2) {
			e2.printStackTrace();
			return null;
			}

		return null;
		}

		
		
}