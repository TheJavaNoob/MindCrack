package org.mindcrack.gui;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mindcrack.files.Configurations;

@SuppressWarnings("serial")
public class MainWin extends JFrame {
	public JPanel mainPanel;
	public LinkedList<Padder> padders;
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
		//Debug start
			Padder padder = new Padder();
			padder.setBounds(0,0,300,300);
			mainPanel.add(padder);
			padders.add(padder);
			Padder padder2 = new Padder();
			padder2.setBounds(0,400,300,300);
			mainPanel.add(padder2);
			padders.add(padder2);
		//Debug end
		add(mainPanel);
	}
}
