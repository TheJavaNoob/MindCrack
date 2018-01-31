package org.mindcrack.gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MPanel extends JPanel {
	public String name;
	public ImageIcon icon;
	@Override
	public String toString() {
		return name;
	}
}
