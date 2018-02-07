package org.mindcrack.files.gui;

import java.awt.Font;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.mindcrack.gui.dialog.Dialog;

@SuppressWarnings("serial")
public class DialogAdd extends Dialog {
	JTextField name;
	public DialogAdd(File dir) {
		super("Add File", true);
		JLabel l1 = new JLabel("Add file");
			l1.setFont(new Font("", Font.PLAIN, 50));
			l1.setBounds(100, 10, 400, 50);
		top.add(l1);
		JLabel l2 = new JLabel("Directory: ");
			l2.setBounds(10, 70, 70, 20);
		top.add(l2);
		JLabel l3 = new JLabel("File name: ");
			l3.setBounds(10, 50, 80, 25);
		top.add(l3);
		name = new JTextField();
			name.setBounds(50, 90, 200, 25);
		top.add(name);
	}

	@Override
	public void onDone() {
		
	}
}
