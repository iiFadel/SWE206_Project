package database;

public abstract class Participant {
	protected String name;
	protected int rank;

	public Participant(String name, int rank) {
		this.name = name;
		this.rank = rank;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public int getRank() {
		return rank;
	}

	public boolean rankDetermined() {
		return rank != -1;
	}

	public String toString() {
		return name + " " + rank;
	}
}
