package application;

import constants.ValueHolder;
import helper.MenuBarBuilder;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import screens.ScreenObject;

public class ApplicationHandler {	
	private Stage stage;
	private static final String APPTITLE = "Einsatzplaner FW Ehrendingen Freienwil";
	private static VBox vbox = new VBox();
	private static final int INITIAL_WIDTH_HEIGHT = 100;
	public ApplicationHandler(Stage stage){
		this.stage = stage;
		this.stage.setTitle(APPTITLE);
	}
	
	public static void setScreen(ScreenObject screenObject){
		vbox.getChildren().remove(1);
		vbox.getChildren().add(screenObject.screen.get());		
	}

	public void start(ScreenObject startObject) {
		
		vbox.getChildren().addAll(MenuBarBuilder.build(stage), startObject.screen.get());
		Scene scene = new Scene(vbox, INITIAL_WIDTH_HEIGHT, INITIAL_WIDTH_HEIGHT);
		scene.getStylesheets().add("application.css");
		stage.setScene(scene);
		stage.setMaximized(true);
		ValueHolder.INSTANCE.setAppWidth(stage.getWidth());
		ValueHolder.INSTANCE.setStage(stage);
		stage.show();
	}
	
}
