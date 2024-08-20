package GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Application.Controller;
import database.Competition;
import database.Database;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ButtonStyler;
import model.SummaryCard;

// This Class for Home Page ***//
public class HomePage {
	// Scene dimensions
	private static final int WIDTH = 1080;
	private static final int HEIGHT = 720;

	// Paths
	private final String BACKGROUND_PATH = "GUI/resources/homePageBG.png";

	// CSS Styling
	private final String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/competitionPageButton.png')";
	private final String BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/competitionPageButton.png')";

	// Stage, Panes and Scenes
	private static AnchorPane homePane;
	private static Scene homeScene;
	private static Stage homeStage;
	private static CompetitionPage competitionPage;
	private static BrowserPage browserPage;

	protected static File inf;
	protected static Database database;
	protected static ArrayList<Competition> competitions;
	protected static GridPane grid;

	public HomePage() {
		homePane = new AnchorPane();
		homeScene = new Scene(homePane, WIDTH, HEIGHT);
		homeStage = new Stage();
		homeStage.setScene(homeScene);
		createBackground();
		creatButtons();
		try {
			generateDatabase();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		createGridPane();
		homeScene.getStylesheets().add(getClass().getResource("homePage.css").toExternalForm());

	}

	public static void generateDatabase() throws IOException {
		competitions = Controller.database.getCompetitionsArrayList();
	}

	public static void update() {
		createGridPane();
	}

	public static void createGridPane() {
		int column = 0;
		int row = 0;
		grid = new GridPane();
		grid.setPrefHeight(400);
		grid.setPrefWidth(776);
		grid.setPadding(new Insets(20));
		grid.setHgap(20);
		grid.setVgap(20);
		for (int i = 0; i < competitions.size(); i++) {
			SummaryCard card = new SummaryCard(competitions.get(i).getName(), competitions.get(i).isActive(),
					competitions.get(i).getHighestParticipantName(), competitions.get(i).getParticipantsNumber());
			if (column == 2) {
				column = 0;
				row++;
			}
			grid.add(card, column++, row);

		}
		ScrollPane scrollPane = new ScrollPane(grid);
		scrollPane.setContent(grid);
		scrollPane.setFitToHeight(true);
		scrollPane.setPannable(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setPrefHeight(400);
		scrollPane.setPrefWidth(782);
		scrollPane.setLayoutX(216);
		scrollPane.setLayoutY(115);
		grid.setStyle("-fx-background-color: #212a3d;");
		scrollPane.setStyle("-fx-background-color: #212a3d;");
		homePane.getChildren().addAll(scrollPane);

	}

//203 115
	// Set Background
	public void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		homePane.setBackground(new Background(background));
	}

	// Methods to Create Side Buttons
	public void creatButtons() {

		createCompetitionPageButton("", 183, 47, 0, 0, 193, BUTTON_PRESSED, BUTTON_FREE);
		createBrowserPageButton("", 183, 47, 0, 0, 239, BUTTON_PRESSED, BUTTON_FREE);
	}

	public void createCompetitionPageButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX,
			double LayoutY, String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		setCompetitionPage(new CompetitionPage());
		homePane.getChildren().add(button);
		button.setOnAction(e -> {
			CompetitionPage.switchScene(Controller.showCompetitions());
		});

	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public void createBrowserPageButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX,
			double LayoutY, String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		homePane.getChildren().add(button);
		button.setOnAction(e -> {
			Controller.showWebsite();
            //BrowserPage.switchScene(BrowserPage.getScene());
		});

	}

	// Method to Switch between Scenes
	public static void switchScene(Scene scene) {
		WelcomePage.getMainStage().setScene(scene);
	}

	// Getters and Setter
	public static BrowserPage getBrowserPage() {
		return browserPage;
	}

	public static void setBrowserPage(BrowserPage browserPage) {
		HomePage.browserPage = browserPage;
	}

	public static CompetitionPage getCompetitionPage() {
		return competitionPage;
	}

	public static void setCompetitionPage(CompetitionPage competitionPage) {
		HomePage.competitionPage = competitionPage;
	}

	public static Scene getScene() {
		return homeScene;
	}
}
