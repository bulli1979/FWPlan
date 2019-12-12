package biz.wgc.fwplan.screens;

import java.util.UUID;

import org.apache.log4j.Logger;

import biz.wgc.fwplan.ApplicationHandler;
import biz.wgc.fwplan.constants.Texts;
import biz.wgc.fwplan.constants.ValueHolder;
import biz.wgc.fwplan.data.Plan;
import biz.wgc.fwplan.data.db.DBPlan;
import biz.wgc.fwplan.tools.Tool;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
	private VBox gridBox = new VBox();
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

		this.gridBox = new VBox();
		editPlan = ValueHolder.INSTANCE.getPlan();
		BorderPane root = new BorderPane();
		try {
			this.gridBox.getChildren().addAll(createGridOne(), paintWatherTransport(), importantContactsPane(),createSaveButtonBox());
			fillGrid();
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setContent(gridBox);
			root.setCenter(scrollPane);
			logger.info("getEditPlanScreen");
		} catch (Exception e) {
			logger.error("Error in create Edit Scene ", e);
		}
		return root;
	}

	private HBox createSaveButtonBox() {
		Button btnSave = new Button(Texts.SAVE_TEXT);
		btnSave.getStyleClass().addAll("btn","primary");
		setSaveAction(btnSave);
		HBox hbBtn = new HBox(btnSave);
		hbBtn.getStyleClass().add("editButtonBox");
		return hbBtn;
	}

	private void fillGrid() {
		if (editPlan != null) {
			PLANNUMBERTEXTFIELD.setText(editPlan.getPlanNumber());
			TITLETEXTFIELD.setText(editPlan.getTitle());
			DESCRIPTIONTEXTFIELD.setText(editPlan.getDescription());
			ADRESSETEXTFIELD.setText(editPlan.getAdress());
			DIRECTSUPPORTFIELD.setText(editPlan.getInstantAction());
			IMPORTENTCONTACTSCOUNTER.setText(editPlan.getImportantContacts());
		}
	}

	private GridPane importantContactsPane() {
		GridPane grid = new GridPane();
		grid.getStyleClass().addAll("grid");
		Label wichtigeKontakte = new Label(Texts.IMPORTANT_CONTACTS_TEXT);
		grid.add(wichtigeKontakte, 0, 0);
		grid.add(this.IMPORTENTCONTACTSCOUNTER, 1, 0);
		return grid;
	}

	private GridPane createGridOne() {
		GridPane grid = new GridPane();
		grid.getStyleClass().addAll("grid");
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
		int countWatherTransport = Tool.INSTANCE.countWhaterTransports();
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
			this.gridBox.getChildren().remove(1);
			this.gridBox.getChildren().add(1, this.paintWatherTransport());
		});
		return grid;
	}
	
	private void setSaveAction(Button btnSave) {
		btnSave.setOnAction(event -> {
			Plan savePlan = new Plan.Builder().setId(editPlan != null ? editPlan.getId() : UUID.randomUUID().toString())
					.withPlanNumber(PLANNUMBERTEXTFIELD.getText()).withTitle(TITLETEXTFIELD.getText())
					.withDescription(DESCRIPTIONTEXTFIELD.getText()).withAdress(ADRESSETEXTFIELD.getText())
					.withInstantAction(DIRECTSUPPORTFIELD.getText()).withWatherTransport1(getWatherValue(1))
					.withWatherTransport2(getWatherValue(2)).withWatherTransport3(getWatherValue(3))
					.withWatherTransport4(getWatherValue(4)).withWatherColor1(getColorSelectValue(1))
					.withWatherColor2(getColorSelectValue(2)).withWatherColor3(getColorSelectValue(3))
					.withWatherColor4(getColorSelectValue(4)).withImportantcontacts(IMPORTENTCONTACTSCOUNTER.getText())
					.build();
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
		TextArea area = (TextArea) gridBox.getScene().lookup("#" + String.format(Texts.WHATERID_TEXT, number));
		if (area != null) {
			return area.getText();
		}
		return whaterText;
	}

	private String getColorSelectValue(int number) {
		String whaterText = "";
		@SuppressWarnings("unchecked")
		ComboBox<String> box = (ComboBox<String>) gridBox.getScene()
				.lookup("#" + String.format(Texts.WHATERID_COLOR, number));
		if (box != null) {
			return box.getValue();
		}
		return whaterText;
	}

	private GridPane paintWatherTransport() {
		GridPane grid = new GridPane();
		grid.getStyleClass().addAll("grid");
		int count = 0;
		int number = 1;
		while (number <= WHATERTRANSPORTCOUNTER.getValue()) {
			addWather(grid, number, count);
			count++;
			addWatherColorSelect(grid, number, count);
			count++;
			number++;
		}
		return grid;
	}

	private void addWather(GridPane grid, int number, int count) {
		Label label = new Label(String.format(Texts.WATHERTRANSPORT, number));
		grid.add(label, 0, count);
		final TextArea watherTextArea = new TextArea();
		watherTextArea.setId(String.format(Texts.WHATERID_TEXT, number));
		if (editPlan != null) {
			switch (number) {
			case 1:
				watherTextArea.setText(editPlan.getWatherTransport1());
				break;
			case 2:
				watherTextArea.setText(editPlan.getWatherTransport2());
				break;
			case 3:
				watherTextArea.setText(editPlan.getWatherTransport3());
				break;
			case 4:
				watherTextArea.setText(editPlan.getWatherTransport4());
				break;
			}
		}
		grid.add(watherTextArea, 1, count);
	}

	private void addWatherColorSelect(GridPane grid, int number, int count) {
		Label label = new Label(String.format(Texts.WATHERCOLOR, number));
		grid.add(label, 0, count);
		ComboBox<String> colorBox = new ComboBox<>();
		colorBox.getItems().add("Schwarz");
		colorBox.getItems().add("Rot");
		colorBox.getItems().add("Grün");
		colorBox.getItems().add("Blau");
		colorBox.getItems().add("Gelb");
		colorBox.getItems().add("Orange");
		colorBox.setId(String.format(Texts.WHATERID_COLOR, number));
		if (editPlan != null) {
			switch (number) {
			case 1:
				colorBox.setValue(editPlan.getWatherColor1());
				break;
			case 2:
				colorBox.setValue(editPlan.getWatherColor2());
				break;
			case 3:
				colorBox.setValue(editPlan.getWatherColor3());
				break;
			case 4:
				colorBox.setValue(editPlan.getWatherColor4());
				break;
			}
		}
		grid.add(colorBox, 1, count);

	}
}
