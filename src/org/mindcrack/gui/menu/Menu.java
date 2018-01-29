package org.mindcrack.gui.menu;

import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {
	public File file;
	public Window window;
	public Menu() {
		file = new File();
		add(file);
		window = new Window();
		add(window);
	}
}
