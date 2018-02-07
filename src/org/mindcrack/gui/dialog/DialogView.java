package org.mindcrack.gui.dialog;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.mindcrack.gui.MPanel;
import org.mindcrack.gui.Padder;
import org.mindcrack.gui.WindowManager;
import org.mindcrack.gui.toolbar.Toolbar;
import org.mindcrack.gui.widget.CustomTree;
import org.mindcrack.main.Main;

@SuppressWarnings("serial")
public class DialogView extends Dialog {
	JTextField search;
	JTree list;
	DefaultTreeModel model;
	DefaultTreeModel currModel;
	DefaultMutableTreeNode newRoot;
	boolean enabled;
	public DialogView() {
		super("IDE View", true);
		this.setResizable(false);
		done.setText("Add");
		done.setForeground(Color.gray);
		enabled = false;
		list = new CustomTree();
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				SwingUtilities.updateComponentTreeUI(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.setBounds(10, 40, 460, 450);
			list.addTreeSelectionListener(new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) list.getLastSelectedPathComponent();
					if (node != null && node.isLeaf()) {
						enabled = true;
						done.setForeground(Color.BLACK);
					} else {
						enabled = false;
						done.setForeground(Color.GRAY);
					}
				}
			});
			list.setRootVisible(false);
			loadList();
		top.add(list);
		search = new JTextField();
		search.setBounds(60, 10, 410, 20);
		search.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				currModel = new DefaultTreeModel(newRoot = new DefaultMutableTreeNode());
//				list.setModel(currModel);
				if (search.getText() != "") {
					filter((TreeNode) model.getRoot(), search.getText());
				}else {
					currModel = model;
				}
				list.setModel(currModel);
				for(int i = 0; i < list.getRowCount(); i++) {
					if(!((DefaultMutableTreeNode)list.getPathForRow(i).getLastPathComponent()).isLeaf())
						list.expandRow(i);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				currModel = new DefaultTreeModel(newRoot = new DefaultMutableTreeNode());
//				list.setModel(currModel);
				if (!search.getText().equals("")) {
					filter((TreeNode) model.getRoot(), search.getText());
				}else {
					currModel = model;
				}
				list.setModel(currModel);
				for(int i = 0; i < list.getRowCount(); i++) {
					if(!((DefaultMutableTreeNode)list.getPathForRow(i).getLastPathComponent()).isLeaf())
						list.expandRow(i);
				}
			}
		});
		top.add(search);
		JLabel l1 = new JLabel("Filter:");
		l1.setBounds(10, 10, 50, 20);
		top.add(l1);
	}

	public void loadList() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		for(WindowManager wm: WindowManager.mods) {
			for (WindowManager.Folder f: wm.folders.values()) {
				CustomTree.CustomMutableTreeNode folder = new CustomTree.CustomMutableTreeNode(f.name);
				for (MPanel mp: f.windows) {
					CustomTree.CustomMutableTreeNode item = new CustomTree.CustomMutableTreeNode(mp);
					item.icon = mp.icon;
					folder.add(item);
				}
				root.add(folder);
			}
		}
		model = new DefaultTreeModel(root);
		list.setModel(model);
	}

	public void filter(TreeNode node, String x) {
		boolean added = false;
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode();
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode curr = (DefaultMutableTreeNode) node.getChildAt(i);
			if (curr.isLeaf()) {
				if(curr.getUserObject().toString().indexOf(x) != -1) {
					if (!added) {
						added = true;
						parent = new CustomTree.CustomMutableTreeNode(((DefaultMutableTreeNode) curr.getParent()).getUserObject());
						currModel.insertNodeInto((CustomTree.CustomMutableTreeNode) parent, (MutableTreeNode) currModel.getRoot(), 0);
						TreePath path = new TreePath(currModel.getPathToRoot(parent));
						list.expandPath(path);
					}
					parent.add(new CustomTree.CustomMutableTreeNode(curr.getUserObject()));
				}
			}else {
				filter(curr, x);
			}
		}
	}

	@Override
	public void onDone() {
		if(!enabled)return;
		try {
			DefaultMutableTreeNode node = ((DefaultMutableTreeNode)list.getLastSelectedPathComponent());
			MPanel panel = (MPanel)node.getUserObject().getClass().newInstance();
			Padder padder = new Padder();
			padder.addTab(panel);
			padder.setBounds(0, 40 * Toolbar.toolbars.size(), 300, 300);
			Main.main_win.mainPanel.add(padder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}