package GUI;

import java.time.ZoneId;
import java.util.Date;

import Application.Controller;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AlertBox;
import model.ButtonStyler;
import database.Competition;
import model.CompetitiorTypeChooser;
import model.DatePickerStyler;
import model.TextFieldStyler;

//*** This Class for Add New Competition ***//
public class AddNewCompetition {
	// Scene dimensions
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 720;
	// Paths
	protected final String BACKGROUND_PATH = "GUI/resources/AddNewCompetitionBG.jpg";
	protected final String FONT_PATH = "model/resources/Roboto-Regular.ttf";

	// CSS Styling
	protected final String CANCEL_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/canelButtonFree.png')";
	protected final String CANCEL_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/canelButtonPressed.png')";
	protected final String CREATE_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonFree.png')";
	protected final String CREATE_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonPressed.png')";

	// Stage, Panes and Scenes
	protected static Stage addNewCompetitionStage;
	protected static AnchorPane addNewCompetitionPane;
	protected static Scene addNewCompetitionScene;
	protected static HomePage homePage;

	protected String competitionName, website;
	protected boolean team;
	protected Date dueDate;
	protected TextFieldStyler competitionTextField;
	protected DatePickerStyler datePicker;
	protected TextFieldStyler websiteTextField;
	protected CompetitiorTypeChooser teamChoice, studentChoice;

	public AddNewCompetition() {
		addNewCompetitionPane = new AnchorPane();
		addNewCompetitionScene = new Scene(addNewCompetitionPane, WIDTH, HEIGHT);
		addNewCompetitionStage = new Stage();
		addNewCompetitionStage.setScene(addNewCompetitionScene);
		addNewCompetitionStage.setOnCloseRequest(e -> {

		});
		createBackground();
		createLabels();
		createButtons();

		competitionTextField = new TextFieldStyler("Competition name", 327, 32, 212, 267);
		datePicker = new DatePickerStyler("Due date", 327, 32, 212, 362);
		websiteTextField = new TextFieldStyler("Website", 327, 32, 212, 450);
		addNewCompetitionPane.getChildren().addAll(competitionTextField, datePicker, websiteTextField,
				createCompetitorTypes());

	}

	// Set Background
	public void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		addNewCompetitionPane.setBackground(new Background(background));
	}

	// Method for Label Styling
	private void createLabel(String text, double fontSize, double layoutX, double layoutY) {
		Text label = new Text(text);
		label.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, fontSize));
		label.setFill(Color.web("#ffffff"));
		label.setLayoutX(layoutX);
		label.setLayoutY(layoutY);
		addNewCompetitionPane.getChildren().add(label);
	}

	// Competition Name Label and TextField
	private void createLabels() {
		createLabel("Add new competition", 20, 190, 190);
		createLabel("Competition name", 15, 212, 255);
		createLabel("Due date", 15, 212, 352);
		createLabel("Website", 15, 212, 437);
		createLabel("Competitors", 15, 687, 285);
	}

	// Competitors Label and RadioButtons

	private VBox createCompetitorTypes() {
		Text teamText = new Text();
		Text studentText = new Text();
		VBox box = new VBox();
		teamChoice = new CompetitiorTypeChooser(teamText, "Team");
		studentChoice = new CompetitiorTypeChooser(studentText, "Students (Individual)");
		box.setSpacing(10);
		box.setLayoutX(687);
		box.setLayoutY(307);
		box.getChildren().addAll(teamChoice, studentChoice);
		teamChoice.setOnMouseClicked(e -> {
			studentChoice.setIsCircleChoosen(false);
			teamChoice.setIsCircleChoosen(true);
			this.team = true;
		});
		studentChoice.setOnMouseClicked(e -> {
			teamChoice.setIsCircleChoosen(false);
			studentChoice.setIsCircleChoosen(true);
			this.team = false;

		});
		return box;
	}

	public void createButtons() {
		createCreateButton();
		createCancelButton();
	}

	// Create and Cancel Buttons
	public void createCreateButton() {
		ButtonStyler button = new ButtonStyler("CREATE", 92, 28, 12, CREATE_BUTTON_PRESSED, CREATE_BUTTON_FREE);
		button.setLayoutY(544);
		button.setLayoutX(920);
		button.setOnAction(e -> {
			if (competitionTextField.getText().isEmpty()) {
				AlertBox.display("ERROR", "Please Enter The Competition Name");
			} else if (websiteTextField.getText().isEmpty()) {
				AlertBox.display("ERROR", "Please Enter The Website");
			} else if (datePicker.getValue() == null) {
				AlertBox.display("ERROR", "Please Pick The Due Date");
			} else if (teamChoice.getIsCircleChoosen() == false && studentChoice.getIsCircleChoosen() == false) {
				AlertBox.display("ERROR", "Please Choose The Competitors Type");
			} else {
				Competition newCompetition;
				if (websiteTextField.getText().startsWith("https://") != true
						|| websiteTextField.getText().startsWith("http://") != true) {
					newCompetition = Controller.database.createCompetition(competitionTextField.getText(),
							"https://" + websiteTextField.getText(), getDate(), this.team);
				} else {
					newCompetition = Controller.database.createCompetition(competitionTextField.getText(),
							websiteTextField.getText(), getDate(), this.team);
				}
				CompetitionPage.update();
				HomePage.update();
				CompetitionPage.switchScene(Controller.showCompetitions());
				competitionTextField.clear();
				datePicker.setValue(null);
				websiteTextField.clear();
				teamChoice.setIsCircleChoosen(false);
				studentChoice.setIsCircleChoosen(false);
			}

		});
		addNewCompetitionPane.getChildren().add(button);
	}

	public void createCancelButton() {
		ButtonStyler button = new ButtonStyler("CANCEL", 92, 28, 12, CANCEL_BUTTON_PRESSED, CANCEL_BUTTON_FREE);
		button.setLayoutY(544);
		button.setLayoutX(826);
		button.setOnAction(e -> {
			competitionTextField.clear();
			datePicker.setValue(null);
			websiteTextField.clear();
			teamChoice.setIsCircleChoosen(false);
			studentChoice.setIsCircleChoosen(false);
			CompetitionPage.switchScene(Controller.showCompetitions());
		});
		addNewCompetitionPane.getChildren().add(button);
	}

	// Getters and Setters
	public HomePage getHomePage() {
		return homePage;
	}

	public void setHomePage(HomePage homePage) {
		this.homePage = homePage;
	}

	public static Scene getScene() {
		return addNewCompetitionScene;
	}

	public static AnchorPane getPane() {
		return addNewCompetitionPane;
	}

	public static Stage getMainStage() {
		return addNewCompetitionStage;
	}

	public Date getDate() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date dueDate = Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());
		return dueDate;
	}

	// Method to Switch between Scenes
	public static void switchScene(Scene scene) {
		WelcomePage.getMainStage().setScene(scene);
	}

}