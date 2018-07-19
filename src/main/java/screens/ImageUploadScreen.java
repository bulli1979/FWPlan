package screens;

import java.io.File;

import application.Constant;
import data.Plan;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ImageUploadScreen implements ApplicationScreen {
	@Override
	public Pane get() {
		Plan plan = Constant.INSTANCE.getPlan();
		BorderPane root = new BorderPane();
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPadding(new Insets(0, 25, 25, 25));
		scrollPane.setStyle("-fx-background-color:transparent;");
		
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PNG Files", "*.png"),
				new FileChooser.ExtensionFilter("PNG Files", "*.jpeg"),
				new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
		final Button openButton = new Button("Bild wählen");
		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File selectedFile = fileChooser.showOpenDialog(Constant.INSTANCE.getStage());
				if(selectedFile != null) {
					System.out.println("toll " + selectedFile.getName());
					//TODO wegspeichern und db changen
				}
			}
		});

		VBox vbox = new VBox(openButton);
		scrollPane.setContent(vbox);
		root.setCenter(scrollPane);
		return root;
	}
}
