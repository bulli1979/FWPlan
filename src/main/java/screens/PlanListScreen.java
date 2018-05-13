package screens;

import data.Plan;
import data.db.DBPlan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PlanListScreen implements ApplicationScreen {

	@SuppressWarnings("unchecked")
	public Pane get() {
		BorderPane root = new BorderPane();
		ObservableList<Plan> data = FXCollections.observableArrayList(DBPlan.getInstance().getAllPlans());
		TableView<Plan> table = new TableView<>(data);

		Label label = new Label("Planliste");
		label.setFont(new Font("Arial", 20));
		System.out.println("size: " + data.size());
		table.setEditable(true);

		TableColumn<Plan, String> numberCol = new TableColumn<>("Plannummer");
		numberCol.setMinWidth(100);
		numberCol.setCellValueFactory(new PropertyValueFactory<>("planNumber"));

		TableColumn<Plan, String> titleCol = new TableColumn<>("Titel");
		titleCol.setMinWidth(100);
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Plan, String> descriptionCol = new TableColumn<>("Description");
		descriptionCol.setMinWidth(200);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		table.getColumns().setAll(numberCol, titleCol, descriptionCol);
		table.setUserData(data);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table);

		root.setCenter(vbox);
		return root;
	}
}
