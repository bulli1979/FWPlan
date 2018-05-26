package screens;

public enum ScreenObject {
	
	
	PLANEDITSCREEN(new CreateOrEditPlan()),
	PLANLISTSCREEN(new PlanListScreen()), 
	MAPSCREEN(new MapScreen()),
	PLANDETAILSCREEN(new MapScreen()), 
	MAPEDITSCREEN(new MapEditScreen()); //TODO Detailscreen erstellen
	
	public ApplicationScreen screen;
	
	private ScreenObject(ApplicationScreen screen){
		this.screen = screen;
	}
}
