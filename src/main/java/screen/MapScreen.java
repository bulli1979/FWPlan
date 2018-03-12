package screen;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MapScreen {
	private final WebView webView = new WebView();
	private String applicationId;

	public MapScreen(String id) {
		applicationId = id;
	}

	public BorderPane getComponent() {
		BorderPane root = new BorderPane();
		Button save = new Button("speichern");
		root.setBottom(save);
		save.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
			save();
		});
		WebEngine webEngine = webView.getEngine();
		//TODO change Link for Edit
		webEngine.load("https://map.search.ch/");
		root.setCenter(webView);
		return root;
	}

	private void save() {
		WritableImage image = webView.snapshot(new SnapshotParameters(), null);
		File file = new File("image_"+applicationId + ".png");
		Document doc = webView.getEngine().getDocument();
		Element adminLink = doc.getElementById("map-drawing-links");
		NodeList inputs = adminLink.getElementsByTagName("input");

		if (inputs.getLength() > 1) {
			Node item = inputs.item(1);
			System.out.println(item.getAttributes().getNamedItem("value").getNodeValue());
		}
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException error) {
			System.out.println("Doof" + error);
		}
	}
}
