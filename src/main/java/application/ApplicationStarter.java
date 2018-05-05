package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import screens.CreateNewPlanScreen;
import screens.MapScreen;
import screens.PlanListScreen;

public class ApplicationStarter extends Application {

	private Stage primaryStage;
	private Pane createForm = CreateNewPlanScreen.getScreen(primaryStage);
	private Scene createPlanScene = new Scene(createForm);
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		VBox vbox = new VBox();
		Pane content = PlanListScreen.getScreen("001");
		double x = Screen.getPrimary().getBounds().getMaxX();
		double y = Screen.getPrimary().getBounds().getMaxY();
		vbox.getChildren().addAll(getMenuBar());
		vbox.getChildren().add(content);
		Scene scene = new Scene(vbox, x, y);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Einsatzplaner FW Ehrendingen Freienwil");
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	private MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu menuEinsatzplan = new Menu("Einsatzplan");
		MenuItem neuerEinsatzplan = new MenuItem("Neuer Einsatzplan");
		neuerEinsatzplan.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage dialog = new Stage();
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
