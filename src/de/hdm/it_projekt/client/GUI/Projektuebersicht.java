/** Die Klasse Projektuebersicht dient dem Ausgeben eines Infotextes, nachdem ein Marktplatz
 * ausgewählt wurde. Der Name des Markplatzes wird über die Methode getBezeichnung aufgerufen 
 * und in den Text der als HTML realisiert ist mit eingefügt. Im HTML Element kann der Text 
 * direkt über HTML Tags angepasst werden. Zuätzlich wird eine CSS Klasse eingebunden um die 
 * Optik anzupassen. */ 
package de.hdm.it_projekt.client.GUI;



import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


public class Projektuebersicht extends Showcase {

	public Projektuebersicht() {
		
		HTML pmHTML = new HTML(MyProjekt.cpm.getBezeichnung());	
		pmHTML.setStyleName("myprojekt-infoleistelabel");
		
		HTML einfuehrungHTML = new HTML("</br><p align='justify'>Herzlich willkommen im Projektmarktplatz <b>" + pmHTML +
				 "</b> </br> Wenn Sie ein neues Projekt oder eine Ausschreibung anlegen wollen, "
				 + "gehen Sie zu <u>Meine Ausschreibungen</u>.</br> Wenn Sie sich auf eine Ausschreibung"
				 + " bewerben wollen, gehen Sie bitte zu <u>Meine Bewerbungen</u>."
				 + "</br>Möchten Sie Ihr Partnerprofil verwalten, gehen Sie einfach zu <u>Mein Profil "
				 + "verwalten</u>. </br>Um Reports zu generieren, gehen Sie zu <u>Report Generator</u>"
				 + "</br> </br> Wir freuen uns, dass Sie sich für <b>getLinked</b> entschieden haben.</p>"); 
		einfuehrungHTML.setStyleName("myprojekt-infoleistelabel");
		
		// einfuehrungHTML.setText(""); 
		

		
		this.add(einfuehrungHTML);
		
	}
	
}
