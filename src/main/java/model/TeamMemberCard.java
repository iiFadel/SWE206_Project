package model;


import GUI.EditTeamMemberDetailsPage;
import database.Student;
import database.Team;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//*** This Class for Styling Members Card That Used in TeamDetails Class ***//
public class TeamMemberCard extends HBox {
	// Paths
	protected final String FONT_PATH = "/model/resources/Roboto-Bold.ttf";

	// CSS Styling
	protected final String EDIT_BUTTON_PATH = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/editButton.png')";
	protected final String EDIT_BUTTON_PRESSED_PATH = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/editButtonPressed.png')";

	protected Text studentName, studentID, major;
	protected HBox studentNameBox, studentIDBox, majorBox, editBox;
	protected ButtonStyler edit;
	protected EditTeamMemberDetailsPage editTeamMemberDetailsPage;

	public TeamMemberCard(Team team, Student student) {
		this.studentName = new Text(student.getName());
		this.studentName.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.studentName.setFill(Color.WHITE);

		this.studentID = new Text("ID: " + student.getId());
		this.studentID.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.studentID.setFill(Color.WHITE);

		this.major = new Text("Major: " + student.getMajor());
		this.major.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.major.setFill(Color.WHITE);

		this.edit = new ButtonStyler("", 30, 30, 0, EDIT_BUTTON_PRESSED_PATH, EDIT_BUTTON_PATH);
		edit.setOnAction(e -> {
			editTeamMemberDetailsPage = new EditTeamMemberDetailsPage(team, student);
			editTeamMemberDetailsPage.switchScene(editTeamMemberDetailsPage.getScene());
		});

		createCard();

	}

	public void createCard() {
		studentNameBox = new HBox();
		studentIDBox = new HBox();
		majorBox = new HBox();
		editBox = new HBox();

		studentNameBox.setMinHeight(75);
		studentNameBox.setMinWidth(240);
		studentNameBox.getChildren().add(studentName);

		studentIDBox.setMinHeight(75);
		studentIDBox.setMinWidth(240);
		studentIDBox.getChildren().add(studentID);

		majorBox.setMinHeight(75);
		majorBox.setMinWidth(255);
		majorBox.getChildren().add(major);

		editBox.setMinHeight(75);
		editBox.setMinWidth(40);
		editBox.getChildren().add(edit);

		setMinHeight(75);
		setMaxHeight(75);
		setMinWidth(890);
		setMaxWidth(890);
		setStyle("-fx-background-color: #2f3b52;" + "-fx-border-radius: 9;" + "-fx-background-radius: 9;");
		getChildren().addAll(studentNameBox, studentIDBox, majorBox, editBox);
		setMargin(studentNameBox, new Insets(23, 0, 0, 10));
		setMargin(studentIDBox, new Insets(23, 0, 0, 0));
		setMargin(majorBox, new Insets(23, 0, 0, 0));
		setMargin(edit, new Insets(23, 0, 0, 0));

	}

}
