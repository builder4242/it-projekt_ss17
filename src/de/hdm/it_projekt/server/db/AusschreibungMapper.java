
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.it_projekt.shared.bo.Ausschreibung;

/**
 * Mapper-Klasse, die <code>Ausschreibung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * @author ElifY
 */

public class AusschreibungMapper {

	/**
	   * Die Klasse AusschreibungMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   */
	
	private static AusschreibungMapper ausschreibungMapper = null;

	  /**
	   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	   * Instanzen dieser Klasse zu erzeugen.
	   */
	  protected AusschreibungMapper() {
	  }
	  

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>AusschreibungMapper.ausschreibungMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
	   * Instanz von <code>AusschreibungMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> AusschreibungMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>AusschreibungMapper</code>-Objekt.
	   */
	  public static AusschreibungMapper ausschreibungMapper() {
	    if (ausschreibungMapper == null) {
	      ausschreibungMapper = new AusschreibungMapper();
	    }

	    return ausschreibungMapper;
	  }
	  
	  /**
	   * Einfuegen eines <code>Ausschreibung</code>-Objekts in die Datenbank. Dabei
	   * wird auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
	   * berichtigt.
	   * 
	   * @param as das zu speichernde Objekt
	   * @return das bereits uebergeben Objekt, jedoch mit ggf. korrigierter
	   * 		<code>id</id>
	   */
	
	  	public Ausschreibung insert(Ausschreibung as){
	  		
	  		//DB-Verbindung herstellen
	  		Connection con = DBConnection.connection();
	  		
	  		try{
	  			//Leeres SQL-Statement (JDBC) anlegen
	  			Statement stmt = con.createStatement();
	  			
	  			//Momentan hoechsten Primaerschluesselwert pruefen
  				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
  			          + "FROM ausschreibungen ");
	  		
  				// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
  				if (rs.next()) {
  					
  					  /*
    		    	   * as erhaelt den bisher maximalen, nun um 1 inkrementierten
    		    	   * Primaerschluessel.
    		    	   */
    		    	  
    		    	  	as.setId(rs.getInt("maxid") + 1);
    		    	  	
    		    	  	stmt = con.createStatement();
    		    	  	
    		    	 // Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
      			        stmt.executeUpdate("INSERT INTO ausschreibungen (id, bezeichnung, ausschreibungstext, bewerbungsfrist) "
      			            + "VALUES (" + Ausschreibung.getId() + ","
      			            + as.getBezeichnung() + ","
      			            + as.getAusschreibungstext() + ","
      			            + as.getBewerbungsfrist() + ")");
  						}	  	
  				}
	  						catch (SQLException e1){
	  						   e1.printStackTrace();
	  						}
						//Rueckgabe, der evtl. korrigierten Ausschreibung.
						return as;
						
						/**
				  		 * Wiederholtes Schreiben eines Objekts in die Datenbank.
				  		 * 
				  		 * @param as
				  		 * 		das Objekt, das in die DB geschrieben werden soll
				  		 * @return das als Parameter uebergebene Objekt
				  		 */
					
						public Ausschreibung update(Ausschreibung as){
							
							//DB-Verbindung herstellen
							Connection con = DBConnection.connection();
				  			
				  			try{
				  				//Leeres SQL-Statement (JDBC) anlegen
				  				Statement stmt = con.createStatement();
				  				
				  				//Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				  				 stmt.executeUpdate("UPDATE ausschreibungen " + "SET bezeichnung=\""
				  				 + as.getBezeichnung() + "\"," + "ausschreibungstext=\"" + as.getAusschreibungstext()
				  				 + "bewerbungsfrist=\"" + as.getBewerbungsfrist()
				  			     + "WHERE id=" + Ausschreibung.getId());		
				  				 
				  			  }	
			  			 	
				  			catch (SQLException e2){
			  				 e2.printStackTrace();
			  			 }
				  		
				  			// Um Analogie zu insert(Ausschreibung as) zu wahren, geben wir as zurueck
			  				return as;
			  		  }
				  				
						/**
				  		  * Loeschen der Daten eines <code>Ausschreibung</code>-Objekts aus der DB
				  		  * @param as
				  		  * 		das aus der DB zu loeschende Objekt
				  		  * @return 
				  		  */
						
						public void delete(Ausschreibung as){
							
							//DB-Verbindung herstellen
			  				Connection con = DBConnection.connection();
							
			  				try{
			  					//Leeres SQL-Statement (JDBC) anlegen
			  					Statement stmt = con.createStatement();
			  					
			  					stmt.executeUpdate("DELETE FROM ausschreibungen" + "WHERE id=" + Ausschreibung.getId());
			  				}
			  				 catch (SQLException e3){
			  					 e3.printStackTrace();
			  				 }
			  			 }
			 
			  					
			  					
			  					
			  					
	  }
	
	
}
