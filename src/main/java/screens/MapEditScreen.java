package screens;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import application.Constant;
import data.Plan;
import data.ToolType;
import data.UserElement;
import data.db.DBUserElement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MapEditScreen implements ApplicationScreen {

	private static final String IMAGE_PREFIX = System.getProperty("user.dir") + File.separator + "maps"
			+ File.separator;
	private static final String ACTIVE_CLASS = "activeTool";
	private static final String BUTTON_CLASS = "button";
	private List<UserElement> userElements;
	private ToolType activeType;
	private BorderPane root;
	private Button line, text, image;
	private static final double WIDTH = PDRectangle.A3.getHeight();
	private static final double HEIGHT = PDRectangle.A3.getWidth();
	public Pane get() {
		this.root = new BorderPane();
		Plan plan = Constant.INSTANCE.getPlan();
		try {
			userElements = DBUserElement.getInstance().getAllElementsForPlan(Constant.INSTANCE.getPlan().getId());
			ScrollPane mapHolder = createMapHolder();
			mapHolder.setMaxWidth(WIDTH);
			mapHolder.setMinWidth(WIDTH);
			mapHolder.setMaxHeight(HEIGHT);
			mapHolder.setMinHeight(HEIGHT);
			Image mapImage = new Image("no-image.jpg");
			ImageView mapImageView = new ImageView();
			if(plan.getMap() != null) {
				File imageFile = new File(plan.getMap());
				if(imageFile.exists()) {
					mapImage = new Image(imageFile.toURI().toURL().toString());
					System.out.println(mapImage.getWidth());
				}
			}
			mapImageView.setImage(mapImage);
			mapHolder.setContent(mapImageView);
			mapHolder.getStyleClass().add("mapView");
			mapImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					openAddImageView();
				}
			});
			VBox centerBox = new VBox();
			HBox toolBox = createToolBox();
			centerBox.getChildren().addAll(toolBox, mapHolder);
			root.setCenter(centerBox);
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		return root;
	}

	private HBox createToolBox() {
		HBox toolBox = new HBox();
		line = createLineButton();
		text = createTextButton();
		image = createImageButton();
		Button save = createSaveButton();
		toolBox.getChildren().addAll(text,line,image, save);
		return toolBox;
	}

	private Button createTextButton() {
		text = new Button("Text");
		text.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeButtonStyle();
				activeType = ToolType.TEXT;
				text.getStyleClass().add(ACTIVE_CLASS);
			}
		});
		return text;
	}

	private Button createImageButton() {
		image = new Button("Bild");
		image.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeButtonStyle();
				activeType = ToolType.IMAGE;
				image.getStyleClass().add(ACTIVE_CLASS);
			}
		});
		return image;
	}

	private Button createLineButton() {
		line = new Button("Linie");
		line.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeButtonStyle();
				activeType = ToolType.LINE;
				line.getStyleClass().add(ACTIVE_CLASS);
			}
		});
		return line;
	}

	private Button createSaveButton() {
		Button save = new Button("speichern");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		return save;
	}

	private void removeButtonStyle() {
		line.getStyleClass().clear();
		line.getStyleClass().add(BUTTON_CLASS);
		text.getStyleClass().clear();
		text.getStyleClass().add(BUTTON_CLASS);
		image.getStyleClass().clear();
		image.getStyleClass().add(BUTTON_CLASS);
	}

	private ScrollPane createMapHolder() throws IOException {
		ScrollPane mapHolder = new ScrollPane();
		File f = new File(IMAGE_PREFIX + Constant.INSTANCE.getPlan().getMap());
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		mapHolder.setContent(imgView);
		return mapHolder;
	}
	
	private void openAddImageView() {
		System.out.println("add");
		Stage dialog = new Stage();
		Scene openFileUpload = new Scene(ScreenObject.IMAGEUPLOADDIALOG.screen.get());
		dialog.setScene(openFileUpload);
		dialog.initOwner(this.root.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
	
}
