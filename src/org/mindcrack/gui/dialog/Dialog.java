package org.mindcrack.gui.dialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.mindcrack.main.Main;

@SuppressWarnings("serial")
public abstract class Dialog extends JDialog {
	JButton done;
	JButton cancel;
	JPanel bottom;
	JPanel top;
	public Dialog(String title, Boolean modal) {
		super(Main.main_win, title, modal);
		initDialog();
	}													
	public void initDialog() {
      	this.setSize(500,600);
		this.setLayout(new BorderLayout());
		bottom = new JPanel();
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
      	bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));//TODO Border
			done = new JButton("���");
				done.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e){
                      onDone();
                    }
				});
			bottom.add(done);
      		done = new JButton("ȡ��");
				done.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e){
                      Dialog.this.setVisible(false);
                    }
				});
			bottom.add(done);
		add(bottom, BorderLayout.SOUTH);
	}
  	public abstract void onDone();
}