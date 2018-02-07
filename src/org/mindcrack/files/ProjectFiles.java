package org.mindcrack.files;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JOptionPane;

import org.mindcrack.editor.gui.GuiEditor;
import org.mindcrack.files.gui.GuiFileExplorer;
import org.mindcrack.project.Project;
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
  	public void delete(File file) {
  		if(JOptionPane.showConfirmDialog(null, "Do you want to delete " + file.getName(), "Confirm delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
  			file.delete();
  		}
  	}
  	public void refresh() {
  		Project.instance = Project.load(Project.instance.path);
  		for(GuiFileExplorer gfe: GuiFileExplorer.loaded) {
  			gfe.updateTree();
  		}
  	}
  	public void rename(File file, String newName) {
  		try {
  			File dest = new File(file.getPath() + "/" + newName);
  			if(!file.renameTo(dest)) {
  				JOptionPane.showMessageDialog(null, "File deleting failed", "Error", JOptionPane.ERROR_MESSAGE);
  			}
  		}catch(Exception e) {
  			e.printStackTrace();
  		}
  	}
}