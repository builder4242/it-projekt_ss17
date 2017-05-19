/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.client.ProjektMarktplatz;

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
public class ProjektMarktplatzMapper {

	/**
	 * Die Klasse ProjektMarktplatzMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	  private static ProjektMarktplatzMapper projektMarktplatzMapper = null;
	  
	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	   * Instanzen dieser Klasse zu erzeugen.
	   * 
	   */
	  protected ProjektMarktplatzMapper() {
	  }
	  
	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>ProjektMarktplatzMapper.projektmarktplatzMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
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
		 * Diese Methode ermöglicht es einen Projektmarktplatz in der Datenbank anzulegen.
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
			finally {
				DBConnection.closeAll(null, preStmt, con);
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
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("UPDATE Projektmarktplatz " + "SET bezeichnung=\""
		          + pm.getBezeichnung() + "\" " + "WHERE id=" + pm.getId();
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Um Analogie zu insert(Vector<ProjektMarktplatz> pm) zu wahren, geben wir pm zurück
		    return pm;
		  }
		
		
		/***
		 * 
		 * @param pm - das aus der DB zu löschende "Objekt"
		 */
		public void delete(ProjektMarktplatz pm){ 
			Connection con = DBConnection.connection();
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM Projektmarktplatz " + "WHERE ID=" + Projektmarktplatz.getId());
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}


}
