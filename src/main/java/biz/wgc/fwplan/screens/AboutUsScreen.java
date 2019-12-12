package biz.wgc.fwplan.screens;

import biz.wgc.fwplan.constants.ApplicationSettings;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class AboutUsScreen implements ApplicationScreen {
	private static final WebView WEBVIEW = new WebView();
	private VBox centerBox;

	public Pane get() {
		BorderPane root = new BorderPane();
		WebEngine webEngine = WEBVIEW.getEngine();
		webEngine.load(ApplicationSettings.ABOUTUS);
		centerBox = new VBox();
		centerBox.getChildren().addAll(WEBVIEW);
		root.setCenter(centerBox);
		return root;
	}
}
