package de.hdm.it_projekt.client.GUI.Cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Eigenschaft;


public class EigenschaftCell extends AbstractCell<Eigenschaft>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Eigenschaft value, SafeHtmlBuilder sb) {

		if(value == null)
			return;
		
		
		sb.appendHtmlConstant("<div class='xx-Cell'>");  //Test CSS Klasse
		sb.appendEscaped(value.getName() + " - " + value.getWert());
		sb.appendHtmlConstant("</div class='xx-Cell'>");  //Test CSS Klasse
		
	}

}