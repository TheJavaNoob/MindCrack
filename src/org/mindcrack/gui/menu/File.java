package org.mindcrack.gui.menu;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class File extends MenuClass {
	public File() {
		super("File");
	}
	@Override
	public void addItems() {
		@SuppressWarnings("serial")
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
