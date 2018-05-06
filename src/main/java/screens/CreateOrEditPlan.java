package screens;

import java.util.UUID;

import data.Plan;
import data.db.DBPlan;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateOrEditPlan {
	
	private static Stage callerStages;
	private static Plan editPlan = null;
	public CreateOrEditPlan() {
	}
	
	public static Pane getScreen(Stage callerStage) {
		BorderPane root = new BorderPane();
		callerStages = callerStage;
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Einsatzplan erstellen");
		scenetitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		
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
		
		/* 
		Button modifyMap = new Button("Neue Karte erstellen");
		modifyMap.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {	
				if(planNumberTextField.getText().equals("") || planNumberTextField.getText() == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Keine Plannummer eingetragen");
					alert.setHeaderText("Bitte trage eine Plannummer ein um eine Karte zu erzeugen.");
					alert.showAndWait();
				}
				else {
					Stage dialog = new Stage();
					Pane mapPane = MapScreen.getScreen(planNumberTextField.getText(), dialog);
					Scene openMapScene = new Scene(mapPane);
					dialog.setScene(openMapScene);
					dialog.initOwner(callerStage);
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.showAndWait();
					File file = new File(MapScreen.getImagePath());
					Image newMapImage = new Image(file.toURI().toString());
					mapImage.setImage(newMapImage);
					mapImage.setFitWidth(700);
					mapImage.setFitHeight(500);
					callerStage.sizeToScene();
				}
			}
		});
		
		grid.add(modifyMap, 1, 6);
		*/
		Button btnSave = new Button("Speichern");
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				Plan savePlan = new Plan.Builder().
					setId(editPlan!=null?editPlan.getId():UUID.randomUUID().toString()).
					withTitle(titleTextField.getText()).
					withPlanNumber(planNumberTextField.getText()).
					withDescription(descriptionTextField.getText()).build();	
					
				if(editPlan == null){
					System.out.println("insert");
					DBPlan.getInstance().insertPlan(savePlan);
				}else{
					System.out.println("update");
					DBPlan.getInstance().updatePlan(savePlan);
				}
				editPlan = savePlan;
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
