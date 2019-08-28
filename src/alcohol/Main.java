package alcohol;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import alcohol.DBConnection;
import alcohol.MyLabel;

public class Main extends Application{

	Stage window;
	Scene welcome;
	int phase=0;
	String searchedText;
	GridPane grid = new GridPane();
	Button buttonSearch = new Button();
	Button buttonFavourites = new Button();
	Button buttonSettings = new Button();
	Button buttonHistory = new Button();
	Button buttonAbout = new Button();
	Button buttonReturn = new Button();
	Button buttonDeleteHistory = new Button();
	HBox searchBox = new HBox(8);
	int viewingFavs = 0;
	int checkboxCount=0;
	public Set<Integer> favouritesSet =new TreeSet<>();
	List<Integer> historyList = new ArrayList<Integer>(30);
	private int selectedId=-1;
	public static void main(String[] args) {
		launch(args);
	}
	ScrollPane scroll = new ScrollPane();
	int[] checkboxes = new int[300];
	int viewingHistory=0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Setting up the window
		window = primaryStage;
		
		
		
		scroll.setContent(grid);
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setFitToWidth(true);
		
		//Input
		TextField searchField = new TextField();
		searchField.setPromptText("Search a drink by name or choose the ingredients below");
		searchField.setPrefWidth(350);

		//Creating favourites set
		
		File tmpDir = new File("fav.ini");
		if(tmpDir.exists()) {
			//reading the current set
			ReadFile r = new ReadFile();
			r.openFile("fav.ini");
			favouritesSet = r.readFile();
			r.closeFile();
		}else {
			//creating a new set
			CreateFile g = new CreateFile();
			g.openFile("fav.ini");
			g.closeFile();
			favouritesSet =new TreeSet<>();
		}
		
		//Creating history list
		
		File historyFile = new File("history.ini");
		if(historyFile.exists()) {
			ReadFile s = new ReadFile();
			s.openFile("history.ini");
			historyList = s.readHistory();
			s.closeFile();
		}else {
			CreateFile g = new CreateFile();
			g.openFile("history.ini");
			g.closeFile();
		}
		
		
		
		
		
		
		//Saving the new favourites and history files on application close
		
		window.setOnCloseRequest(e->{
			CreateFile.saveFav(favouritesSet);
			CreateFile.saveHistory(historyList);
		}
		);
		

		
		//Ingredients menu with checkboxes
		TreeView<String> treeMenu;
		CheckBoxTreeItem<String> root, alcohols, juice, fruitsAndVegetables, others; //categories and root
		root= new CheckBoxTreeItem<String>("Drinkj ingredients");
		alcohols = Tools.createBranch("alcohols", root);
		juice = Tools.createBranch("juice", root);
		fruitsAndVegetables = Tools.createBranch("fruits and vegetables", root);
		others = Tools.createBranch("others", root);
		
		
		//Creating references for the ingredients
		
		//IMPORTANT! INGREDIENTS HAVE TO BE ADDED IN THE SAME ORDER AS THEIR IDs IN THE DATABASE
		CheckBoxTreeItem<String> rum, simpleSyrup, limeJuice, pineapple, cocoLopez, gingerBeer, limeWedge, vodka, dryVermouth, bitters, lemon, olives, whiskey, sweetVermouth, rosemary, lemonJuice, peach, ice;
		//Creating ingredients
		rum = Tools.createBranch("rum", alcohols);
		simpleSyrup = Tools.createBranch("simple syrup", others);
		limeJuice = Tools.createBranch("lime juice",juice);
		pineapple = Tools.createBranch("pineapple",fruitsAndVegetables);
		cocoLopez = Tools.createBranch("Coco Lopez",juice);
		gingerBeer = Tools.createBranch("ginger beer",alcohols);
		limeWedge = Tools.createBranch("lime wedge",fruitsAndVegetables);
		vodka = Tools.createBranch("vodka",alcohols);
		dryVermouth = Tools.createBranch("dry Vermouth",alcohols);
		bitters = Tools.createBranch("bitters",others);
		lemon = Tools.createBranch("lemon",fruitsAndVegetables);
		olives = Tools.createBranch("olives",fruitsAndVegetables);
		whiskey = Tools.createBranch("whiskey",alcohols);
		sweetVermouth = Tools.createBranch("sweet Vermouth",alcohols);
		rosemary = Tools.createBranch("rosemary",others);
		lemonJuice = Tools.createBranch("lemonJuice",juice);
		peach = Tools.createBranch("peach",fruitsAndVegetables);
		ice = Tools.createBranch("ice",others);
		//Creating id values for the ingredients
		
		
		
		treeMenu = new TreeView<>(root);
		treeMenu.setId("treeMenu");
		treeMenu.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
		//Search menu
		
		
		//Icons
		Image img_favourite = new Image(getClass().getResourceAsStream("/favourite.png"));
		Image img_history2 = new Image(getClass().getResourceAsStream("/history2.png"));
		Image img_info = new Image(getClass().getResourceAsStream("/info.png"));
		Image img_search = new Image(getClass().getResourceAsStream("/search.png"));
		Image img_settings = new Image(getClass().getResourceAsStream("/settings.png"));
		Image img_welcome = new Image(getClass().getResourceAsStream("/coctailBig.png"));
		Image img_return = new Image(getClass().getResourceAsStream("/return.png"));
		Image img_garbage = new Image(getClass().getResourceAsStream("/garbage.png"));
		ImageView bigIMG = new ImageView(img_welcome);
		
		buttonSearch.setGraphic(new ImageView(img_search));
		buttonFavourites.setGraphic(new ImageView(img_favourite));
		buttonSettings.setGraphic(new ImageView(img_settings));
		buttonHistory.setGraphic(new ImageView(img_history2));
		buttonAbout.setGraphic(new ImageView(img_info));
		buttonReturn.setGraphic(new ImageView(img_return));
		buttonDeleteHistory.setGraphic(new ImageView(img_garbage));


		
		//About button handling
		buttonAbout.setOnAction(e->{
			String aboutMessage = "Application made in Java \n Icons from www.icons8.com \n SQLite used for database management \n JavaFX functionality thanks to tutorials by thenewboston on YouTube \n Bonus thanks to the wonderful people on StackOverflow \n\n Made by Adrian Klessa (@AdrianKlessa on GitHub)";
			AboutBox.display("About", aboutMessage);
			
		});
		
		
			
		//Grid setup
		
		grid.setHgap(10);
		grid.setVgap(10);
		Insets myInset = new Insets(10,10,10,10);
		grid.setPadding(myInset);
		//Adding elements to grid
		GridPane.setConstraints(searchField,0,0);
		GridPane.setConstraints(buttonSearch,1,0);
		GridPane.setConstraints(buttonFavourites,2,0);
		GridPane.setConstraints(buttonSettings,4,0);
		GridPane.setConstraints(buttonHistory,3,0);
		GridPane.setConstraints(buttonAbout,5,0);
		GridPane.setConstraints(treeMenu,0,1,1,3);
		GridPane.setConstraints(bigIMG,1,1);
		Label test = new Label("Welcome to Alcohol! \n\nYou can search for a drink by its name or by selecting which ingredients you have available. \n\nKeep in mind that if the search box is empty, it will search only by the ingredients, but if there's any content inside it will search for drinks that have BOTH the name and ingredients provided.");
		
		test.setWrapText(true);
		grid.getChildren().addAll(searchField,buttonSearch,buttonFavourites,buttonSettings,buttonHistory,buttonAbout,bigIMG);
		grid.getChildren().add(treeMenu);
		grid.add(test,2,1,4,1);
		
		//aaaaa
		
		//return button
		buttonReturn.setOnAction(e->{
			if(phase==1) {
				grid.getChildren().clear();
				grid.getChildren().addAll(searchField,buttonSearch,buttonFavourites,buttonSettings,buttonHistory,buttonAbout,bigIMG);
				grid.getChildren().add(treeMenu);
				grid.add(test,2,1,4,1);
				searchBox.getChildren().clear();
				viewingFavs=0;
				phase=0;
				viewingHistory=0;
			}else{
				System.out.println("TESTING");
				grid.getChildren().clear();	
				searchBox.getChildren().clear();
				searchBox.getChildren().addAll(buttonReturn, buttonFavourites,buttonHistory, buttonAbout);
				GridPane.setConstraints(searchBox,0,0,4,1);
				grid.getChildren().add(searchBox);
				searchBox.setMinWidth(600);
				try {
					search(searchedText,checkboxes);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				phase=1;
			}
			
			
		});
		
		
		//Search handling
		searchField.setOnKeyPressed(e->{
			
			if(e.getCode()==KeyCode.ENTER) {
				searchedText=searchField.getText();
					getCheckboxValues(rum, simpleSyrup, limeJuice, pineapple, cocoLopez, gingerBeer, limeWedge, vodka, dryVermouth, bitters, lemon, olives, whiskey, sweetVermouth, rosemary, lemonJuice, peach, ice);
					grid.getChildren().remove(bigIMG);
					try {
						phase=1;
						grid.getChildren().removeAll(searchField,buttonSearch,buttonFavourites,buttonSettings,buttonHistory,buttonAbout,treeMenu,bigIMG,test);
						searchBox.getChildren().addAll(buttonReturn, buttonFavourites,buttonHistory, buttonAbout);
						GridPane.setConstraints(searchBox,0,0,4,1);
						grid.getChildren().add(searchBox);
						searchBox.setMinWidth(600);
						search(searchedText,checkboxes);
					} catch (Exception e1) {
						
						e1.printStackTrace();
					}
					
					
					
				
				
			}
			
		});
		
		buttonSearch.setOnAction(e->{
			searchedText=searchField.getText().trim();
			
				getCheckboxValues(rum, simpleSyrup, limeJuice, pineapple, cocoLopez, gingerBeer, limeWedge, vodka, dryVermouth, bitters, lemon, olives, whiskey, sweetVermouth, rosemary, lemonJuice, peach, ice);
				grid.getChildren().remove(bigIMG);
				try {
					phase=1;
					grid.getChildren().removeAll(searchField,buttonSearch,buttonFavourites,buttonSettings,buttonHistory,buttonAbout,treeMenu,bigIMG,test);
					searchBox.getChildren().addAll(buttonReturn, buttonAbout);
					GridPane.setConstraints(searchBox,0,0,4,1);
					grid.getChildren().add(searchBox);
					searchBox.setMinWidth(600);
					search(searchedText,checkboxes);
				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
			
			
		});
		
		
		buttonHistory.setOnAction(e->{
			viewingHistory=1;
			searchedText=searchField.getText().trim();
			
			getCheckboxValues(rum, simpleSyrup, limeJuice, pineapple, cocoLopez, gingerBeer, limeWedge, vodka, dryVermouth, bitters, lemon, olives, whiskey, sweetVermouth, rosemary, lemonJuice, peach, ice);
			grid.getChildren().remove(bigIMG);
			try {
				phase=1;
				grid.getChildren().removeAll(searchField,buttonSearch,buttonFavourites,buttonSettings,buttonHistory,buttonAbout,treeMenu,bigIMG,test);
				searchBox.getChildren().addAll(buttonReturn, buttonAbout);
				GridPane.setConstraints(searchBox,0,0,4,1);
				grid.getChildren().add(searchBox);
				searchBox.setMinWidth(600);
				search(searchedText,checkboxes);
			} catch (Exception e1) {
			
				e1.printStackTrace();
			}
			
		});
		
		buttonFavourites.setOnAction(e->{
			searchedText=searchField.getText();
			grid.getChildren().remove(bigIMG);
			grid.getChildren().removeAll(searchField,buttonSearch,buttonFavourites,buttonSettings,buttonHistory,buttonAbout,treeMenu,bigIMG,test);
			searchBox.getChildren().addAll(buttonReturn,buttonAbout);
			GridPane.setConstraints(searchBox,0,0,4,1);
			phase=1;
			grid.getChildren().add(searchBox);
			searchBox.setMinWidth(600);
			viewingFavs=1;
			try {
				search(searchedText,checkboxes);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		buttonSettings.setOnAction(e->{
			SettingsBox.display("Settings", img_garbage,historyList);
		});
		
		Image logo = new Image(getClass().getResourceAsStream("/coctail.png"));
		window.getIcons().add(logo);
		
		//Finalizing
		welcome = new Scene(scroll, 800,800);
		welcome.getStylesheets().add("style.css");
		window.setScene(welcome);
		window.setTitle("Alcohol");
		window.show();
	}	
	
	private void showDrink(int id) throws Exception {
		phase=2;
		grid.getChildren().clear();
		Button buttFav = new Button();
		Button buttBack = new Button();
		Image img_favourite = new Image(getClass().getResourceAsStream("/favourite.png"));
		Image img_return = new Image(getClass().getResourceAsStream("/return.png"));
		buttFav.setGraphic(new ImageView(img_favourite));
		buttBack.setGraphic(new ImageView(img_return));
		HBox upBox = new HBox(8);
		upBox.getChildren().addAll(buttBack,buttFav);
		GridPane.setConstraints(upBox,0,0,2,1);
		grid.getChildren().add(upBox);
		
		if(favouritesSet.contains(selectedId)) {
			buttFav.setStyle("-fx-background-color: #ff0000; ");
		}
		
		
		ResultSet rs;
		DBConnection db = new DBConnection();
		String myQuery;
		myQuery = "SELECT* FROM DRINK WHERE ID="+selectedId;
	rs=db.selectQuery(myQuery);
		
	

	
	//search for data from the drink table
		String name,desc;
		String image=null;
		
		
		name=rs.getString("name");
		desc=rs.getString("desc");
		Label labelDesc = new Label();
		Label labelName = new Label();
		labelDesc.setText(desc);
		labelName.setText(name);
		labelDesc.setWrapText(true);
		labelName.setFont(new Font("Arial",30));
		if(rs.getString("image2")!=null){
			image=rs.getString("image2");
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(image);
			BufferedImage newimg = ImageIO.read(new ByteArrayInputStream(imageBytes));
			Image almostThere = SwingFXUtils.toFXImage(newimg, null);
			MyImageView viewableImage = new MyImageView(almostThere,id);
			viewableImage.setFitHeight(150);
			viewableImage.setFitWidth(150);
			GridPane.setConstraints(viewableImage,0,2);
			GridPane.setConstraints(labelName,0,1,2,1);
			GridPane.setConstraints(labelDesc,0,3,2,1);
			grid.getChildren().addAll(viewableImage,labelDesc,labelName);
		}else {
			GridPane.setConstraints(labelName,0,1);
			GridPane.setConstraints(labelDesc,0,3,2,1);
			grid.getChildren().addAll(labelDesc,labelName);
		}
		
		//search for data from the ingredients table
			String ingredients="";
			Label labelIngredients = new Label();
			String ingAmount;
			String ingName;
			myQuery = "SELECT name, Amount  FROM Ingredient, DrinkIngredient WHERE DrinkIngredient.IDDrink="+Integer.toString(selectedId)+" AND DrinkIngredient.IDIngredient=Ingredient.ID";
			rs=db.selectQuery(myQuery);
			while(rs.next()) {
				ingAmount=rs.getString("Amount");
				ingName=rs.getString("name");
				ingredients+="• "+ingName+" x "+ingAmount+"\n";
			}
			labelIngredients.setText(ingredients);
			labelIngredients.setFont(new Font("Arial",15));
			if(image==null) {
				GridPane.setConstraints(labelIngredients,0,2,2,1);
			}else {
			GridPane.setConstraints(labelIngredients,1,2,2,1);
			}
			grid.getChildren().add(labelIngredients);
		//back button
		buttBack.setOnAction(e->{
			try {
				grid.getChildren().clear();
				grid.getChildren().add(searchBox);
				search(searchedText,checkboxes);
				phase--;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		//favourites button
		buttFav.setOnAction(e->{
			if(favouritesSet.contains(selectedId)) {
				favouritesSet.remove(selectedId);
				buttFav.setStyle("-fx-background-color: #F5F5F5; ");
			}else {
				favouritesSet.add(selectedId);
				buttFav.setStyle("-fx-background-color: #ff0000; ");
			}
		});
		db.closeDB();
	}
	

	private void getCheckboxValues(CheckBoxTreeItem<String>... checks) {
		int counter = 1;
		for (CheckBoxTreeItem<String> v:checks) {
			if (v.isSelected()==true) {
				checkboxes[counter]=1;
			}else {
				checkboxes[counter]=0;
			}
			counter++;
			checkboxCount++;
		}
		
	}
	
	private void cleanHistory() {
		historyList.clear();
	}
	
	
	private void search(String string, int [] checkboxes) throws Exception {
		ResultSet rs;
		DBConnection db = new DBConnection();
		
		String query1="SELECT * FROM DRINK";
		
		String lacking="";
		int ingCounter=0;
		int first=0;
		//Getting checkboxes that aren't checked
		while(ingCounter<=checkboxCount) {
			if(checkboxes[ingCounter+1]==0) {
				if(first==0) {
					first=1;
					lacking=Integer.toString(ingCounter+1);
				}else{
					lacking+=","+Integer.toString(ingCounter+1);
				}
			}
			
			
			ingCounter++;
		}
		//Setting up the queries
		if(first==0) {
			if(searchedText.isEmpty()) {
				query1="SELECT * FROM DRINK;";
			}else {
				query1="SELECT * FROM DRINK WHERE name LIKE '%"+searchedText+"%';";
			}
			
		}else {
			if(searchedText.isEmpty()) {
				query1="SELECT * FROM DRINK WHERE ID NOT IN(SELECT DISTINCT IDDrink FROM DrinkIngredient WHERE IDIngredient IN ("+lacking+"));";
			}else {
				query1="SELECT * FROM DRINK WHERE ID NOT IN(SELECT DISTINCT IDDrink FROM DrinkIngredient WHERE IDIngredient IN ("+lacking+")) AND name LIKE '%"+searchedText+"%';";
			}
		}
		String favsString="";
		ingCounter=0;
		first=0;
		
		if(viewingFavs==1) {
			for(Integer current: favouritesSet) {
				if(first==0) {
					favsString=Integer.toString(current);
					first=1;
				}else {
					favsString+=", "+Integer.toString(current);
				}
				
				
			}
			query1="SELECT * FROM DRINK WHERE ID IN ("+favsString+");";
		}
		if(viewingHistory==1) {
			String histString="";
			
			
			for (int i = historyList.size(); i-- > 0; ) {
				if(first==0) {
					histString=Integer.toString(historyList.get(i));
					first=1;
				}else {
					histString+=", "+Integer.toString(historyList.get(i));
				}
			    
			}
			
			query1="SELECT * FROM DRINK WHERE ID IN ("+histString+");";
		}
		
		//History branch of search
		if(viewingHistory==1) {
			int counter=1;
			Label label1,label2,label3;
			int id;
			String name, desc;
			String image;
			for (int i = historyList.size(); i-- > 0; ) {
				query1="SELECT * FROM DRINK WHERE ID="+historyList.get(i)+";";
				rs=db.selectQuery(query1);
				id=rs.getInt("ID");
				name=rs.getString("name");
				desc=rs.getString("desc");
				if(rs.getString("image2")!=null){
					image=rs.getString("image2");
					
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(image);
					BufferedImage newimg = ImageIO.read(new ByteArrayInputStream(imageBytes));
					Image almostThere = SwingFXUtils.toFXImage(newimg, null);
					MyImageView viewableImage = new MyImageView(almostThere,id);
					GridPane.setConstraints(viewableImage,3,counter);
					viewableImage.setFitHeight(150);
					viewableImage.setFitWidth(150);
					viewableImage.setOnMouseClicked(e->
					{
						Object source = e.getSource();
						selectedId=((MyImageView)source).get_drink_id();
						try {
							showDrink(selectedId);
							historyList.add(selectedId);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					});
					grid.getChildren().add(viewableImage);
				}
				label1 = new MyLabel(Integer.toString(id),id);
				label2 = new MyLabel(name,id);
				label3 = new MyLabel(desc,id);		
				
	
				
				label1.setOnMouseClicked(e ->
				{
					
					Object source = e.getSource();
					selectedId=((MyLabel)source).get_drink_id();
					try {
						showDrink(selectedId);
						historyList.add(selectedId);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
						
				);
				label2.setOnMouseClicked(e ->
				{
					Object source = e.getSource();
					selectedId=((MyLabel)source).get_drink_id();
					try {
						showDrink(selectedId);
						historyList.add(selectedId);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
						
				);
				label3.setOnMouseClicked(e ->
				{
					Object source = e.getSource();
					selectedId=((MyLabel)source).get_drink_id();
					try {
						showDrink(selectedId);
						historyList.add(selectedId);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
						
				);
				
				label1.setWrapText(true);
				label2.setWrapText(true);
				label3.setWrapText(true);
				label2.setMinWidth(200);
				label3.setMinWidth(350);
				GridPane.setConstraints(label1,0,counter);
				GridPane.setConstraints(label2,1,counter);
				GridPane.setConstraints(label3,2,counter);
				counter++;
				grid.getChildren().addAll(label1,label2,label3);
			}
			if(counter==1) {
				label1 = new Label("Nothing found :(");
				label1.setFont(new Font("Arial", 24));
				GridPane.setConstraints(label1,0,1,4,1);
				grid.getChildren().add(label1);
			}
		}else {
			
			//Normal search (non-history)
			rs=db.selectQuery(query1);
			int id;
			String name, desc;
			String image;
			int counter=1;
			
			Label label1,label2,label3;
			
			while(rs.next()) {
				id=rs.getInt("ID");
				name=rs.getString("name");
				desc=rs.getString("desc");
				if(rs.getString("image2")!=null){
					image=rs.getString("image2");
					
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(image);
					BufferedImage newimg = ImageIO.read(new ByteArrayInputStream(imageBytes));
					Image almostThere = SwingFXUtils.toFXImage(newimg, null);
					MyImageView viewableImage = new MyImageView(almostThere,id);
					GridPane.setConstraints(viewableImage,3,counter);
					viewableImage.setFitHeight(150);
					viewableImage.setFitWidth(150);
					viewableImage.setOnMouseClicked(e->
					{
						Object source = e.getSource();
						selectedId=((MyImageView)source).get_drink_id();
						try {
							showDrink(selectedId);
							historyList.add(selectedId);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					});
					grid.getChildren().add(viewableImage);
				}
				label1 = new MyLabel(Integer.toString(id),id);
				label2 = new MyLabel(name,id);
				label3 = new MyLabel(desc,id);		
				
	
				
				label1.setOnMouseClicked(e ->
				{
					
					Object source = e.getSource();
					selectedId=((MyLabel)source).get_drink_id();
					try {
						showDrink(selectedId);
						historyList.add(selectedId);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
						
				);
				label2.setOnMouseClicked(e ->
				{
					Object source = e.getSource();
					selectedId=((MyLabel)source).get_drink_id();
					try {
						showDrink(selectedId);
						historyList.add(selectedId);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
						
				);
				label3.setOnMouseClicked(e ->
				{
					Object source = e.getSource();
					selectedId=((MyLabel)source).get_drink_id();
					try {
						showDrink(selectedId);
						historyList.add(selectedId);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
						
				);
				
				label1.setWrapText(true);
				label2.setWrapText(true);
				label3.setWrapText(true);
				label2.setMinWidth(200);
				label3.setMinWidth(350);
				GridPane.setConstraints(label1,0,counter);
				GridPane.setConstraints(label2,1,counter);
				GridPane.setConstraints(label3,2,counter);
				counter++;
				grid.getChildren().addAll(label1,label2,label3);
			}
			if(counter==1) {
				label1 = new Label("Nothing found :(");
				label1.setFont(new Font("Arial", 24));
				GridPane.setConstraints(label1,0,1,4,1);
				grid.getChildren().add(label1);
			}
		}
		db.closeDB();	
	}

} 
