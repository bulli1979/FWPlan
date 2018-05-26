package helper;

import application.ApplicationHandler;
import application.Constant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import screens.ScreenObject;

public class MenuBarBuilder {
	
	
	public static MenuBar build(Stage primaryStage) {
		MenuBar menuBar = new MenuBar();
		Menu menuEinsatzplan = new Menu("Einsatzplan");
		MenuItem overview = new MenuItem("Übersicht");
		overview.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Constant.INSTANCE.setPlan(null);
				ApplicationHandler.setScreen(ScreenObject.PLANLISTSCREEN);
			}
		});
		MenuItem neuerEinsatzplan = new MenuItem("Neuer Einsatzplan");
		neuerEinsatzplan.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Constant.INSTANCE.setPlan(null);
				ApplicationHandler.setScreen(ScreenObject.PLANEDITSCREEN);
			}
		});
		menuEinsatzplan.getItems().addAll(overview, neuerEinsatzplan);
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
