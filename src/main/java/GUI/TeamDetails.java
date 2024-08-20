package GUI;

import java.util.ArrayList;
import java.util.Date;

import Application.Controller;
import database.Database;
import database.Student;
import database.Team;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
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
import model.TeamMemberCard;
import model.TextFieldStyler;

//*** This Class for Show The Team Details ***//
public class TeamDetails {
	// Scene dimensions
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 720;
	// Paths
	protected final String BACKGROUND_PATH = "GUI/resources/detailsBG.png";
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

	// Stage, Panes and Scenes
	protected static Stage teamDetailsStage;
	protected static AnchorPane teamDetailsPane;
	protected static Scene teamDetailsScene;
	protected HomePage homePage;
	protected static Database database;

	protected String competitionName, website;
	protected boolean team;
	protected Date dueDate;

	protected static GridPane grid;
	protected static AddStudentMemberPage addStudentPage;

	protected static ArrayList<Student> teamParticipant;
	protected static TextFieldStyler teamNameTextField;
	protected static TextFieldStyler membersNumberTextField;
	protected static TextFieldStyler rankTextField;
	protected static TextFieldStyler mostMajorTextField;
	protected CheckBox editName, editRank;

	public TeamDetails(Team team) {
		teamDetailsPane = new AnchorPane();
		teamDetailsScene = new Scene(teamDetailsPane, WIDTH, HEIGHT);
		teamDetailsStage = new Stage();
		teamDetailsStage.setScene(teamDetailsScene);
		teamDetailsStage.setOnCloseRequest(e -> teamDetailsStage.close());
		teamDetailsScene.getStylesheets().add(getClass().getResource("competitionDetail.css").toExternalForm());

		createBackground();
		createLabels();
		createButtons(team);
		createGridPane(team);
		createTextFields(team);
		createCheckBoxes();

	}

	// Set Background
	public void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		teamDetailsPane.setBackground(new Background(background));
	}

	// Method for Label Styling
	private void createLabel(String text, double fontSize, double layoutX, double layoutY) {
		Text label = new Text(text);
		label.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, fontSize));
		label.setFill(Color.web("#ffffff"));
		label.setLayoutX(layoutX);
		label.setLayoutY(layoutY);
		teamDetailsPane.getChildren().add(label);
	}

	// Competition Name Label and TextField
	private void createLabels() {
		createLabel("Team Details", 20, 80, 90);
		createLabel("Team name", 15, 82, 134);
		createLabel("Members", 15, 82, 210);
		createLabel("Rank", 15, 613, 134);
		createLabel("Most Major", 15, 613, 210);
	}

	public void createButtons(Team team) {
		createAddTeamButton(team);
		createSaveAndCloseButton(team);
		createDeleteTeamButton(team);

	}

	public void createAddTeamButton(Team team) {
		ButtonStyler button = new ButtonStyler("", 32, 33, 0, ADD_COMPETITION_BUTTON_PRESSED,
				ADD_COMPETITION_BUTTON_FREE);
		button.setLayoutY(275);
		button.setLayoutX(977);
		button.setOnAction(e -> {
			addStudentPage = new AddStudentMemberPage(team);
			addStudentPage.switchScene(addStudentPage.getScene());
		});
		teamDetailsPane.getChildren().add(button);
	}

	public void createSaveAndCloseButton(Team team) {
		ButtonStyler button = new ButtonStyler("SAVE & CLOSE", 133, 33, 12, OPEN_BUTTON_PRESSED, OPEN_BUTTON_FREE);
		button.setLayoutY(595);
		button.setLayoutX(880);
		button.setOnAction(e -> {
			if (teamNameTextField.getText().isEmpty()) {
				AlertBox.display("ERROR", "Please Enter The Team Name");
			}else {  if (rankTextField.getText().isEmpty()) {
				team.setName(teamNameTextField.getText());
				team.setRank(-1);
				CompetitionPage.update();
				HomePage.update();
				CompetitionDetailsPageTeam.update(CompetitionDetailsPageTeam.getcomp());
				editName.setSelected(false);
				editRank.setSelected(false);
				CompetitionDetailsPageTeam.switchScene(CompetitionDetailsPageTeam.getScene());
			} else if (Integer.parseInt(rankTextField.getText()) <= 0 && Integer.parseInt(rankTextField.getText()) != -1
					|| !isNumeric(rankTextField.getText())) {
				AlertBox.display("ERROR", "Please Enter a Valid Rank");
			} else {
				team.setName(teamNameTextField.getText());
				team.setRank(Integer.parseInt(rankTextField.getText()));
				CompetitionPage.update();
				HomePage.update();
				CompetitionDetailsPageTeam.update(CompetitionDetailsPageTeam.getcomp());
				editName.setSelected(false);
				editRank.setSelected(false);
				CompetitionDetailsPageTeam.switchScene(CompetitionDetailsPageTeam.getScene());
			}
			}
		});
		teamDetailsPane.getChildren().add(button);
	}

	public void createDeleteTeamButton(Team team) {
		ButtonStyler button = new ButtonStyler("DELETE TEAM", 133, 33, 12, OPEN_BUTTON_PRESSED, OPEN_BUTTON_FREE);
		button.setLayoutY(595);
		button.setLayoutX(80);
		button.setOnAction(e -> {
			Controller.database.removeTeamFromCompetition(team, CompetitionDetailsPageTeam.getcomp());
			CompetitionPage.update();
			TeamDetails.update(team);
			CompetitionDetailsPageTeam.update(CompetitionDetailsPageTeam.getcomp());
			HomePage.update();
			CompetitionDetailsPageTeam.switchScene(CompetitionDetailsPageTeam.getScene());
		});
		teamDetailsPane.getChildren().add(button);
	}

	public static void update(Team team) {
		createGridPane(team);
		createTextFields(team);

	}

	public static void createTextFields(Team team) {
		teamNameTextField = new TextFieldStyler(team.getName(), 327, 32, 82, 150);
		membersNumberTextField = new TextFieldStyler(team.getMemberNumber() + "", 327, 32, 82, 230);
		rankTextField = new TextFieldStyler(team.getRank() + "", 327, 32, 613, 150);
		mostMajorTextField = new TextFieldStyler(team.getMostMajor(), 327, 32, 613, 226);

		teamNameTextField.setText(team.getName());
		membersNumberTextField.setText(team.getMemberNumber() + "");
		rankTextField.setText(team.getRank() + "");
		mostMajorTextField.setText(team.getMostMajor());

		teamNameTextField.setDisable(true);
		membersNumberTextField.setDisable(true);
		rankTextField.setDisable(true);
		mostMajorTextField.setDisable(true);
		teamDetailsPane.getChildren().addAll(teamNameTextField, membersNumberTextField, rankTextField,
				mostMajorTextField);

	}

	public static void createGridPane(Team team) {
		int row = 0;
		teamParticipant = new ArrayList<Student>();

		for (int i = 0; i < team.getMembers().length; i++) {
			teamParticipant.add(team.getMembers()[i]);
		}
		grid = new GridPane();
		grid.setPrefHeight(262);
		grid.setPrefWidth(932);
		grid.setPadding(new Insets(10));
		grid.setHgap(20);
		grid.setVgap(20);
		for (int i = 0; i < teamParticipant.size(); i++) {
			TeamMemberCard card = new TeamMemberCard(team, teamParticipant.get(i));

			grid.add(card, 0, row++);

		}

		ScrollPane scrollPane = new ScrollPane(grid);
		scrollPane.setPrefSize(932, 262);
		scrollPane.setContent(grid);
		scrollPane.setFitToHeight(true);
		scrollPane.setPannable(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setPrefHeight(262);
		scrollPane.setPrefWidth(932);
		scrollPane.setLayoutX(80);
		scrollPane.setLayoutY(315);
		grid.setStyle("-fx-background-color: #212a3d;");
		scrollPane.setStyle("-fx-background-color: #212a3d;");
		teamDetailsPane.getChildren().add(scrollPane);
	}

	public void createCheckBoxes() {
		editName = new CheckBox("Edit");
		editRank = new CheckBox("Edit");

		editName.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, 10));
		editRank.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, 10));

		editName.setStyle("-fx-text-fill: white;");
		editRank.setStyle("-fx-text-fill: white;");

		editName.setLayoutX(415);
		editRank.setLayoutX(946);
		editName.setLayoutY(158);
		editRank.setLayoutY(158);

		editName.setMinHeight(10);
		editName.setMinWidth(10);
		editRank.setMinHeight(10);
		editRank.setMinWidth(10);

		editName.setOnAction(e -> {
			if (editName.isSelected())
				teamNameTextField.setDisable(false);
			else
				teamNameTextField.setDisable(true);
		});
		editRank.setOnAction(e -> {
			if (editRank.isSelected())
				rankTextField.setDisable(false);
			else
				rankTextField.setDisable(true);
		});

		teamDetailsPane.getChildren().addAll(editName, editRank);

	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
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

	public static Stage getTeamDetailsStage() {
		return teamDetailsStage;
	}

	public static void setTeamDetailsStage(Stage teamDetailsStage) {
		TeamDetails.teamDetailsStage = teamDetailsStage;
	}

	public static AnchorPane getTeamDetailsPane() {
		return teamDetailsPane;
	}

	public static void setTeamDetailsPane(AnchorPane teamDetailsPane) {
		TeamDetails.teamDetailsPane = teamDetailsPane;
	}

	public static Scene getScene() {
		return teamDetailsScene;
	}

	public static void setScene(Scene teamDetailsScene) {
		TeamDetails.teamDetailsScene = teamDetailsScene;
	}

}
