package alcohol;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsBox {
	static void display(String title, Image image, List<Integer> list) {
		
		Stage window = new Stage();
		
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMinHeight(250);
		

		Button closeButton = new Button("Close");
		Button clearHistoryButton = new Button("Clear history", new ImageView(image));
		closeButton.setOnAction(e->window.close());
		clearHistoryButton.setOnAction(e->{
		list.clear();
		});
		VBox layout = new VBox(10);
		layout.getChildren().addAll(clearHistoryButton,closeButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		
		Image img_settings = new Image(AboutBox.class.getResourceAsStream("/settings.png"));
		window.getIcons().add(img_settings);
		window.setScene(scene);
		window.show();
		
		
}

}
