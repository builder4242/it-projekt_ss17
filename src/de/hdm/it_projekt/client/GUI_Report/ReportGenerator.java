package de.hdm.it_projekt.client.GUI_Report;


import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


public class ReportGenerator implements EntryPoint {

	
	
	public void onModuleLoad() {

		final HorizontalPanel testpanel = new HorizontalPanel();
		Grid form = new Grid (2,2);
		
		Label text = new Label("Hallo");
		Button einButton = new Button("BTText");
		einButton.setStyleName("myprojekt-formbutton");
		form.setWidget(0, 0, einButton);
		form.setWidget(0, 1, text);
		einButton.addClickHandler(new ChangeClickhandler());
		testpanel.add(form);

		
		RootPanel.get("content").add(new Label("Report Generator"));
		RootPanel.get("content").add(testpanel);
	}
		
	private class ChangeClickhandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			Window.alert("test");
		}
	}	

}
