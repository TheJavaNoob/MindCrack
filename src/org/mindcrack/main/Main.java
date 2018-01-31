package org.mindcrack.main;

import org.mindcrack.files.Configurations;
import org.mindcrack.gui.LoadingWin;
import org.mindcrack.gui.MainWin;
import org.mindcrack.gui.MindcrackWindowManager;

public class Main {
	public static MainWin main_win;
	public static Configurations configurations;
	public static LoadingWin loading_win;
	public static MindcrackWindowManager wm;
	public static void main(String[] args){
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
				
//			}
//		});
		loading_win = new LoadingWin();
		wm = new MindcrackWindowManager();
	}
}
