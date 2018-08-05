package application;

import constants.Settings;
import javafx.application.Application;
import javafx.stage.Stage;
import screens.ScreenObject;

public class ApplicationStarter extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {		
		Settings.setSettings();
		ApplicationHandler handler = new ApplicationHandler(primaryStage);
		handler.start(ScreenObject.$PLAN_LIST_SCREE);
	}
}
