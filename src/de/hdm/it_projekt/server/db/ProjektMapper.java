/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.thies.bankProjekt.shared.bo.Account;

/**
 * Mapper-Klasse, die <code>Projekt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 *
 * Anlehnung an @author Thies
 * @author ElifY
 */
public class ProjektMapper {

  /**
   * Die Klasse ProjektMapper wird nur einmal instantiiert. Man spricht hierbei
   * von einem sogenannten <b>Singleton</b>.
   * <p>
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   * @see projektMapper()
   */
  private static ProjektMapper projektMapper = null;

  /**
   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
   * neue Instanzen dieser Klasse zu erzeugen.
   */
  protected ProjektMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>ProjektMapper.projektMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
   * Instanz von <code>ProjektMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> ProjektMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>ProjektMapper</code>-Objekt.
   * @see projektMapper
   */
  public static ProjektMapper projektMapper() {
    if (projektMapper == null) {
      projektMapper = new ProjektMapper();
    }

    return projektMapper;
  }
	 
  /** 
	 * Diese Methode ermöglicht es ein Projekt in der Datenbank anzulegen.
	 * 
	 * @param projekt
	 * @return
	 * @throws Exception
	 */
	public Vector<Projekt> insert(Projekt pr) 
			throws Exception {
		
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		  
		try {
			
				// Leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
				
				// Momentan h�chsten Prim�rschl�sselwert pr�fen
				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM prs ");

				if (rs.next()) {
	
					/*
					 * pr erh�lt den bisher maximalen, nun um 1
					 * inkrementierten Prim�rschl�ssel.
					 */	
					pr.setId(rs.getInt("maxid") + 1);

					stmt = con.createStatement();

					
				}
		}
	}
}
	
	
