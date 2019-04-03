package csce.programstudio.mancala;

import java.util.ArrayList;

public class User {

	private String name;
	private ArrayList<House> houses;
	private Store store;
	private boolean AI;
	private User opponent;	// get rid of possibly
	
	public User() {
		setStore(new Store());
		setName("UNKNOWN");
		setHouses(new ArrayList<House>());
		setAI(false);
		setOpponent(null);
	}
	
	public User(User u) {
		this.name = u.name;
		this.houses = new ArrayList<House>();
		for(int i = 0; i < u.houses.size(); ++i) {
			this.houses.add(new House(u.houses.get(i)));
		}
		this.store = new Store(u.getStore().getNumSeeds(), u.getStore().getOwner());
		this.AI = u.isAI();
		this.opponent = null;
	}
	
	public User(String name, ArrayList<House> houses, Store store) {
		setName(name);
		setHouses(houses);
		setStore(store);
	}
	
	public User(String name, ArrayList<House> houses, Store store, boolean AI) {
		setName(name);
		setHouses(houses);
		setStore(store);
		setAI(AI);
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<House> getHouses() {
		return houses;
	}

	public void setHouses(ArrayList<House> houses) {
		this.houses = houses;
	}
	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public boolean isAI() {
		return AI;
	}

	public void setAI(boolean aI) {
		AI = aI;
	}

	public User getOpponent() {
		return opponent;
	}

	public void setOpponent(User opponent) {
		this.opponent = opponent;
	}
	
}
