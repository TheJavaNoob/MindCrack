package org.mindcrack.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Scanner;

import org.mindcrack.main.Main;

public class Project {
	public static Project instance;
	public String name;
	public String path;
	public Project() {
		instance = this;
	}
	public Project(String path, String name) {
		try {
			File dir = new File(path);
			dir.mkdir();
			File setup = new File(path + "/.mcprojects");
			setup.createNewFile();
			FileWriter fw = new FileWriter(setup);
			fw.write("name=" + name + "\n");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Project load(String path) {
		Project ret = new Project();
		ret.path = path;
		File pf = new File(path + "/.mcproject");
		try {
			Scanner s = new Scanner(new FileInputStream(pf));
			Class<Project> pc = Project.class;
			while(s.hasNext()) {
				String line = s.nextLine();
				String name = line.substring(0,line.indexOf("="));
				String value = line.substring(line.indexOf("=") + 1);
				Field field;
				try {
					field = pc.getField(name);
				}catch (NoSuchFieldException e) {
					continue;
				}
				String type = field.getType().toString();
				field.setAccessible(true);
				if(type.endsWith("String")){
					field.set(Main.configurations, value);
				}else{
					field.set(Main.configurations, Integer.parseInt(value));
				}
			}
			s.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
