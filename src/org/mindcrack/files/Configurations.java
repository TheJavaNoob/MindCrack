package org.mindcrack.files;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Scanner;

import org.mindcrack.main.Main;
import org.mindcrack.project.Project;


public class Configurations {
	/** Width of main window */
	public static int mainwin_width;
	/** Height of main window */
	public static int mainwin_height;
	/** Y of main window */
	public static int mainwin_top;
	/** X of main window */
	public static int mainwin_left;
	/** Min gap (in pixels) to be allowed between {@link org.mindcrack.gui.Padder Padders} before they align together */
	public static int padder_align_min;
	/** The path to the current project */
	public static String project_opened = "";
	/** A list of file types to be shown in the add menu */
	public static LinkedList<String> defaultAddItems;
	public Configurations(){
		defaultAddItems = new LinkedList<String>();
		loadConfigurations();
		loadProject();
	}
	/** Load the project in {@code project_opened} */
	void loadProject() {
		if(!project_opened.equals("")) {
			Project.instance = Project.load(project_opened);
		}
	}
	/** Load the configuration file */
	public void loadConfigurations(){
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/configurations");
			Scanner scan = new Scanner(fin);
			Class<Configurations> configurations_class = Configurations.class;
			while(scan.hasNext()){
				String line = scan.nextLine();
				if(line.startsWith("@")) {
					String item = line;
					while(!item.equals("@@")){
						switch(item.substring(1)) {
							case "defaultAddItems":{
								defaultAddItems.add(item);
							}
						}
						item = scan.nextLine();
					}
				}else {
					String name = line.substring(0,line.indexOf("="));
					String value = line.substring(line.indexOf("=") + 1);
					Field field = configurations_class.getField(name);
					String type = field.getType().toString();
					field.setAccessible(true);
					if(type.endsWith("String")){
						field.set(Main.configurations, value);
					}else{
						field.set(Main.configurations, Integer.parseInt(value));
					}
				}
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
