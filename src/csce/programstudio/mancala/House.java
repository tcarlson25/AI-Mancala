package csce.programstudio.mancala;

public class House {

	private int numSeeds;
	private String owner;
	
	public House() {
		setNumSeeds(0);
		setOwner("UNKNOWN");
	}
	
	public House(int numSeeds, String owner) {
		setNumSeeds(numSeeds);
		setOwner(owner);
	}
	
	public House(House h) {
		this.numSeeds = h.numSeeds;
		this.owner = h.owner;
	}
	
	public int getNumSeeds() {
		return numSeeds;
	}
	public void setNumSeeds(int numSeeds) {
		this.numSeeds = numSeeds;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

}
