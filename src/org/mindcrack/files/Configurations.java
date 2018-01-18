package org.mindcrack.files;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import org.mindcrack.main.Main;


public class Configurations {
	public static int mainwin_width;
	public static int mainwin_height;
	public static int mainwin_top;
	public static int mainwin_left;
	public static int padder_align_min;
	public Configurations(){
		LoadConfigurations();
	}
	public void LoadConfigurations(){
		
		FileInputStream fin;
		try {
			fin = new FileInputStream("res/configurations");
			Scanner scan = new Scanner(fin);
			Class<Configurations> configurations_class = Configurations.class;
			while(scan.hasNext()){
				String line = scan.nextLine();
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
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
