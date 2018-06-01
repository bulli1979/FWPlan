package screens;

public enum ScreenObject {
	
	
	PLANEDITSCREEN(new CreateOrEditPlan()),
	PLANLISTSCREEN(new PlanListScreen()), 
	MAPSCREEN(new MapScreen()),
	PLANDETAILSCREEN(new PlanDetailScreen()), 
	MAPEDITSCREEN(new MapEditScreen()); //TODO Detailscreen erstellen
	
	public ApplicationScreen screen;
	
	private ScreenObject(ApplicationScreen screen){
		this.screen = screen;
	}
}
