package model;

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

//*** This Class for Show Alert Box ***//
public class AlertBox {
	// Scene dimensions
	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;
	// Paths
	private final static String BACKGROUND_PATH = "GUI/resources/alertBoxBG.png";
	private final static String FONT_PATH = "model/resources/Roboto-Regular.ttf";

	// CSS Styling
	protected final String ADD_COMPETITION_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButton.png')";
	protected final String ADD_COMPETITION_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/addCompetitionButtonPressed.png')";
	protected final static String CREATE_BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonFree.png')";
	protected final static String CREATE_BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/createButtonPressed.png')";

	protected static Stage alertBoxWindow;
	protected static AnchorPane alertBoxPane;
	protected static Scene alertBoxScene;
	protected static ButtonStyler button;

	public static void display(String title, String message) {
		Label label = new Label();

		Image BGImage = new Image(BACKGROUND_PATH, 400, 200, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(BGImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, null);
		Background background = new Background(backgroundImage);

		label.setText(message);
		label.setAlignment(Pos.CENTER);
		label.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 20));
		label.setLayoutX(20);
		label.setLayoutY(70);
		label.setTextFill(Color.web("#ffffff"));

		alertBoxWindow = new Stage();
		alertBoxWindow.initModality(Modality.APPLICATION_MODAL);
		alertBoxWindow.setTitle(title);
		alertBoxWindow.setResizable(false);
		alertBoxWindow.setMinWidth(WIDTH);
		alertBoxWindow.setMinHeight(HEIGHT);
		alertBoxPane = new AnchorPane();
		alertBoxPane.setMinWidth(WIDTH);
		alertBoxPane.setMinHeight(HEIGHT);
		alertBoxScene = new Scene(alertBoxPane);
		alertBoxScene.setFill(Color.web("#242e42"));

		alertBoxPane.setBackground(background);

		button = new ButtonStyler("CLOSE", 92, 28, 12, CREATE_BUTTON_PRESSED, CREATE_BUTTON_FREE);
		button.setLayoutX(286);
		button.setLayoutY(165);
		button.setOnAction(e -> {
			alertBoxWindow.close();
		});

		alertBoxPane.getChildren().addAll(label, button);
		alertBoxWindow.setScene(alertBoxScene);
		alertBoxWindow.showAndWait();
		alertBoxPane.getStyleClass().add("pane");

	}

}
