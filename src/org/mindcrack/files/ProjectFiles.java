package org.mindcrack.files;

import java.io.FileWriter;

import org.mindcrack.editor.gui.GuiEditor;
/** Manage the files in the current project */
public class ProjectFiles {
  	public ProjectFiles() {
    }
  	/** Save all the modified files */
  	public void save() {
      	for(GuiEditor ge: GuiEditor.loaded){
          	if(ge.modified){
          		try {
          			FileWriter fw = new FileWriter(ge.file);
					fw.write(ge.editor.getText());
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}							
            }
        }
    }
}