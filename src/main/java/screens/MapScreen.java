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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tools.PDFCreator;

public class MapScreen {
	private static final WebView WEBVIEW = new WebView();

	private static final String IMAGEENDING = "png";
	private static final String DEFAULTURL = "https://map.search.ch/";
	private static final int HEIGHT_DIFFERENCE = 220;
	private static final int WIDTH_DIFFERENCE = 320;
	private static final String IMAGE_PREFIX = "image_";
	private MapScreen(){}
	public static Pane getScreen(String planNumber){
		BorderPane root = new BorderPane();
		Button save = new Button("speichern");
		root.setBottom(save);
		save.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
			WritableImage image = WEBVIEW.snapshot(new SnapshotParameters(), null);
			cutAndWriteImage(image,planNumber);
			
		});
		WEBVIEW.setMaxWidth(PDRectangle.A4.getHeight()+WIDTH_DIFFERENCE);
		WEBVIEW.setMaxHeight(PDRectangle.A4.getWidth()+HEIGHT_DIFFERENCE);
		WebEngine webEngine = WEBVIEW.getEngine();
		webEngine.load(DEFAULTURL);
		root.setCenter(WEBVIEW);
		return root;
	}
	
	private static void cutAndWriteImage(WritableImage image,String planNumber){
		PixelReader reader = image.getPixelReader();
		int width = (int)image.getWidth()-WIDTH_DIFFERENCE;
		int height = (int)image.getHeight()-HEIGHT_DIFFERENCE;
		WritableImage newImage = new WritableImage  (reader, WIDTH_DIFFERENCE, HEIGHT_DIFFERENCE, width, height);
		File file = new File(IMAGE_PREFIX + planNumber + "." + IMAGEENDING);
	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(newImage, null), IMAGEENDING, file);
	    } catch (IOException error) {
	    	
	    }
	    PDFCreator.writePDF(planNumber);
	}
	
}
