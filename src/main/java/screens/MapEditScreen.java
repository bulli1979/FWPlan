package screens;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

import application.Constant;
import data.ToolType;
import data.UserElement;
import data.db.DBUserElement;
import elements.UserElementTable;
import helper.ImagePaint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
	private static final String ACTIVE_CLASS = "activeTool";
	private static final String BUTTON_CLASS = "button";
	private List<UserElement> userElements;
	private ToolType activeType;
	private BorderPane root;
	private Button carButton, hydranthButton, imageButton;
	private VBox centerBox;
	private Stage uploadDialog;
	private double x;
	private double y;
	public Pane get() {
		this.root = new BorderPane();
		try {
			userElements = DBUserElement.getInstance().getAllElementsForPlan(Constant.INSTANCE.getPlan().getId());
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
		int maxHeight = (int) Screen.getPrimary().getVisualBounds().getHeight()-80;
		int maxWidth = (int) Screen.getPrimary().getVisualBounds().getWidth();
		mapHolder.setMaxWidth(maxWidth);
		mapHolder.setMaxHeight(maxHeight);		
		ImageView mapImageView = ImagePaint.paintTempImage(userElements);
		VBox vbox = new VBox();
		vbox.getChildren().add(mapImageView);
		vbox.getChildren().add(new UserElementTable(userElements,this));
		mapHolder.setContent(vbox);
		mapHolder.getStyleClass().add("mapView");
		mapImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				boolean change = false;
				switch(activeType) {
					case HYDRANTH:
						userElements.add(createUserElement(event));
						change=true;
						break;
					case CAR:
						break;
					case IMAGE:
						openAddImageView(event);
						break;
				}
				if(change) {
					System.out.println("paint New");
					paintNewMap();
				}
				
			}
		});
		return mapHolder;
	}
	
	private UserElement createUserElement(MouseEvent event) {
		return new UserElement.Builder()
				.setPlanId(Constant.INSTANCE.getPlan().getId())
				.setId(null)
				.withLeft(event.getX())
				.withTop(event.getY())
				.withType(activeType.getType())
				.build();
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
		carButton = createCarButton();
		hydranthButton = createHydranthButton();
		imageButton = createImageButton();
		Button save = createSaveButton();
		toolBox.getChildren().addAll(hydranthButton,carButton,imageButton, save);
		return toolBox;
	}

	private Button createHydranthButton() {
		hydranthButton = new Button("Hydrant");
		hydranthButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeButtonStyle();
				activeType = ToolType.HYDRANTH;
				hydranthButton.getStyleClass().add(ACTIVE_CLASS);
			}
		});
		return hydranthButton;
	}

	private Button createImageButton() {
		imageButton = new Button("Bild");
		imageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeButtonStyle();
				activeType = ToolType.IMAGE;
				imageButton.getStyleClass().add(ACTIVE_CLASS);
			}
		});
		return imageButton;
	}

	private Button createCarButton() {
		carButton = new Button("Auto");
		carButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeButtonStyle();
				activeType = ToolType.CAR;
				carButton.getStyleClass().add(ACTIVE_CLASS);
			}
		});
		return carButton;
	}

	private Button createSaveButton() {
		Button save = new Button("speichern");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				userElements.forEach(element->{
					if(element.getId()==null) {
						element.setId(UUID.randomUUID().toString());
						DBUserElement.getInstance().insertElement(element);
					}else {
						DBUserElement.getInstance().updateElement(element);
					}
				});
			}
		});
		return save;
	}

	private void removeButtonStyle() {
		carButton.getStyleClass().clear();
		carButton.getStyleClass().add(BUTTON_CLASS);
		hydranthButton.getStyleClass().clear();
		hydranthButton.getStyleClass().add(BUTTON_CLASS);
		imageButton.getStyleClass().clear();
		imageButton.getStyleClass().add(BUTTON_CLASS);
	}
	
	private void openAddImageView(MouseEvent event) {
		uploadDialog = new Stage();
		Scene openFileUpload = new Scene(ScreenObject.IMAGEUPLOADDIALOG.screen.get());
		Constant.INSTANCE.setMapEditScreen(this);
		this.x = event.getX();
		this.y = event.getY();
		uploadDialog.setScene(openFileUpload);
		uploadDialog.initOwner(this.root.getScene().getWindow());
		uploadDialog.initModality(Modality.APPLICATION_MODAL);
		uploadDialog.showAndWait();
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

	
	
}
