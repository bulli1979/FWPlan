package application;

import data.Plan;

public enum Constant {
	INSTANCE;
	
	private Plan plan;
	private double appWidth;
	
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
	
	
}
