package csce.programstudio.mancala;

public class Store extends House{

	public Store() {
		super();
	}
	
	public Store(String owner) {
		super(0, owner);
	}
	
	public Store(int numSeeds, String owner) {
		super(numSeeds, owner);
	}
	
}
