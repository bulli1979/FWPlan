package screens;

public enum ScreenObject {
	PLANEDITSCREEN(new CreateOrEditPlan()),
	PLANLISTSCREEN(new PlanListScreen()), 
	MAPSCREEN(new MapScreen());
	
	public ApplicationScreen screen;
	
	private ScreenObject(ApplicationScreen screen){
		this.screen = screen;
	}
	
	
	
}
