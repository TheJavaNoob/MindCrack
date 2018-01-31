package org.mindcrack.gui;

import org.mindcrack.editor.gui.GuiEditor;

public class MindcrackWindowManager extends WindowManager {
	public MindcrackWindowManager() {
		WindowManager.Folder development = new WindowManager.Folder("Development");
			development.windows.add(GuiEditor.instance = new GuiEditor("Editor"));
		folders.put("Development", development);
		WindowManager.mods.add(this);
	}

}
