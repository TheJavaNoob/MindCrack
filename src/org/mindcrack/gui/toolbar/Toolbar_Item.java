package org.mindcrack.gui.toolbar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public abstract class Toolbar_Item extends JPanel {
	public JLabel main;
	public JLabel expand;
	public JPopupMenu menu;
	public Toolbar_Item(String image, String tooltip, boolean hasSubItems) {
		this.setBackground(new Color(150,255,150));
		this.setOpaque(false);
		this.setToolTipText(tooltip);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setSize(20,20);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Toolbar_Item.this.setOpaque(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Toolbar_Item.this.setOpaque(false);
			}
		});
		main = new JLabel(new ImageIcon(image));
			main.setBounds(0, 10, 20, 20);
			main.setOpaque(false);
			main.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					click();
				}
			});
		add(main);
		if(hasSubItems) {
			expand = new JLabel(new ImageIcon("res/arrow_down.png"));
			expand.setOpaque(false);
				expand.setBounds(20, 10, 20, 20);
				expand.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						menu.show(Toolbar_Item.this,Toolbar_Item.this.getX() , Toolbar_Item.this.getY() + 20);
					}
				});
				this.setSize(40, 20);
			add(expand);
			menu = new JPopupMenu();
		}
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Toolbar_Item.this.setOpaque(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Toolbar_Item.this.setOpaque(false);
			}
		});
	}
	public abstract void click();
	public void addSubItems() {
	}
}
