package screens;

import java.util.UUID;

import application.ApplicationHandler;
import constants.Texts;
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
	public CreateOrEditPlan() {
	}
	
	@Override
	public Pane get() {
		editPlan = ValueHolder.INSTANCE.getPlan();
		BorderPane root = new BorderPane();
		GridPane grid = new GridPane();
		prepareGrid(grid);
		Integer rowCount = 0;
		Text scenetitle = new Text(Texts.EDIT_OR_CREATE_TEXT);
		scenetitle.setFont(Font.font(Texts.ARIAL_FONT, FontWeight.BOLD, 20));
		grid.add(scenetitle, 0, rowCount, 2, 1);
		rowCount += 2;
		Label planNumber = new Label(Texts.PLANNUMBER_TEXT);
		grid.add(planNumber, 0, rowCount);
		final TextField planNumberTextField = new TextField();
		grid.add(planNumberTextField, 1, rowCount);
		rowCount++;
		Label title = new Label(Texts.TITLE_TEXT);
		grid.add(title, 0, rowCount);
		final TextField titleTextField = new TextField();
		grid.add(titleTextField, 1, rowCount);
		rowCount++;
		Label description = new Label(Texts.DESCRIPTION_TEXT);
		grid.add(description, 0, rowCount);
		final TextArea descriptionTextField = new TextArea();
		grid.add(descriptionTextField, 1, rowCount);
		rowCount++;
		Label adresse = new Label(Texts.ADRESS_TEXT);
		grid.add(adresse, 0, rowCount);
		final TextArea adresseTextField = new TextArea();
		grid.add(adresseTextField, 1, rowCount);
		rowCount++;
		
		Label sofortmassnahmen = new Label(Texts.INSTANT_ACTION_TEXT);
		grid.add(sofortmassnahmen, 0, rowCount);
		final TextArea sofortmassnahmenTextField = new TextArea();
		grid.add(sofortmassnahmenTextField, 1, rowCount);
		rowCount++;
		
		Label wassertransportCounterLabel = new Label(Texts.HOW_MANY_WATHER_TEXT);
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
		vBox.getChildren().add(grid);

		paintWatherTransport(1);
		
		rowCount=0;
		GridPane grid2 = new GridPane();
		prepareGrid(grid2);
		Label wichtigeKontakte = new Label(Texts.IMPORTANT_CONTACTS_TEXT);
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
			wichtigeKontakteTextField.setText(editPlan.getImportantContacts());
		}

		Button btnSave = new Button(Texts.SAVE_TEXT);
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
					withWatherTransport(getWatherValue(0)).
					withImportantContact(wichtigeKontakteTextField.getText()).build();	
					
				if(editPlan == null){
					DBPlan.getInstance().insertPlan(savePlan);
				}else{
					savePlan.setMap(editPlan.getMap());
					DBPlan.getInstance().updatePlan(savePlan);
				}
				editPlan = savePlan;
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(Texts.PLAN_SAVED_TEXT);
				alert.setHeaderText(null);
				alert.setContentText(String.format(Texts.PLAN_SAVE_COMPLETE_TEXT, planNumberTextField.getText()));
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
	
	private String getWatherValue(int number) {
		String ret = "";
		TextArea area = (TextArea)vBox.getScene().lookup("#"+Texts.WHATERID_TEXT + number);
		if(area != null) {
			return area.getText();
		}
		return ret;
	}
	
	private void paintWatherTransport(int value) {
		if(vBox.getChildren().size() == 3) {
			vBox.getChildren().remove(1);
		}
		GridPane grid = new GridPane();
		prepareGrid(grid);
		int count = 0;
		while(count < value) {
			addWather(grid, count);
			count++;
		}
		vBox.getChildren().add(1,grid);
	}
	
	private void addWather(GridPane grid,int number) {
		Label label = new Label(Texts.WATHERTRANSPORT + Texts.SPACE + (number+1));
		grid.add(label, 0, number);
		final TextArea watherTextArea = new TextArea();
		watherTextArea.setId(Texts.WHATERID_TEXT + number);
		grid.add(watherTextArea, 1, number);
	}
	
	
	private void prepareGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
	}
}
