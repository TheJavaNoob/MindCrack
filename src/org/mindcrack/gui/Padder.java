package org.mindcrack.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.mindcrack.files.Configurations;
import org.mindcrack.gui.toolbar.Toolbar;
import org.mindcrack.gui.toolbar.Toolkit;
import org.mindcrack.main.Main;

@SuppressWarnings("serial")
public class Padder extends JPanel {
	static int uuidbase = 0;
	int uuid;
	JPanel head;
	public JPanel body;
	JLabel control;
	JPanel tabCont;
	LinkedList<JPanel> tabs;
	LinkedList<JPanel> containers;
	JLabel left_comp;
	JLabel right_comp;
	JLabel down_comp;
	Point origin;
	Point origin_win;
	/** This is for a bug fixing where componentResized can only be called once */
	boolean init = false;
	protected Tab selected;
	public Padder(){
		containers = new LinkedList<JPanel>();
		origin = new Point();
		origin_win = new Point();
		tabs = new LinkedList<JPanel>();
		uuid = uuidbase++;
		initTabbers();
	}
	public Padder(JPanel pane) {
		body = pane;
		containers = new LinkedList<JPanel>();
		origin = new Point();
		origin_win = new Point();
		tabs = new LinkedList<JPanel>();
		uuid = uuidbase++;
		initTabbers();
	}
	void initTabbers(){
		setLayout(null);
		head = new JPanel(){
			@Override
		    protected void paintComponent(Graphics g1) {
				Graphics2D g = (Graphics2D) g1;
		        super.paintComponent(g1);
		        int height = getHeight();
		        GradientPaint paint = new GradientPaint(0, 0, new Color(50,255,50), 0, height,
		                new Color(200, 255, 200));
		        g.setPaint(paint);
		        RoundRectangle2D rect = new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),10,10);
		        g.fill(rect);
		    }
		};{
			
			head.setLayout(null);
			head.setBounds(0, 0, this.getWidth(), 40);
			head.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					origin.x = e.getX();
					origin.y = e.getY();
					origin_win.x = e.getX() + Padder.this.getX();
					origin_win.y = e.getY() + Padder.this.getY();
				} 
			});
			head.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					int clip_state = -1;
					int stdW = Padder.this.getWidth(),
						stdH = Padder.this.getHeight();
					int stdL = Padder.this.getX(),
						stdT = Padder.this.getY(),
						stdR = stdL + stdW,
						stdD = stdT + stdH;
					int finX = stdL + (e.getX() - origin.x),
						finY = stdT + (e.getY() - origin.y);
					//Check for toolbar
					if(body instanceof Toolkit) {
						for(Toolbar tb:Toolbar.toolbars) {
							if(finY + origin.y > tb.getY() && finY + origin.y < tb.getY() + 40) {
								((Toolkit)body).bar = tb;
								((Toolkit)body).isWindow = false;
								((Toolkit)body).findPlaceInBar(finX);
								tb.rearrange();
								Padder.this.setVisible(false);
								Main.main_win.padders.remove(Padder.this);
							}
						}
					}
					int limit = Configurations.padder_align_min;
					boolean clip_l = stdL < limit,
							clip_r = Main.main_win.getWidth() - stdR < limit,
							clip_u = stdT < limit,
							clip_d = Main.main_win.getHeight() - stdD < limit;
					//Deal with edge clipping
					if(stdL == 0) {//Left clipped, Checking leaving phase
						if(e.getX() - origin_win.x > limit)//Leaving
							finX = e.getX() - origin_win.x;
						else//Staying
							finX = 0;
						clip_state = 0;
					}else if(clip_l){//Entering left clipping zone
						finX = 0;
						origin_win.x = e.getX();
						clip_state = 0;
					}
					if(Main.main_win.getWidth() - stdR == 0) {//Leaving right
						if(origin_win.x - (e.getX() + stdL) > limit)//Leaving
							finX = Main.main_win.getWidth() - stdW - (origin_win.x - e.getX() - stdL);
						else
							finX = Main.main_win.getWidth() - stdW;
						clip_state = 0;
					}else if(clip_r) {//Entering right
						finX = Main.main_win.getWidth() - stdW;
						origin_win.x = e.getX() + stdL;
						clip_state = 0;
					}
					if(stdT == 0) {//Leaving top
						if(e.getY() - origin_win.y > limit)
							finY = e.getY() - origin_win.y;
						else
							finY = 0;
						clip_state = 0;
					}else if(clip_u){//Entering top
						finY = 0;
						origin_win.y = e.getY();
						clip_state = 0;
					}
					if(Main.main_win.getHeight() - stdD == 0) {
						if(origin_win.y - e.getY() - stdT > limit)
							finY = Main.main_win.getHeight() - stdH - (origin_win.y - e.getY() - stdT);
						else
							finY = Main.main_win.getHeight() - stdH;
						clip_state = 0;
					}else if(clip_d) {
						finY = Main.main_win.getHeight() - stdH;
						origin_win.y = e.getY() + stdT;
						clip_state = 0;
					}
					if(clip_state == 0) {
						Padder.this.setLocation(finX, finY);
//						return;
					}
					for(Padder padder: Main.main_win.padders) {
						if(padder.uuid == Padder.this.uuid)continue;
						boolean clip_w = Math.abs(padder.getX() - stdL) < limit;
						boolean clip_e = Math.abs((padder.getX() + padder.getWidth()) - stdR) < limit;
						boolean clip_n = Math.abs(padder.getY() - stdT) < limit;
						boolean clip_s = Math.abs((padder.getY() + padder.getHeight()) - stdD) < limit;
						boolean inref_lr = (stdR <= padder.getX() + padder.getWidth() && stdR >= padder.getX()) || (stdL >= padder.getX() && stdL <= padder.getX() + padder.getWidth());
						boolean inref_ud = (stdT >= padder.getY() && stdT <= padder.getY() + padder.getHeight()) || (stdD <= padder.getY() + padder.getHeight() && stdD >= padder.getY());
						boolean mag_rl = Math.abs(padder.getX() + padder.getWidth() - stdL) < Configurations.padder_align_min && inref_ud;
						boolean mag_lr = Math.abs(stdR - padder.getX()) < Configurations.padder_align_min && inref_ud;
						boolean mag_ud = Math.abs(stdD - padder.getY()) < Configurations.padder_align_min && inref_lr;
						boolean mag_du = Math.abs(stdT - (padder.getY() + padder.getHeight())) < Configurations.padder_align_min && inref_lr;
						//
						if(stdL == padder.getX() + padder.getWidth() && inref_ud) {//Clipping state
							if(Math.abs(e.getX() - (origin_win.x - stdL)) > limit)//Leaving
								finX = stdL + (e.getX() - (origin_win.x - stdL));
							else//Staying
								finX = padder.getX() + padder.getWidth();
							clip_state = 1;
						}else if(mag_rl) {//Entering
							finX = padder.getX() + padder.getWidth();
							origin_win.x = e.getX() + padder.getX() + padder.getWidth();
							clip_state = 1;
						}
						if(stdR == padder.getX() && inref_ud) {//Clipping state
							if(Math.abs(e.getX() - (origin_win.x - stdL)) > limit)//Leaving
								finX =  stdL + (e.getX() - (origin_win.x - stdL));
							else//Staying
								finX = padder.getX() - stdW;
							clip_state = 1;
						}else if(mag_lr) {//Entering
							finX = padder.getX() - stdW;
							origin_win.x = e.getX() + padder.getX() - stdW;
							clip_state = 1;
						}
						//Down - Up
						if(stdT == padder.getY() + padder.getHeight() && inref_lr) {//Clipping state
							if(Math.abs(e.getY() - (origin_win.y - stdH)) > limit)//Leaving
								finY = stdT + (e.getY() - (origin_win.y - stdT));
							else//Staying
								finY = padder.getY() + padder.getHeight();
							clip_state = 2;
						}else if(mag_du) {//Entering
							finY = padder.getY() + padder.getHeight();
							origin_win.y = e.getY() + padder.getY() + padder.getHeight();
							clip_state = 2;
						}
						if(stdD == padder.getY() && inref_lr) {//Clipping state
							if(Math.abs(e.getY() - (origin_win.y - stdT)) > limit)//Leaving
								finY =  stdT + (e.getY() - (origin_win.y - stdT));
							else//Staying
								finY = padder.getY() - stdH;
							clip_state = 2;
						}else if(mag_ud) {//Entering
							finY = padder.getY() - stdH;
							origin_win.y = e.getY() + padder.getY() - stdH;
							clip_state = 2;
						}
						//Align
						if(clip_state == 1) {
							if(stdT == padder.getY()) {//Clipping state
								if(Math.abs(e.getY() - (origin_win.y - stdT)) > limit)//Leaving
									finY =  stdT + (e.getY() - (origin_win.y - stdT));
								else//Staying
									finY = padder.getY();
							}else if(clip_n) {//Entering
								finY = padder.getY();
								origin_win.y = e.getY() + padder.getY();
							}
							if(stdD == padder.getY() + padder.getHeight()) {//Clipping state
								if(Math.abs(e.getY() - (origin_win.y - stdT)) > limit)//Leaving
									finY =  stdT + (e.getY() - (origin_win.y - stdT));
								else//Staying
									finY = padder.getY() + padder.getHeight() - stdH;
							}else if(clip_s) {//Entering
								finY = padder.getY() + padder.getHeight() - stdH;
								origin_win.y = e.getY() + padder.getY() + padder.getHeight() - stdH;
							}
						}else if(clip_state == 2) {
							if(stdL == padder.getX()) {//Clipping state
								if(Math.abs(e.getX() - (origin_win.x - stdL)) > limit)//Leaving
									finX =  stdL + (e.getX() - (origin_win.x - stdL));
								else//Staying
									finX = padder.getX();
							}else if(clip_w) {//Entering
								finX = padder.getX();
								origin_win.x = e.getX() + padder.getX();
							}
							if(stdR == padder.getX() + padder.getWidth()) {//Clipping state
								if(Math.abs(e.getX() - (origin_win.x - stdL)) > limit)//Leaving
									finX =  stdL + (e.getX() - (origin_win.x - stdL));
								else//Staying
									finX = padder.getX() + padder.getWidth() - stdW;
							}else if(clip_e) {//Entering
								finX = padder.getX() + padder.getWidth() - stdW;
								origin_win.x = e.getX() + padder.getX() + padder.getWidth() - stdW;
							}
						}
					}
					Padder.this.setLocation(finX, finY);
				}
			});
			control = new JLabel(new ImageIcon("res/controls.png"));{
				control.setSize(20, 20);
				head.add(control);
			}
			add(head);
			head.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent arg0) {
//					Main.main_win.setComponentZOrder(Padder.this, 0);
				}
			});
		}
		if(body == null) {
			body = new JPanel();
		}
		{
			body.setLayout(null);
			body.setLocation(0, 40);
			left_comp = new JLabel();{
				left_comp.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						int currX = e.getXOnScreen() - Main.main_win.getLocationOnScreen().x;
						int finX = currX;
						int stdX = Padder.this.getX();
						int limit = Configurations.padder_align_min; 
						if(stdX == 0) {//Clipping state
							if(currX > limit)//Leaving
								finX = currX;
							else//Staying
								finX = 0;
						}else if(currX < limit) {//Entering
							finX = 0;
						}
						for(Padder padder:Main.main_win.padders) {
							if(padder.uuid == Padder.this.uuid)continue;
							boolean inref = (Padder.this.getY() >= padder.getY() && Padder.this.getY() <= padder.getY() + padder.getHeight()) || (Padder.this.getY() + Padder.this.getHeight() <= padder.getY() + padder.getHeight() && Padder.this.getY() + Padder.this.getHeight() >= padder.getY());
							boolean mag = Padder.this.getY() + Padder.this.getHeight() == padder.getY() || padder.getY() + padder.getHeight() == Padder.this.getY();
							if(inref) {
								if(currX - (padder.getX() + padder.getWidth()) == 0) {//Clipping state
									if(currX - stdX > limit)//Leaving
										finX = currX - stdX;
									else//Staying
										finX = padder.getX() + padder.getWidth();
								}else if(Math.abs(currX - (padder.getX() + padder.getWidth())) < limit) {//Entering
									finX = padder.getX() + padder.getWidth();
								}
							}
							if(mag) {
								if(currX - padder.getX() == 0) {//Clipping state
									if(currX - stdX > limit)//Leaving
										finX = currX - stdX;
									else//Staying
										finX = padder.getX();
								}else if(Math.abs(currX - padder.getX()) < limit) {//Entering
									finX = padder.getX();
								}
							}
						}
						Padder.this.setSize(Padder.this.getX() + Padder.this.getWidth() - finX, Padder.this.getHeight());
						Padder.this.setLocation(finX, Padder.this.getY());
					}
				});
				left_comp.setSize(2, 0);
				left_comp.setOpaque(false);
				left_comp.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				add(left_comp);
			}
			right_comp = new JLabel();{
				right_comp.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						int currX = e.getXOnScreen() - Main.main_win.getLocationOnScreen().x;
						int finX = currX;
						int stdR = Padder.this.getX() +Padder.this.getWidth();
						int limit = Configurations.padder_align_min;
						if(stdR == Main.main_win.getWidth()) {//Clipping state
							if((currX - stdR) > limit)//Leaving
								finX = currX;
							else//Staying
								finX = Main.main_win.getWidth();
						}else if(Math.abs(currX - Main.main_win.getWidth()) < limit) {//Entering
							finX = Main.main_win.getWidth();
						}
						for(Padder padder:Main.main_win.padders) {
							if(padder.uuid == Padder.this.uuid)continue;
							boolean inref = (Padder.this.getY() >= padder.getY() && Padder.this.getY() <= padder.getY() + padder.getHeight()) || (Padder.this.getY() + Padder.this.getHeight() <= padder.getY() + padder.getHeight() && Padder.this.getY() + Padder.this.getHeight() >= padder.getY());
							boolean mag = Padder.this.getY() + Padder.this.getHeight() == padder.getY() || padder.getY() + padder.getHeight() == Padder.this.getY();
							if(inref) {
								if(currX == padder.getX()) {//Clipping state
									if(currX - stdR > limit)//Leaving
										finX = currX;
									else//Staying
										finX = padder.getX();
								}else if(Math.abs(currX - padder.getX()) < limit) {//Entering
									finX = padder.getX();
								}
							}
							if(mag) {
								if(currX - padder.getX() - padder.getWidth() == 0) {//Clipping state
									if(currX - stdR > limit)//Leaving
										finX = currX;
									else//Staying
										finX = padder.getX() + padder.getWidth();
								}else if(Math.abs(currX - padder.getX() - padder.getWidth()) < limit) {//Entering
									finX = padder.getX() + padder.getWidth();
								}
							}
						}
						Padder.this.setSize(finX - Padder.this.getX(), Padder.this.getHeight());
//						Padder.this.setLocation(finX, Padder.this.getY());
					}
				});
				right_comp.setSize(2, 0);
				right_comp.setOpaque(false);
				right_comp.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				add(right_comp);
			}
			down_comp = new JLabel();{
				down_comp.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						int currY = e.getYOnScreen() - Main.main_win.getLocationOnScreen().y - 27;
						int finY = currY;
						int stdD = Padder.this.getY() +Padder.this.getHeight();
						int limit = Configurations.padder_align_min;
						
						if(stdD == Main.main_win.getWidth()) {//Clipping state
							if((currY - stdD) > limit)//Leaving
								finY = currY;
							else//Staying
								finY = Main.main_win.getHeight();
						}else if(Math.abs(currY - Main.main_win.getHeight()) < limit + 36) {//Entering
							finY = Main.main_win.getHeight();
						}
						for(Padder padder:Main.main_win.padders) {
							if(padder.uuid == Padder.this.uuid)continue;
							boolean inref = (Padder.this.getX() >= padder.getX() && Padder.this.getX() <= padder.getX() + padder.getWidth()) || (Padder.this.getX() + Padder.this.getWidth() <= padder.getX() + padder.getWidth() && Padder.this.getX() + Padder.this.getWidth() >= padder.getX());
							boolean mag = Padder.this.getX() + Padder.this.getWidth() == padder.getX() || padder.getX() + padder.getWidth() == Padder.this.getX();
							if(inref) {
								if(stdD == padder.getY()) {//Clipping state
									if(Math.abs(currY - stdD) > limit)//Leaving
										finY = currY;
									else//Staying
										finY = padder.getY();
								}else if(Math.abs(currY - padder.getY()) < limit) {//Entering
									finY = padder.getY();
								}
							}
							if(mag) {
								if(currY - padder.getY() - padder.getWidth() == 0) {//Clipping state
									if(currY - stdD > limit)//Leaving
										finY = currY;
									else//Staying
										finY = padder.getY() + padder.getHeight();
								}else if(Math.abs(currY - padder.getY() - padder.getHeight()) < limit) {//Entering
									finY = padder.getY() + padder.getHeight();
								}
							}
						}
						
						Padder.this.setSize(Padder.this.getWidth(), finY - Padder.this.getY() - 32);
						down_comp.setLocation(0, Padder.this.getHeight() - 2);
					}
				});
				down_comp.setSize(0, 2);
				down_comp.setOpaque(false);
				down_comp.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
				add(down_comp);
			}
			add(body);
			body.setBackground(Color.WHITE);
		}
		this.addComponentListener(new ComponentAdapter(){
			@Override public void componentResized(ComponentEvent e){
				int width = e.getComponent().getWidth();
				int height = e.getComponent().getHeight();
			    head.setSize(width, 40);
			    control.setLocation(width - 30, 10);
			    body.setSize(width, height - 40);
			    head.repaint();
			    left_comp.setBounds(0, 0, 2,height);
			    right_comp.setBounds(width - 2, 0, 2,height);
			    down_comp.setBounds(0, height - 2, width, 2);
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				head.repaint();
			}
		});
	}
	public void addTab(MPanel panel) {
		Tab tab = new Tab(panel);
		tabs.add(tab);
		head.add(tab);
	}
	class Tab extends JPanel{
		MPanel body;
		JLabel icon;
		JLabel name;
		JLabel close;
		boolean selected;
		public Tab(MPanel panel) {
			body = panel;
			initPanel();
		}
		void initPanel() {
			this.setOpaque(false);
			this.setLayout(null);
			selected = (tabs.size() == 0);
			icon = new JLabel(body.icon);
				icon.setBounds(10, 10, 20, 20);
			add(icon);
			name = new JLabel(body.name);
				FontMetrics fm = name.getFontMetrics(name.getFont());
				name.setBounds(40, 5, fm.charWidth('z') * body.name.length(), 30);
			add(name);
			close = new JLabel(new ImageIcon("res/close.png"));
				close.setBounds(name.getWidth() + 40, 10, 20, 20);
				close.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
					}
				});
			add(close);
			this.setSize(name.getWidth() + 80,40);
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Padder.this.body = Tab.this.body;
					Padder.this.selected = Tab.this;
					Tab.this.selected = true;
					repaint();
				}
			});
		}
		@Override
		public void paint(Graphics g1) {
			Graphics2D g = (Graphics2D) g1;
			if(selected) {
				g.setColor(Color.white);
			}else {
		        GradientPaint paint = new GradientPaint(0, 0, new Color(50,255,50), 0, 40,
		                new Color(200, 255, 200));
		        g.setPaint(paint);
		    }
	        g.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
	        g.fillRect(-5, 35, 185, 5);
	        super.paint(g1);
	    }
	}
}
