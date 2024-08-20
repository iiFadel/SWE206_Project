package model;

import GUI.CompetitionDetailsPageStudent;
import GUI.CompetitionDetailsPageTeam;
import GUI.WelcomePage;
import database.Competition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

//*** This Class for Send Notification If The Winners Have Not Been Updated ***//
public class UpdateRankAlertBox {
	// Scene dimensions
	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 260;
	// Paths
	protected final static String BACKGROUND_PATH = "GUI/resources/unupdatedAlert.png";
	protected final static String FONT_PATH = "model/resources/Roboto-Regular.ttf";

	// CSS Styling
	protected final String ADD_COMPETITION_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButton.png')";
	protected final String ADD_COMPETITION_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButtonPressed.png')";
	protected final static String CREATE_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonFree.png')";
	protected final static String CREATE_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonPressed.png')";

	// Class Stages, Panes and Scene
	protected static Stage alertBoxWindow;
	protected static AnchorPane alertBoxPane;
	protected static Scene alertBoxScene;
	
	// Another Classes
	protected static CompetitionDetailsPageStudent competitionDetailsPageStudent;
	protected static CompetitionDetailsPageTeam competitionDetailsPageTeam;
	
	// Buttons and Choice Box
	protected static ButtonStyler updateButton, laterButton;
	protected static ChoiceBoxStyler choiceBox;


	public static void display(Competition[] competitions) {
		// Labels
		Label label = new Label();
		Label label2 = new Label();
		Image BGImage = new Image(BACKGROUND_PATH, 400, 260, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(BGImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, null);
		Background background = new Background(backgroundImage);
		
		// Labels Styling
		label.setText("There are Some Competitors Ranks\n" + "That Have not Been Updated.");
		label.setAlignment(Pos.CENTER);
		label.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 20));
		label.setLayoutX(20);
		label.setLayoutY(70);
		label.setTextFill(Color.web("#ffffff"));
		
		label2.setText("Competitions Need To Be Updated:");
		label2.setAlignment(Pos.CENTER);
		label2.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 15));
		label2.setLayoutX(20);
		label2.setLayoutY(140);
		label2.setTextFill(Color.web("#ffffff"));
		
		
		alertBoxWindow = new Stage();
		alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
		alertBoxWindow.setTitle("");
		alertBoxWindow.setResizable(false);
		alertBoxWindow.setMinWidth(WIDTH);
		alertBoxWindow.setMinHeight(HEIGHT);
		alertBoxPane = new AnchorPane();
		alertBoxPane.setMinWidth(WIDTH);
		alertBoxPane.setMinHeight(HEIGHT);
		alertBoxScene = new Scene(alertBoxPane);
		alertBoxPane.setBackground(background);

		choiceBox = new ChoiceBoxStyler(competitions, 327, 32, 20, 160);
		choiceBox.setStyle("-fx-context-menu-color: #20293c; ");

		updateButton = new ButtonStyler("UPDATE", 92, 28, 12, CREATE_BUTTON_PRESSED, CREATE_BUTTON_FREE);
		updateButton.setLayoutX(286);
		updateButton.setLayoutY(221);
		updateButton.setOnAction(e -> {
			if (choiceBox.getValue() == null) {
				AlertBox.display("Error", "Please Choose a Competition To Be\nUpdated");
			} else {
				if (((Competition) choiceBox.getValue()).getTeams() == true) {
					competitionDetailsPageTeam = new CompetitionDetailsPageTeam(((Competition) choiceBox.getValue()));
					competitionDetailsPageTeam.switchScene(competitionDetailsPageTeam.getScene());

				} else {
					competitionDetailsPageStudent = new CompetitionDetailsPageStudent(
							((Competition) choiceBox.getValue()));
					competitionDetailsPageStudent.switchScene(competitionDetailsPageStudent.getScene());

				}
				alertBoxWindow.close();
			}
		});

		laterButton = new ButtonStyler("LATER", 92, 28, 12, CREATE_BUTTON_PRESSED, CREATE_BUTTON_FREE);
		laterButton.setLayoutX(185);
		laterButton.setLayoutY(221);
		laterButton.setOnAction(e -> {

			alertBoxWindow.close();
		});


		

		alertBoxScene.getStylesheets().add(UpdateRankAlertBox.class.getResource("updateAlertBox.css").toExternalForm());
		alertBoxPane.getChildren().addAll(label, label2, updateButton, laterButton, choiceBox);
		alertBoxWindow.setScene(alertBoxScene);
		alertBoxWindow.showAndWait();
		alertBoxPane.getStyleClass().add("pane");

	}

	public static void switchScene(Scene scene) {
		WelcomePage.getMainStage().setScene(scene);
	}

	public static Scene getScene() {
		return alertBoxScene;
		
	}
}
