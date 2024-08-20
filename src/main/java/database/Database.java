package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import GUI.CompetitionPage;

public class Database {
	protected ArrayList<Competition> competitions;

	public Database() {
		competitions = new ArrayList<>();
	}

	public Database(File excelFile) throws IOException {
		this();
		readExcel(excelFile);
	}

	public Database(String excelFileAbsPath) throws IOException {
		this();
		readExcel(new File(excelFileAbsPath));
	}

	public void readExcel(File file) throws IOException {
		competitions.clear();
		FileInputStream fileStream = new FileInputStream(file);
		Workbook wb = new XSSFWorkbook(fileStream);
		DataFormatter formatter = new DataFormatter(); // class for converting cells into Strings

		Iterator<Sheet> sheets = wb.sheetIterator();
		while (sheets.hasNext()) {
			Sheet sheet = sheets.next();
			Iterator<Row> rows = sheet.rowIterator();

			// Parsing the competition info
			String compName, compLink;
			Boolean teams;
			Date compDate;

			compName = formatter.formatCellValue(rows.next().getCell(1));
			compLink = formatter.formatCellValue(rows.next().getCell(1));
			compDate = new Date(formatter.formatCellValue(rows.next().getCell(1)));
			teams = formatter.formatCellValue(rows.next().getCell(4)).compareTo("team") == 0;

			Competition newComp = createCompetition(compName, compLink, compDate, teams);

			if (teams) {
				int teamNumber = 0, rank = -1;
				String teamName = "";
				Team team = null;
				
				while (rows.hasNext()) { // while there are more teams add a new team
                    Row row = rows.next();
					teamName = formatter.formatCellValue(row.getCell(5));
					teamNumber = Integer.parseInt(formatter.formatCellValue(row.getCell(4)));
					try {
						rank = Integer.parseInt(formatter.formatCellValue(row.getCell(6)));
					} catch (NumberFormatException e) {
						rank = -1;
					}
					team = createTeam(teamName, rank, newComp); // creates a team and adds it to the competition

					// while the team number has not changed (same team) add new student
					while (Integer.parseInt(formatter.formatCellValue(row.getCell(4))) == teamNumber) {
						String studentName = formatter.formatCellValue(row.getCell(2));
						int studentId = Integer.parseInt(formatter.formatCellValue(row.getCell(1)));
						String studentMajor = formatter.formatCellValue(row.getCell(3));

						createStudent(studentName, studentMajor, studentId, team);
						if (rows.hasNext())
							row = rows.next();
						else
							break;
					}
				}

			} else {
				while (rows.hasNext()) {
					Row row = rows.next();
					String studentName = formatter.formatCellValue(row.getCell(2));
					int studentId = Integer.parseInt(formatter.formatCellValue(row.getCell(1)));
					String studentMajor = formatter.formatCellValue(row.getCell(3));
					int studentRank;
					try {
						studentRank = Integer.parseInt(formatter.formatCellValue(row.getCell(4)));
					} catch (NumberFormatException e) {
						studentRank = -1;
					}
					createStudent(studentName, studentMajor, studentId, studentRank, newComp); // creates a student and
																								// adds it to the
																								// competition
				}
			}
		}
		wb.close();
	}

	public void updateExcel() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Iterator<Competition> comps = competitions.iterator();

		while (comps.hasNext()) {
			Competition comp = comps.next();
			Sheet sheet = workbook.createSheet(comp.getName()); // ! This is where I realize the competitions have
																// organizers...
			CellStyle style = sheet.getWorkbook().createCellStyle();
			style.setBorderBottom(BorderStyle.MEDIUM);
			style.setBorderTop(BorderStyle.MEDIUM);
			style.setBorderRight(BorderStyle.MEDIUM);
			style.setBorderLeft(BorderStyle.MEDIUM);

			// writing competition info
			Row row;
			row = sheet.createRow(0);
			row.createCell(0).setCellValue("Competition Name");
			row.createCell(1).setCellValue(comp.getName());
			row = sheet.createRow(1);
			row.createCell(0).setCellValue("Competition Link");
			row.createCell(1).setCellValue(comp.getWebsite());
			row = sheet.createRow(2);
			row.createCell(0).setCellValue("Competition date");
			row.createCell(1).setCellValue(comp.getDueDate().getDate() + "/" + (comp.getDueDate().getMonth() + 1) + "/"
					+ (comp.getDueDate().getYear() + 1900)); //

			// Writing the Participant info
			if (comp.getTeams()) { // teams competition
				row = sheet.createRow(4); // row.setRowStyle(style);
				row.createCell(1).setCellValue("Student ID");
				row.createCell(2).setCellValue("Student Name");
				row.createCell(3).setCellValue("Major");
				row.createCell(4).setCellValue("team");
				row.createCell(5).setCellValue("Team Name");
				row.createCell(6).setCellValue("Rank");

				int stuCounter = 1, teamConter = 1;
				Participant[] teams = comp.getParticipants();
				for (Participant teamP : teams) {
					Team team = (Team) teamP;
					Student[] members = team.getMembers();
					for (Student member : members) {
						row = sheet.createRow(4 + stuCounter); // row.setRowStyle(style);
						row.createCell(0).setCellValue(stuCounter);
						row.createCell(1).setCellValue(member.getId());
						row.createCell(2).setCellValue(member.getName());
						row.createCell(3).setCellValue(member.getMajor());
						row.createCell(4).setCellValue(teamConter);
						row.createCell(5).setCellValue(team.getName());
						if (team.rankDetermined())
							row.createCell(6).setCellValue(team.getRank());
						else
							row.createCell(6).setCellValue("-");
						stuCounter++;
					}
					teamConter++;
				}
			} else { // singles competition
				row = sheet.createRow(4); // row.setRowStyle(style);
				row.createCell(1).setCellValue("Student ID");
				row.createCell(2).setCellValue("Student Name");
				row.createCell(3).setCellValue("Major");
				row.createCell(4).setCellValue("Rank");

				int counter = 1;
				Participant[] students = comp.getParticipants();

				for (Participant stuP : students) {
					Student stu = (Student) stuP;
					row = sheet.createRow(4 + counter); // row.setRowStyle(style);
					row.createCell(0).setCellValue(counter);
					row.createCell(1).setCellValue(stu.getId());
					row.createCell(2).setCellValue(stu.getName());
					row.createCell(3).setCellValue(stu.getMajor());
					if (stu.rankDetermined())
						row.createCell(4).setCellValue(stu.getRank());
					else
						row.createCell(4).setCellValue("-");
					counter++;
				}
			}
		}

		try (FileOutputStream outputStream = new FileOutputStream("Competitions Participations.xlsx")) {
			workbook.write(outputStream);
		}
		workbook.close();
	}

	// Returns an array of all current competitions in the database
	public Competition[] getCompetitions() {
		return competitions.toArray(new Competition[competitions.size()]);
	}

	public ArrayList<Competition> getCompetitionsArrayList() {
		return competitions;
	}

	
	// Return an array of students or teams depending on the competition
	public Participant[] getParticipants(Competition comp) { // * Repeated method
		return comp.participants.toArray(new Participant[comp.participants.size()]);
	}
	
	public int getParticipantsNumber(Competition comp) {
		return getParticipants(comp).length;
	}

	public Student[] getStudents(Competition comp) {
		return (Student[]) comp.participants.toArray(new Participant[comp.participants.size()]);
	}

	public Team[] getTeams(Competition comp) {
		return (Team[]) comp.participants.toArray(new Participant[comp.participants.size()]);
	}

	public void addStudentToCompetition(Student student, Competition comp) {
		comp.participants.add(student);
	}

	public void addTeamToCompetition(Team team, Competition comp) {
		comp.participants.add(team);
	}

	public void addStudentToTeam(Student student, Team team) {
		team.members.add(student);
	}

	public void removeStudentFromTeam(Student stu, Team team) {
		team.members.remove(stu);
	}

	public void removeStudentFromCompetition(Student stu, Competition comp) {
		comp.participants.remove(stu);
	}

	public void removeTeamFromCompetition(Team team, Competition comp) {
		comp.participants.remove(team);
	}

	// Creates a Student object then adds it to a team
	public Student createStudent(String name, String major, int id, Team team) {
		Student stu = new Student(name, major, id, team.rank);
		team.members.add(stu);
		return stu;
	}

	// Creates a Student object then adds it to a competition
	public Student createStudent(String name, String major, int id, int rank, Competition comp) {
		if (comp.teams)
			throw new ClassCastException("You are trying to add a student to a team competition");
		Student stu = new Student(name, major, id, rank);
		comp.participants.add(stu);
		return stu;
	}

	// Create a new team and add it to a competition
	public static Team createTeam(String name, int rank, Competition comp) {
		Team team = new Team(name, rank);
		comp.participants.add(team);
		return team;
	}

	public Competition createCompetition(String name, String website, Date dueDate, Boolean teams) {
		Competition comp = new Competition(name, website, dueDate, teams);
		competitions.add(comp);
		return comp;
	}


	public void createCompetition(Competition comp) {
		competitions.add(comp);
	}

	public void removeCompetition(Competition comp) {
		competitions.remove(comp);
	}

	
	
	/*
	 * //Incase we need to add a student to a competiton after the student is
	 * created. doubt it will be useful public void addStudentToTeam(Student std,
	 * Team team){ team.members.add(std);
	 * 
	 * }
	 * 
	 * //Adds a student to a student competition or a team to a team competition
	 * public void addToCompetition(Participant part, Competition comp){ if(part
	 * instanceof Student && !comp.teams) comp.participants.add(part); else if(part
	 * instanceof Team && comp.teams) comp.participants.add(part); else{
	 * if(comp.teams) throw new
	 * ClassCastException("You are trying to add a student to a team competition");
	 * else throw new
	 * ClassCastException("You are trying to add a team to an individuals competition"
	 * ); } }
	 * 
	 * //Creates a Student object public Student createStudent(String name, String
	 * major, int id, int rank){ return new Student(name, major, id, rank); }
	 */
}
