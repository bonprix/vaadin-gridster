package org.vaadin.addons.gridsterlayout.demo.basic;

import java.util.Map;

import org.vaadin.addons.gridsterlayout.ComponentResizeListener;
import org.vaadin.addons.gridsterlayout.GridsterLayout;
import org.vaadin.addons.gridsterlayout.StateChangeListener;
import org.vaadin.addons.gridsterlayout.client.GridsterConfig;
import org.vaadin.addons.gridsterlayout.client.GridsterWidgetPosition;

import com.vaadin.data.Item;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class BasicDemoTab extends CustomComponent {

	private int numLayouts = 1;

	public BasicDemoTab() {
		setCaption("Basic demo");

		// Show it in the middle of the screen
		final VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setStyleName("demoContentLayout");
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		setSizeFull();

		// Initialize our new UI component
		final GridsterConfig config = new GridsterConfig();
		config.setMinCols(20);
		config.setMaxCols(20);
		config.setWidgetMargins(10);
		config.setWidgetBaseDimensionX(80);
		config.setWidgetBaseDimensionY(80);
		final GridsterLayout gridsterLayout = new GridsterLayout(config);
		gridsterLayout.setSizeFull();

		final TextArea textArea = new TextArea();
		textArea.setSizeFull();

		// row 1
		gridsterLayout.addComponent(new Label("Label 1"), new GridsterWidgetPosition(1, 1, 3, 3));
		gridsterLayout.addComponent(new Label("Label 2"), new GridsterWidgetPosition(4, 1, 3, 2));
		
		// logic row 2 - given height of row 1 objects.
		gridsterLayout.addComponent(new TextField("Textfield"), new GridsterWidgetPosition(1, 4, 3, 2));
		gridsterLayout.addComponent(new CheckBox("Checkbox"), new GridsterWidgetPosition(4, 4, 3, 2));
		
		// row 3
		gridsterLayout.addComponent(new ComboBox("Combobox"), new GridsterWidgetPosition(1, 4, 3, 2));
		gridsterLayout.addComponent(textArea, new GridsterWidgetPosition(7, 4, 3, 2));
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		
		gridsterLayout.addComponent(layout, new GridsterWidgetPosition(2, 4, 3, 3));
		Table table = new Table("A test table");
		table.setSizeFull();
		layout.addComponent(table);
		
		
		loadTable(table);
		

		final Button addWidgetButton = new Button("Add some Button", new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				gridsterLayout.addComponent(new Label("new Label"), new GridsterWidgetPosition(1, 10, 1, 1));
			}
		});

		final ComboBox layoutCombobox = new ComboBox("Serialized Layouts");

		final Button saveButton = new Button("Save layout", new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				layoutCombobox.addItems(new ReportSet("Layout " + numLayouts++, gridsterLayout.getLayoutState()));
			}
		});
		final Button loadButton = new Button("load selected layout", new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				if (layoutCombobox.getValue() != null) {
					final ReportSet item = (ReportSet) layoutCombobox.getValue();

					gridsterLayout.removeAllComponents();

					for (final Map.Entry<Component, GridsterWidgetPosition> w : item.getPositions().entrySet()) {
						gridsterLayout.addComponent(w.getKey(), w.getValue());
						System.out.println(w.getKey() + ": " + w.getValue());
					}
				} else {
					System.out.println("nothing selected");
				}

			}

		});

		final HorizontalLayout controlLayout = new HorizontalLayout(addWidgetButton, saveButton, layoutCombobox, loadButton);

		mainLayout.addComponent(controlLayout);
		mainLayout.addComponent(gridsterLayout);
		mainLayout.setComponentAlignment(gridsterLayout, Alignment.MIDDLE_CENTER);
		mainLayout.setExpandRatio(gridsterLayout, 1);

		gridsterLayout.addComponentResizeListener(new ComponentResizeListener() {

			@Override
			public void onComponentResize(final ComponentResizeEvent event) {
				Notification.show("Component " + event.getTarget().getCaption() + " resized to " + event.getPosition());

			}
		});
		gridsterLayout.addStateChangeListener(new StateChangeListener() {

			@Override
			public void onStateChange(final StateChangeEvent event) {
				Notification.show("Serializable state of gridsterLayout changed");
			}
		});

		setCompositionRoot(mainLayout);

	}

	@SuppressWarnings("unchecked")
	private void loadTable(Table table)
	{
		// Define two columns for the built-in container
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Mag",  Float.class, null);

		// Add a row the hard way
		Object newItemId = table.addItem();
		Item row1 = table.getItem(newItemId);
		row1.getItemProperty("Name").setValue("Sirius");
		row1.getItemProperty("Mag").setValue(-1.46f);

		// Add a few other rows using shorthand addItem()
		table.addItem(new Object[]{"Canopus",        -0.72f}, 2);
		table.addItem(new Object[]{"Arcturus",       -0.04f}, 3);
		table.addItem(new Object[]{"Alpha Centauri", -0.01f}, 4);

		// Show exactly the currently contained rows (items)
		//table.setPageLength(table.size());
	}

}
