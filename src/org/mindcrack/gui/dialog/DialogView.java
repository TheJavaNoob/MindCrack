package org.mindcrack.gui.dialog;

import java.util.Enumeration;

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

import org.mindcrack.gui.Padder;

@SuppressWarnings("serial")
public class DialogView extends Dialog {
	JTextField search;
	JTree list;
	DefaultTreeModel model;
	DefaultTreeModel currModel;
	DefaultMutableTreeNode newRoot;

	public DialogView() {
		super("IDE View", true);
		done.setText("Add");
		list = new JTree();
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
					done.setEnabled(true);
				} else {
					done.setEnabled(false);
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
				if (search.getText() != "") {
					filter((TreeNode) model.getRoot(), search.getText());
				}else {
					currModel = model;
				}
				list.setModel(currModel);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				currModel = new DefaultTreeModel(newRoot = new DefaultMutableTreeNode());
				if (search.getText() != "") {
					filter((TreeNode) model.getRoot(), search.getText());
				}else {
					currModel = model;
				}
				list.setModel(currModel);
			}
		});
		top.add(search);
		JLabel l1 = new JLabel("Filter:");
		l1.setBounds(10, 10, 50, 20);
		top.add(l1);
	}

	public void loadList() {
		String[] a = new String[] {"Hello", "Thank", "You", "Very", "Much"};
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		model = new DefaultTreeModel(root);
		for (int i = 0; i < 5; i++) {
			DefaultMutableTreeNode folder = new DefaultMutableTreeNode("Source " + new Integer(i).toString());
			for (int j = 0; j < 5; j++) {
				DefaultMutableTreeNode item;
				if(i == 2 && j == 2)
					item = new DefaultMutableTreeNode("ZYH");
				else
					item = new DefaultMutableTreeNode(a[j]);
				folder.add(item);
			}
			root.add(folder);
		}
		model = new DefaultTreeModel(root);
		list.setModel(model);
	}

	public boolean filter(TreeNode node, String x) {
		boolean added = false;
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode();
//		System.out.println(node.getChildCount());
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode curr = (DefaultMutableTreeNode) node.getChildAt(i);
			if (curr.isLeaf()) {
				if(curr.getUserObject().toString().indexOf(x) != -1) {
					if (!added) {
						added = true;
						parent = new DefaultMutableTreeNode(((DefaultMutableTreeNode) curr.getParent()).getUserObject());
						currModel.insertNodeInto((MutableTreeNode) parent, (MutableTreeNode) currModel.getRoot(), 0);
						TreePath path = new TreePath(currModel.getPathToRoot(parent));
						list.setSelectionPath(path);
//						list.expandPath(path);
					}
					parent.add(curr);
				}
			}else {
				filter(curr, x);
			}
		}
		return rootPaneCheckingEnabled;
	}

	@Override
	public void onDone() {
		Padder padder = new Padder();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) list.getLastSelectedPathComponent();
	}
}