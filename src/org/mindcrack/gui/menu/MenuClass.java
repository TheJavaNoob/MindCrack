package org.mindcrack.gui.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

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
		show.setBounds(0, 0, 300, 200);
		show.setLayout(new FlowLayout(FlowLayout.LEFT));
		show.setVisible(false);
		this.addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void menuDeselected(MenuEvent e) {
				expand = false;
				show.setVisible(expand);
			}
			@Override
			public void menuSelected(MenuEvent e) {
				expand = true;
				show.setVisible(expand);
			}
		});
		addItems();
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
