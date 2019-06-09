package biz.wgc.fwplan.screens;

import java.util.UUID;

import org.apache.log4j.Logger;

import biz.wgc.fwplan.ApplicationHandler;
import biz.wgc.fwplan.constants.Texts;
import biz.wgc.fwplan.constants.ValueHolder;
import biz.wgc.fwplan.data.Plan;
import biz.wgc.fwplan.data.db.DBPlan;
import biz.wgc.fwplan.tools.Tool;
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

public class CreateOrEditPlan implements ApplicationScreen {
	private static Logger logger = Logger.getLogger(CreateOrEditPlan.class);

	private Plan editPlan = null;
	private VBox vBox = new VBox();
	private final TextField PLANNUMBERTEXTFIELD = new TextField();
	private final TextField TITLETEXTFIELD = new TextField();
	private final TextArea DESCRIPTIONTEXTFIELD = new TextArea();
	private final TextArea ADRESSETEXTFIELD = new TextArea();
	private final TextArea DIRECTSUPPORTFIELD = new TextArea();
	private final ComboBox<Integer> WHATERTRANSPORTCOUNTER = new ComboBox<Integer>();
	private final TextArea IMPORTENTCONTACTSCOUNTER = new TextArea();
	public CreateOrEditPlan() {
	}

	@Override
	public Pane get() {
		logger.info("start getEditPlanScreen");
		
		this.vBox = new VBox();
		editPlan = ValueHolder.INSTANCE.getPlan();
		BorderPane root = new BorderPane();
		try {
			GridPane grid = new GridPane();
			prepareGrid(grid);
			Integer rowCount = 0;
			Text scenetitle = new Text(Texts.EDIT_OR_CREATE_TEXT);
			scenetitle.setFont(Font.font(Texts.ARIAL_FONT, FontWeight.BOLD, 20));
			grid.add(scenetitle, 0, rowCount, 2, 1);
			rowCount += 2;
			Label planNumber = new Label(Texts.PLANNUMBER_TEXT);
			grid.add(planNumber, 0, rowCount);
			grid.add(this.PLANNUMBERTEXTFIELD, 1, rowCount);
			rowCount++;
			Label title = new Label(Texts.TITLE_TEXT);
			grid.add(title, 0, rowCount);
			grid.add(this.TITLETEXTFIELD, 1, rowCount);
			rowCount++;
			Label description = new Label(Texts.DESCRIPTION_TEXT);
			grid.add(description, 0, rowCount);
			
			grid.add(this.DESCRIPTIONTEXTFIELD, 1, rowCount);
			rowCount++;
			Label adresse = new Label(Texts.ADRESS_TEXT);
			grid.add(adresse, 0, rowCount);
			
			grid.add(this.ADRESSETEXTFIELD, 1, rowCount);
			rowCount++;
	
			Label sofortmassnahmen = new Label(Texts.INSTANT_ACTION_TEXT);
			grid.add(sofortmassnahmen, 0, rowCount);
			
			grid.add(this.DIRECTSUPPORTFIELD, 1, rowCount);
			rowCount++;
			int countWatherTransport =  Tool.INSTANCE.countWhaterTransports();
			Label wassertransportCounterLabel = new Label(Texts.HOW_MANY_WATHER_TEXT);
			grid.add(wassertransportCounterLabel, 0, rowCount);
			this.WHATERTRANSPORTCOUNTER.getItems().add(1);
			this.WHATERTRANSPORTCOUNTER.getItems().add(2);
			this.WHATERTRANSPORTCOUNTER.getItems().add(3);
			this.WHATERTRANSPORTCOUNTER.getItems().add(4);
			this.WHATERTRANSPORTCOUNTER.setValue(countWatherTransport);
			grid.add(WHATERTRANSPORTCOUNTER, 1, rowCount);
			rowCount++;
	
			this.WHATERTRANSPORTCOUNTER.setOnAction(event -> {
				System.out.println("paint");
				paintWatherTransport(WHATERTRANSPORTCOUNTER.getValue());
			});
			vBox.getChildren().add(grid);
	
			paintWatherTransport(countWatherTransport);
	
			rowCount = 0;
			GridPane grid2 = new GridPane();
			prepareGrid(grid2);
			Label wichtigeKontakte = new Label(Texts.IMPORTANT_CONTACTS_TEXT);
			grid2.add(wichtigeKontakte, 0, rowCount);
			
			grid2.add(IMPORTENTCONTACTSCOUNTER, 1, rowCount);
			rowCount++;
			if (editPlan != null) {
				PLANNUMBERTEXTFIELD.setText(editPlan.getPlanNumber());
				TITLETEXTFIELD.setText(editPlan.getTitle());
				DESCRIPTIONTEXTFIELD.setText(editPlan.getDescription());
				ADRESSETEXTFIELD.setText(editPlan.getAdress());
				DIRECTSUPPORTFIELD.setText(editPlan.getInstantAction());
				IMPORTENTCONTACTSCOUNTER.setText(editPlan.getImportantContacts());
			}
	
			Button btnSave = new Button(Texts.SAVE_TEXT);
			setSaveAction(btnSave);
	
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btnSave);
			grid2.add(hbBtn, 1, rowCount);
			vBox.getChildren().add(grid2);
			
			root.setCenter(vBox);
			logger.info("getEditPlanScreen");
		}catch(Exception e) {
			logger.error("Error in create Edit Scene " , e);
		}
		return root;
	}

	private void setSaveAction(Button btnSave) {
		btnSave.setOnAction(event -> {			
			Plan savePlan = new Plan.Builder().setId(editPlan != null ? editPlan.getId() : UUID.randomUUID().toString())
					.withPlanNumber(PLANNUMBERTEXTFIELD.getText())
					.withTitle(TITLETEXTFIELD.getText())
					.withDescription(DESCRIPTIONTEXTFIELD.getText())
					.withAdress(ADRESSETEXTFIELD.getText())
					.withInstantAction(DIRECTSUPPORTFIELD.getText())
					.withWatherTransport1(getWatherValue(0))
					.withWatherTransport2(getWatherValue(1))
					.withWatherTransport3(getWatherValue(2))
					.withWatherTransport4(getWatherValue(3))
					.withImportantContact(IMPORTENTCONTACTSCOUNTER.getText()).build();

			if (editPlan == null) {
				DBPlan.getInstance().insertPlan(savePlan);
			} else {
				savePlan.setMap(editPlan.getMap());
				DBPlan.getInstance().updatePlan(savePlan);
			}
			editPlan = savePlan;

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(Texts.PLAN_SAVED_TEXT);
			alert.setHeaderText(null);
			alert.setContentText(String.format(Texts.PLAN_SAVE_COMPLETE_TEXT, PLANNUMBERTEXTFIELD.getText()));
			alert.showAndWait();

			ApplicationHandler.setScreen(ScreenObject.$PLAN_LIST_SCREE);
		});
	}
	
	private String getWatherValue(int number) {
		String whaterText = "";
		TextArea area = (TextArea) vBox.getScene().lookup("#" + Texts.WHATERID_TEXT + number);
		if (area != null) {
			return area.getText();
		}
		return whaterText;
	}

	private void paintWatherTransport(int value) {
		System.out.println("size: " + vBox.getChildren().size());
		if (vBox.getChildren().size() == 3) {
			vBox.getChildren().remove(1);
		}
		GridPane grid = new GridPane();
		prepareGrid(grid);
		int count = 0;
		while (count < value) {
			addWather(grid, count);
			count++;
		}
		vBox.getChildren().add(1, grid);
	}

	private void addWather(GridPane grid, int number) {
		Label label = new Label(Texts.WATHERTRANSPORT + Texts.SPACE + (number + 1));
		grid.add(label, 0, number);
		final TextArea watherTextArea = new TextArea();
		watherTextArea.setId(Texts.WHATERID_TEXT + number);
		if(editPlan != null) {
			switch(number) {
			case 0:
				watherTextArea.setText( editPlan.getWatherTransport1());
				break;
			case 1:
				watherTextArea.setText( editPlan.getWatherTransport2());
				break;
			case 2: 
				watherTextArea.setText( editPlan.getWatherTransport3());
				break;
			case 3:
				watherTextArea.setText( editPlan.getWatherTransport4());
				break;
			}
		}
		grid.add(watherTextArea, 1, number);
	}

	private void prepareGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
	}
}
