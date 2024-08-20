package model;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//*** This Class for Styling Summary Card That Used in HomePage Class ***//
public class SummaryCard extends HBox {
	// Paths
	protected final String CARD_ICON = "/model/resources/competitionCardSymbol.png";
	protected final String FONT_PATH = "model/resources/Roboto-Light.ttf";
	
	protected VBox vbox;
	protected Text competitionName;
	protected boolean status;
	protected Text statusType;
	protected Text topCompetitor;
	protected Text numOfCompetitors;
	protected ImageView imageView;

	public SummaryCard(String competitionName, boolean status, String topCompetitor, int numOfCompetitors) {
		this.competitionName = new Text(competitionName);
		this.competitionName.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.status = status;

		if (status == true)
			this.statusType = new Text("Status: Active");
		else
			this.statusType = new Text("Status: Done");
		this.topCompetitor = new Text("Highest Rank: " + topCompetitor);
		this.numOfCompetitors = new Text("Number of competitors: " + numOfCompetitors);

		this.competitionName.setFill(Color.WHITE);
		this.statusType.setStyle("-fx-fill: #1f8efa");
		this.topCompetitor.setStyle("-fx-fill: #1f8efa");
		this.numOfCompetitors.setStyle("-fx-fill: #1f8efa");

		createCard();
	}

	public void createCard() {
		setMinHeight(100);
		setMaxHeight(100);
		setMinWidth(360);
		setMaxWidth(360);
		imageView = new ImageView(new Image(CARD_ICON));
		vbox = new VBox();
		vbox.getChildren().addAll(competitionName, statusType, topCompetitor, numOfCompetitors);
		setMargin(imageView, new Insets(20));
		setMargin(vbox, new Insets(5, 30, 10, 0));
		setStyle("-fx-background-color: #2f3b52;" + "-fx-border-radius: 9;" + "-fx-background-radius: 9;");
		getChildren().addAll(imageView, vbox);

	}
}
