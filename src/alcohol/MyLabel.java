package alcohol;

import javafx.scene.control.Label;

public class MyLabel extends Label{

	private int drink_id;
	
	public int get_drink_id() {
		return drink_id;
	}
	
	MyLabel(String name, int id){
		super(name);
		this.drink_id=id;
	}
	
	
	
}
