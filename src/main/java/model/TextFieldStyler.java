package model;

import javafx.scene.control.TextField;

//*** This Class for Customizing TextFields ***//
public class TextFieldStyler extends TextField {
	public TextFieldStyler(String text, double WIDTH, double HEIGHT, double LAYOUTX, double LAYOUTY) {
		setStyle("-fx-control-inner-background: #20293c;" + " -fx-text-box-border: #20293c;"
				+ " -fx-focus-color: #15589a;" + " -fx-prompt-text-fill:#869aac;" + " -fx-text-fill: #1f8efa;");
		setPromptText(text);
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setLayoutX(LAYOUTX);
		setLayoutY(LAYOUTY);
	}

}
