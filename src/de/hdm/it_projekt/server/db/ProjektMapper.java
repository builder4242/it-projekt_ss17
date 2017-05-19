package de.hdm.it_projekt.server.db;

import java.sql.Connection;

/**
 * Mapper-Klasse, die <code>Projekt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verf√ºgung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gel√∂scht werden k√∂nnen. Das Mapping ist bidirektional. D.h., Objekte k√∂nnen
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
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal f√ºr
   * s√§mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   * @see projektMapper()
   */
	private static ProjektMapper projektMapper = null;

  /**
   * Gesch√ºtzter Konstruktor - verhindert die M√∂glichkeit, mit <code>new</code>
   * neue Instanzen dieser Klasse zu erzeugen.
   */
  protected ProjektMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>ProjektMapper.projektMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie daf√ºr sorgt, dass nur eine einzige
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
	 * Diese Methode erm√∂glicht es ein Projekt in der Datenbank anzulegen.
	 * 
	 * @param Projekt
	 * @return
	 */
	public Projekt insert(Projekt pr){
		
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		  
		try {
			
				// Leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
				
				// Momentan hˆchsten Prim‰rschl¸sselwert pr¸fen
				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM prs ");

				if (rs.next()) {
	
					/*
					 * pr erh‰lt den bisher maximalen, nun um 1
					 * inkrementierten Prim‰rschl¸ssel.
					 */	
					
					pr.setId(rs.getInt("maxid") + 1);

					stmt = con.createStatement();

			        // Jetzt erst erfolgt die tats√§chliche Einf√ºgeoperation.
			        stmt.executeUpdate("INSERT INTO prs (name, startDatum, endDatum, beschreibung) "
			            + "VALUES (" + pr.getName() + ",'" + pr.getstartDatum() + "','" + pr.getendDatum() + "','" + pr.beschreibung() + "')");
			      }
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }
			
				
			//R√ºckgabe, des evtl. korrigierten Projekts.
			    return pr;
			  }

		
		/**	
		 * Wiederholtes Schreiben eines Projekt-Objekts in die Datenbank.
		 * 
		 * @param pr
		 * 		das Objekt, das in die DB geschrieben werden soll
		 * @return das als Parameter uebergebene Objekt	
		 */

		 public Vector<Projekt> update(Projekt pr){
			 
			 //DB-Verbindung holen
			 Connection con= DBConnection.connection();
			 
			 try{
				 
				 // Leeres SQL-Statement(JDBC) anlegen
				 Statement stmt = con.createStatement();
				 
				 stmt.executeUpdate("UPDATE prs " + "SET name=\""
			     + pr.getname() + "\", " + "startDatum=\"" + pr.getstartDatum() 
			     + "\", " + "endDatum=\"" + pr.getendDatum() 
			     + "\", " + "beschreibung=\"" + pr.getBeschreibung() + "\" "
			     + "WHERE id=" + pr.getID());
			 
		 }
		 catch (SQLException e2){
			 e2.printStackTrace();
		 }
		 
		//Rueckgabe des evtl. korrigierten Projekts
		return pr;
		
		 }
		 
	}

	
	


	
					
								
		
	}

	
	

