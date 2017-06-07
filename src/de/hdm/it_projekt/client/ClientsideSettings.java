package de.hdm.it_projekt.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.shared.*;


public class ClientsideSettings extends CommonSettings {

	 private static ProjektAdministrationAsync projektVerwaltung = null;
	 
	 private static ReportGeneratorAsync reportGenerator = null;
	 
	 /**
	   * Name des Client-seitigen Loggers.
	   */
	  private static final String LOGGER_NAME = "it_projekt Web Client";
	  
	  /**
	   * Instanz des Client-seitigen Loggers.
	   */
	  private static final Logger log = Logger.getLogger(LOGGER_NAME);
	  
	  
	  public static Logger getLogger() {
		    return log;
		  }
	  
	  
}
