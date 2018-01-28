package org.mindcrack.gui.toolbar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mindcrack.gui.Padder;
import org.mindcrack.main.Main;

@SuppressWarnings("serial")
public class Toolkit extends JPanel {
	public boolean isWindow;
	public Toolbar bar;
	public JLabel mover;
	Point origin;
	int currPos = -1;
	public Toolkit(Toolbar toolbar){
		isWindow = false;
		bar = toolbar;
		origin = new Point();
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		mover = new JLabel(new ImageIcon("res/toolkit.png"));
			mover.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					origin.x = e.getX();
					origin.y = e.getY();
				}
			});
			mover.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if(e.getY() - origin.y > 40 && !isWindow) {
						isWindow = true;
						mover.setVisible(false);
						bar.remove(Toolkit.this);
						Padder np = new Padder(Toolkit.this);
						np.setLocation(e.getXOnScreen() - Main.main_win.getX() - origin.x, e.getYOnScreen() - Main.main_win.getY() - origin.y);
						np.setSize(Toolkit.this.getWidth(), 80);
						setOpaque(true);
						Toolkit.this.setBackground(Color.white);
						Main.main_win.mainPanel.add(np);
						Main.main_win.mainPanel.repaint();
						bar.repaint();
					}else {
						findPlaceInBar(e.getX());
					}
				}
			});
		add(mover);
		//TODO Debug
		add(new Run());
	}
	public void findPlaceInBar(int X) {
		int i = 0;
		for(Toolkit kit:this.bar.toolkits) {
			if(X > kit.getX() + kit.getWidth()) {
				bar.toolkits.remove(currPos);
				currPos = i + 1;
				bar.toolkits.add(currPos, this);
				bar.rearrange();
				break;
			}
			i++;
		}
	}
	
}
