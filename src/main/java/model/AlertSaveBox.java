package model;

import GUI.CompetitionDetailsPageStudent;
import GUI.CompetitionDetailsPageTeam;
import GUI.WelcomePage;
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

//*** This Class for Show Alert Confirmation ***//
public class AlertSaveBox {
	// Scene dimensions
	private static final int WIDTH = 400;
	private static final int HEIGHT = 260;
	// Paths
	private final static String BACKGROUND_PATH = "GUI/resources/unupdatedAlert.png";
	private final static String FONT_PATH = "model/resources/Roboto-Regular.ttf";

	// CSS Styling
	protected final String ADD_COMPETITION_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButton.png')";
	protected final String ADD_COMPETITION_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButtonPressed.png')";
	private final static String CREATE_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonFree.png')";
	private final static String CREATE_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonPressed.png')";

	protected static Stage alertBoxWindow;
	protected static AnchorPane alertBoxPane;
	protected static Scene alertBoxScene;
	protected static int confirmation;
	protected static ButtonStyler saveButton, dontSaveButton, cancelButton;
	protected static ChoiceBoxStyler choiceBox;
	protected static CompetitionDetailsPageStudent competitionDetailsPageStudent;
	protected static CompetitionDetailsPageTeam competitionDetailsPageTeam;

	public static int display() {
		Label label = new Label();

		Image BGImage = new Image(BACKGROUND_PATH, 400, 260, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(BGImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, null);
		Background background = new Background(backgroundImage);

		label.setText("Do You Want To Save The Changes?");
		label.setAlignment(Pos.CENTER);
		label.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 20));
		label.setLayoutX(42);
		label.setLayoutY(125);
		label.setTextFill(Color.web("#ffffff"));

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
		saveButton = new ButtonStyler("SAVE", 92, 28, 12, CREATE_BUTTON_PRESSED, CREATE_BUTTON_FREE);
		saveButton.setLayoutX(286);
		saveButton.setLayoutY(221);
		saveButton.setOnAction(e -> {
			confirmation = 1;

			alertBoxWindow.close();

		});

		dontSaveButton = new ButtonStyler("DON'T SAVE", 92, 28, 12, CREATE_BUTTON_PRESSED, CREATE_BUTTON_FREE);
		dontSaveButton.setLayoutX(185);
		dontSaveButton.setLayoutY(221);
		dontSaveButton.setOnAction(e -> {
			confirmation = -1;

			alertBoxWindow.close();
		});

		cancelButton = new ButtonStyler("CANCEL", 92, 28, 12, CREATE_BUTTON_PRESSED, CREATE_BUTTON_FREE);
		cancelButton.setLayoutX(15);
		cancelButton.setLayoutY(221);
		cancelButton.setOnAction(e -> {
			confirmation = 0;

			alertBoxWindow.close();
		});
		alertBoxScene.getStylesheets().add(UpdateRankAlertBox.class.getResource("updateAlertBox.css").toExternalForm());

		alertBoxPane.getChildren().addAll(label, saveButton, dontSaveButton, cancelButton);

		alertBoxWindow.setScene(alertBoxScene);
		alertBoxWindow.showAndWait();
		alertBoxPane.getStyleClass().add("pane");

		return confirmation;
	}

	public static void switchScene(Scene scene) {
		WelcomePage.getMainStage().setScene(scene);
	}
}
