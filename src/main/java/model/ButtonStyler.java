package model;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//*** This Class to Style The Buttons of The Program ***//

public class ButtonStyler extends Button {
	protected final String FONT_PATH = "src/model/resources/Roboto-Regular.ttf";
	protected final String BUTTON_PRESSED;
	protected final String BUTTON_FREE;

	double HEIGHT;
	double WIDTH;

	public ButtonStyler(String text, double WIDTH, double HEIGHT, double font, String BUTTON_PRESSED,
			String BUTTON_FREE) {
		// Set Button Dimensions
		this.HEIGHT = HEIGHT;
		this.WIDTH = WIDTH;
		this.BUTTON_PRESSED = BUTTON_PRESSED;
		this.BUTTON_FREE = BUTTON_FREE;
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setStyle(BUTTON_FREE);
		setText(text);
		setButtonFont(font);
		setButtonAction();
	}

	// Set Font for The Button Text
	private void setButtonFont(double font) {
		setFont(Font.font(FONT_PATH, FontWeight.LIGHT, font));
		setTextFill(Color.web("#ffffff"));
	}

	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED);
		setPrefHeight(HEIGHT - 1);
		setLayoutY(getLayoutY() + 1);
	}

	private void setButtonFreeStyle() {
		setStyle(BUTTON_FREE);
		setPrefHeight(HEIGHT);
		setLayoutY(getLayoutY() - 1);
	}

	private void setButtonAction() {
		setOnMousePressed(e -> {
			setButtonPressedStyle();
		});

		setOnMouseReleased(e -> {
			setButtonFreeStyle();
		});

	}

}
