package org.mindcrack.files.gui;

import org.mindcrack.gui.toolbar.Toolbar_Item;
import org.mindcrack.project.Project;

/** A class containing all the class of {@link Toolbar_Item Toolbar Item} */
@SuppressWarnings("serial")
public class ToolbarFiles {
	/** Save all button */
  	public static class Save extends Toolbar_Item {
        public Save(){
            super("res/toolbar/save_all.png", "Save All", false);
        }
		@Override
		public void click() {
			Project.instance.files.save();
		}	
    }
}