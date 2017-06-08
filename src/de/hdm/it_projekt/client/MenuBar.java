package de.hdm.it_projekt.client;

import com.google.appengine.repackaged.com.google.io.base.shell.Command;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItem;

public class MenuBar {
	
	public static interface CwConstants extends Constants {
	    String cwMenuBarDescription();

	    String cwMenuBarEditCategory();

	    String[] cwMenuBarEditOptions();

	    String cwMenuBarFileCategory();

	    String[] cwMenuBarFileOptions();

	    String[] cwMenuBarFileRecents();

	    String[] cwMenuBarGWTOptions();

	    String cwMenuBarHelpCategory();

	    String[] cwMenuBarHelpOptions();

	    String cwMenuBarName();

	    String[] cwMenuBarPrompts();
	  }

	
	 /**
	   * An instance of the constants.
	   */
	  private final CwConstants constants;

	  /**
	   * Initialize this example.
	   */
	  
	  public Widget onInitialize() {
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

	    // Create a sub menu of recent documents
	    MenuBar recentDocsMenu = new MenuBar(true);
	    String[] recentDocs = constants.cwMenuBarFileRecents();
	    for (int i = 0; i < recentDocs.length; i++) {
	      recentDocsMenu.addItem(recentDocs[i], menuCommand);
	    }

	    // Create the file menu
	    MenuBar fileMenu = new MenuBar(true);
	    fileMenu.setAnimationEnabled(true);
	    menu.addItem(new MenuItem(constants.cwMenuBarFileCategory(), fileMenu));
	    String[] fileOptions = constants.cwMenuBarFileOptions();
	    for (int i = 0; i < fileOptions.length; i++) {
	      if (i == 3) {
	        fileMenu.addSeparator();
	        fileMenu.addItem(fileOptions[i], recentDocsMenu);
	        fileMenu.addSeparator();
	      } else {
	        fileMenu.addItem(fileOptions[i], menuCommand);
	      }
	    }

	    // Create the edit menu
	    MenuBar editMenu = new MenuBar(true);
	    menu.addItem(new MenuItem(constants.cwMenuBarEditCategory(), editMenu));
	    String[] editOptions = constants.cwMenuBarEditOptions();
	    for (int i = 0; i < editOptions.length; i++) {
	      editMenu.addItem(editOptions[i], menuCommand);
	    }

	    // Create the GWT menu
	    MenuBar gwtMenu = new MenuBar(true);
	    menu.addItem(new MenuItem("GWT", true, gwtMenu));
	    String[] gwtOptions = constants.cwMenuBarGWTOptions();
	    for (int i = 0; i < gwtOptions.length; i++) {
	      gwtMenu.addItem(gwtOptions[i], menuCommand);
	    }

	    // Create the help menu
	    MenuBar helpMenu = new MenuBar(true);
	    menu.addSeparator();
	    menu.addItem(new MenuItem(constants.cwMenuBarHelpCategory(), helpMenu));
	    String[] helpOptions = constants.cwMenuBarHelpOptions();
	    for (int i = 0; i < helpOptions.length; i++) {
	      helpMenu.addItem(helpOptions[i], menuCommand);
	    }

	    // Return the menu
	    menu.ensureDebugId("cwMenuBar");
	    return menu;
	  }

	private void setAnimationEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void setAutoOpen(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void setWidth(String string) {
		// TODO Auto-generated method stub
		
	}

	private void addItem(MenuItem menuItem) {
		// TODO Auto-generated method stub
		
	}

	private void addItem(String string, Command menuCommand) {
		// TODO Auto-generated method stub
		
	}

	private void addSeparator() {
		// TODO Auto-generated method stub
		
	}

}
