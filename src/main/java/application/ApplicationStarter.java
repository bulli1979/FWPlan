package application;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ApplicationStarter extends Application{
	private final WebView webView = new WebView();
	public static void main(String[] args)  {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Button save = new Button("speichern");
		root.setBottom(save);
		save.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
			WritableImage image = webView.snapshot(new SnapshotParameters(), null);
			File file = new File("chart.png");
			save();
		    try {
		        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		    } catch (IOException error) {
		       System.out.println("Doof" + error);
		    }
		});
		
		WebEngine webEngine = webView.getEngine();
		webEngine.load("https://map.search.ch/");
		root.setCenter(webView);
		Scene scene = new Scene(root, 800, 800);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Einsatzplaner");
		primaryStage.show();
	}

	private void save(){
		Document doc = webView.getEngine().getDocument();
		Element adminLink = doc.getElementById("map-drawing-links");
		NodeList inputs = adminLink.getElementsByTagName("input");
		
		if(inputs.getLength()>1){
			Node item = inputs.item(1);
			System.out.println(item.getAttributes().getNamedItem("value").getNodeValue());
		}
	}
	
}
