package org.mindcrack.files.gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeModel;

import org.mindcrack.gui.MPanel;
import org.mindcrack.gui.widget.CustomTree;
import org.mindcrack.gui.widget.CustomTree.CustomMutableTreeNode;
import org.mindcrack.project.Project;

@SuppressWarnings("serial")
public class GuiFileExplorer extends MPanel {
	CustomTree tree;
	DefaultTreeModel model;
	public static GuiFileExplorer instance;
	public GuiFileExplorer() {
		name = "File Explorer";
		this.setLayout(new BorderLayout());
		tree = new CustomTree();
		add(tree, BorderLayout.CENTER);
		updateTree();
	}
	public void updateTree() {
		if(Project.instance != null) {
			System.out.println("AA");
			CustomMutableTreeNode root = new CustomMutableTreeNode(new File(Project.instance.path));
			root.icon = new ImageIcon("res/project.png");
			model = new DefaultTreeModel(root);
			back(root);
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
