package csce.programstudio.mancala;

import java.util.ArrayList;
import java.util.Random;

public class Board {

	private ArrayList<House> boardComponents;
	private User player1;
	private User player2;
	private int numPlayerHouses;
	private int numSeedsPerHouse;
	private boolean randomSeedCount;
	private int currentPlayer;
	private String errorMessage;
	
	public Board(Board b) {
		this.boardComponents = new ArrayList<House>();
		// copy player 1 houses
		for(int i = 0; i < (b.getBoardComponents().size()/2)-1; ++i) {
			House currentHouse = b.getBoardComponents().get(i);
			this.boardComponents.add(new House(currentHouse.getNumSeeds(), currentHouse.getOwner()));
		}
		Store player1Store = (Store) b.getBoardComponents().get((b.getBoardComponents().size()/2)-1);
		this.boardComponents.add(new Store(player1Store.getNumSeeds(), player1Store.getOwner()));
		
		// copy player 2 houses
		for(int i = b.getBoardComponents().size()/2; i < (b.getBoardComponents().size())-1; ++i) {
			House currentHouse = b.getBoardComponents().get(i);
			this.boardComponents.add(new House(currentHouse.getNumSeeds(), currentHouse.getOwner()));
		}
		Store player2Store = (Store) b.getBoardComponents().get(b.getBoardComponents().size()-1);
		this.boardComponents.add(new Store(player2Store.getNumSeeds(), player2Store.getOwner()));
		
		this.player1 = new User(b.player1);
		this.player2 = new User(b.player2);
		this.numPlayerHouses = b.numPlayerHouses;
		this.numSeedsPerHouse = b.numSeedsPerHouse;
		this.currentPlayer = b.currentPlayer;
		
	}

	public Board(int numPlayerHouses, int numSeedsPerHouse, boolean randomSeedCount) {
		ArrayList<House> boardComponents = new ArrayList<House>();
		ArrayList<House> player1Houses = new ArrayList<House>();
		ArrayList<House> player2Houses = new ArrayList<House>();
		setNumPlayerHouses(numPlayerHouses);
		setNumSeedsPerHouse(numSeedsPerHouse);
		setRandomSeedCount(randomSeedCount);
		currentPlayer = 0;
		
		if(!randomSeedCount) {
			for(int i = 0; i < numPlayerHouses; ++i) {
				player1Houses.add(new House(numSeedsPerHouse, "Player 1"));
				player2Houses.add(new House(numSeedsPerHouse, "Player 2"));
			}
		} else {
			Random rand = new Random();
			
			int[] playerSeedVals = new int[numPlayerHouses];
			
			// initialize all seedVals to 0
			for (int i = 0; i < numPlayerHouses; ++i) {
				playerSeedVals[i] = 0;
			}
			
			// generate player 1 and 2's seed distribution
			int numSeedsRemaining = numSeedsPerHouse*numPlayerHouses;
			int currHouse = 0;
			while (numSeedsRemaining > 0) {
				int addSeed = rand.nextInt(2);
				if (addSeed == 1) {
					playerSeedVals[currHouse] += 1;
					--numSeedsRemaining;
				}
				++currHouse;
				if (currHouse >= numPlayerHouses) {
					currHouse = 0;
				}
			}
			
			// add player 1 and 2's seeds to houses
			for(int i = 0; i < numPlayerHouses; ++i) {
				player1Houses.add(new House(playerSeedVals[i], "Player 1"));
				player2Houses.add(new House(playerSeedVals[i], "Player 2"));
			}
		}
		
		// create player 1 and 2
		player1 = new User("Player 1", player1Houses, new Store("Player 1"));
		player2 = new User("Player 2", player2Houses, new Store("Player 2"));
		
		// set opponents
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		
		for(int i = 0; i < player1Houses.size(); ++i) {
			boardComponents.add(player1Houses.get(i));
		}
		boardComponents.add(player1.getStore());

		for(int i = 0; i < player2Houses.size(); ++i) {
			boardComponents.add(player2Houses.get(i));
		}
		boardComponents.add(player2.getStore());
		
		// finalize components
		setBoardComponents(boardComponents);	
	}
	
	public Board(int numPlayerHouses, int numSeedsPerHouse, ArrayList<Integer> randomSeeds) {
		ArrayList<House> boardComponents = new ArrayList<House>();
		ArrayList<House> player1Houses = new ArrayList<House>();
		ArrayList<House> player2Houses = new ArrayList<House>();
		setNumPlayerHouses(numPlayerHouses);
		setNumSeedsPerHouse(numSeedsPerHouse);
		setRandomSeedCount(randomSeedCount);
		currentPlayer = 0;
		
		
		
		
		// add player 1 and 2's seeds to houses
		for(int i = 0; i < numPlayerHouses; ++i) {
			player1Houses.add(new House(randomSeeds.get(i), "Player 1"));
			player2Houses.add(new House(randomSeeds.get(i), "Player 2"));
		}
		
		// create player 1 and 2
		player1 = new User("Player 1", player1Houses, new Store("Player 1"));
		player2 = new User("Player 2", player2Houses, new Store("Player 2"));
		
		// set opponents
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		
		for(int i = 0; i < player1Houses.size(); ++i) {
			boardComponents.add(player1Houses.get(i));
		}
		boardComponents.add(player1.getStore());

		for(int i = 0; i < player2Houses.size(); ++i) {
			boardComponents.add(player2Houses.get(i));
		}
		boardComponents.add(player2.getStore());
		
		// finalize components
		setBoardComponents(boardComponents);	
	}
	
	public ArrayList<House> getBoardComponents() {
		return boardComponents;
	}

	public void setBoardComponents(ArrayList<House> boardComponents) {
		this.boardComponents = boardComponents;
	}

	public User getPlayer1() {
		return player1;
	}

	public void setPlayer1(User player1) {
		this.player1 = player1;
	}

	public User getPlayer2() {
		return player2;
	}

	public void setPlayer2(User player2) {
		this.player2 = player2;
	}
	
	public void setCurrentPlayer(int i) {
		this.currentPlayer = i;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getNumPlayerHouses() {
		return numPlayerHouses;
	}

	public void setNumPlayerHouses(int numSeedsPerHouse) {
		this.numPlayerHouses = numSeedsPerHouse;
	}

	public int getNumSeedsPerHouse() {
		return numSeedsPerHouse;
	}

	public void setNumSeedsPerHouse(int numSeedsPerHouse) {
		this.numSeedsPerHouse = numSeedsPerHouse;
	}
	
	public boolean isRandomSeedCount() {
		return randomSeedCount;
	}

	public void setRandomSeedCount(boolean randomSeedCount) {
		this.randomSeedCount = randomSeedCount;
	}

}
