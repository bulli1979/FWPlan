package application;

import data.Plan;

public enum Constant {
	INSTANCE;
	
	private Plan plan;

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	
}
