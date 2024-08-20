package GUI;

import java.io.IOException;
import java.util.ArrayList;

import Application.Controller;
import database.Competition;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
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
import javafx.stage.WindowEvent;
import model.AlertSaveBox;
import model.ButtonStyler;

//*** This Class for Welcome Page ***//
public class WelcomePage {
	// Scene dimensions
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 720;
	// Paths
	protected final String BACKGROUND_PATH = "GUI/resources/WelcomePageBG.jpg";
	protected final String BOLDFONT_PATH = "src/model/resources/Roboto-Bold.ttf";
	protected final String LIGHTFONT_PATH = "src/model/resources/Roboto-Light.ttf";

	// CSS Styling
	protected final String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/BlueButtonPressed.png')";
	protected final String BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/BlueButton.png')";

	// Stage, Panes and Scenes
	protected static Stage mainStage;
	protected static AnchorPane mainPane;
	protected static Scene mainScene;
	protected static HomePage homePage = new HomePage();
	protected static ArrayList<Competition> competitions = Controller.database.getCompetitionsArrayList();
	public static ArrayList<Competition> competitionsOb;

	public WelcomePage() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		mainStage.setOnCloseRequest(e -> closeProgram(e));
		createButtons();
		createBackground();
		addTextLight("Welcome", LIGHTFONT_PATH, "#ffffff", 50, 34, 337);
		addTextBold("Competition Tracker", BOLDFONT_PATH, "#ffffff", 70, 34, 404);

	}

	// Set Background
	public void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}

	// Methods to Create Side Buttons
	public void createButtons() {
		createHomeButton("HOME", 262, 56, 20, 735, 304, BUTTON_PRESSED, BUTTON_FREE);
		createExitButton("EXIT", 262, 56, 20, 735, 408, BUTTON_PRESSED, BUTTON_FREE);

	}

	public void createHomeButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX, double LayoutY,
			String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		mainPane.getChildren().add(button);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		button.setOnAction(e -> {
			switchScene(Controller.showHome());
            Controller.checkStatus();

		});

	}

	public static ArrayList<Competition> getUnupdatedWinners() {
		competitionsOb = new ArrayList<>();
		for (int i = 0; i < competitions.size(); i++) {
			for (int j = 0; j < competitions.get(i).getParticipants().length; j++) {
				if (competitions.get(i).getParticipants()[j].getRank() == -1
						&& competitions.get(i).isActive() == false) {
					competitionsOb.add(competitions.get(i));
					break;
				}
			}
		}
		return competitionsOb;
	}

	public void createExitButton(String text, double WIDTH, double HEIGHT, double font, double LayoutX, double LayoutY,
			String BUTTON_PRESSED, String BUTTON_FREE) {
		ButtonStyler button = new ButtonStyler(text, WIDTH, HEIGHT, font, BUTTON_PRESSED, BUTTON_FREE);
		mainPane.getChildren().add(button);
		button.setLayoutX(LayoutX);
		button.setLayoutY(LayoutY);
		button.setOnAction(e -> mainStage.close());
		button.setOnMouseEntered(e -> {
			button.setEffect(new DropShadow());
		});

		button.setOnMouseExited(e -> {
			button.setEffect(null);
		});

	}

	// Method to Switch between Scenes
	public void switchScene(Scene scene) {
		mainStage.setScene(scene);
	}

	// Methods for Text Styling
	public void addTextBold(String text, String font, String color, double size, double LayoutX, double LayoutY) {
		Text myText = new Text();
		myText.setText(text);
		myText.setFont(Font.font(font, FontWeight.BOLD, size));
		myText.setFill(Color.web(color));
		myText.setLayoutX(LayoutX);
		myText.setLayoutY(LayoutY);
		mainPane.getChildren().add(myText);

	}

	public void addTextLight(String text, String font, String color, double size, double LayoutX, double LayoutY) {
		Text myText = new Text();
		myText.setText(text);
		myText.setFont(Font.font(font, FontWeight.EXTRA_LIGHT, size));
		myText.setFill(Color.web(color));
		myText.setLayoutX(LayoutX);
		myText.setLayoutY(LayoutY);
		mainPane.getChildren().add(myText);

	}

	public void closeProgram(WindowEvent event) {
		int confirmation = AlertSaveBox.display();
		if (confirmation == 1) {
			try {
				Controller.database.updateExcel();
				mainStage.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (confirmation == 0) {
			event.consume();
		} else {
			mainStage.close();
		}
	}

	// Getters and Setters
	public HomePage getHomePage() {
		return homePage;
	}

	public void setHomePage(HomePage homePage) {
		this.homePage = homePage;
	}

	public Scene getScene() {
		return mainScene;
	}

	public static AnchorPane getPane() {
		return mainPane;
	}

	public static Stage getMainStage() {
		return mainStage;
	}
}