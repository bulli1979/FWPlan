package application;


import java.io.File;

import data.Plan;
import javafx.stage.Stage;
import screens.MapEditScreen;

public enum ValueHolder {
	INSTANCE;
	
	private Plan plan;
	private double appWidth;
	private Stage stage;
	private String imagePrefix = System.getProperty("user.dir") + File.separator + "maps" + File.separator;
	private String userImagePrfix = System.getProperty("user.dir") + File.separator + "userimage" + File.separator;
	private String outputPath = System.getProperty("user.dir") + File.separator + "pdfs" + File.separator;
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
		return outputPath;
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
	
	
}
