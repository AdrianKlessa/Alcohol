package alcohol;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tools {
	public static TreeItem<String> createBranch(String title, TreeItem<String> upper){
		TreeItem<String> item = new TreeItem<>(title);
		item.setExpanded(false);
		upper.getChildren().add(item);
		return item;
		
		
	}
	
	public static ImageView base64toView(String data) throws IOException {
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(data);
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
		Image almostThere = SwingFXUtils.toFXImage(img, null);
		ImageView viewableImage = new ImageView(almostThere);
		return viewableImage;
	}
}
