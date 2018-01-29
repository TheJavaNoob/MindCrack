package org.mindcrack.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class DialogGuide extends Dialog {
  	public LinkedList<SPanel> steps = new LinkedList<SPanel>();
  	public int step;
  	public JButton next, prev;
  	public DialogGuide(String title) {
      	super(title, true);
      	step = 0;
      	prev = new JButton("prev");
      		prev.addMouseListener(new MouseAdapter() {
      			@Override
      			public void mouseClicked(MouseEvent e) {
	              	step -= 1;
	              	top = steps.get(step);
      			}
            });
      	bottom.add(prev);
      	done.setText("ÏÂÒ»²½>");
    }
	class SPanel extends JPanel {
      	JPanel top;
      	JPanel body;
    	JLabel ins;
    	ImageIcon img;
		public SPanel(String image, String instruction) {
          	this.setLayout(new BorderLayout());
          	img = new ImageIcon(image);
          	top = new JPanel() {
          		public void paintComponent(Graphics g) {  
          	        super.paintComponent(g);   
          	        g.drawImage(img.getImage(), 0, 0,this.getWidth(), this.getHeight(), this);  
          	    }
          	};
          		top.setPreferredSize(new Dimension(0,150));
          		top.setLayout(null);
          		ins = new JLabel(instruction);
          			ins.setBounds(20, 100, 500, 20);
          		top.add(ins);
          	add(top, BorderLayout.NORTH);
          	body = new JPanel();
          	add(body, BorderLayout.SOUTH);
        }
    }
}