package org.mindcrack.gui.menu;

import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {
	public MenuFile file;
	public MenuWindow window;
	public Menu() {
		file = new MenuFile();
		add(file);
		window = new MenuWindow();
		add(window);
	}
}
