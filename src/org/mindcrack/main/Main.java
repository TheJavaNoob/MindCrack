package org.mindcrack.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.mindcrack.files.Configurations;
import org.mindcrack.gui.LoadingWin;
import org.mindcrack.gui.MainWin;

public class Main {
	public static MainWin main_win;
	public static Configurations configurations;
	public static LoadingWin loading_win;
	public static void main(String[] args){
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
				
//			}
//		});
		loading_win = new LoadingWin();
	}
}
