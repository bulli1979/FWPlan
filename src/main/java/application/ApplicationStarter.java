package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import screens.MapScreen;
import screens.PlanListScreen;

public class ApplicationStarter extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = PlanListScreen.getScreen("001");
		double x = Screen.getPrimary().getBounds().getMaxX();
		double y = Screen.getPrimary().getBounds().getMaxY();
		Scene scene = new Scene(root, x, y);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Einsatzplaner FW Ehrendingen Freienwil");
		primaryStage.show();
	}
}
