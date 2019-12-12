package biz.wgc.fwplan.elements;

import biz.wgc.fwplan.data.UserElement;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class UserElementTypeFactory
		implements Callback<TableColumn<UserElement, Integer>, TableCell<UserElement, Integer>> {

	@Override
	public TableCell<UserElement, Integer> call(TableColumn<UserElement, Integer> col) {
		return new TableCell<UserElement, Integer>() {

			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				if ((item == null) || empty) {
					setText(null);
					return;
				}
				switch (item) {
				case 1:
					setText("Hydranth");
					break;
				case 2:
					setText("User Bild");
					break;
				default:
					setText("");
				}
			}

		};
	}

}
