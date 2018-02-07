package org.mindcrack.gui;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mindcrack.files.Configurations;
import org.mindcrack.gui.menu.Menu;
import org.mindcrack.gui.toolbar.Toolbar;

@SuppressWarnings("serial")
public class MainWin extends JFrame {
	public JPanel mainPanel;
	public LinkedList<Padder> padders;
	public Menu menu;
	public MainWin(){
		padders = new LinkedList<Padder>();
		initWindow();
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				int i = 0;
				for(Toolbar toolbar:Toolbar.toolbars) {
					toolbar.setBounds(0, i * 40, MainWin.this.getWidth(), 40);
				}
			}
		});
	}
	public void initWindow(){
		this.setBounds(Configurations.mainwin_left,Configurations.mainwin_top,Configurations.mainwin_width,Configurations.mainwin_height);
		this.setVisible(true);
		this.setTitle("Mindcrack IDE");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel = new JPanel();
			mainPanel.setLayout(null);
		add(mainPanel, BorderLayout.CENTER);
		this.setJMenuBar(menu = new Menu());
		addMenu();
		mainPanel.add(new Toolbar());
		Padder p = new Padder();
		p.setBounds(0, 40, 300, 300);
		mainPanel.add(p);
	}
	public void addMenu() {
		mainPanel.add(menu.file.show);
		mainPanel.add(menu.window.show);
	}
}
