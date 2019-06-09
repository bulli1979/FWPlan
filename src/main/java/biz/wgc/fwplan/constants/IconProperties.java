package biz.wgc.fwplan.constants;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import biz.wgc.fwplan.data.EditIcon;

public class IconProperties {
	private static IconProperties instance = new IconProperties();
	private static List<EditIcon> icons = new ArrayList<EditIcon>();
	
	public static List<EditIcon> getEditIcons(){
		return icons;
	}
	
	public static IconProperties instance() {
		return instance;
	}
	
	public void setIcons(){
		icons.clear();
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(ApplicationSettings.EDITICONS_PROPERTIES)){
			prop.load(input);
			int nr = 1;
			String iconPrefix = "icon";
			String title = "title";
			String text = "text";
			String image = "image";
			String left = "startLeft";
			String top = "startTop";
			String fullName;
			while(true) {
				fullName = iconPrefix+nr+title;
				if(prop.containsKey(fullName)) {
					EditIcon icon = new EditIcon();
					icon.setTitle(prop.getProperty(fullName));
					icon.setImage(prop.getProperty(iconPrefix+nr+image));
					icon.setText(Integer.parseInt(prop.getProperty(iconPrefix+nr+text)));
					icon.setLeft(Integer.parseInt(prop.getProperty(iconPrefix+nr+left)));
					icon.setTop(Integer.parseInt(prop.getProperty(iconPrefix+nr+top)));
					icons.add(icon);
				}else {
					break;
				}
				nr++;
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
