package org.mindcrack.gui;

import java.util.HashMap;
import java.util.LinkedList;

public class WindowManager {
	public static LinkedList<WindowManager> mods = new LinkedList<WindowManager>();
	public HashMap<String, Folder> folders = new HashMap<String, Folder>();
	public static class Folder {
		public String name;
		public LinkedList<MPanel> windows = new LinkedList<MPanel>();
		public Folder(String name) {
			this.name = name;
		}
	}
}
