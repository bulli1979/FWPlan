package application;


import java.io.File;

import data.Plan;
import javafx.stage.Stage;

public enum Constant {
	INSTANCE;
	
	private Plan plan;
	private double appWidth;
	private Stage stage;
	private String imagePrefix = System.getProperty("user.dir") + File.separator + "maps"
			+ File.separator;
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
	
}
