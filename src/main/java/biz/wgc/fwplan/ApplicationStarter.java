package biz.wgc.fwplan;

import biz.wgc.fwplan.constants.ApplicationSettings;
import biz.wgc.fwplan.screens.ScreenObject;
import biz.wgc.fwplan.update.UpdateClass;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationStarter extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {		
		ApplicationSettings.setSettings();
		UpdateClass updater = new UpdateClass();
		updater.chkForUpdates();
		ApplicationHandler handler = new ApplicationHandler(primaryStage);
		handler.start(ScreenObject.$PLAN_LIST_SCREE);
	}
}
