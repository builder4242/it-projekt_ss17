package de.hdm.it_projekt.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.client.GUI_Report.ReportGenerator;
import de.hdm.it_projekt.shared.*;


public class ClientsideSettings extends CommonSettings {
	
	/**
	   * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen Dienst
	   * namens <code>ProjektAdministration</code>.
	   */

	 private static ProjektAdministrationAsync projektAdministration = null;
	 
	 /**
	   * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen Dienst
	   * namens <code>ReportGenerator</code>.
	   */
	 
	 private static ReportGeneratorAsync reportGenerator = null;
	 
	 /**
	   * Name des Client-seitigen Loggers.
	   */
	  private static final String LOGGER_NAME = "it_projekt Web Client";
	  
	  /**
	   * Instanz des Client-seitigen Loggers.
	   */
	  private static final Logger log = Logger.getLogger(LOGGER_NAME);
	  
	  /* Rückgabe der Logger Instanz */ 
	  
	  public static Logger getLogger() {
		    return log;
		  }
	  
	  
	  public static ProjektAdministrationAsync getProjektAdministration() {
		    // Erzeugen einer ProjektAdministrations Instanz wenn noch keine vorhanden ist. 
		    if (projektAdministration == null) {
		      // projektAdministration wird instanziert 
		    	projektAdministration = GWT.create(ProjektAdministration.class);
		    }

		    // Rückgabe der projektAdministration. 
		    return projektAdministration;
		  }
	  
	  
	  
	  public static ReportGeneratorAsync getReportGenerator() {
		    //Erzeugen einer ReportGenerator Instanz wenn noch keine vorhanden ist. 
		    if (reportGenerator == null) {
		      // Zunächst instantiieren wir ReportGenerator
		      reportGenerator = GWT.create(ReportGenerator.class);

		      final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
		        @Override
				public void onFailure(Throwable caught) {
		          ClientsideSettings.getLogger().severe(
		              "Der Report Generator konnte nicht initialisiert werden.");
		        }

		        @Override
				public void onSuccess(Void result) {
		          ClientsideSettings.getLogger().info(
		              "Der Report Generator wurde initialisiert.");
		        }
		      };

		      reportGenerator.init(initReportGeneratorCallback); 
		    }

		    // Rückgabe des Report Generators 
		    return reportGenerator;
		  }
	  
}
