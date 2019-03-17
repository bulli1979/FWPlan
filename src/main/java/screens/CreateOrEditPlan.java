package screens;

import java.util.UUID;

import application.ApplicationHandler;
import constants.ValueHolder;
import data.Plan;
import data.db.DBPlan;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CreateOrEditPlan implements ApplicationScreen{
	
	private Plan editPlan = null;
	private VBox vBox = new VBox();
	private final TextArea wassertransportTextField2=new TextArea();
	private final TextArea wassertransportTextField3=new TextArea();
	private final TextArea wassertransportTextField4=new TextArea();
	
	public CreateOrEditPlan() {
	}
	
	@Override
	public Pane get() {
		editPlan = ValueHolder.INSTANCE.getPlan();
		BorderPane root = new BorderPane();
		GridPane grid = new GridPane();
		prepareGrid(grid);
		int rowCount = 0;
		
		

		Text scenetitle = new Text("Einsatzplan erstellen");
		scenetitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(scenetitle, 0, rowCount, 2, 1);
		rowCount+=2;
		
		Label planNumber = new Label("Plannummer:");
		grid.add(planNumber, 0, rowCount);
		final TextField planNumberTextField = new TextField();
		grid.add(planNumberTextField, 1, rowCount);
		rowCount++;
		Label title = new Label("Titel:");
		grid.add(title, 0, rowCount);
		final TextField titleTextField = new TextField();
		grid.add(titleTextField, 1, rowCount);
		rowCount++;
		Label description = new Label("Beschreibung:");
		grid.add(description, 0, rowCount);
		final TextArea descriptionTextField = new TextArea();
		grid.add(descriptionTextField, 1, rowCount);
		rowCount++;
		Label adresse = new Label("Adresse:");
		grid.add(adresse, 0, rowCount);
		final TextArea adresseTextField = new TextArea();
		grid.add(adresseTextField, 1, rowCount);
		rowCount++;
		
		Label sofortmassnahmen = new Label("Sofortmassnahmen:");
		grid.add(sofortmassnahmen, 0, rowCount);
		final TextArea sofortmassnahmenTextField = new TextArea();
		grid.add(sofortmassnahmenTextField, 1, rowCount);
		rowCount++;
		
		Label wassertransportCounterLabel = new Label("Wieviele Wassertransporte:");
		grid.add(wassertransportCounterLabel, 0, rowCount);
		ComboBox<Integer> wasserTransportCounter = new ComboBox<Integer>();
		wasserTransportCounter.getItems().add(1);
		wasserTransportCounter.getItems().add(2);
		wasserTransportCounter.getItems().add(3);
		wasserTransportCounter.getItems().add(4);
		wasserTransportCounter.setValue(1);
		grid.add(wasserTransportCounter, 1, rowCount);
		rowCount++;
		wasserTransportCounter.setOnAction(event ->{
			paintWatherTransport(wasserTransportCounter.getValue());
		});
		
		Label wassertransport = new Label("Wassertransport 1:");
		grid.add(wassertransport, 0, rowCount);
		final TextArea wassertransportTextField = new TextArea();
		grid.add(wassertransportTextField, 1, rowCount);	
		
		vBox.getChildren().add(grid);
		
			
		
		paintWatherTransport(1);
		
		
		rowCount=0;
		GridPane grid2 = new GridPane();
		prepareGrid(grid2);
		Label wichtigeKontakte = new Label("Wichtige Kontakte:");
		grid2.add(wichtigeKontakte, 0, rowCount);
		final TextArea wichtigeKontakteTextField = new TextArea();
		grid2.add(wichtigeKontakteTextField, 1, rowCount);
		rowCount++;
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
					DBPlan.getInstance().insertPlan(savePlan);
				}else{
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
		grid2.add(hbBtn, 1, rowCount);
		vBox.getChildren().add(grid2);
		root.setCenter(vBox);
		return root;
	}

	
	private void paintWatherTransport(int value) {
		int rowCount = 0;
		System.out.println("val: " + value);
		if(vBox.getChildren().size()==3) {
			System.out.println("remove 1");
			vBox.getChildren().remove(1);
		}
		if(value>1) {
			GridPane grid = new GridPane();
			prepareGrid(grid);
			Label wassertransport2 = new Label("Wassertransport 2:");
			grid.add(wassertransport2, 0, rowCount);
			grid.add(wassertransportTextField2, 1, rowCount);
			if(value>2) {
				rowCount++;
				Label wassertransport3 = new Label("Wassertransport 3:");
				grid.add(wassertransport3, 0, rowCount);
				grid.add(wassertransportTextField3, 1, rowCount);
			
			}else {
				wassertransportTextField3.setText("");
			}
			if(value>3) {
				rowCount++;
				
				Label wassertransport4 = new Label("Wassertransport 4:");
				grid.add(wassertransport4, 0, rowCount);
				grid.add(wassertransportTextField4, 1, rowCount);
		
			}else {
				wassertransportTextField4.setText("");
			}	
			System.out.println("add grid");
			vBox.getChildren().add(1,grid);
		}else {
			wassertransportTextField2.setText("");
		}
	}
	
	private void prepareGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
	}
}
