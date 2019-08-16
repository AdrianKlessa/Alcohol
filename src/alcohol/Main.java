package alcohol;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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

	Stage window, options, favourites;
	Scene welcome,selection;
	int phase=0;
	String searchedText;
	GridPane grid = new GridPane();
	Button buttonSearch = new Button();
	Button buttonFavourites = new Button();
	Button buttonSettings = new Button();
	Button buttonHistory = new Button();
	Button buttonAbout = new Button();
	Button buttonReturn = new Button();
	HBox searchBox = new HBox(8);
	public Set<Integer> favouritesSet =new TreeSet<>();
	private int selectedId=-1;
	public static void main(String[] args) {
		launch(args);
	}
	ScrollPane scroll = new ScrollPane();
	int[] checkboxes = new int[300];

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
		//printing out the current set
		for(Integer id:favouritesSet) {
			System.out.println(id);
		};
		
		
		
		//Saving the new favourites file on application close
		
		window.setOnCloseRequest(e->{
			CreateFile.saveFav(favouritesSet);
		}
		);
		

		//Input foldable ingredients menu
		TreeView<String> treeMenu; //whole tree
		TreeItem<String> root, juice, liquors, others; //main items and root
		root = new TreeItem<>("Drink ingredients");
		root.setExpanded(false);
		juice = Tools.createBranch("juice",root);
		liquors = Tools.createBranch("liquors",root);
		others = Tools.createBranch("others",root);
		TreeItem<String> orange,lemon,coconutMilk,whiskey,redWine,whiteWine,rum,water; //subitems
		orange = Tools.createBranch("orange",juice);
		lemon = Tools.createBranch("lemon",juice);
		coconutMilk = Tools.createBranch("coconut milk",juice);
		
		
		whiskey = Tools.createBranch("whiskey",liquors);
		redWine = Tools.createBranch("red wine",liquors);
		whiteWine = Tools.createBranch("white wine",liquors);
		
		
		water = Tools.createBranch("water", others);
		

		
		
		
		treeMenu= new TreeView<>(root);
		treeMenu.setId("treeMenu");
		//Search menu
		
		
		//Icons
		Image img_favourite = new Image(getClass().getResourceAsStream("/favourite.png"));
		Image img_history2 = new Image(getClass().getResourceAsStream("/history2.png"));
		Image img_info = new Image(getClass().getResourceAsStream("/info.png"));
		Image img_search = new Image(getClass().getResourceAsStream("/search.png"));
		Image img_settings = new Image(getClass().getResourceAsStream("/settings.png"));
		Image img_welcome = new Image(getClass().getResourceAsStream("/coctailBig.png"));
		Image img_return = new Image(getClass().getResourceAsStream("/return.png"));
		ImageView bigIMG = new ImageView(img_welcome);
		
		buttonSearch.setGraphic(new ImageView(img_search));
		buttonFavourites.setGraphic(new ImageView(img_favourite));
		buttonSettings.setGraphic(new ImageView(img_settings));
		buttonHistory.setGraphic(new ImageView(img_history2));
		buttonAbout.setGraphic(new ImageView(img_info));
		buttonReturn.setGraphic(new ImageView(img_return));
		


		
		//About button handling
		buttonAbout.setOnAction(e->{
			String aboutMessage = "Application made in Java \n Icons from www.icons8.com \n SQLite used for database management \n JavaFX functionality thanks to tutorials by thenewboston on YouTube \n Bonus thanks to the wonderful people on StackOverflow";
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
		Label test = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris imperdiet diam vel fringilla facilisis. Aenean sit amet turpis consectetur, lobortis velit vel, scelerisque tortor. Ut est quam, dictum sed venenatis sit amet, auctor eget dui. Sed id nisi a est congue faucibus. Maecenas ac mi rutrum sem dictum commodo. Duis fringilla placerat egestas. Fusce in eros eget libero elementum pharetra malesuada sit amet nulla. Nullam congue, ante eget maximus sagittis, ex augue consequat justo, at blandit elit augue at neque. Pellentesque ac felis ante. Donec eu pretium nisl. ");
		
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
				phase=0;
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				phase=1;
			}
			
			
		});
		
		
		//Search handling
		searchField.setOnKeyPressed(e->{
			
			if(e.getCode()==KeyCode.ENTER) {
				searchedText=searchField.getText();
				if(!searchedText.isEmpty()) {
					System.out.println("Searched: "+searchedText);
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
				
			}
			
		});
		
		buttonSearch.setOnAction(e->{
			String searchedText=searchField.getText().trim();
			if(!searchedText.isEmpty()) {
				System.out.println("Searched: "+searchedText);
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
		
		ResultSet rs;
		DBConnection db = new DBConnection();
		String myQuery;
		myQuery = "SELECT* FROM DRINK WHERE ID="+selectedId;
	rs=db.selectQuery(myQuery);
		
	//back button
	buttBack.setOnAction(e->{
		System.out.println("TEST"); //TODO
		try {
			grid.getChildren().clear();
			grid.getChildren().add(searchBox);
			search(searchedText,checkboxes);
			phase--;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	});
	
	
	
	
	//search
		String name,desc;
		String image;
		String ingredients;
		
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
			GridPane.setConstraints(labelName,0,1);
			GridPane.setConstraints(labelDesc,0,3	,2,1);
			grid.getChildren().addAll(viewableImage,labelDesc,labelName);
		}else {
			GridPane.setConstraints(labelName,0,1);
			GridPane.setConstraints(labelDesc,0,2,2,1);
			grid.getChildren().addAll(labelDesc,labelName);
		}
	}
	

	
	
	private void search(String string, int [] checkboxes) throws Exception {
		ResultSet rs;
		DBConnection db = new DBConnection();
		rs=db.selectQuery("SELECT * FROM DRINK");
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
					System.out.println("Selected: "+selectedId);
					try {
						showDrink(selectedId);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
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
				System.out.println("Selected: "+selectedId);
				try {
					showDrink(selectedId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
					
			);
			label2.setOnMouseClicked(e ->
			{
				Object source = e.getSource();
				selectedId=((MyLabel)source).get_drink_id();
				System.out.println("Selected: "+selectedId);
				try {
					showDrink(selectedId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
					
			);
			label3.setOnMouseClicked(e ->
			{
				Object source = e.getSource();
				selectedId=((MyLabel)source).get_drink_id();
				System.out.println("Selected: "+selectedId);
				try {
					showDrink(selectedId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
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
		
		db.closeDB();	
		
		
		System.out.println("Searched sth");
	}

} 
