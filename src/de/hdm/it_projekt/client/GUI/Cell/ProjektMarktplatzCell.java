package de.hdm.it_projekt.client.GUI.Cell;

/** */ 

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;

import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class ProjektMarktplatzCell extends AbstractCell<ProjektMarktplatz>{

	private Anchor deleteLink = new Anchor("Löschen");
	private Anchor updateLink = new Anchor("Ändern");
	
	public ProjektMarktplatzCell() {
		deleteLink.addClickHandler(new DeleteClickHandler());
		updateLink.addClickHandler(new UpdateClickHandler());
	}
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, ProjektMarktplatz value, SafeHtmlBuilder sb) {

		if(value == null)
			return;

		sb.appendHtmlConstant("<link type='text/css' rel='stylesheet' href='style.css'>");
		sb.appendHtmlConstant("<div class='ProjektMarktplatz-Cell'>");  //Test CSS Klasse
		sb.appendEscaped(value.getBezeichnung());
		sb.appendEscaped(updateLink.getElement().getInnerHTML());
		sb.appendEscaped(deleteLink.getElement().getInnerHTML());
		sb.appendHtmlConstant("</div class='ProjektMarktplatz-Cell'>");  //Test CSS Klasse
		
	}
	
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class UpdateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
