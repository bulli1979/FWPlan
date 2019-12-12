package biz.wgc.fwplan.screens;

import biz.wgc.fwplan.screens.dialog.IconFreeTextDialog;
import biz.wgc.fwplan.screens.dialog.ImageUploadScreen;
import biz.wgc.fwplan.screens.dialog.PlanDetailDialog;
public enum ScreenObject {
	
	
	PLAN_EDIT_SCREEN(new CreateOrEditPlan()),
	$PLAN_LIST_SCREE(new PlanListScreen()), 
	MAP_SCREEN(new MapScreen()),
	PLAN_DETAIL_DIALOG(new PlanDetailDialog()), 
	MAP_EDIT_SCREEN(new MapEditScreen()),
	IMAGE_UPLOAD_DIALOG(new ImageUploadScreen()),
	ABOUT_SCREEN(new AboutUsScreen()),
	EDIT_ICON_TEXT_DIALOG(new IconFreeTextDialog());
	
	public ApplicationScreen screen;
	
	private ScreenObject(ApplicationScreen screen){
		this.screen = screen;
	}
}
