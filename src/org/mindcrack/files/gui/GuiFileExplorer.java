package org.mindcrack.files.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultTreeModel;

import org.mindcrack.files.Configurations;
import org.mindcrack.gui.MPanel;
import org.mindcrack.gui.widget.CustomTree;
import org.mindcrack.gui.widget.CustomTree.CustomMutableTreeNode;
import org.mindcrack.project.Project;

/** The GUI of file explorer */
@SuppressWarnings("serial")
public class GuiFileExplorer extends MPanel {
	/** A {@link CustomTree} representing the file system of the project. */
	public CustomTree tree;
	/** The JLabel shown when no project is opened */
	JLabel message;
	DefaultTreeModel model;
	/**
	 * A instance of GuiFileExplorer, used in
	 * {@link org.mindcrack.gui.dialog.DialogView Show Window Dialog}.
	 */
	public static GuiFileExplorer instance;
	/** All the instances of GuiFileExplorers loaded */
	public static LinkedList<GuiFileExplorer> loaded = new LinkedList<GuiFileExplorer>();
	/** The instance of popup menu */
	Popup popup;

	public GuiFileExplorer() {
		popup = new Popup();
		name = "File Explorer";
		this.setLayout(new BorderLayout());
		tree = new CustomTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3) {
					CustomMutableTreeNode node;
					try {
						node = (CustomMutableTreeNode)tree.getPathForLocation(e.getX(), e.getY()).getLastPathComponent();
					}catch (NullPointerException exc) {
						return;
					}
					if(node != null) {
						popup.show(tree, e.getX(), e.getY());
					}
				}
			}
		});
		add(tree, BorderLayout.CENTER);
		message = new JLabel("No project is opened currently.");
		message.setVisible(false);
		add(message, BorderLayout.NORTH);
		loaded.add(this);
		updateTree();
	}

	/** Update the view of the project, used when the file system has changed. */
	public void updateTree() {
		if (Project.instance != null) {
			tree.setVisible(true);
			tree.setRootVisible(false);
			tree.setShowsRootHandles(false);
			message.setVisible(false);
			CustomMutableTreeNode root = new CustomMutableTreeNode("");
			CustomMutableTreeNode proj = new CustomMutableTreeNode(new CustomFile(Project.instance.path));
			root.add(proj);
			proj.icon = new ImageIcon("res/project.png");
			model = new DefaultTreeModel(root);
			back(proj);
			tree.setModel(model);
		} else {
			tree.setVisible(false);
			message.setVisible(true);
		}
	}

	private void back(CustomMutableTreeNode node) {
		File file = (File) node.getUserObject();
		for (File f : file.listFiles()) {
			CustomMutableTreeNode add = new CustomMutableTreeNode(new CustomFile(f));
			if (f.isDirectory()) {
				add.icon = new ImageIcon("res/folder.png");
			} else {
				add.icon = new ImageIcon("res/filetype/"
						+ f.getName().substring(f.getName().lastIndexOf('.') + 1, f.getName().length()));
			}
			node.add(add);
			if (f.isDirectory()) {
				back(add);
			}
		}
	}

	class Popup extends JPopupMenu {
		JMenuItem add;
		LinkedList<JMenuItem> defaultFiles;
		public Popup() {
			initAdd();
		}
		void initAdd() {
			add = new JMenuItem("Add File");
			defaultFiles = new LinkedList<JMenuItem>();
			for (String s : Configurations.defaultAddItems) {
				JMenuItem item = new JMenuItem(s.substring(0, s.indexOf(":")));
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(Popup.this.getInvoker());
					}
				});
			}
		}
	}

	class CustomFile extends File {
		public CustomFile(File file) {
			this(file.getAbsolutePath());
		}

		public CustomFile(String pathname) {
			super(pathname);
		}

		@Override
		public String toString() {
			return this.getName();
		}
	}
}
