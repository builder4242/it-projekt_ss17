package de.hdm.it_projekt.client.GUI.Cell;

/** Die Klasse Ausschreibung Cell dient der Erstellung einer CellList in der 
 * Ausschreibungen angezeigt werden. 
 */

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Ausschreibung;

/**
 * 
 * @author Daniel
 *
 */
public class AusschreibungCell extends AbstractCell<Ausschreibung>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Ausschreibung value, SafeHtmlBuilder sb) {

		if(value == null)
			return;
		
		
		sb.appendHtmlConstant("<div class='Ausschreibung-Cell'>");  //Einbinden der CSS Klasse
		sb.appendEscaped("Ausschreibung: " + value.getBezeichnung());
		sb.appendHtmlConstant("</div class='Ausschreibung-Cell'>");  //Einbinden CSS Klasse
		
	}

}
