package org.mindcrack.gui.menu;

@SuppressWarnings("serial")
public class MenuFile extends MenuClass {
	public MenuFile() {
		super("File");
	}
	@Override
	public void addItems() {
		ItemClass new_project = new ItemClass("res/menu/new_project.png", "New Project") {
			@Override
			public void click() {
				//Add project
			}
		};
		this.items.add(new_project);
		show.add(new_project);
	}
}
