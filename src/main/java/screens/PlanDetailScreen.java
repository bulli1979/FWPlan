package screens;

import java.io.File;
import java.net.MalformedURLException;
import application.Constant;
import data.Plan;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PlanDetailScreen implements ApplicationScreen {
	

	@Override
	public Pane get() {
		Plan plan = Constant.INSTANCE.getPlan();
		BorderPane root = new BorderPane();
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPadding(new Insets(0, 25, 25, 25));
		scrollPane.setStyle("-fx-background-color:transparent;");
		VBox vbox = new VBox();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Einsatzplan " + plan.getPlanNumber() + " - " + plan.getTitle());
		scenetitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		
		
		Label description = new Label("Beschreibung:");
		grid.add(description, 0, 4);
		Label descriptionValue = new Label(plan.getDescription());
		grid.add(descriptionValue, 1, 4);
		
		Label adresse = new Label("Adresse:");
		grid.add(adresse, 0, 5);
		Label adressValue = new Label(plan.getAdress());
		grid.add(adressValue, 1, 5);
		
		Label sofortmassnahmen = new Label("Sofortmassnahmen:");
		grid.add(sofortmassnahmen, 0, 6);
		Label sofortmassnahmenValue = new Label(plan.getInstantAction());
		grid.add(sofortmassnahmenValue, 1, 6);
		
		Label wassertransport = new Label("Wassertransport:");
		grid.add(wassertransport, 0, 7);
		Label wassertransportValue = new Label(plan.getWatherTransport());
		grid.add(wassertransportValue, 1, 7);
		
		Label wichtigeKontakte = new Label("Wichtige Kontakte:");
		grid.add(wichtigeKontakte, 0, 8);
		Label wichtigeKontakteValue = new Label(plan.getImportantContacts());
		grid.add(wichtigeKontakteValue, 1, 8);

		Image mapImage = new Image("no-image.jpg");
		ImageView mapImageView = new ImageView();
		
		try {
			if(plan.getMap() != null) {
				File imageFile = new File(plan.getMap());
				if(imageFile.exists()) {
					mapImage = new Image(Constant.INSTANCE.getImagePrefix() + imageFile.toURI().toURL().toString());
					mapImageView.setFitWidth(700);
					mapImageView.setFitHeight(500);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapImageView.setImage(mapImage);
		
		Button btnPrint = new Button("PDF drucken");
		btnPrint.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				/*PrinterJob job = PrinterJob.createPrinterJob();
				if(job != null){
					   job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
					   job.printPage(root);
					   job.endJob();
				}*/
				System.out.println("TODO Print PDF");
			}
		});	
		HBox hbBtn = new HBox(10);
		hbBtn.setPadding(new Insets(25, 0, 0, 0));
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(btnPrint);
		vbox.getChildren().addAll(grid, mapImageView, hbBtn);
		scrollPane.setContent(vbox);
		root.setCenter(scrollPane);
		return root;
	}

}
