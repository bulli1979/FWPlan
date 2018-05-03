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

public class PlanListScreen {
	//TODO Simon bitte aufräumen und schick machen buttons rein usw. 
	//2ten Screen für Formular bauen
	private static TableView<Plan> table = new TableView<>();
	private static ObservableList<Plan> data = FXCollections.observableArrayList(DBPlan.getInstance().getAllPlans());
	private static BorderPane root = new BorderPane();
	
	public PlanListScreen() {
		
	}
	
	public static Pane getScreen(String planNumber) {
		Label label = new Label("Planliste");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);

		TableColumn<Plan, String> numberCol = new TableColumn<Plan, String>("Plannummer");
		numberCol.setMinWidth(100);
		numberCol.setCellValueFactory(new PropertyValueFactory<>("planNumber"));

		TableColumn<Plan, String> titleCol = new TableColumn<Plan, String>("Titel");
		titleCol.setMinWidth(100);
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Plan, String> descriptionCol = new TableColumn<Plan, String>("Description");
		descriptionCol.setMinWidth(200);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		table.setUserData(data);
		

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table);

		root.setCenter(vbox);
		return root;
	}
}
