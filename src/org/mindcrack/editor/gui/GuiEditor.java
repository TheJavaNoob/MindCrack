package org.mindcrack.editor.gui;

import java.io.File;

import javax.swing.ImageIcon;

import org.mindcrack.gui.MPanel;

public class GuiEditor extends MPanel {
	String filename;
	public GuiEditor(File file) {
		filename = file.getName();
//		System.out.println();
		this.icon = new ImageIcon("res/" + filename.substring(filename.lastIndexOf('.') + 1, filename.length()) + ".png");
		this.name = filename;
	}
}
