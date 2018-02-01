package org.mindcrack.editor.gui;

import java.awt.Color;
import java.io.File;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;

import org.mindcrack.gui.MPanel;

/** 
 * The class of the GUI of standard command editor 
 */
@SuppressWarnings("serial")
public class GuiEditor extends MPanel {
	/** The filename of the file that the Editor is currently editing. */
	String filename;
	/** The {@link java.io.File File} instance the Editor is currently editing. */
	public File file;
	/** Whether the editor has modified since the last save. */
	public boolean modified;
	/** A {@link LinkedList} containing all the instances of GuiEditor. */
	public static LinkedList<GuiEditor> loaded = new LinkedList<GuiEditor>();
	/** A instance of GuiEditor used in {@link org.mindcrack.gui.dialog.DialogView Show Window Dialog} */
	public static GuiEditor instance;
	/** The instance of the editor itself (where you type the code) */
	public Editor editor;
	public GuiEditor() {
		this("Editor");
		loaded.add(this);
	}
	/** 
	 * Initialize the editor with a <b>absolute</b> file path
	 * @param file - The <b>absolute</b> path of the file
	 */
	public GuiEditor(String file) {
		filename = file;
		if(filename.lastIndexOf('.') == -1) {
			this.icon = new ImageIcon("res/editor/icon.png");
		}else {
			this.icon = new ImageIcon("res/filetype" + filename.substring(filename.lastIndexOf('.') + 1, filename.length()) + ".png");
		}
		this.name = filename;
	}
	/** The class of the editor where you write code
	 */
	public class Editor extends JEditorPane {
		
	}
}
