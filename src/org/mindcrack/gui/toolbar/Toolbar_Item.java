package org.mindcrack.gui.toolbar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/** 
 * Declaring the item in a {@link Toolkit}
 */
@SuppressWarnings("serial")
public abstract class Toolbar_Item extends JPanel {
	public JLabel main;
	public JLabel expand;
	public JPopupMenu menu;
	boolean menuEnabled;
	public String img;
	/**
	 * Initialize the {@linkplain Toolbar_Item Toolbar Item} with a image filename and a tooltip text
	 * @param image - The <b>filename</b> of image, <b>without</b> the suffix
	 * @param tooltip - The text shown while the mouse is above the item 
	 * @param hasMenu - Whether this item has a pop-up menu beside it or not 
	 */
	public Toolbar_Item(String image, String tooltip, boolean hasMenu) {
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
		img = image;
		main = new JLabel(new ImageIcon("res/toolbar/" + img + ".png"));
			main.setBounds(0, 10, 20, 20);
			main.setOpaque(false);
			main.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(menuEnabled)
						click();
					if(menuEnabled)
						main.setIcon(new ImageIcon("res/toolbar/" + img + ".png"));
					else
						main.setIcon(new ImageIcon("res/toolbar/" + img + "_disabled.png"));
				}
			});
		add(main);
		if(hasMenu) {
			expand = new JLabel(new ImageIcon("res/toolbar/arrow_down.png"));
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
	/** Called when the <b>main button (not the menu)</b> is clicked */
	public abstract void click();
	/** 
	 * Add the menu items to the pop-up menu (if exists), add JMenuItems into the {@code menu} field.<br>
	 * <b>Note:</b> Only override it when {@code hasMenuItem} is true.
	 * 
	 */
	public void addMenuItems() {
	}
}
