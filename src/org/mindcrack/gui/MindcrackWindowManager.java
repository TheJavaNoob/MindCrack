package org.mindcrack.gui;

import org.mindcrack.editor.gui.GuiEditor;
import org.mindcrack.files.gui.GuiFileExplorer;

public class MindcrackWindowManager extends WindowManager {
	public MindcrackWindowManager() {
		WindowManager.Folder development = new WindowManager.Folder("Development");
			development.windows.add(GuiEditor.instance = new GuiEditor());
			development.windows.add(GuiFileExplorer.instance = new GuiFileExplorer());
		folders.put("Development", development);
		WindowManager.mods.add(this);
	}

}
