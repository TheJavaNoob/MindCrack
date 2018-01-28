package org.mindcrack.gui.menu;

import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class File extends MenuClass {
	public File() {
		super("File");
	}
	@Override
	public void addItems() {
		ItemClass new_project = new ItemClass("res/new_project.png", "New Project") {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Add project
			}
		};
		this.items.add(new_project);
		show.add(new_project);
	}
}
