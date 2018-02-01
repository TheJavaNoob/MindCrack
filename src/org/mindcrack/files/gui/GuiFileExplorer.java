package org.mindcrack.files.gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.tree.DefaultTreeModel;

import org.mindcrack.gui.MPanel;
import org.mindcrack.gui.widget.CustomTree;
import org.mindcrack.gui.widget.CustomTree.CustomMutableTreeNode;
import org.mindcrack.project.Project;

/** The GUI of file explorer */
@SuppressWarnings("serial")
public class GuiFileExplorer extends MPanel {
	/** A {@link CustomTree} representing the file system of the project. */
	CustomTree tree;
	DefaultTreeModel model;
	/** A instance of GuiFileExplorer, used in {@link org.mindcrack.gui.dialog.DialogView Show Window Dialog}. */
	public static GuiFileExplorer instance;
	public GuiFileExplorer() {
		name = "File Explorer";
		this.setLayout(new BorderLayout());
//		tree = new CustomTree();
//		add(tree, BorderLayout.CENTER);
		JLabel jl = new JLabel("AAAA");
		add(jl, BorderLayout.CENTER);
//		updateTree();
	}
	/** Update the view of the project, used when the file system has changed. */
	public void updateTree() {
		if(Project.instance != null) {
			CustomMutableTreeNode root = new CustomMutableTreeNode(new File(Project.instance.path));
			root.icon = new ImageIcon("res/project.png");
			model = new DefaultTreeModel(root);
			back(root);
			tree.setModel(model);
		}
		
	}
	private void back(CustomMutableTreeNode node) {
		File file = ((File) node.getUserObject());
		for(File f: file.listFiles()) {
			CustomMutableTreeNode add = new CustomMutableTreeNode(f);
			if(f.isDirectory()) {
				add.icon = new ImageIcon("res/folder.png");
			}else {
				add.icon = new ImageIcon("res/filetype/" + f.getName().substring(f.getName().lastIndexOf('.') + 1, f.getName().length()));
			}
			node.add(add);
			if(f.isDirectory()) {
				back(add);
			}
		}
	}
}
