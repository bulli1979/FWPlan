package biz.wgc.fwplan.screens.dialog;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import biz.wgc.fwplan.ApplicationHandler;
import biz.wgc.fwplan.constants.CSSClassNames;
import biz.wgc.fwplan.constants.ValueHolder;
import biz.wgc.fwplan.data.UserElement;
import biz.wgc.fwplan.data.db.DBUserElement;
import biz.wgc.fwplan.helper.ImagePaint;
import biz.wgc.fwplan.screens.ApplicationScreen;
import biz.wgc.fwplan.screens.ScreenObject;
import biz.wgc.fwplan.tools.Constants;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class IconFreeTextDialog implements ApplicationScreen{

	@Override
	public Pane get() {
		BorderPane root = new BorderPane();
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPadding(new Insets(0, 25, 25, 25));
		scrollPane.getStyleClass().add(CSSClassNames.TRANSPARENT);
		
		
		Label label = new Label("Text für Icon eingeben");
		TextField iconText = new TextField();
		Button createButton = new Button("Icon erstellen");
		createButton.setOnAction(value -> {
			
			ValueHolder.INSTANCE.getMapEditScreen().closeTextDialog();
			ValueHolder.INSTANCE.getMapEditScreen().getUserElements().add(createCustomUserElement(iconText.getText()));
			ApplicationHandler.setScreen(ScreenObject.MAP_EDIT_SCREEN);
		});
		VBox vbox = new VBox(label,iconText,createButton);
		scrollPane.setContent(vbox);
		root.setCenter(scrollPane);
		return root;
	}



	private UserElement createCustomUserElement(String text) {
		
		
		String id = UUID.randomUUID().toString();
		File endFile = new File(ValueHolder.INSTANCE.getUserImagePrfix()+id+Constants.POINT+Constants.IMAGEENDING);
		
		UserElement userElement = new UserElement.Builder().
				forPlan(ValueHolder.INSTANCE.getPlan().getId()).
				setId(id).
				withText(text).
				withLeft(ValueHolder.INSTANCE.getMapEditScreen().getX()).
				withTop(ValueHolder.INSTANCE.getMapEditScreen().getY()).
				withImage(endFile.getName()).
				withTextPosition(ValueHolder.INSTANCE.getMapEditScreen().getActiveIcon().getText()).
				withType(2).
				build();
		ImagePaint.copyImage(new File(ValueHolder.INSTANCE.getEDITICONS() + ValueHolder.INSTANCE.getMapEditScreen().getActiveIcon().getImage()), endFile);
		try {
			ImagePaint.writeText(endFile,userElement,ValueHolder.INSTANCE.getMapEditScreen().getActiveIcon());
		} catch (IOException e) {
			e.printStackTrace();
		}
		DBUserElement.getInstance().insertElement(userElement);
		return userElement;
	}
}
