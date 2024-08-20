package Application;
import GUI.*;
import database.*;
import javafx.scene.Scene;
import model.UpdateRankAlertBox;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Controller {
    public static Desktop desktop;
	public static Database database;
	public static ArrayList<Competition> competitions;

    // Generating The Database
    public static void generateDatabase() throws IOException {
        File inf = new File("Competitions Participations.xlsx");
        database = new Database(inf.getAbsolutePath());
        competitions = database.getCompetitionsArrayList();
    }

	public static void sendEmail(Competition comp) throws Exception {
		if (comp.getTeams()) {
			for (int i = 0; i < comp.getParticipantsNumber(); i++) {
				if (comp.getParticipants()[i].getRank() < 4 && comp.getParticipants()[i].getRank() > 0) {
					sendEmail(comp, (Team) comp.getParticipants()[i]);
				}
			}
		} else {
			for (int i = 0; i < comp.getParticipantsNumber(); i++) {
				if (comp.getParticipants()[i].getRank() < 4 && comp.getParticipants()[i].getRank() > 0) {
					sendEmail(comp, (Student) comp.getParticipants()[i]);
				}
			}
		}
	}

    protected static void sendEmail(Competition comp, Student student) throws Exception {
		String studentName, studentID, competitionName;
		String mailTo, subject, cc, body;

		studentName = student.getName();
		studentID = student.getId() + "";
		competitionName = comp.getName();

		mailTo = "s" + studentID + "@kfupm.edu.sa";
		subject = "Conguratulation";
		cc = "-";
		body = URLEncoder.encode("Dear " + studentName + ",\n\nConguratulation on your achievement in "
				+ competitionName + "."
				+ " This achievement is deeply appreciated by the unversity and we will announce it in the approbrite medias."
				+ "\nIn case you have Photos you want to share with the news post, reply to this email with the photos."
				+ "\n\nRegards and Congrats,\nKFUPM News Team", StandardCharsets.UTF_8);
		body = body.replace("+", "%20");

		String mailURIStr = String.format("mailto:%s?subject=%s&cc=%s&body=%s", mailTo, subject, cc, body);
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			URI mailcontents = new URI(mailURIStr);
			desktop.mail(mailcontents);
		} else {
			System.out.println("wat");
		}
	}

	protected static void sendEmail(Competition comp, Team team) throws Exception {
		ArrayList<String> teamIDs;
		String teamName, competitionName;
		String mailTo = "", subject, cc, body;

		teamIDs = new ArrayList<>();
		for (int i = 0; i < team.getMemberNumber(); i++) {
			teamIDs.add(team.getMembers()[i].getId() + "");
		}

		for (int i = 0; i < teamIDs.size(); i++) {
			if (i == teamIDs.size() - 1)
				mailTo += "s" + teamIDs.get(i) + "@kfupm.edu.sa";
			else
				mailTo += "s" + teamIDs.get(i) + "@kfupm.edu.sa" + ",";

		}

		teamName = team.getName();
		competitionName = comp.getName();
		subject = "Conguratulation";
		cc = "-";
		body = URLEncoder.encode("Dear " + teamName + ",\n\nConguratulation on your achievement in " + competitionName
				+ "."
				+ " This achievement is deeply appreciated by the unversity and we will announce it in the approbrite medias."
				+ "\nIn case you have Photos you want to share with the news post, reply to this email with the photos."
				+ "\n\nRegards and Congrats,\nKFUPM News Team", StandardCharsets.UTF_8);
		body = body.replace("+", "%20");

		String mailURIStr = String.format("mailto:%s?subject=%s&cc=%s&body=%s", mailTo, subject, cc, body);
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			URI mailcontents = new URI(mailURIStr);
			desktop.mail(mailcontents);
		} else {
			System.out.println("wat");
		}
	}

    public static Scene showHome(){
        return HomePage.getScene();
    }

	public static Scene showCompetitions() {
		return CompetitionPage.getScene();
	}

	public static Scene showWebsite(String website) {
        BrowserPage.openWebsite(website);
		return BrowserPage.getScene();
	}

    public static Scene showWebsite() {
		return BrowserPage.getScene();
	}

	public static Scene showTeamDetails() {
		return TeamDetails.getScene();
	}

	public static void checkStatus() {
        Competition[] competitionsWithMissingRanks = checkMissingRanks(database.getCompetitions());
		if (competitionsWithMissingRanks.length != 0) {
			UpdateRankAlertBox
					.display(competitionsWithMissingRanks);
		}

	}
    protected static Competition[] checkMissingRanks(Competition[] competitions) {
		ArrayList<Competition> competitionsOb = new ArrayList<>();
		for (int i = 0; i < competitions.length; i++) {
			for (int j = 0; j < competitions[i].getParticipants().length; j++) {
				if (competitions[i].getParticipants()[j].getRank() == -1
						&& competitions[i].isActive() == false) {
					competitionsOb.add(competitions[i]);
					break;
				}
			}
		}
		return competitionsOb.toArray(new Competition[competitionsOb.size()]);
	}

}
