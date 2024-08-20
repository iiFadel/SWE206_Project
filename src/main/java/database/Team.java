package database;

import java.util.ArrayList;

public class Team extends Participant {
	protected ArrayList<Student> members;
	protected int membersNumber;
	protected String mostMajor;

	// Enter rank -1 if rank is unavailable
	public Team(String name, int rank) {
		super(name, rank);
		members = new ArrayList<>();
	}

	public Student[] getMembers() {
		return members.toArray(new Student[members.size()]);
	}

	public ArrayList<Student> getMembersArrayList() {
		return members;
	}

	public String toString() {
		StringBuilder s = new StringBuilder(super.toString());
		for (Student member : members) {
			s.append("\n" + member.toString());
		}
		return s.toString();

	}

	public int getMemberNumber() {
		return members.size();
	}

	public String getMostMajor() {
		return freq();
	}

	public String freq() {
		ArrayList<String> majors = new ArrayList<String>();
		for (int i = 0; i < members.size(); i++) {
			majors.add((this.members).get(i).getMajor());
		}

		String[] majorsArray = majors.toArray(new String[majors.size()]);
		boolean[] seen = new boolean[majorsArray.length];
		String major = null;
		int result_count = 0;
		for (int i = 0; i < majorsArray.length; i++) {
			if (!seen[i]) {
				seen[i] = true;
				int count = 1;
				for (int j = i + 1; j < majorsArray.length; j++) {
					if (!seen[j]) {
						if (majorsArray[i].equals(majorsArray[j])) {
							seen[j] = true;
							count++;
						}
					}
				}
				if (count > result_count) {
					result_count = count;
					major = majorsArray[i];
				}
			}
		}

		return major;
	}
}
