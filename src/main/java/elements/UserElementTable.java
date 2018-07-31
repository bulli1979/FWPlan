package elements;

import java.util.List;

import data.UserElement;
import data.db.DBUserElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import screens.MapEditScreen;

public class UserElementTable extends TableView<UserElement> {
	private MapEditScreen parent;
	@SuppressWarnings("unchecked")
	public UserElementTable(List<UserElement> userElements,MapEditScreen parent) {
		super();
		this.parent = parent;
		ObservableList<UserElement> data = FXCollections.observableArrayList(userElements);
		TableColumn<UserElement, Integer> typeCol = createTypeColumn();
		TableColumn<UserElement, Double> topCol = createDoubleColumn("Abstand von oben", "top", 200);
		
		topCol.setOnEditCommit(new EventHandler<CellEditEvent<UserElement, Double>>() {
			@Override
			public void handle(CellEditEvent<UserElement, Double> t) {
				UserElement element = t.getTableView().getItems().get(t.getTablePosition().getRow());
				element.setTop(t.getNewValue());
				save(element);
			}
		});
		TableColumn<UserElement, Double> leftCol = createDoubleColumn("Abstand von links", "left", 200);
		leftCol.setOnEditCommit(new EventHandler<CellEditEvent<UserElement, Double>>() {
			@Override
			public void handle(CellEditEvent<UserElement, Double> t) {
				UserElement element = t.getTableView().getItems().get(t.getTablePosition().getRow());
				element.setLeft(t.getNewValue());
				save(element);
			}
		});
		TableColumn<UserElement, Double> widthCol = createDoubleColumn("Breite", "width", 200);
		widthCol.setOnEditCommit(new EventHandler<CellEditEvent<UserElement, Double>>() {
			@Override
			public void handle(CellEditEvent<UserElement, Double> t) {
				UserElement element = t.getTableView().getItems().get(t.getTablePosition().getRow());
				element.setWidth(t.getNewValue());
				save(element);
			}
		});
		TableColumn<UserElement, Double> heightCol = createDoubleColumn("Höhe", "height", 200);
		heightCol.setOnEditCommit(new EventHandler<CellEditEvent<UserElement, Double>>() {
			@Override
			public void handle(CellEditEvent<UserElement, Double> t) {
				UserElement element = t.getTableView().getItems().get(t.getTablePosition().getRow());
				element.setHeight(t.getNewValue());
				save(element);
			}
		});
		
		this.setItems(data);
		this.getColumns().addAll(typeCol, topCol, leftCol,widthCol,heightCol);
		this.setEditable(true);

	}

	private void save(UserElement userElement) {
		DBUserElement.getInstance().updateElement(userElement);
		parent.paintNewMap();
	}
	
	private TableColumn<UserElement, Double> createDoubleColumn(String headLine, String propertie, int width) {
		TableColumn<UserElement, Double> col = new TableColumn<>(headLine);
		col.setCellValueFactory(new PropertyValueFactory<UserElement, Double>(propertie));
		col.setMinWidth(width);
		col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
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
