package org.mindcrack.gui.toolbar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Toolbar extends JPanel {
	public static LinkedList<Toolbar> toolbars = new LinkedList<Toolbar>();
	public LinkedList<Toolkit> toolkits = new LinkedList<Toolkit>();
	public Toolbar() {
		toolbars.add(this);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setOpaque(false);
		//DEBUG
		Toolkit kit1 = new Toolkit(this);
		add(kit1);
	}
	@Override
	public void paint(Graphics g) {
		Graphics2D g1 = (Graphics2D) g;
        int height = getHeight();
        GradientPaint paint = new GradientPaint(0, 0, new Color(200,255,200), 0, height,
                new Color(100, 255, 100));
        g1.setPaint(paint);
        RoundRectangle2D rect = new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),10,10);
        g1.fill(rect);
        super.paint(g);
    }
	public void rearrange() {
		this.removeAll();
		for(Toolkit kit:toolkits) {
			add(kit);
		}
	}
}
