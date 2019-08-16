package alcohol;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutBox {

	static void display(String title, String message) {
		
			Stage window = new Stage();
			
			window.setTitle(title);
			window.setMinWidth(250);
			window.setMinHeight(250);
			
			Label label = new Label();
			label.setText(message);
			Button closeButton = new Button("Close");
			closeButton.setOnAction(e->window.close());
			
			VBox layout = new VBox(10);
			layout.getChildren().addAll(label,closeButton);
			layout.setAlignment(Pos.CENTER);
			Scene scene = new Scene(layout);
			
			Image img_info = new Image(AboutBox.class.getResourceAsStream("/info.png"));
			window.getIcons().add(img_info);
			window.setScene(scene);
			window.show();
			
			
	}
	
	
	
}
