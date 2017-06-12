/**
 * 
 */
package de.hdm.it_projekt.client.GUI_in_dev;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.it_projekt.client.ClientsideSettings;
//import de.hdm.it_projekt.client.alteGUI.AusschreibungForm.CreateAusschreibungCallback;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;


import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;


/**
 * @author Daniel Fleps
 *
 */
public class MarktplatzUebersicht extends VerticalPanel{
	
	
Label marktplatzUebersichtLabel = new Label("Marktplatzübersicht"); 
	
	VerticalPanel marktplatzPanel = new VerticalPanel();
	
	final ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();
	
	final ListBox marktplaetze = new ListBox();
	
	private Anchor formLink = new Anchor(de.hdm.it_projekt.client.GUI_in_dev.MarktplatzForm); 
	
	public MarktplatzUebersicht(){
	
	
	Button anlegenMarktplatzButton = new Button("Marktplatz angelgen"); 
	anlegenMarktplatzButton.addClickHandler(new AnlegenMarktplatzClickHandler()); 
	marktplatzPanel.add(anlegenMarktplatzButton);
	
	Button anzeigenMarktplatzButton = new Button("Marktplätze anzeigen"); 
	anzeigenMarktplatzButton.addClickHandler(new AnzeigenMarktplatzClickHandler()); 
	
	
	
	
	}
	
	private class AnlegenMarktplatzClickHandler implements ClickHandler{
		public void onClick(ClickEvent event){
			Window.alert("Test");
			// Verlinkung auf MarktplatzForm.java
		}
	}
	
	private class AnzeigenMarktplatzClickHandler implements ClickHandler{
		public void onClick(ClickEvent event){
			Window.alert("Test"); 
			
			pa.getProjektMarktplatzById(Integer.parseInt(marktplaetze.getSelectedValue()),
					new AsyncCallback<ProjektMarktplatz>() {

						@Override
						public void onFailure(Throwable caught) {
							marktplatzPanel.add(new Label("Fehler im Projektmarktplatz."));
						}

						@Override
						public void onSuccess(ProjektMarktplatz pm_result) {

							pa.getAlleProjekteFor(pm_result, new AsyncCallback<Vector<Projekt>>() {

								@Override
								public void onFailure(Throwable caught) {

									marktplatzPanel.add(new Label("Fehler bei den Projekte."));
								}

								public void onSuccess(Vector<Projekt> pr_result) {

									marktplatzPanel.clear();
									marktplatzPanel.add(new Label("Projekte:"));
									for (Projekt p : pr_result) {
										marktplatzPanel.add(new Button(p.toString()));
									}

								}

							});

						}
					});
		}
	}
	
	
	
	
	
	
	
}
