package biz.wgc.fwplan.screens.dialog;

import java.io.File;
import java.net.MalformedURLException;
import java.util.UUID;

import biz.wgc.fwplan.constants.CSSClassNames;
import biz.wgc.fwplan.constants.ValueHolder;
import biz.wgc.fwplan.data.UserElement;
import biz.wgc.fwplan.data.db.DBUserElement;
import biz.wgc.fwplan.helper.ImagePaint;
import biz.wgc.fwplan.screens.ApplicationScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
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
		scrollPane.getStyleClass().add(CSSClassNames.TRANSPARENT);
		
		final FileChooser fileChooser = new FileChooser();
		final Button openButton = new Button("Bild wählen");
		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File selectedFile = fileChooser.showOpenDialog(ValueHolder.INSTANCE.getStage());
				if(selectedFile != null) {
					UserElement element = null;
					try {
						element = createUserElement(selectedFile);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					ValueHolder.INSTANCE.getMapEditScreen().closeImageDialog();
					ValueHolder.INSTANCE.getMapEditScreen().getUserElements().add(element);
					ValueHolder.INSTANCE.getMapEditScreen().paintNewMap();
				}
			}
		});
		VBox vbox = new VBox(openButton);
		scrollPane.setContent(vbox);
		root.setCenter(scrollPane);
		return root;
	}
	
	private UserElement createUserElement(File selectedFile) throws MalformedURLException {
		String id = UUID.randomUUID().toString();
		File endFile = new File(ValueHolder.INSTANCE.getUserImagePrfix()+selectedFile.getName());
		ImagePaint.copyImage(selectedFile, endFile);
		Image img = new Image(endFile.toURI().toURL().toString());

		UserElement userElement = new UserElement.Builder().
				forPlan(ValueHolder.INSTANCE.getPlan().getId()).
				setId(id).
				withLeft(ValueHolder.INSTANCE.getMapEditScreen().getX()).
				withTop(ValueHolder.INSTANCE.getMapEditScreen().getY()).
				withWidth(img.getWidth()).
				withHeight(img.getHeight()).
				withType(2).
				withImage(endFile.getName()).
				build();
		DBUserElement.getInstance().insertElement(userElement);
		return userElement;
		
	}
}
