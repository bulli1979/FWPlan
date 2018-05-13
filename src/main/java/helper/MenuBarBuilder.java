package helper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import screens.ScreenObject;

public class MenuBarBuilder {
	
	
	public static MenuBar build(Stage primaryStage) {
		MenuBar menuBar = new MenuBar();
		Menu menuEinsatzplan = new Menu("Einsatzplan");
		MenuItem neuerEinsatzplan = new MenuItem("Neuer Einsatzplan");
		neuerEinsatzplan.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage dialog = new Stage();
				Pane createForm = ScreenObject.PLANEDITSCREEN.screen.get();
				Scene createPlanScene = new Scene(createForm);
				dialog.setScene(createPlanScene);
				dialog.initOwner(primaryStage);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.showAndWait();
			}
		});
		menuEinsatzplan.getItems().addAll(neuerEinsatzplan);
		Menu menuHelp = new Menu("Hilfe");
		MenuItem about = new MenuItem("About");
		about.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("TODO about...");
			}
		});
		menuHelp.getItems().addAll(about);
		menuBar.getMenus().addAll(menuEinsatzplan, menuHelp);
		return menuBar;
	}
}
