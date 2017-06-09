/**
 * 
 */
package de.hdm.it_projekt.client.GUI_in_dev;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author Julian Reimenthal
 *
 */
public class BewertungForm {
			
	Label BewertungLabel = new Label(); 
	TextBox BewertungsScoreTextBox = new TextBox();  // Wert muss zwischen ß und 1 liegen 
	TextArea BewertungsTextTextArea = new TextArea(); 
	Button AbsensenButton = new Button("Bewerten"); 
}
