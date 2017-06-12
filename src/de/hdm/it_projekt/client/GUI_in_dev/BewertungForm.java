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
			
	Label bewertungLabel = new Label(); 
	TextBox bewertungsScoreTextBox = new TextBox();  // Wert muss zwischen ß und 1 liegen 
	TextArea bewertungsTextTextArea = new TextArea(); 
	Button absensenButton = new Button("Bewerten"); 
}
