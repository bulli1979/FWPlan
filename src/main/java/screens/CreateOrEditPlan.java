package screens;

import java.util.UUID;
import application.ApplicationHandler;
import application.ValueHolder;
import data.Plan;
import data.db.DBPlan;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CreateOrEditPlan implements ApplicationScreen{
	
	private Plan editPlan = null;
	
	public CreateOrEditPlan() {
	}
	
	@Override
	public Pane get() {
		editPlan = ValueHolder.INSTANCE.getPlan();
		System.out.println(editPlan);
		BorderPane root = new BorderPane();
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
		final TextField planNumberTextField = new TextField();
		grid.add(planNumberTextField, 1, 2);
		
		Label title = new Label("Titel:");
		grid.add(title, 0, 3);
		final TextField titleTextField = new TextField();
		grid.add(titleTextField, 1, 3);
		
		Label description = new Label("Beschreibung:");
		grid.add(description, 0, 4);
		final TextArea descriptionTextField = new TextArea();
		grid.add(descriptionTextField, 1, 4);
		
		Label adresse = new Label("Adresse:");
		grid.add(adresse, 0, 5);
		final TextArea adresseTextField = new TextArea();
		grid.add(adresseTextField, 1, 5);
		
		Label sofortmassnahmen = new Label("Sofortmassnahmen:");
		grid.add(sofortmassnahmen, 0, 6);
		final TextArea sofortmassnahmenTextField = new TextArea();
		grid.add(sofortmassnahmenTextField, 1, 6);
		
		Label wassertransport = new Label("Wassertransport:");
		grid.add(wassertransport, 0, 7);
		final TextArea wassertransportTextField = new TextArea();
		grid.add(wassertransportTextField, 1, 7);
		
		Label wichtigeKontakte = new Label("Wichtige Kontakte:");
		grid.add(wichtigeKontakte, 0, 8);
		final TextArea wichtigeKontakteTextField = new TextArea();
		grid.add(wichtigeKontakteTextField, 1, 8);
		
		if(editPlan != null) {
			planNumberTextField.setText(editPlan.getPlanNumber());
			titleTextField.setText(editPlan.getTitle());
			descriptionTextField.setText(editPlan.getDescription());
			adresseTextField.setText(editPlan.getAdress());
			sofortmassnahmenTextField.setText(editPlan.getInstantAction());
			wassertransportTextField.setText(editPlan.getWatherTransport());
			wichtigeKontakteTextField.setText(editPlan.getImportantContacts());
		}
		
		Button btnSave = new Button("Speichern");
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				Plan savePlan = new Plan.Builder().
					setId(editPlan!=null?editPlan.getId():UUID.randomUUID().toString()).
					withPlanNumber(planNumberTextField.getText()).
					withTitle(titleTextField.getText()).
					withDescription(descriptionTextField.getText()).
					withAdress(adresseTextField.getText()).
					withInstantAction(sofortmassnahmenTextField.getText()).
					withWatherTransport(wassertransportTextField.getText()).
					withImportantContact(wichtigeKontakteTextField.getText()).build();	
					
				if(editPlan == null){
					System.out.println("insert");
					DBPlan.getInstance().insertPlan(savePlan);
				}else{
					System.out.println("update");
					savePlan.setMap(editPlan.getMap());
					DBPlan.getInstance().updatePlan(savePlan);
				}
				editPlan = savePlan;
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Einsatzplan Speicherung");
				alert.setHeaderText(null);
				alert.setContentText("Der Einsatzplan " + planNumberTextField.getText() + " wurde erfolgreich gespeichert.");
				alert.showAndWait();
				
				ApplicationHandler.setScreen(ScreenObject.$PLAN_LIST_SCREE);
			}
		});
	
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btnSave);
		grid.add(hbBtn, 1, 9);
		root.setCenter(grid);
		return root;
	}
}
