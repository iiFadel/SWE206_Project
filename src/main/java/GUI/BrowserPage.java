package GUI;

import Application.Controller;
import database.Competition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.ButtonStyler;
import model.ChoiceBoxStyler;
import model.TextFieldStyler;

//*** This Class for Browser Page ***//
public class BrowserPage {

	// Scene dimensions
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 720;

	// Paths
	protected final String BACKGROUND_PATH = "GUI/resources/browserPageBG.png";

	// CSS Styling
	protected final String BLUEBUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/BlueButtonPressed.png')";
	protected final String BLUEBUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/BlueButton.png')";
	protected final String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/competitionPageButton.png')";
	protected final String BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/competitionPageButton.png')";
	protected final String BACK_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/backButtonPressed.png')";
	protected final String BACK_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/backButton.png')";
	protected final String FORWARD_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/forwardButtonPressed.png')";
	protected final String FORWARD_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/forwardButton.png')";
	protected final String HOME_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/homeButtonPressed.png')";
	protected final String HOME_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/homeButton.png')";
	protected final String ZOOMIN_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/zoomInButtonPressed.png')";
	protected final String ZOOMIN_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/zoomInButton.png')";
	protected final String ZOOMOUT_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/zoomOutButtonPressed.png')";
	protected final String ZOOMOUT_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/zoomOutButton.png')";
	protected final String RELOAD_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/reloadButtonPressed.png')";
	protected final String RELOAD_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/reloadButton.png')";

	// Stage, Panes and Scenes
	protected static AnchorPane browserPane;
	protected static Scene browserScene;
	protected static Stage browserStage;
	protected static HomePage homePage;
	protected static TextFieldStyler urlTextField;
	protected ButtonStyler backButton, forwardButton, reloadButton, homeButton, zoomInButton, zoomOutButton;
	protected static WebView browser;
	protected static WebEngine engine;
	protected static WebHistory history;
	protected ChoiceBoxStyler choiceBox;
	private double webZoom;

	public BrowserPage() {
		browserPane = new AnchorPane();
		browserScene = new Scene(browserPane, WIDTH, HEIGHT);
		browserStage = new Stage();
		browserStage.setScene(browserScene);
		browserStage.setOnCloseRequest(e -> browserStage.close());
		browserScene.getStylesheets().add(getClass().getResource("competitionDetail.css").toExternalForm());
		urlTextField = new TextFieldStyler("Enter website", 509, 41, 387, 122);
		urlTextField.setAlignment(Pos.CENTER);
		urlTextField.setStyle("-fx-control-inner-background: #20293c;" + " -fx-text-box-border: #20293c;"
				+ " -fx-focus-color: #15589a;" + " -fx-prompt-text-fill:#869aac;" + " -fx-text-fill: white;");

		webZoom = 1;
		createBackground();
		creatButtons();
		openWebsite("https://www.google.com");
		createBrowserButton();
		createWebsitesChoiceBox();

		browserPane.getChildren().add(urlTextField);
	}

	// Set Background
	public void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		browserPane.setBackground(new Background(background));
	}

	public static void openWebsite(String website) {
		browser = new WebView();
		engine = browser.getEngine();
		engine.setUserAgent(website);
		engine.load(website);
		browser.setPrefHeight(462);
		browser.setPrefWidth(822);
		browser.setLayoutX(226);
		browser.setLayoutY(169);
		urlTextField.setText(website);
		urlTextField.setDisable(true);
		browserPane.getChildren().add(browser);

	}

	// Methods to Create Side Buttons
	public void creatButtons() {
		createHomePageButton("", 183, 47, 0, 0, 147, BUTTON_PRESSED, BUTTON_FREE);
		createCompetitionPageButton("", 183, 47, 0, 0, 193, BUTTON_PRESSED, BUTTON_FREE);
		createOpenButton("OPEN WEBSITE", 133, 33, 12, 569, 658, BLUEBUTTON_PRESSED, BLUEBUTTON_FREE);

	}

	public void createCompetitionPageButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX,
			double LayoutY, String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		browserPane.getChildren().add(button);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		button.setOnAction(e -> {
			CompetitionPage.switchScene(Controller.showCompetitions());
			openWebsite("https://www.google.com");
			choiceBox.setValue(null);

		});

	}

	public void createHomePageButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX,
			double LayoutY, String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		browserPane.getChildren().add(button);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		button.setOnAction(e -> {
			HomePage.switchScene(Controller.showHome());
			openWebsite("https://www.google.com");
			choiceBox.setValue(null);
		});

	}

	public void createBrowserButton() {
		backButton = new ButtonStyler("", 37, 25, 0, BACK_BUTTON_PRESSED, BACK_BUTTON_FREE);
		forwardButton = new ButtonStyler("", 37, 25, 0, FORWARD_BUTTON_PRESSED, FORWARD_BUTTON_FREE);
		reloadButton = new ButtonStyler("", 27, 27, 0, RELOAD_BUTTON_PRESSED, RELOAD_BUTTON_FREE);
		homeButton = new ButtonStyler("", 24, 24, 0, HOME_BUTTON_PRESSED, HOME_BUTTON_FREE);
		zoomInButton = new ButtonStyler("", 30, 33, 0, ZOOMIN_BUTTON_PRESSED, ZOOMIN_BUTTON_FREE);
		zoomOutButton = new ButtonStyler("", 30, 30, 0, ZOOMOUT_BUTTON_PRESSED, ZOOMOUT_BUTTON_FREE);

		backButton.setLayoutX(244);
		forwardButton.setLayoutX(294);
		reloadButton.setLayoutX(346);
		homeButton.setLayoutX(907);
		zoomOutButton.setLayoutX(945);
		zoomInButton.setLayoutX(987);

		backButton.setLayoutY(130);
		forwardButton.setLayoutY(130);
		reloadButton.setLayoutY(130);
		homeButton.setLayoutY(128);
		zoomOutButton.setLayoutY(126);
		zoomInButton.setLayoutY(126);

		backButton.setOnAction(e -> {
			history = engine.getHistory();
			ObservableList<WebHistory.Entry> entries = history.getEntries();
			if (entries.size() != 0) {
				history.go(-1);
			}
		});

		forwardButton.setOnAction(e -> {
			history = engine.getHistory();
			ObservableList<WebHistory.Entry> entries = history.getEntries();
			if (entries.size() != 0) {
				history.go(1);
			}
		});

		reloadButton.setOnAction(e -> {
			engine.reload();
		});

		homeButton.setOnAction(e -> {
			openWebsite("https://www.google.com");
		});

		zoomInButton.setOnAction(e -> {
			webZoom -= 0.25;
			browser.setZoom(webZoom);
		});

		zoomOutButton.setOnAction(e -> {
			webZoom += 0.25;
			browser.setZoom(webZoom);
		});

		browserPane.getChildren().addAll(backButton, forwardButton, reloadButton, homeButton, zoomInButton,
				zoomOutButton);

	}

	public void createWebsitesChoiceBox() {
		Competition[] competitions = Controller.database.getCompetitions();
		choiceBox = new ChoiceBoxStyler(competitions, 327, 32, 214, 659);
		browserPane.getChildren().add(choiceBox);
	}

	public void createOpenButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX, double LayoutY,
			String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		browserPane.getChildren().add(button);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		button.setOnAction(e -> {
			if (choiceBox.getValue() == null) {

			} else {
				openWebsite(((Competition) choiceBox.getValue()).getWebsite());

			}
		});

	}

	// Method to Switch between Scenes
	public static void switchScene(Scene scene) {
		WelcomePage.getMainStage().setScene(scene);
	}

	// Getters and Setter
	public static HomePage getHomePage() {
		return homePage;
	}

	public static void setHomePage(HomePage homePage) {
		BrowserPage.homePage = homePage;
	}

	public static Scene getScene() {
		return browserScene;
	}
}
