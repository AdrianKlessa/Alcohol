package alcohol;

import java.util.Formatter;
import java.util.Set;

public class CreateFile {

	private Formatter x;
	
	public void openFile(String name) {
		
		try {
			x= new Formatter(name);
		}catch(Exception e){
			System.out.println("Error:"+e.getMessage());
		}
		
	}
	
	public void addRecord(String x) {
		this.x.format("%s%n",x);
	}
	public void closeFile() {
		x.close();
	}
	
	public static void saveFav(Set<Integer> favourites) {
		CreateFile g = new CreateFile();
		g.openFile("fav.ini");
		
		for(Integer id:favourites) {
			g.addRecord(Integer.toString(id));
		};
		
		
		g.closeFile();
	}
	
}
