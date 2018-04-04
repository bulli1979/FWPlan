package screens;

import data.Plan;
import fx.TableViewSample.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PlanListScreen {
	private final TableView<Plan> table = new TableView<>();
	private final ObservableList<Plan> data = FXCollections.observableArrayList(new Plan.Builder().setId("asdf")
			.withPlanNumber("001").withDescription("blabla").withTitle("Plan 1").build());

	final Label label = new Label("Address Book");label.setFont(new Font("Arial",20));

	table.setEditable(true);

	TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>(
			"First Name");firstNameCol.setMinWidth(100);firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

	TableColumn<Person, String> lastNameCol = new TableColumn<Person, String>(
			"Last Name");lastNameCol.setMinWidth(100);lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

	TableColumn<Person, String> emailCol = new TableColumn<Person, String>(
			"Email");emailCol.setMinWidth(200);emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

	firstNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());firstNameCol.setOnEditCommit((
	CellEditEvent<Person, String> t)->
	{
		((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFirstName(t.getNewValue());
	});

	lastNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());lastNameCol.setOnEditCommit((
	CellEditEvent<Person, String> t)->
	{
		((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
	});

	emailCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());emailCol.setOnEditCommit((
	CellEditEvent<Person, String> t)->
	{
		((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
	});table.setItems(data);table.getColumns().addAll(firstNameCol,lastNameCol,emailCol);

	final TextField addFirstName = new TextField();addFirstName.setPromptText("First Name");addFirstName.setMaxWidth(firstNameCol.getPrefWidth());

	final TextField addLastName = new TextField();addLastName.setMaxWidth(lastNameCol.getPrefWidth());addLastName.setPromptText("Last Name");

	final TextField addEmail = new TextField();addEmail.setMaxWidth(emailCol.getPrefWidth());addEmail.setPromptText("Email");

	final Button addButton = new Button("Add");addButton.setOnAction((
	ActionEvent e)->
	{
         data.add(new Person(
             addFirstName.getText(),
             addLastName.getText(),
             addEmail.getText()
         ));
         addFirstName.clear();
         addLastName.clear();
         addEmail.clear();
     });
	VBox form = new VBox(addFirstName, addLastName, addEmail, addButton);

	final VBox vbox = new VBox();vbox.setSpacing(5);vbox.setPadding(new Insets(10,0,0,10));vbox.getChildren().addAll(label,table,form);

	((Group)scene.getRoot()).getChildren().addAll(vbox);

	stage.setScene(scene);stage.show();
}
