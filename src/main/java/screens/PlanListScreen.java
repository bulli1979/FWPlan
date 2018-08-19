package screens;

import application.ApplicationHandler;
import constants.ValueHolder;
import data.Plan;
import data.db.DBPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PlanListScreen implements ApplicationScreen {

	public Pane get() {
		BorderPane root = new BorderPane();
		ObservableList<Plan> data = FXCollections.observableArrayList(DBPlan.getInstance().getAllPlans());
		TableView<Plan> table = new TableView<>(data);
		
		Label label = createLabel(table);
		TextField searchField = createSearchField(table);

		TableColumn<Plan, String> numberCol = createColumn("Plannummer", "planNumber", 100);
		TableColumn<Plan, String> titleCol = createColumn("Titel", "title", 100);
		TableColumn<Plan, String> descriptionCol = createColumn("Beschreibung", "description", 100);
		TableColumn<Plan, String> editColumn = createEditColumn();
		TableColumn<Plan, String> mapColumn = createMapColumn();
		TableColumn<Plan, String> mapEditColumn = createMapEditColumn();

		ObservableList<TableColumn<Plan, ?>> colls = table.getColumns();
		colls.add(numberCol);
		colls.add(titleCol);
		colls.add(descriptionCol);
		colls.add(mapColumn);
		colls.add(editColumn);
		colls.add(mapEditColumn);
		table.setItems(data);	
		
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount()==2) {
					ValueHolder.INSTANCE.setPlan(table.getSelectionModel().getSelectedItem());
					openPlanDetailView(root);
				}
			}
		});
		

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, searchField, table);

		root.setCenter(vbox);
		return root;
	}

	private TableColumn<Plan, String> createMapEditColumn() {
		TableColumn<Plan, String> mapEditColumn = new TableColumn<>("Kartenbild");
		Callback<TableColumn<Plan, String>, TableCell<Plan, String>> takeMapEditCB = addCallbackForMapEditTaken();
		mapEditColumn.setCellFactory(takeMapEditCB);
		return mapEditColumn;
	}

	private TableColumn<Plan, String> createEditColumn() {
		TableColumn<Plan, String> editColumn = new TableColumn<>("Bearbeiten");
		Callback<TableColumn<Plan, String>, TableCell<Plan, String>> editCB = createEditCallback();
		editColumn.setCellFactory(editCB);
		return editColumn;
	}

	private TableColumn<Plan, String> createMapColumn() {
		TableColumn<Plan, String> mapColumn = new TableColumn<>("Kartenbild");
		Callback<TableColumn<Plan, String>, TableCell<Plan, String>> takeMapCB = addCallbackForMapTaken();
		mapColumn.setCellFactory(takeMapCB);
		return mapColumn;
	}

	private TableColumn<Plan, String> createColumn(String headLine, String propertie, int width) {
		TableColumn<Plan, String> col = new TableColumn<>(headLine);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<Plan, String>(propertie));
		return col;
	}

	private Label createLabel(TableView<Plan> table) {
		Label label = new Label("Planliste");
		label.setFont(new Font("Arial", 20));
		table.setEditable(true);
		return label;
	}
	
	private TextField createSearchField(TableView<Plan> table) {
		final TextField searchField = new TextField();
		searchField.setFocusTraversable(false);
		searchField.setPromptText("Einsatzpläne durchsuchen");
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			//data.clear();
			ObservableList<Plan> data = FXCollections.observableArrayList(DBPlan.getInstance().getPlansWithKeyword(newValue));
			table.setItems(data);
			table.refresh();
		});
		return searchField;
	}

	private Callback<TableColumn<Plan, String>, TableCell<Plan, String>> createEditCallback() {
		return new Callback<TableColumn<Plan, String>, TableCell<Plan, String>>() {

			public TableCell<Plan, String> call(final TableColumn<Plan, String> param) {
				final TableCell<Plan, String> cell = new TableCell<Plan, String>() {

					final Button btn = new Button("Plan Bearbeiten");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Plan plan = getTableView().getItems().get(getIndex());
								ValueHolder.INSTANCE.setPlan(plan);
								ApplicationHandler.setScreen(ScreenObject.PLAN_EDIT_SCREEN);
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}

		};
	}

	private Callback<TableColumn<Plan, String>, TableCell<Plan, String>> addCallbackForMapTaken() {
		return new Callback<TableColumn<Plan, String>, TableCell<Plan, String>>() {

			public TableCell<Plan, String> call(final TableColumn<Plan, String> param) {
				final TableCell<Plan, String> cell = new TableCell<Plan, String>() {

					final Button btn = new Button("Map aufnehmen");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Plan plan = getTableView().getItems().get(getIndex());
								ValueHolder.INSTANCE.setPlan(plan);
								ApplicationHandler.setScreen(ScreenObject.MAP_SCREEN);
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}

		};
	}

	private Callback<TableColumn<Plan, String>, TableCell<Plan, String>> addCallbackForMapEditTaken() {
		return new Callback<TableColumn<Plan, String>, TableCell<Plan, String>>() {

			public TableCell<Plan, String> call(final TableColumn<Plan, String> param) {
				final TableCell<Plan, String> cell = new TableCell<Plan, String>() {

					final Button btn = new Button("Auf Karte zeichnen");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Plan plan = getTableView().getItems().get(getIndex());
								ValueHolder.INSTANCE.setPlan(plan);
								ApplicationHandler.setScreen(ScreenObject.MAP_EDIT_SCREEN);
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
	}
	
	private void openPlanDetailView(Pane root) {
		Stage dialog = new Stage();
		Scene openMapScene = new Scene(ScreenObject.PLAN_DETAIL_DIALOG.screen.get());
		dialog.setScene(openMapScene);
		dialog.initOwner(root.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
}