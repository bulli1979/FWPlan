package screens;

public enum ScreenObject {
	
	
	PLANEDITSCREEN(new CreateOrEditPlan()),
	PLANLISTSCREEN(new PlanListScreen()), 
	MAPSCREEN(new MapScreen()),
	PLANDETAILSCREEN(new MapScreen()); //TODO Detailscreen erstellen
	
	public ApplicationScreen screen;
	
	private ScreenObject(ApplicationScreen screen){
		this.screen = screen;
	}
}
