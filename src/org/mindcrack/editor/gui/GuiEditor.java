package org.mindcrack.editor.gui;

import javax.swing.ImageIcon;

import org.mindcrack.gui.MPanel;

@SuppressWarnings("serial")
public class GuiEditor extends MPanel {
	String filename;
	public static GuiEditor instance;
//	Editor editor;
	public GuiEditor() {
		this("Editor");
	}
	public GuiEditor(String file) {
		filename = file;
		if(filename.lastIndexOf('.') == -1) {
			this.icon = new ImageIcon("res/editor/icon.png");
		}else {
			this.icon = new ImageIcon("res/filetype" + filename.substring(filename.lastIndexOf('.') + 1, filename.length()) + ".png");
		}
		this.name = filename;
	}
}
