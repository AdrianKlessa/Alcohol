package alcohol;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutBox {

	static void display(String title, String message) {
		
			Stage window = new Stage();
			
			window.setTitle(title);
			window.setMinWidth(400);
			window.setMinHeight(500);
			
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			Insets myInset = new Insets(10,10,10,10);
			grid.setPadding(myInset);
			
			
			
			
			
			
			Label label = new Label();
			label.setText(message);
			Button closeButton = new Button("Close");
			closeButton.setOnAction(e->window.close());
			
			
			GridPane.setConstraints(label,0,0);
			GridPane.setConstraints(closeButton,0,1);
			grid.getChildren().addAll(label,closeButton);
			Scene scene = new Scene(grid);
			
			Image img_info = new Image(AboutBox.class.getResourceAsStream("/info.png"));
			window.getIcons().add(img_info);
			window.setScene(scene);
			window.show();
			
			
	}
	
	
	
}
