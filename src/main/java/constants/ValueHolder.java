package constants;


import java.io.File;

import data.Plan;
import javafx.stage.Stage;
import screens.MapEditScreen;

public enum ValueHolder {
	INSTANCE;
	private final String USERDATA = "userdata";
	private Plan plan;
	private double appWidth;
	private Stage stage;
	private String imagePrefix = System.getProperty("user.dir") + File.separator + USERDATA + File.separator +"maps" + File.separator;
	private String userImagePrfix = System.getProperty("user.dir") + File.separator + USERDATA + File.separator + "userimage" + File.separator;
	private final String OUTPUTPATH = System.getProperty("user.dir") + File.separator + USERDATA + File.separator + "pdfs" + File.separator;
	private final String USERDIR = System.getProperty("user.dir") + File.separator + USERDATA + File.separator + "images"  + File.separator;
	private final String EDIT_ICONS_PATH = System.getProperty("user.dir") + File.separator + USERDATA + File.separator + "images" + File.separator + "editicons" + File.separator;
	private MapEditScreen mapEditScreen;
	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setAppWidth(double appWith) {
		
		
	}
	public double getAppWidth() {
		return appWidth;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public Stage getStage() {
		return this.stage;
	}
	public String getImagePrefix() {
		return imagePrefix;
	}

	public String getOutputPath() {
		return OUTPUTPATH;
	}

	public String getUserImagePrfix() {
		return userImagePrfix;
	}

	public MapEditScreen getMapEditScreen() {
		return mapEditScreen;
	}

	public void setMapEditScreen(MapEditScreen mapEditScreen) {
		this.mapEditScreen = mapEditScreen;
	}

	public String getUserDir() {
		return this.USERDIR;
	}

	public String getEDITICONS() {
		return EDIT_ICONS_PATH;
	}
	
	
}
