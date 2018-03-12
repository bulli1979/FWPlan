package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import screen.MapScreen;

public class ApplicationStarter extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MapScreen screen = new MapScreen("id");
		BorderPane root = screen.getComponent();
		Scene scene = new Scene(root, 1200, 800);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Einsatzplaner");
		primaryStage.show();
	}
}
