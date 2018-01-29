package org.mindcrack.gui.toolbar;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class Run extends Toolbar_Item {
	public Run() {
		super("res/toolbar/run.png", "Run", true);
	}
	@Override
	public void click() {
		//TODO Click run toolbar
	}
	@Override
	public void addSubItems() {
		//TODO Selector & reading configurations
		//DEBUGGING!
		JMenuItem test = new JMenuItem("Temp");
		menu.add(test);
	}
}
