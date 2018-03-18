package application;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import screens.MapScreen;

public class ApplicationStarter extends Application{
	
	private static final float APP_WIDTH = 350;
	private static final float APP_HEIGHT = 250;

	public static void main(String[] args)  {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = MapScreen.getScreen("1");
		Scene scene = new Scene(root, PDRectangle.A4.getHeight()+APP_WIDTH, PDRectangle.A4.getWidth()+APP_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Einsatzplaner");
		primaryStage.show();
	}

}
