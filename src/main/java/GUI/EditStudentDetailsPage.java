package GUI;

import java.util.ArrayList;
import java.util.Date;

import Application.Controller;
import database.Competition;
import database.Database;
import database.Student;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AlertBox;
import model.ButtonStyler;
import model.ChoiceBoxStyler;
import model.TextFieldStyler;

//*** This Class for Edit The Student Detail ***//
public class EditStudentDetailsPage {

	// Scene dimensions
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 720;
	// Paths
	protected final String BACKGROUND_PATH = "GUI/resources/addStudentBG.png";
	protected final String BOLDFONT_PATH = "src/model/resources/Roboto-Bold.ttf";
	protected final String LIGHTFONT_PATH = "src/model/resources/Roboto-Light.ttf";
	protected final String FONT_PATH = "model/resources/Roboto-Regular.ttf";

	// CSS Styling
	protected final String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/BlueButtonPressed.png')";
	protected final String BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/BlueButton.png')";
	protected final String OPEN_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/openWebsiteButton.png')";
	protected final String OPEN_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/openWebsiteButtonPressed.png')";
	protected final String ADD_COMPETITION_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButton.png')";
	protected final String ADD_COMPETITION_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButtonPressed.png')";
	protected final String CANCEL_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/canelButtonFree.png')";
	protected final String CANCEL_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/canelButtonPressed.png')";
	protected final String CREATE_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonFree.png')";
	protected final String CREATE_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonPressed.png')";

	// Stage, Panes and Scenes
	protected static Stage editStudentDetailsStage;
	protected static AnchorPane editStudentDetailsPane;
	protected static Scene editStudentDetailsScene;
	protected HomePage homePage;
	protected static Database database;

	protected String[] majors = { "CS", "SWE", "COE", "ISE", "MATH", "AS", "AE", "EE", "ME", "PHYS", "CISE", "CHE",
			"MSE", "CHEM", "MGT", "FIN", "MKT", "ACCT", "MIS", "CE", "ARC", "ARE", "PETE", "GEOP", "GEOL" };
	protected String competitionName, website;
	protected boolean team;
	protected Date dueDate;

	protected static GridPane grid;
	protected CompetitionDetailsPageStudent competitionDetailsPageStudent;
	protected ArrayList<Student> teamParticipant;
	protected TextFieldStyler studentNameTextField, studentID, rank;
	protected ChoiceBoxStyler majorsChoiceBox;
	protected CompetitionPage competitionPage;

	public EditStudentDetailsPage(Competition comp, Student student) {
		editStudentDetailsPane = new AnchorPane();
		editStudentDetailsScene = new Scene(editStudentDetailsPane, WIDTH, HEIGHT);
		editStudentDetailsStage = new Stage();
		editStudentDetailsStage.setScene(editStudentDetailsScene);
		editStudentDetailsStage.setOnCloseRequest(e -> editStudentDetailsStage.close());
		editStudentDetailsScene.getStylesheets().add(getClass().getResource("competitionDetail.css").toExternalForm());

		createBackground();
		createLabels();
		createButtons(comp, student);

		studentNameTextField = new TextFieldStyler("Student name", 327, 32, 350, 213);
		studentID = new TextFieldStyler("XXXXXXXXX", 327, 32, 350, 280);
		majorsChoiceBox = new ChoiceBoxStyler(majors, 150, 32, 350, 351);
		rank = new TextFieldStyler("Rank", 150, 32, 540, 351);

		studentNameTextField.setText(student.getName());
		studentID.setText(student.getId() + "");
		majorsChoiceBox.setValue(student.getMajor());
		rank.setText(student.getRank() + "");

		editStudentDetailsPane.getChildren().addAll(studentNameTextField, studentID, majorsChoiceBox, rank);
	}

	// Set Background
	public void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		editStudentDetailsPane.setBackground(new Background(background));
	}

	// Method for Label Styling
	private void createLabel(String text, double fontSize, double layoutX, double layoutY) {
		Text label = new Text(text);
		label.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, fontSize));
		label.setFill(Color.web("#ffffff"));
		label.setLayoutX(layoutX);
		label.setLayoutY(layoutY);
		editStudentDetailsPane.getChildren().add(label);
	}

	// Competition Name Label and TextField
	private void createLabels() {
		createLabel("Add Student", 20, 350, 160);
		createLabel("Student name", 15, 350, 207);
		createLabel("Student ID", 15, 350, 275);
		createLabel("Major", 15, 350, 345);
		createLabel("Rank", 15, 540, 345);
	}

	public void createButtons(Competition comp, Student student) {
		createDeleteButton(comp, student);
		createSaveButton(comp, student);
		createCancelButton();
	}

	public void createDeleteButton(Competition comp, Student student) {
		ButtonStyler button = new ButtonStyler("DELETE", 133, 33, 12, OPEN_BUTTON_PRESSED, OPEN_BUTTON_FREE);
		button.setLayoutY(400);
		button.setLayoutX(350);
		button.setOnAction(e -> {
			Controller.database.removeStudentFromCompetition(student, comp);
			CompetitionDetailsPageStudent.update();
			CompetitionPage.update();
			HomePage.update();
			competitionDetailsPageStudent.switchScene(competitionDetailsPageStudent.getScene());

		});
		editStudentDetailsPane.getChildren().add(button);
	}

	public void createSaveButton(Competition comp, Student student) {
		ButtonStyler button = new ButtonStyler("SAVE", 92, 28, 12, CREATE_BUTTON_PRESSED, CREATE_BUTTON_FREE);
		button.setLayoutY(494);
		button.setLayoutX(606);
		button.setOnAction(e -> {
			if (studentNameTextField.getText().isEmpty()) {
				AlertBox.display("ERROR", "Please Enter The Student Name");

			} else if (studentID.getText().isEmpty()) {
				AlertBox.display("ERROR", "Please Enter The Student ID");
			} else if (!isNumeric(studentID.getText())) {
				AlertBox.display("ERROR", "Student ID Must Be Numbers Only!");
			} else if (studentID.getText().length() > 9 || studentID.getText().length() < 9) {
				AlertBox.display("ERROR", "Student ID Must Be 9 Digits!");
			} else if (majorsChoiceBox.getValue() == null) {
				AlertBox.display("ERROR", "Please Choose The Major");
			}else{ if (rank.getText().isEmpty()) {
				Controller.database.removeStudentFromCompetition(student, comp);
				Student newStudent = Controller.database.createStudent(studentNameTextField.getText(),
						(String) majorsChoiceBox.getValue(), Integer.parseInt(studentID.getText()), -1, comp);
				competitionDetailsPageStudent = new CompetitionDetailsPageStudent(comp);
				competitionDetailsPageStudent.teamParticipant.add(newStudent);
				CompetitionPage.update();
				HomePage.update();
				competitionDetailsPageStudent.switchScene(competitionDetailsPageStudent.getScene());
			
		} else if ((!isNumeric(rank.getText())) ||( Integer.parseInt(rank.getText()) <= 0
					&& Integer.parseInt(rank.getText()) != -1)) {
				AlertBox.display("ERROR", "Please Enter a Valid Rank");
			}  else {
					Controller.database.removeStudentFromCompetition(student, comp);
					Student newStudent = Controller.database.createStudent(studentNameTextField.getText(),
							(String) majorsChoiceBox.getValue(), Integer.parseInt(studentID.getText()),
							Integer.parseInt(rank.getText()), comp);
					competitionDetailsPageStudent = new CompetitionDetailsPageStudent(comp);
					competitionDetailsPageStudent.teamParticipant.add(newStudent);
					CompetitionPage.update();
					HomePage.update();
					competitionDetailsPageStudent.switchScene(competitionDetailsPageStudent.getScene());
				
				}
			}

		});

		editStudentDetailsPane.getChildren().add(button);

	}

	public void createCancelButton() {
		ButtonStyler button = new ButtonStyler("CANCEL", 92, 28, 12, CANCEL_BUTTON_PRESSED, CANCEL_BUTTON_FREE);
		button.setLayoutY(494);
		button.setLayoutX(500);
		button.setOnAction(e -> {
			competitionDetailsPageStudent.switchScene(competitionDetailsPageStudent.getScene());
		});
		editStudentDetailsPane.getChildren().add(button);
	}

	// Getters and Setters
	public HomePage getHomePage() {
		return homePage;
	}

	public void setHomePage(HomePage homePage) {
		this.homePage = homePage;
	}

	// Method to Switch between Scenes
	public static void switchScene(Scene scene) {
		WelcomePage.getMainStage().setScene(scene);
	}

	public static Scene getScene() {
		return editStudentDetailsScene;
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
