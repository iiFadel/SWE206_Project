package database;

import java.util.Date;
import GUI.CompetitionDetailsPageStudent;
import GUI.CompetitionDetailsPageTeam;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ButtonStyler;
import java.util.ArrayList;
import java.util.Iterator;

//*** This Class for Competitions ***//
public class Competition {

	protected String name, website, dueDateFormated, highestParticipantName;
	protected Date dueDate;
	protected Boolean teams; // is the competition team based
	protected ArrayList<Participant> participants;
	protected int participantsNumber;

    // CSS and Styling GUI
	protected static final String ACTIVE_STATUS_PATH = "model/resources/statusActive.png";
	protected static final String DONE_STATUS_PATH = "model/resources/statusDone.png";
	protected static final String DETAILS_BUTTON_PATH = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/detailsButton.png')";
	protected static final String DETAILS_BUTTON_PRESSED_PATH = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/detailsButtonPressed.png')";
	protected ImageView statusImage;
	protected ButtonStyler details;
	protected static CompetitionDetailsPageTeam competitionDetailsPageTeam;
	protected static CompetitionDetailsPageStudent competitionDetailsPageStudent;

	public Competition(String name, String website, Date dueDate, Boolean teams) {
		this.dueDate = dueDate;
		this.name = name;
		this.website = website;
		this.teams = teams;
		participants = new ArrayList<>();

		if (isActive()) {
			this.setStatusImage(new ImageView(new Image(getActiveStatusPath())));
		} else {
			this.setStatusImage(new ImageView(new Image(getDoneStatusPath())));
		}

		this.setDetails(new ButtonStyler(" ", 16, 16, 0, DETAILS_BUTTON_PRESSED_PATH, DETAILS_BUTTON_PATH));
		this.details.setOnAction(e -> {
			if (this.teams == true) {
				this.competitionDetailsPageTeam = new CompetitionDetailsPageTeam(this);
				competitionDetailsPageTeam.switchScene(competitionDetailsPageTeam.getScene());
			} else {
				this.competitionDetailsPageStudent = new CompetitionDetailsPageStudent(this);
				competitionDetailsPageStudent.switchScene(competitionDetailsPageStudent.getScene());
			}
		});

	}

	public String getDateString() {
		return dueDate.toString();

	}

	public Date getDueDate() {
		return dueDate;
	}

	public String getName() {
		return name;
	}

	public Boolean getTeams() {
		return teams;
	}

	public String getWebsite() {
		return website;
	}

	public Participant[] getParticipants() {
		return participants.toArray(new Participant[participants.size()]);
	}

	public String toString() {
		return name;

	}

	public int getParticipantsNumber() {
		return participants.size();
	}

	public String getHighestParticipantName() {
		if (participants.isEmpty()) {
			return "";
		} else {
			Iterator<Participant> itr = participants.iterator();
			Participant highestParticipant = itr.next();
			while (itr.hasNext()) {
				Participant nextPart = itr.next();
				if ((highestParticipant.getRank() == -1) || (nextPart.getRank() != -1 && nextPart.getRank() < highestParticipant.getRank()))
					highestParticipant = nextPart;
			}
            if(highestParticipant.getRank() != -1)
                return highestParticipant.getName();
            else
                return "";
		}
	}

	public ImageView getStatusImage() {
		return statusImage;
	}

	public void setStatusImage(ImageView statusImage) {
		this.statusImage = statusImage;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public ButtonStyler getDetails() {
		return details;
	}

	public void setDetails(ButtonStyler details) {
		this.details = details;
	}

	public String getDueDateFormated() {
		return dueDate.toString().substring(8, 10) + "-" + dueDate.toString().substring(4, 7) + "-"
				+ dueDate.toString().substring(24, 28);
	}

	public static String getActiveStatusPath() {
		return ACTIVE_STATUS_PATH;
	}

	public static String getDoneStatusPath() {
		return DONE_STATUS_PATH;
	}

	public boolean isActive() {
		Date todayDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);

		if ((this.dueDate).after(todayDate))
			return true;
		else
			return false;
	}

	
}