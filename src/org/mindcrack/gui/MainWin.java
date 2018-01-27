package org.mindcrack.gui;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mindcrack.files.Configurations;
import org.mindcrack.gui.menu.Menu;

@SuppressWarnings("serial")
public class MainWin extends JFrame {
	public JPanel mainPanel;
	public LinkedList<Padder> padders;
	Menu menu;
	public MainWin(){
		padders = new LinkedList<Padder>();
		initWindow();

	}
	public void initWindow(){
		this.setBounds(Configurations.mainwin_left,Configurations.mainwin_top,Configurations.mainwin_width,Configurations.mainwin_height);
		this.setVisible(true);
		this.setTitle("Mindcrack IDE");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		add(mainPanel);
		this.setJMenuBar(menu = new Menu());
		mainPanel.add(menu.file.show);
	}
}
