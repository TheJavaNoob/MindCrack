package org.mindcrack.gui.dialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

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
      		list.setBounds(10, 80, 480, 510);
      		list.addTreeSelectionListener(new TreeSelectionListener(){
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) list.getLastSelectedPathComponent();
					if(node != null && node.isLeaf()){
						done.setEnabled(true);
					}else {
						done.setEnabled(false);
					}
			    }
		    });
      		loadList();
      	top.add(list);
    	search = new JTextField();
      		search.setBounds(10, 50, 480, 20);
      		search.addKeyListener(new KeyAdapter(){
              	@Override
              	public void keyPressed(KeyEvent e){
              		currModel = new DefaultTreeModel(newRoot = new DefaultMutableTreeNode());
                	filter((TreeNode) currModel.getRoot(), search.getText());
                }
            });
      	top.add(search);
    }
  	public void loadList(){
      	//TODO load list (in workspace)
    }
  	public boolean filter(TreeNode node, String x) {
  		boolean added = false;
  		DefaultMutableTreeNode parent = new DefaultMutableTreeNode();
  		for(int i = 0; i < node.getChildCount(); i++) {
  			DefaultMutableTreeNode curr = (DefaultMutableTreeNode) node.getChildAt(i);
  			if(curr.isLeaf()) {
  				if(!added) {
  					added = true;
  					parent = new DefaultMutableTreeNode(((DefaultMutableTreeNode) curr.getParent()).getUserObject());
  	  				currModel.insertNodeInto((MutableTreeNode) parent, (MutableTreeNode) currModel.getRoot(), 0);
  				}
  				parent.add(curr);
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