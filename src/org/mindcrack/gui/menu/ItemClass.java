package org.mindcrack.gui.menu;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class ItemClass extends JLabel implements MouseListener {
	boolean entered;
	ImageIcon image;
	public ItemClass(String image, String tooltip) {
		this.setSize(80, 80);
		this.image = new ImageIcon(image);
		this.setIcon(this.image);
		this.setToolTipText(tooltip);
		this.addMouseListener(this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		image.setImage(image.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
		this.setSize(100,100);
		this.setIcon(image);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
		this.setSize(80, 80);
		this.setIcon(image);
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
