package alcohol;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyImageView extends ImageView{
	private int drink_id;
	
	public int get_drink_id() {
		return drink_id;
	}
	
	MyImageView(Image img, int id){
		super(img);
		this.drink_id=id;
	}
	
	
}
