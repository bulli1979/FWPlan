package application;


import data.Plan;
import javafx.stage.Stage;

public enum Constant {
	INSTANCE;
	
	private Plan plan;
	private double appWidth;
	private Stage stage;
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
	
}
