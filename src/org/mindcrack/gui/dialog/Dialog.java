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

/** Base class of a standard dialog */
@SuppressWarnings("serial")
public abstract class Dialog extends JDialog {
	/** Done button */
	JButton done;
	/** Cancel button */
	JButton cancel;
	/** The button panel */
	JPanel bottom;
	/** The top panel */
	JPanel top;
	/** Initialize the dialog with a title
	 * @param title - The title of the dialog
	 * @param modal - Whether the user can operate the main window when the dialog is shown
	 */
	public Dialog(String title, Boolean modal) {
		super(Main.main_win, title, modal);
		initDialog();
	}
	public void initDialog() {
      	this.setSize(500,600);
      	this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		top = new JPanel();
			top.setLayout(null);
		add(top, BorderLayout.CENTER);
		bottom = new JPanel();
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
      	bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
			done = new JButton("Done");
				done.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e){
                      onDone();
                    }
				});
			bottom.add(done);
      		cancel = new JButton("Cancel");
      			cancel.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e){
                      Dialog.this.dispose();
                    }
				});
			bottom.add(cancel);
		add(bottom, BorderLayout.SOUTH);
	}
	/** Called when the done button is clicked */
  	public abstract void onDone();
}