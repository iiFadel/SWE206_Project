package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//*** This Class for Customizing Radio Buttons ***//

public class CompetitiorTypeChooser extends HBox {
	protected ImageView circleImage;
	protected Text competitor;

	// Pictures Paths
	protected String circleNotChoosen = "model/resources/circleNotChoosen.png";
	protected String circleChoosen = "model/resources/circleChoosen.png";
	protected final String FONT_PATH = "src/model/resources/Roboto-Regular.ttf";

	protected boolean isCircleChoosen;

	// Set The Style of The Radio Buttons
	public CompetitiorTypeChooser(Text competitor, String text) {
		circleImage = new ImageView(circleNotChoosen);
		competitor = new Text(text);
		competitor.setFont(Font.font(FONT_PATH, FontWeight.LIGHT, 15));
		competitor.setFill(Color.web("#869aac"));
		isCircleChoosen = false;
		this.competitor = competitor;
		this.setSpacing(10);
		this.getChildren().add(circleImage);
		this.getChildren().add(competitor);
	}

	// Getter
	public Text getCompatetorType() {
		return competitor;
	}

	// Radio Button Chosen/Not Chosen
	public void setIsCircleChoosen(boolean isCircleChoosen) {
		this.isCircleChoosen = isCircleChoosen;
		String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
		circleImage.setImage(new Image(imageToSet));
	}

	public boolean getIsCircleChoosen() {
		return this.isCircleChoosen;
	}

}
