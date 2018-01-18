package org.mindcrack.gui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.mindcrack.files.Configurations;
import org.mindcrack.main.Main;

@SuppressWarnings("serial")
public class LoadingWin extends JFrame {
	JLabel back;
	JProgressBar jp;
	JLabel desc;
	public LoadingWin(){
		initWindow();
		try {
			Main.configurations = new Configurations();
			jp.setValue(1);
			desc.setText("Loading configurations...");
			Thread.sleep(2000);
			Main.main_win = new MainWin();
			this.setVisible(false);
			Main.loading_win = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void initWindow(){
		this.setSize(500,310);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		ImageIcon image = new ImageIcon("res/load_back.png");
		back = new JLabel(image);
		back.setBounds(0, 0, this.getWidth(), this.getHeight() - 10);
		add(back);
		jp = new JProgressBar();
		jp.setMaximum(2);
		jp.setBounds(0, 300, this.getWidth(), 10);
		add(jp);
		//TODO Description for loading window not working
		desc = new JLabel();
		desc.setBounds(0, 0, 100, 100);
		desc.setBackground(Color.red);
		desc.setHorizontalTextPosition(JLabel.LEFT);
		add(desc);
		this.setVisible(true);
	}
	
}
