package biz.wgc.fwplan.elements;

import java.util.ArrayList;
import java.util.List;

import biz.wgc.fwplan.constants.CSSClassNames;
import biz.wgc.fwplan.constants.IconProperties;
import biz.wgc.fwplan.data.EditIcon;
import biz.wgc.fwplan.data.ToolType;
import biz.wgc.fwplan.screens.MapEditScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class MapEditIconButtons {
	private List<Button> buttons = new ArrayList<Button>();

	public MapEditIconButtons() {
		IconProperties.instance().setIcons();
	}
	
	public List<Button> getButtons(MapEditScreen screen) {
		buttons.clear();
		createButtons(screen);
		return buttons;
	}

	private void createButtons(MapEditScreen screen) {
		for(EditIcon icon : IconProperties.getEditIcons()) {
			buttons.add(createButton(screen,icon));
		}
		buttons.add(createImageButton(screen));
	}
	
	private Button createImageButton(MapEditScreen screen) {
		Button imageButton = new Button("Bild");
		imageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeButtonStyle();
				screen.setActiveType(ToolType.IMAGE);
				screen.setActiveIcon(null);
				imageButton.getStyleClass().add(CSSClassNames.ACTIVE);
			}
		});
		return imageButton;
	}
	
	private Button createButton(MapEditScreen screen, EditIcon icon) {
		Button b = new Button(icon.getTitle());
		b.setId(icon.getId());
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeButtonStyle();
				screen.setActiveType(ToolType.ICON);
				screen.setActiveIcon(icon);
				b.getStyleClass().add(CSSClassNames.ACTIVE);
			}
		});
		return b;
	}
	
	public void removeButtonStyle() {
		for(Button b : buttons)
			b.getStyleClass().remove(CSSClassNames.ACTIVE);
	}
}
