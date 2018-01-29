package org.mindcrack.gui.menu;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public abstract class ItemClass extends JLabel {
	boolean entered;
	ImageIcon image;
	public ItemClass(String img, String tooltip) {
		this.setSize(80, 80);
		this.image = new ImageIcon(img);
		this.setIcon(this.image);
		this.setToolTipText(tooltip);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				image.setImage(image.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
				ItemClass.this.setSize(100,100);
				ItemClass.this.setIcon(image);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
				ItemClass.this.setSize(80, 80);
				ItemClass.this.setIcon(image);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				click();
			}
		});
	}
	public void click() {
		
	}
}
