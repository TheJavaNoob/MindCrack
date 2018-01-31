package org.mindcrack.gui.widget;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class CustomTree extends JTree {
	public CustomTree() {
		this.setCellRenderer(new Renderer());
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
	}
	class Renderer extends JPanel implements TreeCellRenderer, MouseListener {
		JLabel switchComp;
		DefaultMutableTreeNode node;
		DefaultTreeCellRenderer def;
		int called = 0;
		int currRow;
		public Renderer() {
			this.setOpaque(false);
			def = new DefaultTreeCellRenderer();
			this.setLayout(new BorderLayout());
			this.addMouseListener(this);
		}
	    public void adapte(int x, int y) {
	    	TreePath path = getPathForLocation(x, y);
            CustomMutableTreeNode node = (CustomMutableTreeNode) path.getLastPathComponent();
		}
		@Override    
	    public Component getTreeCellRendererComponent(JTree tree, Object value,    
	            boolean sel, boolean expanded, boolean leaf, int row,    
	            boolean hasFocus) {
	    	JLabel main = (JLabel) def.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
	    	node = (DefaultMutableTreeNode)value;
	        switchComp = new JLabel(new ImageIcon(expanded?"res/switch_expanded.png": "res/switch_closed.png"));
        	switchComp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});
        	add(switchComp, BorderLayout.WEST);
        	if(value instanceof CustomMutableTreeNode) {
	    		((CustomMutableTreeNode)value).switchComp = switchComp;
	    	}
	        if(value instanceof CustomMutableTreeNode) {
	        	CustomMutableTreeNode node = (CustomMutableTreeNode) value;
	        	if(node.icon != null) {
	        		main.setIcon(node.icon);
	        	}
	        }
	        add(main, BorderLayout.EAST);
	        return this;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("AAAAAA");
			int x = (int) e.getX();
            int y = (int) e.getY();
            TreePath path = getPathForLocation(x, y);
            CustomMutableTreeNode node = (CustomMutableTreeNode) path.getLastPathComponent();
            int locX = node.switchComp.getX(),
            	locY = node.switchComp.getY();
            if(x >= locX && x <= locX + node.switchComp.getWidth() &&
            		y >= locY && y <= locY + node.switchComp.getHeight()) {
				boolean expanded = isExpanded(path);
				CustomTree.this.setExpandedState(path, !expanded);
				node.switchComp.setIcon(new ImageIcon(expanded?"res/switch_expanded.png": "res/switch_closed.png"));
            }
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	public static class CustomMutableTreeNode extends DefaultMutableTreeNode {
		public ImageIcon icon;
		public JLabel switchComp;
		public CustomMutableTreeNode(Object value) {
			super(value);
		}
	}
}
