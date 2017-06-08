/**
 * 
 */
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;


/**
 * @author Daniel Fleps
 *
 */
public class MarktplatzUebersicht {

	/**
	 * The constants used in this Content Widget.
	 */
	public static interface CwConstants extends Constants {
		String cwMenuBarDescription();

		String cwMenuBarMarktplatzCategory();

		String[] cwMenuBarMarktplatzOptions();

		String cwMenuBarProjektCategory();

		String[] cwMenuBarProjektOptions();

		String cwMenuBarProfilCategory();

		String[] cwMenuBarProfilOptions();

		String cwMenuBarBewerbungCategory();

		String[] cwMenuBarBewerbungOptions();

		String cwMenuBarAbmeldenCategory();

		String[] cwMenuBarAbmeldenOptions();

		String cwMenuBarName();

		String[] cwMenuBarPrompts();
	}

	/**
	 * An instance of the constants.
	 */
	private final CwConstants constants = null;

	/**
	 * Initialize this example.
	 */

	public MenuBar onInitialize() {
		// Create a command that will execute on menu item selection
		Command menuCommand = new Command() {
			private int curPhrase = 0;
			private final String[] phrases = constants.cwMenuBarPrompts();

			public void execute() {
				Window.alert(phrases[curPhrase]);
				curPhrase = (curPhrase + 1) % phrases.length;
			}
		};

		// Create a menu bar
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("500px");
		menu.setAnimationEnabled(true);

		// Create the Projekt menu
		MenuBar projektMenu = new MenuBar(true);
		projektMenu.setAnimationEnabled(true);
		menu.addItem(new MenuItem(constants.cwMenuBarProjektCategory(), projektMenu));
		String[] fileOptions = constants.cwMenuBarProjektOptions();
		for (int i = 0; i < fileOptions.length; i++) {
			if (i == 3) {
				projektMenu.addSeparator();
				projektMenu.addSeparator();
			} else {
				projektMenu.addItem(fileOptions[i], menuCommand);
			}
		}

		// Create the Marktplatz menu
		MenuBar marktplatzMenu = new MenuBar(true);
		menu.addItem(new MenuItem(constants.cwMenuBarMarktplatzCategory(), marktplatzMenu));
		String[] editOptions = constants.cwMenuBarMarktplatzOptions();
		for (int i = 0; i < editOptions.length; i++) {
			marktplatzMenu.addItem(editOptions[i], menuCommand);
		}

		// Create the GWT menu
		MenuBar profilMenu = new MenuBar(true);
		menu.addItem(new MenuItem("Profil", true, profilMenu));
		String[] profilOptions = constants.cwMenuBarProfilOptions();
		for (int i = 0; i < profilOptions.length; i++) {
			profilMenu.addItem(profilOptions[i], menuCommand);
		}

		MenuBar bewerbungMenu = new MenuBar(true);
		menu.addItem(new MenuItem(constants.cwMenuBarBewerbungCategory(), bewerbungMenu));
		String[] bewerbungOptions = constants.cwMenuBarMarktplatzOptions();
		for (int s = 0; s < editOptions.length; s++) {
			bewerbungMenu.addItem(editOptions[s], menuCommand);
		}

		// Create the help menu
		MenuBar abmeldenMenu = new MenuBar(true);
		menu.addSeparator();
		menu.addItem(new MenuItem(constants.cwMenuBarAbmeldenCategory(), abmeldenMenu));
		String[] abmeldenOptions = constants.cwMenuBarAbmeldenOptions();
		for (int i = 0; i < abmeldenOptions.length; i++) {
			abmeldenMenu.addItem(abmeldenOptions[i], menuCommand);
		}

		// Return the menu
		menu.ensureDebugId("cwMenuBar");
		return menu;
	}

}
