package csce.programstudio.mancala;

import java.util.ArrayList;

//All data and methods go in here
public class MancalaModel {

	private Board mancalaBoard;
	private int turnNumber;
	private int remainingTime;
	private int inputedTime;
	private Server mancalaServer;
	private Client mancalaClient;
	private MultiClientServer mancalaMultiClientServer;
	private String playerMove;
	private int playerNumber;
	
	public MancalaModel() {
		playerMove = "";
		mancalaClient = null;
		mancalaServer = null;
		mancalaMultiClientServer = null;
		mancalaBoard = null;
		turnNumber = 1;
		remainingTime = 0;
		inputedTime = 0;
	}
	
	public MancalaModel(MancalaModel mm) {
		//this.mancalaBoard = new Board(mm.mancalaBoard);
		this.turnNumber = mm.turnNumber;
		this.remainingTime = mm.remainingTime;
		this.inputedTime = mm.inputedTime;
		playerMove = "";
	}
	
	public Board getMancalaBoard() {
		return mancalaBoard;
	}

	public void setMancalaBoard(Board mancalaBoard) {
		this.mancalaBoard = mancalaBoard;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}
	
	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getInputedTime() {
		return inputedTime;
	}

	public void setInputedTime(int inputedTime) {
		this.inputedTime = inputedTime;
	}

	public Server getMancalaServer() {
		return mancalaServer;
	}

	public void setMancalaServer(Server mancalaServer) {
		this.mancalaServer = mancalaServer;
	}

	public MultiClientServer getMancalaMultiClientServer() {
		return mancalaMultiClientServer;
	}

	public void setMancalaMultiClientServer(MultiClientServer mancalaMultiClientServer) {
		this.mancalaMultiClientServer = mancalaMultiClientServer;
	}

	public Client getMancalaClient() {
		return mancalaClient;
	}

	public void setMancalaClient(Client mancalaClient) {
		this.mancalaClient = mancalaClient;
	}

	public String getPlayerMove() {
		return playerMove;
	}

	public void setPlayerMove(String playerMove) {
		this.playerMove = playerMove;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public String getGameDetails(boolean isFirstPlayer) {
		String gameDetails = "";
		String random = "";
		String firstPlayer = "";
		if(mancalaBoard.isRandomSeedCount()) {
			random = "R ";
			ArrayList<House> houses = mancalaBoard.getPlayer1().getHouses();
			for(int i = 0; i < houses.size(); ++i) {
				random += houses.get(i).getNumSeeds() + " ";
			}
			random = random.trim();
		} else {
			random = "S";
		}
		if(isFirstPlayer) {
			firstPlayer = "F";
		} else {
			firstPlayer = "S";
		}
		gameDetails += mancalaBoard.getNumPlayerHouses() + " " + mancalaBoard.getNumSeedsPerHouse() + " " 
				+ inputedTime + " " + firstPlayer + " " + random;
		
		return gameDetails.trim();
	}

	public void pieRule() {
		User player1 = mancalaBoard.getPlayer1();
		User player2 = mancalaBoard.getPlayer2();
		ArrayList<House> newBoardComponents = new ArrayList<House>();
		
		// switch player 1 and player 2
		User playerTemp = new User(player1.getName(), player1.getHouses(), player1.getStore());
//		player1.setName(player2.getName());
		player1.setHouses(player2.getHouses());
		for(int i = 0; i < player1.getHouses().size(); ++i) {
			player1.getHouses().get(i).setOwner(player1.getName());
		}
		player1.setStore(player2.getStore());
		player1.getStore().setOwner(player1.getName());
		
//		player2.setName(playerTemp.getName());
		player2.setHouses(playerTemp.getHouses());
		for(int i = 0; i < player2.getHouses().size(); ++i) {
			player2.getHouses().get(i).setOwner(player2.getName());
		}
		player2.setStore(playerTemp.getStore());
		player2.getStore().setOwner(player2.getName());
				
		mancalaBoard.setPlayer1(player1);
		mancalaBoard.setPlayer2(player2);
		
		// switch player 1 and player 2 components
		newBoardComponents.addAll(player1.getHouses());
		newBoardComponents.add(player1.getStore());
		newBoardComponents.addAll(player2.getHouses());
		newBoardComponents.add(player2.getStore());
		
		mancalaBoard.setBoardComponents(newBoardComponents);
		
		// switch current player
		mancalaBoard.setCurrentPlayer(1 - mancalaBoard.getCurrentPlayer());
	}
	
	// Returns error message if invalid move is attempted
	public String basicOnlineMove(int currentPlayer, int housePressedIndex, int playerNumber) {
		// currentPlayer = 0 -> Player 1
		// currentPlayer = 1 -> Player 2
		User currentUser;
		if(currentPlayer == 0) {
			currentUser = mancalaBoard.getPlayer1();
		} else {
			currentUser = mancalaBoard.getPlayer2();
		}
		
		// check if the server/client's turn depending on their playerNumber
		if(currentPlayer == playerNumber) {
			ArrayList<House> boardComponents = mancalaBoard.getBoardComponents();
			House housePressed = boardComponents.get(housePressedIndex);
			int currentHouseSeeds = -1;
			int newIndex = -1;
			int newHouseIndex = housePressedIndex;
			
			if(housePressed.getClass() != Store.class) {
				// Check if the house pressed belongs to the current player
				if(housePressed.getOwner() == currentUser.getName()) {
					int seedsInHousePressed = housePressed.getNumSeeds();
					// Check if there are seeds in the house pressed
					if(seedsInHousePressed != 0) {
						housePressed.setNumSeeds(0);
						while(seedsInHousePressed > 0) {
							// newIndex makes sure that the index wraps back to the front of the list
							newIndex = (newHouseIndex+1) % (boardComponents.size());
							House currentHouse = boardComponents.get(newIndex);
							// Don't put your seeds in the other player's store
							if((currentHouse.getClass() == Store.class) && (currentHouse.getOwner() != currentUser.getName())) {
								newHouseIndex++;
								continue;
							} else {
								currentHouseSeeds = currentHouse.getNumSeeds();
								currentHouse.setNumSeeds(currentHouseSeeds+1);
								newHouseIndex++;
								seedsInHousePressed--;
							}
						}
						mancalaBoard.setBoardComponents(boardComponents);
						playerMove += housePressedIndex + " ";
						
						if (isAdjacentHouseEmpty(currentUser, newIndex)) {
							adjacentHouseEmpty(currentUser, newIndex);
						}
						if (isCollectRemainingSeeds()) {
							collectRemainingSeeds(currentPlayer);
						}
						if (isGameOver()) {
							User winner = gameOver(false);
							if(mancalaServer != null) {
								mancalaServer.getHandler().sendMessage(playerMove.trim());
							} else if(mancalaClient != null) {
								mancalaClient.sendMessage(playerMove.trim());
							}
							playerMove = "";
							if(winner == null) {
								return "TIE";
							}
							return winner.getName();
						}
						if (isGoAgain(currentUser, newIndex) && !isGameOver()) {
							// return early so that currentPlayer is not updated
							if(mancalaBoard.getCurrentPlayer() == 1 && turnNumber == 2) {
								setTurnNumber(turnNumber + 1);
							}
							return "GOAGAIN";
						}
						setTurnNumber(turnNumber + 1);
						mancalaBoard.setCurrentPlayer(1 - mancalaBoard.getCurrentPlayer());
						if(mancalaServer != null) {
							mancalaServer.getHandler().sendMessage(playerMove.trim());
						} else if(mancalaClient != null) {
							mancalaClient.sendMessage(playerMove.trim());
						}
						playerMove = "";
						
					} else {
						return "You can't click on a house that has no seeds in it";
					}
				} else {
					return "The house you chose does not belong to you";
				}		
			} else {
				return "You cannot move seeds that are already in a store";
			}
		} else {
			return "Please wait until it is your turn";
		}
		return "";
	}
	
	// Returns error message if invalid move is attempted
	public String forceBasicMove(int currentPlayer, int housePressedIndex) {
		// currentPlayer = 0 -> Player 1
		// currentPlayer = 1 -> Player 2
		User currentUser;
		if(currentPlayer == 0) {
			currentUser = mancalaBoard.getPlayer1();
		} else {
			currentUser = mancalaBoard.getPlayer2();
		}
		
		ArrayList<House> boardComponents = mancalaBoard.getBoardComponents();
		House housePressed = boardComponents.get(housePressedIndex);
		int currentHouseSeeds = -1;
		int newIndex = -1;
		
		int seedsInHousePressed = housePressed.getNumSeeds();
		housePressed.setNumSeeds(0);
		while(seedsInHousePressed > 0) {
			// newIndex makes sure that the index wraps back to the front of the list
			newIndex = (housePressedIndex+1) % (boardComponents.size());
			House currentHouse = boardComponents.get(newIndex);
			// Don't put your seeds in the other player's store
			if((currentHouse.getClass() == Store.class) && (currentHouse.getOwner() != currentUser.getName())) {
				housePressedIndex++;
				continue;
			} else {
				currentHouseSeeds = currentHouse.getNumSeeds();
				currentHouse.setNumSeeds(currentHouseSeeds+1);
				housePressedIndex++;
				seedsInHousePressed--;
			}
		}
		mancalaBoard.setBoardComponents(boardComponents);
		
		if (isAdjacentHouseEmpty(currentUser, newIndex)) {
			adjacentHouseEmpty(currentUser, newIndex);
		}
		if (isCollectRemainingSeeds()) {
			collectRemainingSeeds(currentPlayer);
		}
		if (isGameOver()) {
			User winner = gameOver(false);
			if(winner == null) {
				return "TIE";
			}
			return winner.getName();
		}
		if (isGoAgain(currentUser, newIndex) && !isGameOver()) {
			// return early so that currentPlayer is not updated
			if(mancalaBoard.getCurrentPlayer() == 1 && turnNumber == 2) {
				setTurnNumber(turnNumber + 1);
			}
			return "";
		}
		setTurnNumber(turnNumber + 1);
		mancalaBoard.setCurrentPlayer(1 - mancalaBoard.getCurrentPlayer());
					
				
		return "";
	}

	// Returns error message if invalid move is attempted
	public String basicMove(int currentPlayer, int housePressedIndex) {
		// currentPlayer = 0 -> Player 1
		// currentPlayer = 1 -> Player 2
		User currentUser;
		if(currentPlayer == 0) {
			currentUser = mancalaBoard.getPlayer1();
		} else {
			currentUser = mancalaBoard.getPlayer2();
		}
		
		ArrayList<House> boardComponents = mancalaBoard.getBoardComponents();
		House housePressed = boardComponents.get(housePressedIndex);
		int currentHouseSeeds = -1;
		int newIndex = -1;
		
		// check if the user tried clicking on a store
		if(housePressed.getClass() != Store.class) {
			// Check if the house pressed belongs to the current player
			if(housePressed.getOwner() == currentUser.getName()) {
				int seedsInHousePressed = housePressed.getNumSeeds();
				// Check if there are seeds in the house pressed
				if(seedsInHousePressed != 0) {
					housePressed.setNumSeeds(0);
					while(seedsInHousePressed > 0) {
						// newIndex makes sure that the index wraps back to the front of the list
						newIndex = (housePressedIndex+1) % (boardComponents.size());
						House currentHouse = boardComponents.get(newIndex);
						// Don't put your seeds in the other player's store
						if((currentHouse.getClass() == Store.class) && (currentHouse.getOwner() != currentUser.getName())) {
							housePressedIndex++;
							continue;
						} else {
							currentHouseSeeds = currentHouse.getNumSeeds();
							currentHouse.setNumSeeds(currentHouseSeeds+1);
							housePressedIndex++;
							seedsInHousePressed--;
						}
					}
					mancalaBoard.setBoardComponents(boardComponents);
					// loop through player 1 houses
					int count = 0;
					ArrayList<House> player1Houses = mancalaBoard.getPlayer1().getHouses();
					for(int i = 0; i < mancalaBoard.getNumPlayerHouses(); ++i) {
						player1Houses.get(i).setNumSeeds(mancalaBoard.getBoardComponents().get(count).getNumSeeds());
						count++;
					}
					// set player 1 store
					mancalaBoard.getPlayer1().getStore().setNumSeeds(mancalaBoard.getBoardComponents().get(count).getNumSeeds());
					count++;
					
					// loop through player 2 houses
					ArrayList<House> player2Houses = mancalaBoard.getPlayer2().getHouses();
					for(int i = 0; i < mancalaBoard.getNumPlayerHouses(); ++i) {
						player2Houses.get(i).setNumSeeds(mancalaBoard.getBoardComponents().get(count).getNumSeeds());
						count++;
					}
					// set player 2 store
					mancalaBoard.getPlayer2().getStore().setNumSeeds(mancalaBoard.getBoardComponents().get(count).getNumSeeds());
					
					if (isAdjacentHouseEmpty(currentUser, newIndex)) {
						adjacentHouseEmpty(currentUser, newIndex);
					}
					if (isCollectRemainingSeeds()) {
						collectRemainingSeeds(currentPlayer);
					}
					if (isGameOver()) {
						User winner = gameOver(false);
						if(winner == null) {
							return "TIE";
						}
						return winner.getName();
					}
					if (isGoAgain(currentUser, newIndex) && !isGameOver()) {
						// return early so that currentPlayer is not updated
						if(mancalaBoard.getCurrentPlayer() == 1 && turnNumber == 2) {
							setTurnNumber(turnNumber + 1);
						}
						return "GOAGAIN";
					}
					setTurnNumber(turnNumber + 1);
					mancalaBoard.setCurrentPlayer(1 - mancalaBoard.getCurrentPlayer());
					
				} else {
					return "You can't click on a house that has no seeds in it";
				}
			} else {
				return "The house you chose does not belong to you";
			}		
		} else {
			return "You cannot move seeds that are already in a store";
		}
		return "";
	}

	public boolean isGoAgain(User currentUser, int index) {
		if (index != -1) {
			if (currentUser.getStore().getOwner() == mancalaBoard.getBoardComponents().get(index).getOwner() && 
					mancalaBoard.getBoardComponents().get(index).getClass() == Store.class) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAdjacentHouseEmpty(User currentUser, int index) {
		if (index != -1) { 
			ArrayList<House> boardComponents = mancalaBoard.getBoardComponents();
			House finalHouse = boardComponents.get(index);
			if(finalHouse.getClass() != Store.class) {
				House adjacentHouse = boardComponents.get(boardComponents.size()-2-index);
				if (finalHouse.getOwner() == currentUser.getName() && finalHouse.getNumSeeds() == 1) {
					if (adjacentHouse.getNumSeeds() != 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void adjacentHouseEmpty(User currentUser, int index) {
		ArrayList<House> boardComponents = mancalaBoard.getBoardComponents();
		House adjacentHouse = boardComponents.get(boardComponents.size()-2-index);
		House currentHouse = boardComponents.get(index);
		
		currentUser.getStore().setNumSeeds(currentUser.getStore().getNumSeeds()+adjacentHouse.getNumSeeds()+1);
		adjacentHouse.setNumSeeds(0);
		currentHouse.setNumSeeds(0);
	}
	
	public boolean isCollectRemainingSeeds() {
		boolean collectSeeds = true;
		User user1 = mancalaBoard.getPlayer1();
		ArrayList<House> userHouses = user1.getHouses();
		for (int i = 0; i < user1.getHouses().size(); i++) {
			House currentHouse = userHouses.get(i);
			if (currentHouse.getNumSeeds() != 0) {
				collectSeeds = false;
				break;
			}
		}
		if (collectSeeds = true) {
			return true;
		}
		
		for (int i = 0; i < user1.getHouses().size(); i++) {
			House currentHouse = userHouses.get(i);
			if (currentHouse.getNumSeeds() != 0) {
				collectSeeds = false;
				break;
			}
		}
		return collectSeeds;
	}
	
	public void collectRemainingSeeds(int currentPlayer) {
		User user1 = mancalaBoard.getPlayer1();
		User user2 = mancalaBoard.getPlayer2();
		ArrayList<House> user1Houses = user1.getHouses();
		ArrayList<House> user2Houses = user2.getHouses();
		
		int playerToCollect = 2;
		for (int i = 0; i < user1.getHouses().size(); i++) {
			House currentHouse = user1Houses.get(i);
			if (currentHouse.getNumSeeds() != 0) {
				playerToCollect = -1;
				break;
			}
		}
		if (playerToCollect == 2) {
			for (int i = 0; i < user2.getHouses().size(); i++) {
				House currentHouse = user2Houses.get(i);
				if (currentHouse.getNumSeeds() != 0) {
					user2.getStore().setNumSeeds(user2.getStore().getNumSeeds()+currentHouse.getNumSeeds());
					currentHouse.setNumSeeds(0);
				}
			}
			return;
		}
		
		playerToCollect = 1;
		for (int i = 0; i < user2.getHouses().size(); i++) {
			House currentHouse = user2Houses.get(i);
			if (currentHouse.getNumSeeds() != 0) {
				playerToCollect = -1;
				break;
			}
		}
		if (playerToCollect == 1) {
			for (int i = 0; i < user1.getHouses().size(); i++) {
				House currentHouse = user1Houses.get(i);
				if (currentHouse.getNumSeeds() != 0) {
					user1.getStore().setNumSeeds(user1.getStore().getNumSeeds()+currentHouse.getNumSeeds());
					currentHouse.setNumSeeds(0);
				}
			}
		}
	}
	
	public boolean isGameOver() {
		boolean gameIsOver = true;
		ArrayList<House> player1Houses = mancalaBoard.getPlayer1().getHouses();
		ArrayList<House> player2Houses = mancalaBoard.getPlayer2().getHouses();
		for (int i = 0; i < player1Houses.size(); i++) {
			if (player1Houses.get(i).getNumSeeds() != 0 || player2Houses.get(i).getNumSeeds() != 0) {
				gameIsOver = false;
				break;
			}
		}
		return gameIsOver;
	}
	
	// return User of winner
	public User gameOver(boolean timeOut) {
		User winner = null;
		if(timeOut) {
			// The winner is the player that isn't the current player
			if(mancalaBoard.getCurrentPlayer() == 0) {
				winner = mancalaBoard.getPlayer2();
			} else {
				winner = mancalaBoard.getPlayer1();
			}
		} else {
			if (isTie()) {
				tie();
			} else {
				Store player1Store = mancalaBoard.getPlayer1().getStore();
				Store player2Store = mancalaBoard.getPlayer2().getStore();
				if(player1Store.getNumSeeds() > player2Store.getNumSeeds()) {
					winner = mancalaBoard.getPlayer1();
				} else {
					winner = mancalaBoard.getPlayer2();
				}
			}
		}
		return winner;
	}
	
	public boolean isTie() {
		if (mancalaBoard.getPlayer1().getStore().getNumSeeds() == mancalaBoard.getPlayer2().getStore().getNumSeeds()) {
			return true;
		}
		return false;
	}
	
	public void tie() { 
		System.out.println("Players tied!"); //TEMP: We can do w/e we want from here
	}
	
}
