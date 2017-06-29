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
		
		HTML einfuehrungHTML = new HTML("<center>Herzlich willkommen im Prjektmartplatz <b>" + pmHTML +
				 "</b> Wenn Sie ein neues Projekt oder eine Ausschreibung anlegen wollen "
				 + "gehen Sie zu \"Meine Ausschreibungen\".</br> Wenn Sie sich auf eine Ausschreibung"
				 + " bewerben wollen gehen Sie bitte zu \"Meine Bewerbungen\"."
				 + "</br>Möchten Sie Ihr Partnerprofil verwalten gehen Sie einfach zu \"Mein Prfil "
				 + "verwalten\".</br> Wir freuen uns, dass Sie sich für getLinked entschieden haben.</center>"); 
		einfuehrungHTML.setStyleName("myprojekt-infoleistelabel");
		
		// einfuehrungHTML.setText(""); 
		
	//Test
		
		this.add(einfuehrungHTML);
		
	}
	
}
