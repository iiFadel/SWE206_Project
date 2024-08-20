package model;

import database.Competition;
import javafx.scene.control.ChoiceBox;

//*** This Class for Styling ChoiceBox ***//
public class ChoiceBoxStyler extends ChoiceBox {

	public ChoiceBoxStyler(String[] choices, double WIDTH, double HEIGHT, double LAYOUTX, double LAYOUTY) {
		for (int i = 0; i < choices.length; i++) {
			getItems().add(choices[i]);
		}
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setLayoutX(LAYOUTX);
		setLayoutY(LAYOUTY);
	}

	public ChoiceBoxStyler(Competition[] choices, double WIDTH, double HEIGHT, double LAYOUTX, double LAYOUTY) {
		for (int i = 0; i < choices.length; i++) {
			getItems().add(choices[i]);
		}
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setLayoutX(LAYOUTX);
		setLayoutY(LAYOUTY);
	}
}
