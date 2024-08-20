package database;

public class Student extends Participant {
	protected int id;
	protected String major;


	// Enter rank -1 if rank is unavailable
	protected Student(String name, String major, int id, int rank) {
		super(name, rank);
		this.id = id;
		this.major = major;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getId() {
		return id;
	}

	public String getMajor() {
		return major;
	}

	@Override
	public String toString() {
		return super.toString() + " " + id + " " + major;
	}
}
