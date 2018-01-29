package org.mindcrack.gui.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.mindcrack.main.Main;

@SuppressWarnings("serial")
public abstract class MenuClass extends JMenu{
	boolean expand = false;
	public JPanel show;
	public LinkedList<ItemClass> items = new LinkedList<ItemClass>();
	public MenuClass(String name) {
		super(name);
		show = new JPanel();
		show.setBorder(BorderFactory.createEtchedBorder());
		show.setBackground(Color.white);
		show.setSize(300, 200);
		this.setMenuLocation(500, 0);
//		Main.main_win.menu.file
		show.setLayout(new FlowLayout(FlowLayout.LEFT));
		show.setVisible(false);
		this.addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(MenuEvent e) {
			}
			@Override
			public void menuDeselected(MenuEvent e) {
				expand = false;
				show.setVisible(expand);
			}
			@Override
			public void menuSelected(MenuEvent e) {
				expand = true;
				show.setLocation(MenuClass.this.getX(), 0);
				show.setVisible(expand);
			}
		});
		addItems();
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	public void addItem(ItemClass item) {
		items.add(item);
		show.add(item);
	}
	public abstract void addItems();
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		System.out.println("Invoked");
//		expand = !expand;
//		show.setVisible(expand);
//	}
}
