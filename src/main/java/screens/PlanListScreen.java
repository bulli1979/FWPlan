package screens;

import application.ApplicationHandler;
import application.Constant;
import data.Plan;
import data.db.DBPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class PlanListScreen implements ApplicationScreen {

	public Pane get() {
		BorderPane root = new BorderPane();
		ObservableList<Plan> data = FXCollections.observableArrayList(DBPlan.getInstance().getAllPlans());
		TableView<Plan> table = new TableView<>(data);

		Label label = new Label("Planliste");
		label.setFont(new Font("Arial", 20));
		table.setEditable(true);

		TableColumn<Plan, String> numberCol = new TableColumn<>("Plannummer");
		numberCol.setMinWidth(100);
		numberCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("planNumber"));

		TableColumn<Plan, String> titleCol = new TableColumn<>("Titel");
		titleCol.setMinWidth(100);
		titleCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("title"));

		TableColumn<Plan, String> descriptionCol = new TableColumn<>("Description");
		descriptionCol.setMinWidth(200);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("description"));
		
		TableColumn mapColumn = new TableColumn("Kartenbild");
		mapColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		
		Callback<TableColumn<Plan, String>, TableCell<Plan, String>> cellFactory
        = //
        new Callback<TableColumn<Plan, String>, TableCell<Plan, String>>() {

				public TableCell call(final TableColumn<Plan, String> param) {
						final TableCell<Plan, String> cell = new TableCell<Plan, String>() {

							final Button btn = new Button("Map bearbeiten");

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btn.setOnAction(event -> {
										Plan plan = getTableView().getItems().get(getIndex());
										Constant.INSTANCE.setPlan(plan);
										ApplicationHandler.setScreen(ScreenObject.MAPSCREEN);
									});
									setGraphic(btn);
		                    setText(null);
		                }
		            }
		        };
		        return cell;
		    }
	
		};

		mapColumn.setCellFactory(cellFactory);
		
		TableColumn editColumn = new TableColumn("Bearbeiten");
		editColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		
		Callback<TableColumn<Plan, String>, TableCell<Plan, String>> cellFactoryEditColumn
        = //
        new Callback<TableColumn<Plan, String>, TableCell<Plan, String>>() {

				public TableCell call(final TableColumn<Plan, String> param) {
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
										Constant.INSTANCE.setPlan(plan);
										ApplicationHandler.setScreen(ScreenObject.PLANEDITSCREEN);
									});
									setGraphic(btn);
		                    setText(null);
		                }
		            }
		        };
		        return cell;
		    }
	
		};

		editColumn.setCellFactory(cellFactoryEditColumn);
		
		table.getColumns().setAll(numberCol, titleCol, descriptionCol, mapColumn, editColumn);
		table.setUserData(data);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table);

		root.setCenter(vbox);
		return root;
	}
}
