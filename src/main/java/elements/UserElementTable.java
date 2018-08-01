package elements;

import java.io.File;
import java.io.IOException;
import java.util.List;

import application.Constant;
import data.UserElement;
import data.db.DBUserElement;
import helper.ImagePaint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import screens.MapEditScreen;
import tools.Tool;

public class UserElementTable extends TableView<UserElement> {
	private MapEditScreen parent;
	@SuppressWarnings("unchecked")
	public UserElementTable(List<UserElement> userElements,MapEditScreen parent) {
		super();
		this.parent = parent;
		ObservableList<UserElement> data = FXCollections.observableArrayList(userElements);
		TableColumn<UserElement, Integer> typeCol = createTypeColumn();
		TableColumn<UserElement, Double> topCol = createDoubleColumn("Abstand von oben", "top", 200);
		TableColumn<UserElement,String> deleteCol = createDeleteColumn();
		topCol.setOnEditCommit(new EventHandler<CellEditEvent<UserElement, Double>>() {
			@Override
			public void handle(CellEditEvent<UserElement, Double> t) {
				UserElement userElement = t.getTableView().getItems().get(t.getTablePosition().getRow());
				userElement.setTop(t.getNewValue());
				save(userElement);
			}
		});
		TableColumn<UserElement, Double> leftCol = createDoubleColumn("Abstand von links", "left", 200);
		leftCol.setOnEditCommit(new EventHandler<CellEditEvent<UserElement, Double>>() {
			@Override
			public void handle(CellEditEvent<UserElement, Double> t) {
				UserElement userElement = t.getTableView().getItems().get(t.getTablePosition().getRow());
				userElement.setLeft(t.getNewValue());
				save(userElement);
			}
		});
		TableColumn<UserElement, Double> widthCol = createDoubleColumn("Breite", "width", 200);
		widthCol.setOnEditCommit(new EventHandler<CellEditEvent<UserElement, Double>>() {
			@Override
			public void handle(CellEditEvent<UserElement, Double> t) {
				UserElement userElement = t.getTableView().getItems().get(t.getTablePosition().getRow());
				Tool.INSTANCE.resizeWidth(userElement, t.getNewValue());
				try {
					ImagePaint.resizeImage(userElement);
				} catch (IOException e) {
					e.printStackTrace();
				}
				save(userElement);
			}
		});
		TableColumn<UserElement, Double> heightCol = createDoubleColumn("H�he", "height", 200);
		heightCol.setOnEditCommit(new EventHandler<CellEditEvent<UserElement, Double>>() {
			@Override
			public void handle(CellEditEvent<UserElement, Double> t) {
				UserElement userElement = t.getTableView().getItems().get(t.getTablePosition().getRow());
				Tool.INSTANCE.resizeHeight(userElement, t.getNewValue());
				try {
					ImagePaint.resizeImage(userElement);
				} catch (IOException e) {
					e.printStackTrace();
				}
				save(userElement);
			}
		});
		
		this.setItems(data);
		this.getColumns().addAll(typeCol, topCol, leftCol, widthCol, heightCol, deleteCol);
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
	
	private TableColumn<UserElement, String> createDeleteColumn() {
		TableColumn<UserElement, String> editColumn = new TableColumn<>("l�schen");
		Callback<TableColumn<UserElement, String>, TableCell<UserElement, String>> editCB = createDeleteCallback();
		editColumn.setCellFactory(editCB);
		return editColumn;
	}

	private Callback<TableColumn<UserElement, String>, TableCell<UserElement, String>> createDeleteCallback() {
		return new Callback<TableColumn<UserElement, String>, TableCell<UserElement, String>>() {

			public TableCell<UserElement, String> call(final TableColumn<UserElement, String> param) {
				final TableCell<UserElement, String> cell = new TableCell<UserElement, String>() {

					final Button btn = new Button("Element l�schen");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								UserElement userElement = getTableView().getItems().get(getIndex());
								deleteElement(userElement);
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
	
	private void deleteElement(UserElement userElement) {
		String fileName = userElement.getImage();
		if(userElement.getType()==2 && fileName != null) {
			File file = new File(Constant.INSTANCE.getUserImagePrfix() + userElement.getImage());
			if(file != null && file.exists()) {
				file.delete();
			}
		}
		DBUserElement.getInstance().deleteElement(userElement);
		parent.paintNewMap();
	}

}
