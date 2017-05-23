package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.it_projekt.shared.bo.Eigenschaft;

/**
 * Mapper-Klasse, die <code>Eigenschaft</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * @author ElifY
 */

public class EigenschaftMapper {

  /**
   * Die Klasse EigenschaftMapper wird nur einmal instantiiert. Man spricht hierbei
   * von einem sogenannten <b>Singleton</b>.
   * <p>
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   */
  private static EigenschaftMapper eigenschaftMapper = null;

  /**
   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
   * Instanzen dieser Klasse zu erzeugen.
   */
  protected EigenschaftMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>EigenschaftMapper.eigenschaftMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
   * Instanz von <code>EigenschaftMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> EigenschaftMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>EigenschaftMapper</code>-Objekt.
   */
  public static EigenschaftMapper eigenschaftMapper() {
    if (eigenschaftMapper == null) {
      eigenschaftMapper = new EigenschaftMapper();
    }

    return eigenschaftMapper;
  }

  /**
   * Einfuegen eines <code>Eigenschaft</code>-Objekts in die Datenbank. Dabei
   * wird auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
   * berichtigt.
   * 
   * @param e das zu speichernde Objekt
   * @return das bereits uebergeben Objekt, jedoch mit ggf. korrigierter
   * 		<code>id</id>
   */
  		public Eigenschaft insert(Eigenschaft e){
  			
  			//DB-Verbindung herstellen
  			Connection con = DBConnection.connection();
			
  			try{
  				//Leeres SQL-Statement (JDBC) anlegen
  				Statement stmt = con.createStatement();
  				
  				//Momentan hoechsten Primaerschluesselwert pruefen
  				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
  			          + "FROM transactions ");
  				
  				// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
  				if (rs.next()) {
  		    	  
  		    	  /*
  		    	   * e erhaelt den bisher maximalen, nun um 1 inkrementierten
  		    	   * Primaerschluessel.
  		    	   */
  		    	  
  		    	  	e.setId(rs.getInt("maxid") + 1);
  		    	  	
  		    	  	stmt = con.createStatement();
  		    	  	
  		    	  	// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
  			        stmt.executeUpdate("INSERT INTO eigenschaften (id, name, wert) "
  			            + "VALUES (" + Eigenschaft.getId() + ","
  			            + e.getName() + ","
  			            + e.getWert() + ")");
  			      }
  			}
  					catch (SQLException e1) {
  						e1.printStackTrace();
  					}
  					//Rueckgabe, der evtl. korrigierten Beteiligung.
  					return e;
  		  }
  		
}


