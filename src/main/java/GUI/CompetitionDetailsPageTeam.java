package GUI;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import Application.Controller;
import database.Competition;
import database.Database;
import database.Team;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import model.DatePickerStyler;
import model.TeamCard;
//import model.SummaryCard;
//import model.TeamCard;
import model.TextFieldStyler;

//*** This Class That Show The Competition (Team) Details ***//
public class CompetitionDetailsPageTeam {
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
	protected static Stage competitionDetailsTeamStage;
	protected static AnchorPane competitionDetailsTeamPane;
	protected static Scene competitionDetailsTeamScene;
	protected HomePage homePage;
	protected static BrowserPage browserPage;
	protected static Database database;

	protected String competitionName, website;
	protected boolean team;
	protected Date dueDate;

	protected static GridPane grid;
	protected static AddTeamPage addTeamPage;
	protected static Competition comp;
	protected static TextFieldStyler competitionTextField;
	protected static DatePickerStyler datePicker;
	protected static TextFieldStyler websiteTextField;
	protected static ArrayList<Team> teamParticipant;
	protected CheckBox editName, editDueDate, editWebsite;

	public CompetitionDetailsPageTeam(Competition comp) {
		this.comp = comp;
		competitionDetailsTeamPane = new AnchorPane();
		competitionDetailsTeamScene = new Scene(competitionDetailsTeamPane, WIDTH, HEIGHT);
		competitionDetailsTeamStage = new Stage();
		competitionDetailsTeamStage.setScene(competitionDetailsTeamScene);
		competitionDetailsTeamStage.setOnCloseRequest(e -> competitionDetailsTeamStage.close());
		competitionDetailsTeamScene.getStylesheets()
				.add(getClass().getResource("competitionDetail.css").toExternalForm());

		createBackground();
		createLabels();
		createButtons(comp);
		createTextFields(comp);
		createGridPane(comp);
		createCheckBoxes();

	}

	// Set Background
	public void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		competitionDetailsTeamPane.setBackground(new Background(background));
	}

	// Method for Label Styling
	private void createLabel(String text, double fontSize, double layoutX, double layoutY) {
		Text label = new Text(text);
		label.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, fontSize));
		label.setFill(Color.web("#ffffff"));
		label.setLayoutX(layoutX);
		label.setLayoutY(layoutY);
		competitionDetailsTeamPane.getChildren().add(label);
	}

	// Competition Name Label and TextField
	private void createLabels() {
		createLabel("Competition Details", 20, 80, 90);
		createLabel("Competition name", 15, 82, 134);
		createLabel("Due date", 15, 82, 210);
		createLabel("Competitors", 15, 82, 305);
		createLabel("Website", 15, 613, 134);
	}

	public void createButtons(Competition comp) {
		createSendEmailButton(comp);
		createOpenWebsiteButton(comp.getWebsite());
		createSaveAndCloseButton(comp);
		createAddTeamButton(comp);
		createDeleteCompetitionButton(comp);
	}

	public void createAddTeamButton(Competition comp) {
		ButtonStyler button = new ButtonStyler("", 32, 33, 0, ADD_COMPETITION_BUTTON_PRESSED,
				ADD_COMPETITION_BUTTON_FREE);
		button.setLayoutY(275);
		button.setLayoutX(977);
		button.setOnAction(e -> {
			addTeamPage = new AddTeamPage(comp);
			addTeamPage.switchScene(addTeamPage.getScene());
		});
		competitionDetailsTeamPane.getChildren().add(button);
	}

	public void createSendEmailButton(Competition comp) {
		ButtonStyler button = new ButtonStyler("SEND EMAIL", 133, 33, 12, OPEN_BUTTON_PRESSED, OPEN_BUTTON_FREE);
		button.setLayoutY(238);
		button.setLayoutX(614);
		button.setOnAction(e -> {
			try {
				Controller.sendEmail(comp);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		competitionDetailsTeamPane.getChildren().add(button);
	}

	public void createDeleteCompetitionButton(Competition comp) {
		ButtonStyler button = new ButtonStyler("DELETE COMPETITION", 133, 33, 11, OPEN_BUTTON_PRESSED, OPEN_BUTTON_FREE);
		button.setLayoutY(595);
		button.setLayoutX(80);
		button.setOnAction(e -> {
			Controller.database.removeCompetition(comp);
			CompetitionPage.update();
			HomePage.update();
			CompetitionPage.switchScene(Controller.showCompetitions());

		});
		competitionDetailsTeamPane.getChildren().add(button);
	}

	public void createOpenWebsiteButton(String website) {
		ButtonStyler button = new ButtonStyler("OPEN WEBSITE", 133, 33, 12, OPEN_BUTTON_PRESSED, OPEN_BUTTON_FREE);
		button.setLayoutY(238);
		button.setLayoutX(804);
		button.setOnAction(e -> {
			BrowserPage.switchScene(Controller.showWebsite(website));

		});
		competitionDetailsTeamPane.getChildren().add(button);
	}

	public static void createTextFields(Competition comp) {
		competitionTextField = new TextFieldStyler(comp.getName(), 327, 32, 82, 150);
		datePicker = new DatePickerStyler(comp.getDueDateFormated(), 327, 32, 82, 230);
		websiteTextField = new TextFieldStyler(comp.getWebsite(), 327, 32, 613, 150);

		competitionTextField.setText(comp.getName());
		datePicker.setValue(comp.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		websiteTextField.setText(comp.getWebsite());

		competitionTextField.setDisable(true);
		datePicker.setDisable(true);
		websiteTextField.setDisable(true);

		competitionDetailsTeamPane.getChildren().addAll(competitionTextField, datePicker, websiteTextField);

	}

	public void createSaveAndCloseButton(Competition comp) {
		ButtonStyler button = new ButtonStyler("SAVE & CLOSE", 133, 33, 12, OPEN_BUTTON_PRESSED, OPEN_BUTTON_FREE);
		button.setLayoutY(595);
		button.setLayoutX(880);
		button.setOnAction(e -> {
			if (competitionTextField.getText().isEmpty()) {
				AlertBox.display("ERROR", "Please Enter The Competition Name");
			} else if (websiteTextField.getText().isEmpty()) {
				AlertBox.display("ERROR", "Please Enter The Website");
			} else if (datePicker.getValue() == null) {
				AlertBox.display("ERROR", "Please Pick The Due Date");
			} else {
				if (websiteTextField.getText().startsWith("https://") == false
						&& websiteTextField.getText().startsWith("http://") == false) {
					comp.setWebsite("https://" + websiteTextField.getText());
				} else {
					comp.setWebsite(websiteTextField.getText());
				}

				if (isActive()) {
					comp.setStatusImage(new ImageView(new Image(comp.getActiveStatusPath())));
				} else {
					comp.setStatusImage(new ImageView(new Image(comp.getDoneStatusPath())));
				}
				comp.setName(competitionTextField.getText());
				comp.setDueDate(getDate());
				CompetitionPage.update();
				HomePage.update();
				editName.setSelected(false);
				editDueDate.setSelected(false);
				editWebsite.setSelected(false);
				CompetitionPage.switchScene(Controller.showCompetitions());
			
			}
			});
		competitionDetailsTeamPane.getChildren().add(button);
	}

	public static void update(Competition comp) {
		createTextFields(comp);
		createGridPane(comp);
	}

	public static void createGridPane(Competition comp) {
		int row = 0;
		teamParticipant = new ArrayList<Team>();

		for (int i = 0; i < comp.getParticipants().length; i++) {
			teamParticipant.add((Team) (comp.getParticipants()[i]));
		}
		grid = new GridPane();
		grid.setPrefHeight(262);
		grid.setPrefWidth(932);
		grid.setPadding(new Insets(10));
		grid.setHgap(20);
		grid.setVgap(20);
		for (int i = 0; i < teamParticipant.size(); i++) {
			TeamCard card = new TeamCard(teamParticipant.get(i));

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
		competitionDetailsTeamPane.getChildren().add(scrollPane);

	}

	public void createCheckBoxes() {
		editName = new CheckBox("Edit");
		editDueDate = new CheckBox("Edit");
		editWebsite = new CheckBox("Edit");

		editName.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, 10));
		editDueDate.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, 10));
		editWebsite.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, 10));

		editName.setStyle("-fx-text-fill: white;");
		editDueDate.setStyle("-fx-text-fill: white;");
		editWebsite.setStyle("-fx-text-fill: white;");

		editName.setLayoutX(415);
		editDueDate.setLayoutX(415);
		editWebsite.setLayoutX(946);
		editName.setLayoutY(158);
		editDueDate.setLayoutY(238);
		editWebsite.setLayoutY(158);

		editName.setMinHeight(10);
		editName.setMinWidth(10);
		editDueDate.setMinHeight(10);
		editDueDate.setMinWidth(10);
		editWebsite.setMinHeight(10);
		editWebsite.setMinWidth(10);

		editName.setOnAction(e -> {
			if (editName.isSelected())
				competitionTextField.setDisable(false);
			else
				competitionTextField.setDisable(true);
		});
		editDueDate.setOnAction(e -> {
			if (editDueDate.isSelected())
				datePicker.setDisable(false);
			else
				datePicker.setDisable(true);
		});
		editWebsite.setOnAction(e -> {
			if (editWebsite.isSelected())
				websiteTextField.setDisable(false);
			else
				datePicker.setDisable(true);
		});

		competitionDetailsTeamPane.getChildren().addAll(editName, editDueDate, editWebsite);

	}

	public boolean isActive() {
		Date todayDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);

		if ((getDate()).after(todayDate))
			return true;
		else
			return false;
	}

	// Getters and Setters
	public HomePage getHomePage() {
		return homePage;
	}

	public void setHomePage(HomePage homePage) {
		this.homePage = homePage;
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

	public static Stage getCompetitionDetailsTeamStage() {
		return competitionDetailsTeamStage;
	}

	public static void setCompetitionDetailsTeamStage(Stage competitionDetailsTeamStage) {
		CompetitionDetailsPageTeam.competitionDetailsTeamStage = competitionDetailsTeamStage;
	}

	public static AnchorPane getCompetitionDetailsTeamPane() {
		return competitionDetailsTeamPane;
	}

	public static void setCompetitionDetailsTeamPane(AnchorPane competitionDetailsTeamPane) {
		CompetitionDetailsPageTeam.competitionDetailsTeamPane = competitionDetailsTeamPane;
	}

	public static Scene getCompetitionDetailsTeamScene() {
		return competitionDetailsTeamScene;
	}

	public static void setCompetitionDetailsTeamScene(Scene competitionDetailsTeamScene) {
		CompetitionDetailsPageTeam.competitionDetailsTeamScene = competitionDetailsTeamScene;
	}

	public static Scene getScene() {
		return competitionDetailsTeamScene;
	}

	public static Competition getcomp() {
		return comp;
	}

}
