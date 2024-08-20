package model;

import javafx.scene.control.DatePicker;

//*** This Class for Customizing DatePicker ***//
public class DatePickerStyler extends DatePicker {
	public DatePickerStyler(String text, double WIDTH, double HEIGHT, double LAYOUTX, double LAYOUTY) {
		setValue(getValue());
		setStyle("-fx-base: #20293c; -fx-control-inner-background: #20293c; -fx-text-fill:#869aac;");
		setPromptText(text);
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setLayoutX(LAYOUTX);
		setLayoutY(LAYOUTY);

	}
}
