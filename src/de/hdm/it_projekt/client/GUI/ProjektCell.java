package de.hdm.it_projekt.client.GUI;

/**
 * @author Julian Reimenthal
 *
 */
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import de.hdm.it_projekt.shared.bo.Projekt;
import com.google.gwt.cell.client.AbstractCell;

public class ProjektCell extends AbstractCell<Projekt>{
	/**
	 * Klasse zur Darstellung von AusschreibungsObjekten. 
	 * 
	 */

	/* (non-Javadoc)
	 * @see com.google.gwt.cell.client.AbstractCell#render(com.google.gwt.cell.client.Cell.Context, java.lang.Object, com.google.gwt.safehtml.shared.SafeHtmlBuilder)
	 */
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Projekt value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		sb.appendHtmlConstant("<div>Ausschreibung ");
		sb.appendEscaped(value.toString());
		sb.appendHtmlConstant("</div>");
	}

}
