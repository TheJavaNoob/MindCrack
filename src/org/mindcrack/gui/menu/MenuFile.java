package org.mindcrack.gui.menu;

import javax.swing.JFileChooser;

import org.mindcrack.main.Main;
import org.mindcrack.project.Project;

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
		ItemClass open_project = new ItemClass("res/menu/open_project.png", "Open Project") {
			@Override
			public void click() {
				JFileChooser jf = new JFileChooser();
				jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int value = jf.showOpenDialog(Main.main_win);
				if(value == JFileChooser.APPROVE_OPTION) {
					Project.instance = Project.load(jf.getSelectedFile().getAbsolutePath());
				}
			}
		};
		this.items.add(open_project);
		show.add(open_project);
	}
}
