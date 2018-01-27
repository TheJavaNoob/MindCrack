package org.mindcrack.gui.menu;

import javax.swing.JMenuBar;

public class Menu extends JMenuBar {
	public File file;
	public Menu() {
		file = new File();
		add(file);
	}
}
