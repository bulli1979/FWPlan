package screens;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tools.PDFCreator;

public class MapScreen {
	private static final WebView WEBVIEW = new WebView();

	private static final String IMAGEENDING = "png";
	private static final String DEFAULTURL = "https://map.search.ch/";
	private static final int HEIGHT_TOP_DIFFERENCE = 220;
	private static final int HEIGHT_BOTTOM_DIFFERENCE = 100;
	private static final int WIDTH_DIFFERENCE = 340;
	
	private static final String IMAGE_PREFIX = "image_";

	private MapScreen() {
	}

	public static Pane getScreen(String planNumber) {
		BorderPane root = new BorderPane();

		Button save = new Button("speichern");
		save.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
			WritableImage image = WEBVIEW.snapshot(new SnapshotParameters(), null);
			cutAndWriteImage(image, planNumber);

		});
		
		WEBVIEW.setMaxWidth(PDRectangle.A3.getHeight() + WIDTH_DIFFERENCE);
		WEBVIEW.setMaxHeight(PDRectangle.A3.getWidth() + HEIGHT_TOP_DIFFERENCE + HEIGHT_BOTTOM_DIFFERENCE);
		WEBVIEW.setMinHeight(PDRectangle.A3.getWidth() + HEIGHT_TOP_DIFFERENCE + HEIGHT_BOTTOM_DIFFERENCE);
		WEBVIEW.setMinWidth(PDRectangle.A3.getHeight() + WIDTH_DIFFERENCE);

		WebEngine webEngine = WEBVIEW.getEngine();
		webEngine.load(DEFAULTURL);
		Rectangle topRed = new Rectangle(0, 0, WIDTH_DIFFERENCE, 10);
		topRed.setFill(Color.RED);
		Rectangle topGreen = new Rectangle(0, 0, PDRectangle.A3.getHeight(), 10);
		topGreen.setFill(Color.GREEN);
		VBox centerBox = new VBox( WEBVIEW, save);
		root.setCenter(centerBox);
		return root;
	}

	private static void cutAndWriteImage(WritableImage image, String planNumber) {
		PixelReader reader = image.getPixelReader();
		int width = (int) image.getWidth() - WIDTH_DIFFERENCE;
		int height = (int) image.getHeight() - HEIGHT_TOP_DIFFERENCE;
		WritableImage newImage = new WritableImage(reader, WIDTH_DIFFERENCE, HEIGHT_TOP_DIFFERENCE, width, height);
		File file = new File(IMAGE_PREFIX + planNumber + "." + IMAGEENDING);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(newImage, null), IMAGEENDING, file);
		} catch (IOException error) {

		}
		PDFCreator.writePDF(planNumber);
	}

}