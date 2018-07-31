package screens;

import java.io.File;
import java.util.UUID;

import application.Constant;
import data.UserElement;
import data.db.DBUserElement;
import helper.ImagePaint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ImageUploadScreen implements ApplicationScreen {
	@Override
	public Pane get() {
		BorderPane root = new BorderPane();
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPadding(new Insets(0, 25, 25, 25));
		scrollPane.setStyle("-fx-background-color:transparent;");
		
		final FileChooser fileChooser = new FileChooser();
		final Button openButton = new Button("Bild w�hlen");
		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File selectedFile = fileChooser.showOpenDialog(Constant.INSTANCE.getStage());
				if(selectedFile != null) {
					UserElement element = createUserElement(selectedFile);
					Constant.INSTANCE.getMapEditScreen().closeImageDialog();
					Constant.INSTANCE.getMapEditScreen().getUserElements().add(element);
					Constant.INSTANCE.getMapEditScreen().paintNewMap();
				}
			}
		});
		VBox vbox = new VBox(openButton);
		scrollPane.setContent(vbox);
		root.setCenter(scrollPane);
		return root;
	}
	
	private UserElement createUserElement(File selectedFile) {
		String id = UUID.randomUUID().toString();
		File endFile = new File(Constant.INSTANCE.getUserImagePrfix()+selectedFile.getName());
		ImagePaint.copyImage(selectedFile, endFile);
		UserElement userElement = new UserElement.Builder().
				forPlan(Constant.INSTANCE.getPlan().getId()).
				setId(id).
				withLeft(Constant.INSTANCE.getMapEditScreen().getX()).
				withTop(Constant.INSTANCE.getMapEditScreen().getY()).
				withType(2).
				withImage(endFile.getName()).
				build();
		DBUserElement.getInstance().insertElement(userElement);
		return userElement;
		
	}
}
