package model;

import Application.Controller;
import GUI.TeamDetails;
import database.Team;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//*** This Class for Styling Team Card That Used in CompetitionDetailsPageTeam Class ***//
public class TeamCard extends HBox {

	// Paths
	protected final String FONT_PATH = "/model/resources/Roboto-Bold.ttf";
	
	// CSS Styling
	protected final String DETAILS_BUTTON_PATH = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/detailsButton.png')";
	protected final String DETAILS_BUTTON_PRESSED_PATH = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/detailsButtonPressed.png')";
	
	protected ButtonStyler details;
	protected Text teamName, members, mostMajors, rank;
	protected HBox competitionNameBox, membersBox, mostMajorsBox, rankBox, detailsBox;

	public TeamCard(Team team) {
		this.teamName = new Text(team.getName());
		this.teamName.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.teamName.setFill(Color.WHITE);

		this.members = new Text("Members: " + team.getMemberNumber());
		this.members.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.members.setFill(Color.WHITE);

		this.mostMajors = new Text("Most Majors: " + team.getMostMajor());
		this.mostMajors.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.mostMajors.setFill(Color.WHITE);

		if(team.getRank()==-1) {
			this.rank = new Text("Rank: -");
			this.rank.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
			this.rank.setFill(Color.WHITE);
		}else {
			this.rank = new Text("Rank: " + team.getRank());
			this.rank.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
			this.rank.setFill(Color.WHITE);
		}

		this.details = new ButtonStyler(" ", 16, 16, 0, DETAILS_BUTTON_PRESSED_PATH, DETAILS_BUTTON_PATH);
		details.setOnAction(e -> {
			TeamDetails teamDetails = new TeamDetails(team);
			teamDetails.switchScene(Controller.showTeamDetails());
		});
		createCard();

	}

	public void createCard() {
		competitionNameBox = new HBox();
		membersBox = new HBox();
		mostMajorsBox = new HBox();
		rankBox = new HBox();
		detailsBox = new HBox();
		competitionNameBox.setMinHeight(75);
		competitionNameBox.setMinWidth(240);
		competitionNameBox.getChildren().add(teamName);

		membersBox.setMinHeight(75);
		membersBox.setMinWidth(240);
		membersBox.getChildren().add(members);

		mostMajorsBox.setMinHeight(75);
		mostMajorsBox.setMinWidth(255);
		mostMajorsBox.getChildren().add(mostMajors);

		rankBox.setMinHeight(75);
		rankBox.setMinWidth(115);
		rankBox.getChildren().add(rank);
		detailsBox.setMinHeight(75);
		detailsBox.setMinWidth(40);
		detailsBox.getChildren().add(details);

		setMinHeight(75);
		setMaxHeight(75);
		setMinWidth(890);
		setMaxWidth(890);
		setStyle("-fx-background-color: #2f3b52;" + "-fx-border-radius: 9;" + "-fx-background-radius: 9;");
		getChildren().addAll(competitionNameBox, membersBox, mostMajorsBox, rankBox, detailsBox);
		setMargin(teamName, new Insets(23, 0, 0, 10));
		setMargin(members, new Insets(23, 0, 0, 0));
		setMargin(mostMajors, new Insets(23, 0, 0, 0));
		setMargin(rank, new Insets(23, 0, 0, 0));
		setMargin(details, new Insets(30, 0, 0, 0));

	}

	public ButtonStyler getDetails() {
		return details;
	}

	public void setDetails(ButtonStyler details) {
		this.details = details;
	}
}
