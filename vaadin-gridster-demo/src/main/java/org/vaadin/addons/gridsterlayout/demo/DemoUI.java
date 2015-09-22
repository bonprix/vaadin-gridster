package org.vaadin.addons.gridsterlayout.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.gridsterlayout.demo.basic.BasicDemoTab;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

@Theme("demo")
@Title("MyComponent Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.addons.gridsterlayout.demo.DemoWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(final VaadinRequest request) {

		final TabSheet mainLayout = new TabSheet();
		mainLayout.setSizeFull();

		mainLayout.addComponent(new BasicDemoTab());

		setContent(mainLayout);
	}
}
