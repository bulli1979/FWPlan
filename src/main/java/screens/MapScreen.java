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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MapScreen implements ApplicationScreen {
	private static final WebView WEBVIEW = new WebView();

	private static final String IMAGEENDING = "png";
	private static final String DEFAULTURL = "https://map.search.ch/";
	private static final int HEIGHT_TOP_DIFFERENCE = 220;
	private static final int HEIGHT_BOTTOM_DIFFERENCE = 100;
	private static final int WIDTH_DIFFERENCE = 340;
	
	private static final double WIDTH = PDRectangle.A3.getHeight() + WIDTH_DIFFERENCE;
	private static final double HEIGHT = PDRectangle.A3.getWidth() + HEIGHT_TOP_DIFFERENCE + HEIGHT_BOTTOM_DIFFERENCE;
	private static final String IMAGE_PREFIX = System.getProperty("user.dir") + File.separator + "maps"+File.separator+"image_";
	private static String imagePath = null;
	
	public Pane get() {
		BorderPane root = new BorderPane();
		Button save = createsaveButton(); 
		ScrollPane mapHolder = createMapHolder();
		VBox centerBox = new VBox();
		centerBox.getChildren().addAll(save, mapHolder);
		root.setCenter(centerBox);
		return root;
	}

	private ScrollPane createMapHolder() {
		ScrollPane mapHolder = new ScrollPane();
		mapHolder.setMinWidth(WIDTH);
		mapHolder.setMinWidth(HEIGHT);
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
		Button save = new Button("speichern");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				WritableImage image = WEBVIEW.snapshot(new SnapshotParameters(), null);
				cutAndWriteImage(image);
			}
		});
		return save;
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
		//SRE hinzugefügt mkdirs um Ordnerstruktur zu erstellen falls diese noch nicht exisitert...
		file.mkdirs();
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(newImage, null), IMAGEENDING, file);
		} catch (IOException error) {
			error.printStackTrace();
		}				
		setImagePath(file.getAbsolutePath());
		//SRE geändert Map Value ist neu absoluter pfad / nicht nur Filename
		Constant.INSTANCE.getPlan().setMap(file.getAbsolutePath());
		DBPlan.getInstance().updatePlan(Constant.INSTANCE.getPlan());
	}

	public static String getImagePath() {
		return imagePath;
	}

	public static void setImagePath(String imagePath) {
		MapScreen.imagePath = imagePath;
	}

}
