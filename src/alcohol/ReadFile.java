package alcohol;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class ReadFile {

	private Scanner x;
	
	public void openFile(String name) {
		try {
			x=new Scanner(new File(name));
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		

	}
	
	public Set<Integer> readFile() {
		Set<Integer> favourites = new TreeSet<>();
		while(x.hasNext()) {
			Integer intObj = Integer.parseInt(x.next());
			favourites.add(intObj);
			
			
		}
		return favourites;
	}
	
	
	public List<Integer> readHistory(){
		List<Integer> history = new ArrayList<Integer>(30);
		while(x.hasNext()) {
			Integer intObj=Integer.parseInt(x.next());
			history.add(intObj);
		}		
		return history;
	}
	
	public void closeFile() {
		x.close();
	}
}
