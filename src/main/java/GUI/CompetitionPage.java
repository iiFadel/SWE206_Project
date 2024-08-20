package GUI;

import Application.Controller;
import database.Competition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ButtonStyler;

import java.io.IOException;

//This Class for Competition Page ***//
public class CompetitionPage {
	// Scene dimensions
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 720;
	// Paths
	protected final String BACKGROUND_PATH = "GUI/resources/competitionsPageBG.png";
	protected final String FONT_PATH = "model/resources/Roboto-Regular.ttf";

	// CSS Styling
	protected final String SIDEBUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/competitionPageButton.png')";
	protected final String SIDEBUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/competitionPageButton.png')";
	protected final String ADD_COMPETITION_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButton.png')";
	protected final String ADD_COMPETITION_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButtonPressed.png')";
	protected final String CANCEL_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/canelButtonFree.png')";
	protected final String CANCEL_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/canelButtonPressed.png')";
	protected final String CREATE_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonFree.png')";
	protected final String CREATE_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonPressed.png')";

	// Stage, Panes and Scenes
	protected static AnchorPane competitionPane;
	protected static Scene competitionScene;
	protected static Stage competitionStage;
	protected static HomePage homePage;
	protected static BrowserPage browserPage;
	protected static AddNewCompetition addNewCompetitionPage;

	// Add Competition SubScene
	protected static TableView<Competition> tableView;

	public CompetitionPage() {
		competitionPane = new AnchorPane();
		competitionScene = new Scene(competitionPane, WIDTH, HEIGHT);
		competitionStage = new Stage();
		competitionStage.setScene(competitionScene);

		competitionScene.getStylesheets().add(getClass().getResource("competition.css").toExternalForm());
		createBackground();
		try {
			Controller.generateDatabase();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		creatButtons();
		createCompetitionTableView();
		competitionStage.setOnCloseRequest(e -> {

		});

	}

	// Set Background
	public void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		competitionPane.setBackground(new Background(background));
	}

	// Methods to Create Side Buttons
	public void creatButtons() {
		createHomePageButton("", 183, 47, 0, 0, 147, SIDEBUTTON_PRESSED, SIDEBUTTON_FREE);
		createBrowserPageButton("", 183, 47, 0, 0, 239, SIDEBUTTON_PRESSED, SIDEBUTTON_FREE);
		createAddCompetitionButton("", 32, 33, 0, 1029, 72, ADD_COMPETITION_BUTTON_PRESSED,
				ADD_COMPETITION_BUTTON_FREE);

	}

	public void createHomePageButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX,
			double LayoutY, String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		competitionPane.getChildren().add(button);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		button.setOnAction(e -> {
			HomePage.update();
			HomePage.switchScene(Controller.showHome());
		});

	}

	public void createBrowserPageButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX,
			double LayoutY, String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		competitionPane.getChildren().add(button);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		setBrowserPage(new BrowserPage());
		button.setOnAction(e -> {
			BrowserPage.switchScene(Controller.showWebsite());
		});

	}

	// Method for Add New Competition Button
	public void createAddCompetitionButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX,
			double LayoutY, String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		competitionPane.getChildren().add(button);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		setAddNewCompetitionPage(new AddNewCompetition());
		button.setOnAction(e -> {
			AddNewCompetition.switchScene(AddNewCompetition.getScene());

		});
	}

	// Method for Label Styling
	private Text createLabel(String text, double fontSize, double layoutX, double layoutY) {
		Text label = new Text(text);
		label.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, fontSize));
		label.setFill(Color.web("#ffffff"));
		label.setLayoutX(layoutX);
		label.setLayoutY(layoutY);
		return label;
	}

	

	public static void update() {
		createCompetitionTableView();
	}

	// Table view
	public static ObservableList<Competition> getCompetitions() {
		ObservableList<Competition> competitionsOb = FXCollections.observableArrayList();
		for (int i = 0; i < Controller.competitions.size(); i++) {
			competitionsOb.add(Controller.competitions.get(i));
		}
		return competitionsOb;

	}

	@SuppressWarnings("unchecked")
	public static void createCompetitionTableView() {
		TableColumn<Competition, String> competitionNameColumn = new TableColumn<>("Competition Name");
		competitionNameColumn.setMinWidth(253);
		competitionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Competition, Integer> competitorsNumberColumn = new TableColumn<>("Competitor(s)");
		competitorsNumberColumn.setMinWidth(156);
		competitorsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("participantsNumber"));

		TableColumn<Competition, String> topCompetitorColumn = new TableColumn<>("Highest Rank");
		topCompetitorColumn.setMinWidth(170);
		topCompetitorColumn.setCellValueFactory(new PropertyValueFactory<>("highestParticipantName"));

		TableColumn<Competition, String> dueDateColumn = new TableColumn<>("Due Date");
		dueDateColumn.setMinWidth(170);
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDateFormated"));

		TableColumn<Competition, ImageView> statusColumn = new TableColumn<>("Status");
		statusColumn.setMinWidth(60);
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("statusImage"));

		TableColumn<Competition, ButtonStyler> detailsColumn = new TableColumn<>(" ");
		detailsColumn.setMinWidth(16);
		detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));

		tableView = new TableView<>();
		tableView.setItems(getCompetitions());
		tableView.getColumns().addAll(competitionNameColumn, competitorsNumberColumn, topCompetitorColumn,
				dueDateColumn, statusColumn, detailsColumn);
		tableView.setMinWidth(847);
		tableView.setMaxWidth(847);
		tableView.setMinHeight(563);
		tableView.setMaxHeight(563);
		tableView.setFixedCellSize(70);
		tableView.setLayoutX(214);
		tableView.setLayoutY(116);
		competitionPane.getChildren().add(tableView);
	}

	public static AddNewCompetition getAddNewCompetitionPage() {
		return addNewCompetitionPage;
	}

	public static void setAddNewCompetitionPage(AddNewCompetition addNewCompetitionPage) {
		CompetitionPage.addNewCompetitionPage = addNewCompetitionPage;
	}

	// Getters and Setters
	public static HomePage getHomePage() {
		return homePage;
	}

	public static void setHomePage(HomePage homePage) {
		CompetitionPage.homePage = homePage;
	}

	public static BrowserPage getBrowserPage() {
		return browserPage;
	}

	public static void setBrowserPage(BrowserPage browserPage) {
		CompetitionPage.browserPage = browserPage;
	}

	public static Scene getScene() {
		return competitionScene;
	}

	public static AnchorPane getPane() {
		return competitionPane;
	}

	// Method to Switch between Scenes
	public static void switchScene(Scene scene) {
		WelcomePage.getMainStage().setScene(scene);
	}

	

}
