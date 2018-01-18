package org.mindcrack.main;

import org.mindcrack.files.Configurations;
import org.mindcrack.gui.LoadingWin;
import org.mindcrack.gui.MainWin;

public class Main {
	public static MainWin main_win;
	public static Configurations configurations;
	public static LoadingWin loading_win;
	public static void main(String[] args){
		loading_win = new LoadingWin();
	}
}
