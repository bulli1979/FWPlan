package screens;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

import application.ApplicationHandler;
import constants.CSSClassNames;
import constants.ValueHolder;
import data.EditIcon;
import data.ToolType;
import data.UserElement;
import data.db.DBUserElement;
import elements.MapEditIconButtons;
import elements.UserElementTable;
import helper.ImagePaint;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MapEditScreen implements ApplicationScreen {
	
	private static final int SCEENSITZE_RESERVE = 80;

	private List<UserElement> userElements;
	private ToolType activeType;
	private BorderPane root;
	private VBox centerBox;
	private Stage uploadDialog;
	private Stage iconTextDialog;
	private double x;
	private double y;
	private EditIcon icon;
	public Pane get() {
		this.root = new BorderPane();
		try {
			userElements = DBUserElement.getInstance().getAllElementsForPlan(ValueHolder.INSTANCE.getPlan().getId());
			ScrollPane mapHolder = createMapHolder();
			centerBox = new VBox();
			HBox toolBox = createToolBox();
			centerBox.getChildren().addAll(toolBox, mapHolder);
			root.setCenter(centerBox);
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		return root;
	}

	private ScrollPane createMapHolder() throws IOException, MalformedURLException {
		ScrollPane mapHolder = new ScrollPane();
		int maxHeight = (int) Screen.getPrimary().getVisualBounds().getHeight()-SCEENSITZE_RESERVE;
		int maxWidth = (int) Screen.getPrimary().getVisualBounds().getWidth()-SCEENSITZE_RESERVE;
		mapHolder.setMaxWidth(maxWidth);
		mapHolder.setMaxHeight(maxHeight);		
		ImageView mapImageView = ImagePaint.paintTempImage(userElements);
		VBox vbox = new VBox();
		vbox.getChildren().add(mapImageView);
		vbox.getChildren().add(new UserElementTable(userElements));
		mapHolder.setContent(vbox);
		mapHolder.getStyleClass().add(CSSClassNames.MAPVIEW);
		mapImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				switch(activeType) {
					case ICON:
						handleUserElementClick(event);
						break;
					case IMAGE:
						openAddImageView(event);
						ApplicationHandler.setScreen(ScreenObject.MAP_EDIT_SCREEN);
						break;
					default:
						break;
				}
			}

			
		});
		return mapHolder;
	}
	
	private void handleUserElementClick(MouseEvent event) {
		if(this.icon.getText()>0) {
			openEditIconTextDialog(event);
		}else {
			userElements.add(createUserElement(event));
		}
		
	}
	
	private UserElement createUserElement(MouseEvent event) {
		UserElement userElement = new UserElement.Builder()
				.setPlanId(ValueHolder.INSTANCE.getPlan().getId())
				.setId(UUID.randomUUID().toString())
				.withLeft(event.getX())
				.withTop(event.getY())
				.withType(activeType.getType())
				.withImage(this.icon.getImage())
				.build();
		DBUserElement.getInstance().insertElement(userElement);
		return userElement;
	}

	public void paintNewMap() {
		try {
			ScrollPane mapHolder = createMapHolder();
			centerBox.getChildren().remove(1);
			centerBox.getChildren().add(mapHolder);
		}catch(Exception e) {
			System.out.println("Error in mapHolder Click");
			e.printStackTrace();
		}
	}
	
	private HBox createToolBox() {
		HBox toolBox = new HBox();
		MapEditIconButtons buttonCreator = new MapEditIconButtons();
		toolBox.getChildren().addAll(buttonCreator.getButtons(this));
		return toolBox;
	}

	

	
	
	private void openEditIconTextDialog(MouseEvent event) {
		iconTextDialog = new Stage();
		Scene editIconText = new Scene(ScreenObject.EDIT_ICON_TEXT_DIALOG.screen.get());
		ValueHolder.INSTANCE.setMapEditScreen(this);
		this.x = event.getX();
		this.y = event.getY();
		iconTextDialog.setScene(editIconText);
		iconTextDialog.initOwner(this.root.getScene().getWindow());
		iconTextDialog.initModality(Modality.APPLICATION_MODAL);
		iconTextDialog.showAndWait();
	}
	
	private void openAddImageView(MouseEvent event) {
		uploadDialog = new Stage();
		Scene openFileUpload = new Scene(ScreenObject.IMAGE_UPLOAD_DIALOG.screen.get());
		ValueHolder.INSTANCE.setMapEditScreen(this);
		this.x = event.getX();
		this.y = event.getY();
		uploadDialog.setScene(openFileUpload);
		uploadDialog.initOwner(this.root.getScene().getWindow());
		uploadDialog.initModality(Modality.APPLICATION_MODAL);
		uploadDialog.showAndWait();
	}
	
	public void closeTextDialog() {
		iconTextDialog.close();
	}
	
	public void closeImageDialog() {
		uploadDialog.close();
	}

	public List<UserElement> getUserElements() {
		return userElements;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public ToolType getActiveType() {
		return activeType;
	}

	public void setActiveType(ToolType activeType) {
		this.activeType = activeType;
	}

	public void setActiveIcon(EditIcon icon) {
		this.icon = icon;
		
	}

	public EditIcon getActiveIcon() {
		return this.icon;
		
	}
	
}
