package model;


import GUI.EditStudentDetailsPage;
import database.Competition;
import database.Student;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//*** This Class for Styling Student Card That Used in CompetitionDetailsPageStudent Class ***//
public class StudentCard extends HBox {
	// Paths
	protected final String CARD_ICON = "/model/resources/competitionCardSymbol.png";
	protected final String FONT_PATH = "/model/resources/Roboto-Bold.ttf";

	// CSS Styling
	protected final String EDIT_BUTTON_PATH = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/editButton.png')";
	protected final String EDIT_BUTTON_PRESSED_PATH = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/editButtonPressed.png')";

	protected ButtonStyler edit;
	protected Text studentName, studentID, major, rank;
	protected HBox studentNameBox, studentIDBox, majorBox, rankBox, editBox;
	protected EditStudentDetailsPage editStudentDetailsPage;

	public StudentCard(Competition comp, Student student) {
		this.studentName = new Text(student.getName());
		this.studentName.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.studentName.setFill(Color.WHITE);

		this.studentID = new Text("ID: " + student.getId());
		this.studentID.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.studentID.setFill(Color.WHITE);

		this.major = new Text("Major: " + student.getMajor());
		this.major.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
		this.major.setFill(Color.WHITE);

		if(student.getRank()==-1) {
			this.rank = new Text("Rank: -");
			this.rank.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
			this.rank.setFill(Color.WHITE);
		}else {
			this.rank = new Text("Rank: " + student.getRank());
			this.rank.setFont(Font.font(FONT_PATH, FontWeight.BOLD, 25));
			this.rank.setFill(Color.WHITE);
		}
		

		this.edit = new ButtonStyler("", 30, 30, 0, EDIT_BUTTON_PRESSED_PATH, EDIT_BUTTON_PATH);
		edit.setOnAction(e -> {
			editStudentDetailsPage = new EditStudentDetailsPage(comp, student);
			editStudentDetailsPage.switchScene(editStudentDetailsPage.getScene());
		});

		createCard();

	}

	public void createCard() {
		studentNameBox = new HBox();
		studentIDBox = new HBox();
		majorBox = new HBox();
		rankBox = new HBox();
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

		rankBox.setMinHeight(75);
		rankBox.setMinWidth(115);
		rankBox.getChildren().add(rank);

		editBox.setMinHeight(75);
		editBox.setMinWidth(40);
		editBox.getChildren().add(edit);

		setMinHeight(75);
		setMaxHeight(75);
		setMinWidth(890);
		setMaxWidth(890);
		setStyle("-fx-background-color: #2f3b52;" + "-fx-border-radius: 9;" + "-fx-background-radius: 9;");
		getChildren().addAll(studentNameBox, studentIDBox, majorBox, rankBox, editBox);
		setMargin(studentName, new Insets(23, 0, 0, 10));
		setMargin(studentID, new Insets(23, 0, 0, 0));
		setMargin(major, new Insets(23, 0, 0, 0));
		setMargin(rank, new Insets(23, 0, 0, 0));
		setMargin(edit, new Insets(23, 0, 0, 0));

	}

	public ButtonStyler getDetails() {
		return edit;
	}

	public void setDetails(ButtonStyler details) {
		this.edit = details;
	}
}
