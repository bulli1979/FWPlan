package screens;

public enum ScreenObject {
	PLANEDITSCREEN(new CreateOrEditPlan()),
	PLANLISTSCREEN(new PlanListScreen());
	
	public ApplicationScreen screen;
	
	private ScreenObject(ApplicationScreen screen){
		this.screen = screen;
	}
	
	
	
}
