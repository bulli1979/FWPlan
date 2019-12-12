package biz.wgc.fwplan.helper;

import biz.wgc.fwplan.ApplicationHandler;
import biz.wgc.fwplan.constants.ValueHolder;
import biz.wgc.fwplan.screens.ScreenObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuBarBuilder {
	
	
	public static MenuBar build(Stage primaryStage) {
		MenuBar menuBar = new MenuBar();
		Menu menuEinsatzplan = new Menu("Einsatzplan");
		MenuItem overview = new MenuItem("Übersicht");
		overview.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ValueHolder.INSTANCE.setPlan(null);
				ApplicationHandler.setScreen(ScreenObject.$PLAN_LIST_SCREE);
			}
		});
		MenuItem neuerEinsatzplan = new MenuItem("Neuer Einsatzplan");
		neuerEinsatzplan.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ValueHolder.INSTANCE.setPlan(null);
				ApplicationHandler.setScreen(ScreenObject.PLAN_EDIT_SCREEN);
			}
		});
		menuEinsatzplan.getItems().addAll(overview, neuerEinsatzplan);
		Menu menuHelp = new Menu("Hilfe");
		MenuItem about = new MenuItem("About");
		about.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ValueHolder.INSTANCE.setPlan(null);
				ApplicationHandler.setScreen(ScreenObject.ABOUT_SCREEN);
			}
		});
		menuHelp.getItems().addAll(about);
		menuBar.getMenus().addAll(menuEinsatzplan, menuHelp);
		return menuBar;
	}
}
