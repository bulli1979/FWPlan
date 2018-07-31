package elements;

import java.util.List;

import application.ApplicationHandler;
import application.Constant;
import data.Plan;
import data.UserElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import screens.ScreenObject;

public class UserElementTable extends TableView<UserElement> {
	@SuppressWarnings("unchecked")
	public UserElementTable(List<UserElement> userElements) {
		super();
		ObservableList<UserElement> data = FXCollections.observableArrayList(userElements);
		TableColumn<UserElement, Integer> typeCol = createTypeColumn();
		TableColumn<UserElement, Double> topCol = createDoubleColumn("Abstand von oben", "top", 200);		
		TableColumn<UserElement, Double> leftCol = createDoubleColumn("Abstand von links", "left", 200);
		this.getColumns().addAll(typeCol,topCol,leftCol);
		this.setEditable(true);
		this.setItems(data);

	}
	

	private TableColumn<UserElement, Double> createDoubleColumn(String headLine, String propertie, int width) {
		TableColumn<UserElement, Double> col = new TableColumn<>(headLine);
		col.setCellValueFactory(new PropertyValueFactory<UserElement, Double>(propertie));
		col.setMinWidth(width);
		return col;
	}
	
	private TableColumn<UserElement, Integer> createTypeColumn() {
		TableColumn<UserElement, Integer> typeColumn = new TableColumn<>("Type");
		typeColumn.setCellValueFactory(new PropertyValueFactory<UserElement, Integer>("type"));
		typeColumn.setMinWidth(200);
		typeColumn.setCellFactory(new UserElementTypeFactory());
		return typeColumn;
	}
	
	
}
