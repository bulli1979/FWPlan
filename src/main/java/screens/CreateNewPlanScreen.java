package screens;

import data.Plan;
import data.db.DBPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateNewPlanScreen {
	
	private static BorderPane root = new BorderPane();
	private static Pane mapPane = MapScreen.getScreen("");
	private static Scene openMapScene = new Scene(mapPane);
	private static Stage callerStages;
	
	public CreateNewPlanScreen() {
		getClass().getResource("/no_image.gif");
	}
	
	public static Pane getScreen(Stage callerStage) {
	
		callerStages = callerStage;
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Einsatzplan erstellen");
		scenetitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label id = new Label("ID:");
		grid.add(id, 0, 1);
		TextField idTextField = new TextField();
		grid.add(idTextField, 1, 1);
		
		Label planNumber = new Label("Plannummer:");
		grid.add(planNumber, 0, 2);
		TextField planNumberTextField = new TextField();
		grid.add(planNumberTextField, 1, 2);

		Label title = new Label("Titel:");
		grid.add(title, 0, 3);
		TextField titleTextField = new TextField();
		grid.add(titleTextField, 1, 3);
		
		Label description = new Label("Beschreibung:");
		grid.add(description, 0, 4);
		TextArea descriptionTextField = new TextArea();
		grid.add(descriptionTextField, 1, 4);
		
		Label map = new Label("Karte:");
		grid.add(map, 0, 5);
		ImageView mapImage = new ImageView();
		Image image = new Image("no-image.jpg");
		mapImage.setImage(image);
		grid.add(mapImage, 1, 5);
		
		Button modifyMap = new Button("Karte Hinzufügen / Bearbeiten");
		modifyMap.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Stage dialog = new Stage();
				dialog.setScene(openMapScene);
				dialog.initOwner(callerStage);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.showAndWait();
			}
		});
		grid.add(modifyMap, 1, 6);
		
		Button btnSave = new Button("Speichern");
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("TODO Save Values...");
			}
		});
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btnSave);
		grid.add(hbBtn, 1, 7);
		
		
		root.setCenter(grid);
		return root;
	}
}
