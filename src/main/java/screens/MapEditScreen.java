package screens;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import application.Constant;
import data.ToolType;
import data.UserElement;
import data.db.DBUserElement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MapEditScreen implements ApplicationScreen {

	private static final String IMAGE_PREFIX = System.getProperty("user.dir") + File.separator + "maps"
			+ File.separator;
	private static final String ACTIVE_CLASS = "activeTool";
	private static final String BUTTON_CLASS = "button";
	private List<UserElement> userElements;
	private ToolType activeType;

	private Button line, text, image;

	public Pane get() {
		BorderPane root = new BorderPane();
		try {
			userElements = DBUserElement.getInstance().getAllElementsForPlan(Constant.INSTANCE.getPlan().getId());
			ScrollPane mapHolder = createMapHolder();

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
}
