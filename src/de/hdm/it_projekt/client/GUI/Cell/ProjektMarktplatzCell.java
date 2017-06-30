package de.hdm.it_projekt.client.GUI.Cell;

/** */ 

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class ProjektMarktplatzCell extends AbstractCell<ProjektMarktplatz>{


	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, ProjektMarktplatz value, SafeHtmlBuilder sb) {

		if(value == null)
			return;


		sb.appendHtmlConstant("<link type='text/css' rel='stylesheet' href='style.css'>");
		sb.appendHtmlConstant("<div class='ProjektMarktplatz-Cell'>");  //Test CSS Klasse
		sb.appendEscaped(value.getBezeichnung());
		sb.appendHtmlConstant("</div class='ProjektMarktplatz-Cell'>");  //Test CSS Klasse
		
	}
	
}
