package application;

import helper.MenuBarBuilder;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import screens.ScreenObject;

public class ApplicationHandler {	
	private Stage stage;
	private static final String APPTITLE = "Einsatzplaner FW Ehrendingen Freienwil";
	private static VBox vbox = new VBox();
	private Scene scene;
	public ApplicationHandler(Stage stage){
		
		this.stage = stage;
		this.stage.setTitle(APPTITLE);
		
	}
	
	public static void setScreen(ScreenObject screenObject){
		vbox.getChildren().remove(1);
		vbox.getChildren().add(screenObject.screen.get());		
	}

	public void start(ScreenObject startObject) {
		double x = Screen.getPrimary().getBounds().getMaxX();
		double y = Screen.getPrimary().getBounds().getMaxY();
		vbox.getChildren().addAll(MenuBarBuilder.build(stage), startObject.screen.get());
		this.scene = new Scene(vbox, x, y);
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}
	
}
