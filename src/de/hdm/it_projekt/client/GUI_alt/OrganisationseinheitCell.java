package de.hdm.it_projekt.client.GUI_alt;

/**
 * @author Julian Reimenthal
 *
 */
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import com.google.gwt.cell.client.AbstractCell;

public class OrganisationseinheitCell extends AbstractCell<Organisationseinheit>{
	/**
	 * Klasse zur Darstellung von OrganisationseinheitObjekten. 
	 * 
	 */		

	/* (non-Javadoc)
	 * @see com.google.gwt.cell.client.AbstractCell#render(com.google.gwt.cell.client.Cell.Context, java.lang.Object, com.google.gwt.safehtml.shared.SafeHtmlBuilder)
	 */
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Organisationseinheit value,
			SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		sb.appendHtmlConstant("<div>Ausschreibung ");
		sb.appendEscaped(value.toString());
		sb.appendHtmlConstant("</div>");
	}
}


