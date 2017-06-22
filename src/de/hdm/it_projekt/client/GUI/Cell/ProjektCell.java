package de.hdm.it_projekt.client.GUI.Cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Projekt;

public class ProjektCell extends AbstractCell<Projekt>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Projekt value, SafeHtmlBuilder sb) {

		if(value == null)
			return;
		
		
		sb.appendHtmlConstant("<div class='Projekt-Cell'>");  //Test CSS Klasse
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div class='Projekt-Cell'>");  //Test CSS Klasse
		
	}

}
