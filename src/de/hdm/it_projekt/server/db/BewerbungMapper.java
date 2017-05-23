/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Bewerbung;

/**
 * Mapper-Klasse, die <code>Bewerbung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * @author ElifY
 */

public class BewerbungMapper {
	
	 /**
	   * Die Klasse BewerbungMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   */
	 
	private static BewerbungMapper bewerbungMapper = null;

	 /**
	   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	   * Instanzen dieser Klasse zu erzeugen.
	   */
	  protected BewerbungMapper() {
	  }
	  
	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>BewerbungMapper.bewerbungMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
	   * Instanz von <code>BewerbungMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> BewerbungMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>BewerbungMapper</code>-Objekt.
	   */
	  
	  	public static BewerbungMapper bewerbungMapper() {
			if (bewerbungMapper == null) {
		      bewerbungMapper = new BewerbungMapper();
	  			}

	  			return bewerbungMapper;
		  		}

	  	 /**
	     * Einfuegen eines <code>Bewerbung</code>-Objekts in die Datenbank. Dabei
	     * wird auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
	     * berichtigt.
	     * 
	     * @param bw das zu speichernde Objekt
	     * @return das bereits uebergeben Objekt, jedoch mit ggf. korrigierter
	     * 		<code>id</id>
	     */
	  	
	  		public Bewerbung insert(Bewerbung bw){
	  			
	  			//DB-Verbindung herstellen
	  			Connection con = DBConnection.connection();
	  			
	  			try{
	  				//Leeres SQL-Statement (JDBC) anlegen
	  				Statement stmt = con.createStatement();
	  				
	  				//Momentan hoechsten Primaerschluesselwert pruefen
	  				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
	  			          + "FROM bewerbungs ");
	  				
	  				// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
	  				if (rs.next()) {
	  					
	  					 /**
	    		    	   * bw erhaelt den bisher maximalen, nun um 1 inkrementierten
	    		    	   * Primaerschluessel.
	    		    	   */
	  						bw.setId(rs.getInt("maxid") + 1);
	  		    	  	
	  						stmt = con.createStatement();
	  						
	  						// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
	  	  			        stmt.executeUpdate("INSERT INTO bewerbungs (id, erstelldatum, bewerbungstext) "
	  	  			            + "VALUES (" + Bewerbung.getId() + ","
	  	  			            + bw.getErstelldatum() + ","
	  	  			            + bw.getBewerbungstext() + ")");
	  	  			      }
	  	  			}
	  						catch (SQLException e1) {
	  							e1.printStackTrace();
	  							}
	  					//Rueckgabe, der evtl. korrigierten Bewerbung.
						return bw;
	  					}
	  				
	  		/**
	  		 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	  		 * 
	  		 * @param bw
	  		 * 		das Objekt, das in die DB geschrieben werden soll
	  		 * @return das als Parameter uebergebene Objekt
	  		 */
	  		
	  		public Bewerbung update(Bewerbung bw){
	  			
	  			//DB-Verbindung herstellen
	  			Connection con = DBConnection.connection();
	  			
	  			try{
	  				//Leeres SQL-Statement (JDBC) anlegen
	  				Statement stmt = con.createStatement();
	  				
	  				//Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
	  				 stmt.executeUpdate("UPDATE bewerbungs" + "SET erstelldatum=\""
	  				 + bw.getErstelldatum() + "\"," + "bewerbungstext=\"" + bw.getBewerbungstext()
	  			     + "WHERE id=" + Bewerbung.getId());		
	  				}
	  				
	  					catch (SQLException e2){
	  						e2.printStackTrace();
	  					}
	  		
	  					//Um Analogie zu insert(Bewerbung bw) zu wahren, geben wir bw zurueck
	  					return bw;
	  					}
	  		
	  			/**
	  			  * Loeschen der Daten eines <code>Bewerbung</code>-Objekts aus der DB
	  			  * @param bw
	  			  * 		das aus der DB zu loeschende Objekt
	  			  * @return 
	  			  */
	  		
	  				public void delete(Bewerbung bw){
  				
	  					//DB-Verbindung herstellen
	  					Connection con = DBConnection.connection();
	  					
	  					try{
	  	  					//Leeres SQL-Statement (JDBC) anlegen
	  	  					Statement stmt = con.createStatement();
  				
	  	  					stmt.executeUpdate("DELETE FROM bewerbungs" + "WHERE id=" + Bewerbung.getId());
	  	  					}
	  					
	  						catch (SQLException e3){
	  	  					 e3.printStackTrace();
	  						}
	  	  			 	}
	  		 
	  				
	  				/**
	  				 * Auslesen aller Bewerbungen
	  				 * @return Ein Vektor mit Bewerbung-Objekten, die saemtliche
	  				 * Bewerber repraesentieren. Bei evtl. Exceptions wird eine
	  				 * partiell gefuellter oder ggf. auch leerer Vektor zurueckgeliefert
	  				 * @return
	  				 */
	  				
	  				public Vector<Bewerbung> findAll(){
	  					
	  					//DB-Verbindung herstellen
	  					Connection con = DBConnection.connection();
	  					
	  					//Ergebnisvektor vorbereiten
	  					Vector<Bewerbung> result = new Vector<Bewerbung>();
	  					
	  					try{
	  						//Leeres SQL-Statement (JDBC) anlegen
	  						Statement stmt = con.createStatement();
	  			 			 
	  			 			 ResultSet rs = stmt.executeQuery("SELECT id, erstelldatum, bewerbungstext "
	  			 				+ "FROM bewerbungs " + " ORDER BY id");
	  			 			 
	  			 			 //Fuer jeden Eintrag im Suchergebnis wird nun ein
	  			 			 //Bewerbung-Objekt erstellt.
	  			 			 while (rs.next()) {
	  			 				 Bewerbung bw = new Bewerbung();
	  			 				 bw.setId(rs.getInt("id"));
	  			 				 bw.setErstelldatum(rs.getDate("erstelldatum"));
	  			 				 bw.setBewerbungstext(rs.getString("bewerbungstext"));
	  			 			 
	  			 			//Hinzufuegen des neuen Objekts zum Ergebnisvektor
	    			 		     result.addElement(bw);
	    			 		      		}
	    			 		 		}
	    			 		    catch (SQLException e4) {
	    			 		    e4.printStackTrace();
	    			 		    		}	

	    			 		   	// Ergebnisvektor zurueckgeben
	    			 		   	return result;
	  			 			 
	    			 		   	
	    			 		    /** Suchen einer Bewerbung mit vorgegebener ID. Da diese eindeutig ist,
	    					 	  * wird genau ein Objekt zurueckgegeben.
	    					 	  * 
	    					 	  * @param id
	    					 	  * 	Primaerschluesselattribut in DB
	    					 	  * @return Bewerbung-Objekt, das dem uebergebenen Schluessel entspricht,
	    					 	  * null bei nicht vorhandenem DB-Tupel.
	    					 	  */
	    			 		   
	    			 		   	public Vector<Bewerbung> findById(int id){
	    			 			   
	    			 		   		//DB-Verbindung herstellen
	    					 		 Connection con = DBConnection.connection();
	    					 		 
	    					 		 try{
	    					 			 
	    					 		  //Leeres SQL-Statement (JDBC) anlegen
	    					 		  Statement stmt = con.createStatement();
	    					 			
	    					 		  //Statement ausfuellen und als Query an die DB schicken
	    					 		  ResultSet rs= stmt.executeQuery("SELECT id, erstelldatum, bewerbungstext FROM bewerbungs "
	    						 				 + "WHERE id=" + id + "ORDER BY id");
	    			 		  
	    					 		  	 /*
	    						 		  * Da id der Primaerschluessel ist, kann maximal nur ein Tupel
	    						 		  * zurueckgegeben werden.
	    						 		  * Pruefung, ob ein Ergebnis vorliegt.
	    						 		  */
	    					 		  		if (rs.next()){
	    						
	    					 		  		//Umwandlung des Ergebnis-Tupel in ein Objekt und
	    						 			//Ausgabe des Ergebnis-Objekts.
	    						 				 
	    						 				 Bewerbung bw = new Bewerbung();
	    						 				 bw.setId(rs.getInt("id"));
	    						 				 bw.setErstelldatum(rs.getDate("erstelldatum"));
	    						 				 bw.setBewerbungstext(rs.getString("bewerbungstext"));
	    						 				 
	    						 				return bw;
	    						 			 }
	    						 		 } 
	    					 		 	catch (SQLException e5){
	    				 		 		e5.printStackTrace();
	    				 		 		return null;
	    					 		 	}
	    				 		 
	    					 		 	return null;
	    				 	 	}
	    					 		
	    			 		   	
	    			 		   	
	  					}
	  				}