package application;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import screens.MapScreen;

public class ApplicationStarter extends Application{
	
	public static void main(String[] args)  {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = MapScreen.getScreen("1");
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Einsatzplaner");
		primaryStage.show();
	}

}
