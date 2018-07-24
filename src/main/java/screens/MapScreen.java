package screens;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import application.ApplicationHandler;
import application.Constant;
import data.db.DBPlan;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;

public class MapScreen implements ApplicationScreen {
	private static final WebView WEBVIEW = new WebView();

	private static final String IMAGEENDING = "png";
	private static final String DEFAULTURL = "https://map.search.ch/";
	private static final int HEIGHT_TOP_DIFFERENCE = 220;
	private static final int HEIGHT_BOTTOM_DIFFERENCE = 100;
	private static final int WIDTH_DIFFERENCE = 340;
	private static final String SAVE = "speichern";
	private static final String PORTRAIT = "hochkant";
	private static final String LANDSCAPE = "Leinwand";
	private static double WIDTH;
	private static double HEIGHT;
	private static final String IMAGE_PREFIX = System.getProperty("user.dir") + File.separator + "maps"+File.separator+"image_";
	private static String imagePath = null;
	private int displayForm = 1;
	private VBox centerBox;
	private HBox buttons;
	public Pane get() {
		BorderPane root = new BorderPane();
		Button saveButton = createsaveButton(); 
		Button directionButton = createDirectionButton();
		buttons = new HBox(saveButton,directionButton);
		setSizing();
		ScrollPane mapHolder = createMapHolder();
		centerBox = new VBox();
		centerBox.getChildren().addAll(buttons, mapHolder);
		root.setCenter(centerBox);
		return root;
	}

	private ScrollPane createMapHolder() {
		ScrollPane mapHolder = new ScrollPane();
		mapHolder.setMinWidth(WIDTH);
		mapHolder.setMinHeight(400);
		int maxHeight = (int) Screen.getPrimary().getVisualBounds().getHeight()-80;
		mapHolder.setMinHeight(maxHeight);
		WEBVIEW.setMaxWidth(WIDTH);
		WEBVIEW.setMinWidth(WIDTH);
		WEBVIEW.setMaxHeight(HEIGHT);
		WEBVIEW.setMinHeight(HEIGHT);
		WebEngine webEngine = WEBVIEW.getEngine();
		webEngine.load(DEFAULTURL);
		mapHolder.setContent(WEBVIEW);
		return mapHolder;
	}

	private Button createsaveButton() {
		Button button = new Button(SAVE);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				WritableImage image = WEBVIEW.snapshot(new SnapshotParameters(), null);
				cutAndWriteImage(image);
			}
		});
		return button;
	}

	private Button createDirectionButton() {
		
		Button button = new Button(displayForm==1?PORTRAIT:LANDSCAPE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayForm = displayForm==1?2:1;
				setSizing();
				buildWebView();
			}
		});
		return button;
	}
	
	private void setSizing() {
		if(displayForm==1) {
			WIDTH = PDRectangle.A3.getHeight() + WIDTH_DIFFERENCE;
			HEIGHT = PDRectangle.A3.getWidth() + HEIGHT_TOP_DIFFERENCE + HEIGHT_BOTTOM_DIFFERENCE;
		}else {
			WIDTH = PDRectangle.A3.getWidth() + WIDTH_DIFFERENCE;
			HEIGHT = PDRectangle.A3.getHeight() + HEIGHT_TOP_DIFFERENCE + HEIGHT_BOTTOM_DIFFERENCE;
		}
	}
	
	private void buildWebView() {
		ScrollPane mapHolder = createMapHolder();
		centerBox.getChildren().remove(1);
		centerBox.getChildren().add(mapHolder);
		buttons.getChildren().remove(1);
		buttons.getChildren().add(createDirectionButton());
	}
	
	private static void cutAndWriteImage(WritableImage image) {
		File file = new File(IMAGE_PREFIX + Constant.INSTANCE.getPlan().getId() + "." + IMAGEENDING);
		if(file.exists()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Kartenbild Überschreiben");
			alert.setHeaderText(null);
			alert.setContentText("Für diesen Einsatzplan exisitert schon ein Kartenbild.\n\n"
					+ "Soll dieses Kartenbild überschrieben werden?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				saveMap(file, image);
			} 
		}
		else {
			saveMap(file, image);
		}
		ApplicationHandler.setScreen(ScreenObject.PLANLISTSCREEN);
	}
	
	private static void saveMap(File file, WritableImage image) {
		PixelReader reader = image.getPixelReader();
		int width = (int) image.getWidth() - WIDTH_DIFFERENCE;
		int height = (int) image.getHeight() - HEIGHT_TOP_DIFFERENCE - HEIGHT_BOTTOM_DIFFERENCE;
		WritableImage newImage = new WritableImage(reader, WIDTH_DIFFERENCE, HEIGHT_TOP_DIFFERENCE, width, height);
		file.mkdirs();
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(newImage, null), IMAGEENDING, file);
		} catch (IOException error) {
			error.printStackTrace();
		}				
		setImagePath(file.getAbsolutePath());
		Constant.INSTANCE.getPlan().setMap(file.getName());
		DBPlan.getInstance().updatePlan(Constant.INSTANCE.getPlan());
	}

	public static String getImagePath() {
		return imagePath;
	}

	public static void setImagePath(String imagePath) {
		MapScreen.imagePath = imagePath;
	}

}
