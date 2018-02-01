package org.mindcrack.gui.menu;

import org.mindcrack.gui.dialog.DialogView;

@SuppressWarnings("serial")
public class MenuWindow extends MenuClass {
	ItemClass window;
	ItemClass toolbar;
	DialogView dialog_view;
	public MenuWindow() {
		super("Window");
	}

	@Override
	public void addItems() {
		window = new ItemClass("res/menu/show.png", "Show Window") {
			@Override
			public void click() {
				if(dialog_view == null)
					dialog_view = new DialogView();
				dialog_view.setVisible(true);
			}
		};
		addItem(window);
		toolbar = new ItemClass("res/menu/toolbar.png", "Edit Toolbar") {
			@Override
			public void click() {

			}
		};
		addItem(toolbar);
	}
}