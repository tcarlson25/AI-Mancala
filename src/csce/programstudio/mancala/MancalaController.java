package csce.programstudio.mancala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class MancalaController {

	private static MancalaView mancalaView;
	private static MancalaModel mancalaModel;
	
	public MancalaController(MancalaView mancalaView, MancalaModel mancalaModel) {
		setMancalaView(mancalaView);
		setMancalaModel(mancalaModel);
		mancalaView.getMancalaHome().addMancalaHomeListeners(new MancalaHomeListener());
	}
	
	public static MancalaView getMancalaView() {
		return mancalaView;
	}

	public static void setMancalaView(MancalaView mancalaView) {
		MancalaController.mancalaView = mancalaView;
	}

	public static MancalaModel getMancalaModel() {
		return mancalaModel;
	}

	public static void setMancalaModel(MancalaModel mancalaModel) {
		MancalaController.mancalaModel = mancalaModel;
	}
	
	public static boolean checkForPieRule() {
		if(mancalaModel.getPlayerNumber() == 1) {
			int turnNumber = mancalaModel.getTurnNumber();
			if(turnNumber == 2) {
				mancalaView.getMancalaGame().getPieRule().setVisible(true);
				return true;
			}
			mancalaView.getMancalaGame().getPieRule().setVisible(false);
		}
		return false;
	}
	
	public static void serverCheckForWinner() {
		if(mancalaModel.isGameOver()) {
			String myName = "";
			if(mancalaModel.getPlayerNumber() == 0) {
				myName = "Player 1";
			} else {
				myName = "Player 2";
			}
			User winner = mancalaModel.gameOver(false);
			if(winner == null) {
				mancalaModel.getMancalaServer().getHandler().sendMessage("TIE");
				mancalaView.getMancalaGame().getTimerLabel().setText("TIE!");
			} else if(winner.getName().equals(myName)) {
				mancalaModel.getMancalaServer().getHandler().sendMessage("LOSER");
				mancalaView.getMancalaGame().getTimerLabel().setText("You Won!");
			} else {
				mancalaModel.getMancalaServer().getHandler().sendMessage("WINNER");
				mancalaView.getMancalaGame().getTimerLabel().setText("You Lost!");
			}
			mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
			mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("");
			disableBoardComponents();
		}
	}
	
	public static void multiClientServerCheckForWinner() {
		if(mancalaModel.isGameOver()) {
			User winner = mancalaModel.gameOver(false);
			if(winner == null) {
				mancalaModel.getMancalaMultiClientServer().getHandlers()[0].sendMessage("TIE");
				mancalaModel.getMancalaMultiClientServer().getHandlers()[1].sendMessage("TIE");
			} else if(winner.getName().equals("Player 1")) {
				mancalaModel.getMancalaMultiClientServer().getHandlers()[0].sendMessage("WINNER");
				mancalaModel.getMancalaMultiClientServer().getHandlers()[1].sendMessage("LOSER");
			} else {
				mancalaModel.getMancalaMultiClientServer().getHandlers()[0].sendMessage("LOSER");
				mancalaModel.getMancalaMultiClientServer().getHandlers()[1].sendMessage("WINNER");
			}
			mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
			disableBoardComponents();
		}
	}
	
	public static void updateSeedCountLabels() {
		// set button labels to the number of seeds in its house
		MancalaGameView gameView = mancalaView.getMancalaGame();
		ArrayList<JLabel> player1HouseLabels = gameView.getPlayer1HouseLabels();
		ArrayList<JLabel> player2HouseLabels = gameView.getPlayer2HouseLabels();
		ArrayList<JButton> player1HouseButtons = gameView.getPlayer1SubPanelButtons();
		ArrayList<JButton> player2HouseButtons = gameView.getPlayer2SubPanelButtons();
		User player1 = mancalaModel.getMancalaBoard().getPlayer1();
		User player2 = mancalaModel.getMancalaBoard().getPlayer2();
		for(int i = 0; i < player1HouseLabels.size(); ++i) {
			player1HouseLabels.get(i).setText(Integer.toString(player1.getHouses().get(i).getNumSeeds()));
			player2HouseLabels.get(player1HouseLabels.size()-1-i).setText(Integer.toString(player2.getHouses().get(i).getNumSeeds()));
		}
		// update house images
		for(int i = 0; i < player1HouseLabels.size(); ++i) {
			// player 1 houses
			if (player1.getHouses().get(i).getNumSeeds() < 1) {
				player1HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/emptyHouse.png")));
			}
			else if (player1.getHouses().get(i).getNumSeeds() == 1) {
				player1HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed1House.png")));
			}
			else if (player1.getHouses().get(i).getNumSeeds() == 2) {
				player1HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed2House.png")));
			}
			else if (player1.getHouses().get(i).getNumSeeds() == 3) {
				player1HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed3House.png")));
			}
			else if (player1.getHouses().get(i).getNumSeeds() == 4) {
				player1HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed4House.png")));
			} else {
				player1HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed5House.png")));
			}
		}
		
		for(int i = 0; i < player2HouseLabels.size(); ++i) {
			// player 2 houses
			if (player2.getHouses().get(player2HouseLabels.size()-1-i).getNumSeeds() < 1) {
				player2HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/emptyHouse.png")));
			}
			else if (player2.getHouses().get(player2HouseLabels.size()-1-i).getNumSeeds() == 1) {
				player2HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed1House.png")));
			}
			else if (player2.getHouses().get(player2HouseLabels.size()-1-i).getNumSeeds() == 2) {
				player2HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed2House.png")));
			}
			else if (player2.getHouses().get(player2HouseLabels.size()-1-i).getNumSeeds() == 3) {
				player2HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed3House.png")));
			}
			else if (player2.getHouses().get(player2HouseLabels.size()-1-i).getNumSeeds() == 4) {
				player2HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed4House.png")));
			} else {
				player2HouseButtons.get(i).setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed5House.png")));
			}
			//System.out.println(player2.getHouses().get(player2HouseLabels.size()-1-i).getNumSeeds());
		}
		
		// update Store icons
		if (player1.getStore().getNumSeeds() < 1) {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/testStore.png")));
		}
		else if (player1.getStore().getNumSeeds() == 1) {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed1Store.png")));
		}
		else if (player1.getStore().getNumSeeds() == 2) {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed2Store.png")));
		}
		else if (player1.getStore().getNumSeeds() == 3) {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed3Store.png")));
		}
		else if (player1.getStore().getNumSeeds() == 4) {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed4Store.png")));
		} else if (player1.getStore().getNumSeeds() >= 5 && player1.getStore().getNumSeeds() < 10) {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed5Store.png")));
		}
		else if (player1.getStore().getNumSeeds() >= 10 && player1.getStore().getNumSeeds() < 15) {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed10Store.png")));
		}
		else if (player1.getStore().getNumSeeds() >= 15 && player1.getStore().getNumSeeds() < 20) {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed15Store.png")));
		}
		else {
			gameView.getPlayer1Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed20Store.png")));
		}

		if (player2.getStore().getNumSeeds() < 1) {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/testStore.png")));
		}
		else if (player2.getStore().getNumSeeds() == 1) {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed1Store.png")));
		}
		else if (player2.getStore().getNumSeeds() == 2) {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed2Store.png")));
		}
		else if (player2.getStore().getNumSeeds() == 3) {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed3Store.png")));
		}
		else if (player2.getStore().getNumSeeds() == 4) {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed4Store.png")));
		} else if (player2.getStore().getNumSeeds() >= 5 && player2.getStore().getNumSeeds() < 10) {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed5Store.png")));
		}
		else if (player2.getStore().getNumSeeds() >= 10 && player2.getStore().getNumSeeds() < 15) {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed10Store.png")));
		}
		else if (player2.getStore().getNumSeeds() >= 15 && player2.getStore().getNumSeeds() < 20) {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed15Store.png")));
		}
		else {
			gameView.getPlayer2Store().setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/seed20Store.png")));
		}
		
		gameView.getPlayer1StoreLabel().setText(Integer.toString(player1.getStore().getNumSeeds()));
		gameView.getPlayer2StoreLabel().setText(Integer.toString(player2.getStore().getNumSeeds()));
		gameView.setPlayer1HouseLabels(player1HouseLabels);
		gameView.setPlayer2HouseLabels(player2HouseLabels);
	}
	
	public static void disableBoardComponents() {
		MancalaGameView gameView = mancalaView.getMancalaGame();
		ArrayList<JButton> player1HouseButtons = gameView.getPlayer1SubPanelButtons();
		ArrayList<JButton> player2HouseButtons = gameView.getPlayer2SubPanelButtons();
		// remove player 1 action listeners
		for(JButton currentPlayer1Button : player1HouseButtons) {
			for(ActionListener al : currentPlayer1Button.getActionListeners()) {
				currentPlayer1Button.removeActionListener(al);
			}
		}
		// remove player 2 action listeners
		for(JButton currentPlayer2Button : player2HouseButtons) {
			for(ActionListener al : currentPlayer2Button.getActionListeners()) {
				currentPlayer2Button.removeActionListener(al);
			}
		}
		
		gameView.getErrorMessage().setText("");
		gameView.getCurrentPlayerLabel().setText("");
		gameView.getTimer().stop();
	}
	
	public static void initializeClientBoard(int numHousesPerPlayer, int numSeedsPerHouse, boolean randomSeedCount, int timeInMS, ArrayList<Integer> randomSeeds) {
		// initialize the mancala game view
		mancalaView.setMancalaGame(new MancalaGameView(numHousesPerPlayer));
		mancalaView.add(mancalaView.getMancalaGame());
		mancalaView.getMancalaJoin().setVisible(false);
		mancalaView.getMancalaGame().setVisible(true);
		
		// initialize the mancala board
		if(randomSeedCount) {
			mancalaModel.setMancalaBoard(new Board(numHousesPerPlayer, numSeedsPerHouse, randomSeeds));
		} else {
			mancalaModel.setMancalaBoard(new Board(numHousesPerPlayer, numSeedsPerHouse, randomSeedCount));
		}
		
		// add listeners for game view
		MancalaGameView gameView = mancalaView.getMancalaGame();
		gameView.addMancalaHouseListeners(new MancalaHouseOnlineListener());
		gameView.addPieRuleListener(new MancalaPieRuleListener());
		gameView.addReturnToMenuListener(new ReturnToMenuListener());
		
		// add timer if input != 0
		mancalaModel.setInputedTime(timeInMS);
		if(mancalaModel.getInputedTime() != 0) {
			gameView.addTimerListener(new ClientTimerListener(mancalaModel.getInputedTime()));
		} else {
			gameView.getTimerLabel().setVisible(false);
		}
							
		// set button labels to the number of seeds in its house
		updateSeedCountLabels();
	}
	
	static class MancalaHomeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// local button clicked
			if(event.getSource() == mancalaView.getMancalaHome().getLocalButton()) {
				// initialize the mancala local view
				mancalaView.setMancalaLocal(new MancalaLocalView());
				mancalaView.add(mancalaView.getMancalaLocal());
				mancalaView.getMancalaHome().setVisible(false);
				mancalaView.getMancalaLocal().setVisible(true);
				mancalaView.getMancalaLocal().addMancalaLocalListeners(new MancalaLocalListener());
				mancalaView.getMancalaLocal().addReturnToMenuListeners(new ReturnToMenuListener());
				
			// online button clicked
			} else if (event.getSource() == mancalaView.getMancalaHome().getOnlineButton()) {
				// initialize the mancala online view
				mancalaView.setMancalaOnline(new MancalaOnlineView());
				mancalaView.add(mancalaView.getMancalaOnline());
				mancalaView.getMancalaHome().setVisible(false);
				mancalaView.getMancalaOnline().setVisible(true);
				mancalaView.getMancalaOnline().addMancalaOnlineListeners(new MancalaOnlineListener());
				mancalaView.getMancalaOnline().addReturnToMenuListeners(new ReturnToMenuListener());
			// tutorial button clicked
			} 
		}
		
	}
	
	static class MancalaOnlineListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// host button clicked
			if(event.getSource() == mancalaView.getMancalaOnline().getHostButton()) {
				// initialize the mancala host view
				mancalaView.setMancalaHost(new MancalaHostView());
				mancalaView.add(mancalaView.getMancalaHost());
				mancalaView.getMancalaOnline().setVisible(false);
				mancalaView.getMancalaHost().setVisible(true);
				mancalaView.getMancalaHost().addMancalaHostListeners(new MancalaHostListener());
				mancalaView.getMancalaHost().addReturnToMenuListeners(new ReturnToMenuListener());
				
			// join button clicked
			} else {
				// initialize the mancala join view
				mancalaView.setMancalaJoin(new MancalaJoinView());
				mancalaView.add(mancalaView.getMancalaJoin());
				mancalaView.getMancalaOnline().setVisible(false);
				mancalaView.getMancalaJoin().setVisible(true);
				mancalaView.getMancalaJoin().addMancalaJoinListeners(new MancalaJoinListener());
				mancalaView.getMancalaJoin().addReturnToMenuListeners(new ReturnToMenuListener());
			}
		}
		
	}
	
	// Listener Class that is for home screen buttons
	static class MancalaLocalListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			boolean randomSeedCount = false;
			int numHousesPerPlayer = 0;
			int numSeedsPerHouse = 0;
			
			MancalaLocalView localView = mancalaView.getMancalaLocal();
			randomSeedCount = localView.getRandomSeeds().isSelected();
			numSeedsPerHouse = localView.getNumSeedsInput().getValue();
			numHousesPerPlayer = localView.getNumHousesInput().getValue();
			boolean isError = false;
			
			// first check for errors on input
			try {
				int inputedTime = (int) Integer.parseInt(localView.getTimerInput().getText())/10;
				System.out.println("Inputed Time: " + inputedTime);
				mancalaModel.setInputedTime(inputedTime);
			} catch(Exception e) {
				localView.getErrorMessage().setText("Invalid time input. Enter 0 for no time limit.");
				isError = true;
			}
			
			if(!isError) {
				// initialize the mancala board
				mancalaModel.setMancalaBoard(new Board(numHousesPerPlayer, numSeedsPerHouse, randomSeedCount));
				// Player vs Player
				if(event.getSource() == mancalaView.getMancalaLocal().getpVpButton()) {
					mancalaModel.getMancalaBoard().getPlayer1().setAI(false);
					mancalaModel.getMancalaBoard().getPlayer2().setAI(false);
					
				// Player vs AI
				} else if(event.getSource() == mancalaView.getMancalaLocal().getpVaButton()) {
					mancalaModel.getMancalaBoard().getPlayer1().setAI(false);
					mancalaModel.getMancalaBoard().getPlayer2().setAI(true);
					
				// AI vs AI
				} else if(event.getSource() == mancalaView.getMancalaLocal().getaVaButton()) {
					mancalaModel.getMancalaBoard().getPlayer1().setAI(true);
					mancalaModel.getMancalaBoard().getPlayer2().setAI(true);
				}
				mancalaModel.setPlayerNumber(1); // set locally so pie rule will show
				
				// initialize the mancala game view
				mancalaView.setMancalaGame(new MancalaGameView(numHousesPerPlayer));
				mancalaView.add(mancalaView.getMancalaGame());
				mancalaView.getMancalaLocal().setVisible(false);
				mancalaView.getMancalaGame().setVisible(true);
				
				// add listeners for game view
				MancalaGameView gameView = mancalaView.getMancalaGame();
				if(event.getSource() != mancalaView.getMancalaLocal().getaVaButton()) {
					gameView.addMancalaHouseListeners(new MancalaHouseListener());
				}
				gameView.addPieRuleListener(new MancalaPieRuleListener());
				gameView.addReturnToMenuListener(new ReturnToMenuListener());
				
				// add timer if input != 0
				if(mancalaModel.getInputedTime() != 0) {
					gameView.addTimerListener(new LocalTimerListener(mancalaModel.getInputedTime()));
					gameView.getTimer().start();
				} else {
					gameView.getTimerLabel().setVisible(false);
				}
				
				// set button labels to the number of seeds in its house
				updateSeedCountLabels();
				mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Player 1's Turn");
				
				if(event.getSource() == mancalaView.getMancalaLocal().getaVaButton()) {
					MultiAIThread multiAIThread = new MultiAIThread();
					multiAIThread.start();
				}
				
			}
		}
		
	}
	
	// Listener Class that is for host screen buttons
	static class MancalaHostListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			boolean randomSeedCount = false;
			int numHousesPerPlayer = 0;
			int numSeedsPerHouse = 0;
			
			// Play as Player or AI
			if(event.getSource() == mancalaView.getMancalaHost().getPlayAsPlayerButton() || event.getSource() == mancalaView.getMancalaHost().getPlayAsAIButton()) {
				MancalaHostView hostView = mancalaView.getMancalaHost();
				randomSeedCount = hostView.getRandomSeeds().isSelected();
				numSeedsPerHouse = hostView.getNumSeedsInput().getValue();
				numHousesPerPlayer = hostView.getNumHousesInput().getValue();
				String portNum = mancalaView.getMancalaHost().getPortNumberInput().getText().trim();
				int portNumber = 0;
				boolean isError = false;
				
				// first check for errors on input
				try {
					int inputedTime = (int) Integer.parseInt(hostView.getTimerInput().getText())/10;
					System.out.println("Inputed Time: " + inputedTime);
					mancalaModel.setInputedTime(inputedTime);
				} catch(Exception e) {
					hostView.getErrorMessage().setText("Invalid time input. Enter 0 for no time limit.");
					isError = true;
				}
				
				try {
					portNumber = (int) Integer.parseInt(portNum);
				} catch(Exception e) {
					hostView.getErrorMessage().setText("Invalid Port Number.");
					isError = true;
				}
				
				if(!isError) {
					// initialize the mancala game view
					mancalaView.setMancalaGame(new MancalaGameView(numHousesPerPlayer));
					
					// initialize the mancala board
					mancalaModel.setMancalaBoard(new Board(numHousesPerPlayer, numSeedsPerHouse, randomSeedCount));
					mancalaModel.setPlayerNumber(0);
					if(event.getSource() == mancalaView.getMancalaHost().getPlayAsAIButton()){
						mancalaModel.getMancalaBoard().getPlayer1().setAI(true);
					} else { 
						mancalaModel.getMancalaBoard().getPlayer1().setAI(false);
					}
					
					// add listeners for game view
					MancalaGameView gameView = mancalaView.getMancalaGame();
					gameView.addPieRuleListener(new MancalaPieRuleListener());
					gameView.addReturnToMenuListener(new ReturnToMenuListener());
					
					// add timer if input != 0
					if(mancalaModel.getInputedTime() != 0) {
						gameView.addTimerListener(new OnlineTimerListener(mancalaModel.getInputedTime()));
					} else {
						gameView.getTimerLabel().setVisible(false);
					}
										
					// set button labels to the number of seeds in its house
					updateSeedCountLabels();
					
					if(mancalaModel.getMancalaBoard().getCurrentPlayer() == mancalaModel.getPlayerNumber()) {
						mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Your Turn");
					} else {
						mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Opponent's Turn");
					}
					
					// disable host buttons
	    				JButton playerButton = mancalaView.getMancalaHost().getPlayAsPlayerButton();
	    				JButton aiButton = mancalaView.getMancalaHost().getPlayAsAIButton();
	    				JButton waitButton = mancalaView.getMancalaHost().getWaitForClientButton();
	    				for(ActionListener al : playerButton.getActionListeners()) {
	    					playerButton.removeActionListener(al);
	    				}
	            		for(ActionListener al : aiButton.getActionListeners()) {
	            			aiButton.removeActionListener(al);
	        			}
	            		for(ActionListener al : waitButton.getActionListeners()) {
	            			waitButton.removeActionListener(al);
	        			}
            		
					// start server
					mancalaModel.setMancalaServer(new Server(portNumber, mancalaModel, mancalaView));
					mancalaModel.getMancalaServer().start();
					
				}
				
			// wait for Client
			} else if(event.getSource() == mancalaView.getMancalaHost().getWaitForClientButton()) {
				// Same as PvP for now.. add stuff later
				MancalaHostView hostView = mancalaView.getMancalaHost();
				randomSeedCount = hostView.getRandomSeeds().isSelected();
				numSeedsPerHouse = hostView.getNumSeedsInput().getValue();
				numHousesPerPlayer = hostView.getNumHousesInput().getValue();
				String portNum = mancalaView.getMancalaHost().getPortNumberInput().getText().trim();
				int portNumber = 0;
				boolean isError = false;
				
				// first check for errors on input
				try {
					int inputedTime = (int) Integer.parseInt(hostView.getTimerInput().getText())/10;
					System.out.println("Inputed Time: " + inputedTime);
					mancalaModel.setInputedTime(inputedTime);
				} catch(Exception e) {
					hostView.getErrorMessage().setText("Invalid time input. Enter 0 for no time limit.");
					isError = true;
				}
				
				try {
					portNumber = (int) Integer.parseInt(portNum);
				} catch(Exception e) {
					hostView.getErrorMessage().setText("Invalid Port Number.");
					isError = true;
				}
				
				if(!isError) {
					// initialize the mancala game view
					mancalaView.setMancalaGame(new MancalaGameView(numHousesPerPlayer));
					mancalaModel.setPlayerNumber(1);

					// initialize the mancala board
					mancalaModel.setMancalaBoard(new Board(numHousesPerPlayer, numSeedsPerHouse, randomSeedCount));
					
					// add timer if input != 0
					if(mancalaModel.getInputedTime() != 0) {
						mancalaView.getMancalaGame().addTimerListener(new MultiClientTimerListener(mancalaModel.getInputedTime()));
					}
					
					// disable host buttons
	    				JButton playerButton = mancalaView.getMancalaHost().getPlayAsPlayerButton();
	    				JButton aiButton = mancalaView.getMancalaHost().getPlayAsAIButton();
	    				JButton waitButton = mancalaView.getMancalaHost().getWaitForClientButton();
	    				for(ActionListener al : playerButton.getActionListeners()) {
	    					playerButton.removeActionListener(al);
	    				}
	            		for(ActionListener al : aiButton.getActionListeners()) {
	            			aiButton.removeActionListener(al);
	        			}
	            		for(ActionListener al : waitButton.getActionListeners()) {
	            			waitButton.removeActionListener(al);
	        			}
	            		
	            		// display message
	            		mancalaView.getMancalaHost().getErrorMessage().setText("Server has started.. Waiting for clients to connect");
										
					// start server
					mancalaModel.setMancalaMultiClientServer(new MultiClientServer(portNumber, mancalaModel, mancalaView));
					mancalaModel.getMancalaMultiClientServer().start();
					
				}
			}
		}
		
	}
	
	static class MancalaJoinListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// initialize the mancala host view
			String portNum = mancalaView.getMancalaJoin().getPortNumberInput().getText().trim();
			String ipAddress = mancalaView.getMancalaJoin().getipAddressInput().getText().trim();
			try {
				int portNumber = (int) Integer.parseInt(portNum);
				System.out.println("Port Number inputed: " + portNumber);
				if(event.getSource() == mancalaView.getMancalaJoin().getPlayAsAIButton()) {
					mancalaModel.setMancalaClient(new Client(ipAddress, portNumber, mancalaModel, mancalaView, true));
				} else {
					mancalaModel.setMancalaClient(new Client(ipAddress, portNumber, mancalaModel, mancalaView, false));
				}
			} catch(Exception e) {
				mancalaView.getMancalaJoin().getErrorMessage().setText("Invalid Port Number Input");
			}
		}
		
	}
	
	
	// internal class to listen for clicks online
	static class MancalaHouseOnlineListener implements ActionListener {
		
		private ArrayList<JButton> player1HouseButtons;
		private ArrayList<JButton> player2HouseButtons;
		
		public void actionPerformed(ActionEvent event) {
			MancalaGameView gameView = mancalaView.getMancalaGame();
			player1HouseButtons = gameView.getPlayer1SubPanelButtons();
			player2HouseButtons = gameView.getPlayer2SubPanelButtons();
			int indexFound = 0;
			for(int i = 0; i < player1HouseButtons.size(); ++i) {
				if(event.getSource() == player1HouseButtons.get(i)) {
					indexFound = i;
					break;
				} else if(event.getSource() == player2HouseButtons.get(i)) {
					indexFound = (player2HouseButtons.size()-i) + player1HouseButtons.size();
					break;
				}
			}
			String errorReturned = mancalaModel.basicOnlineMove(mancalaModel.getMancalaBoard().getCurrentPlayer(), indexFound, mancalaModel.getPlayerNumber());
			if(mancalaModel.getMancalaClient().isAI()) {
				mancalaView.getMancalaGame().getPieRule().setVisible(false);
			} else {
				checkForPieRule();
			}
			if (mancalaModel.getMancalaBoard().getCurrentPlayer() == mancalaModel.getPlayerNumber()) {
				gameView.setCurrentPlayerLabel("Your Turn");
			} else {
				gameView.setCurrentPlayerLabel("Opponent's Turn");
			}
			if(errorReturned != "" && errorReturned != "GOAGAIN") {
				gameView.setErrorMessage(errorReturned);
				gameView.getErrorMessage().setVisible(true);
			} else {
				gameView.getErrorMessage().setVisible(false);
			}
			
			updateSeedCountLabels();
			
		}
		
	}
	
	// internal class to listen for clicks locally
	static class MancalaHouseListener implements ActionListener {
		
		private ArrayList<JButton> player1HouseButtons;
		private ArrayList<JButton> player2HouseButtons;
		
		public void actionPerformed(ActionEvent event) {
			MancalaGameView gameView = mancalaView.getMancalaGame();
			player1HouseButtons = gameView.getPlayer1SubPanelButtons();
			player2HouseButtons = gameView.getPlayer2SubPanelButtons();
			int indexFound = 0;
			for(int i = 0; i < player1HouseButtons.size(); ++i) {
				if(event.getSource() == player1HouseButtons.get(i)) {
					indexFound = i;
					break;
				} else if(event.getSource() == player2HouseButtons.get(i)) {
					indexFound = (player2HouseButtons.size()-i) + player1HouseButtons.size();
					break;
				}
			}
			String errorReturned = mancalaModel.basicMove(mancalaModel.getMancalaBoard().getCurrentPlayer(), indexFound);
			if(!mancalaModel.getMancalaBoard().getPlayer2().isAI()) {
				checkForPieRule();
			}
			if (mancalaModel.getMancalaBoard().getCurrentPlayer() == 0) {
				gameView.setCurrentPlayerLabel("Player 1's Turn");
			} else {
				gameView.setCurrentPlayerLabel("Player 2's Turn");
			}
			if(errorReturned != "" && errorReturned != "GOAGAIN") {
				if(errorReturned != "") {
					User player1 = mancalaModel.getMancalaBoard().getPlayer1();
					User player2 = mancalaModel.getMancalaBoard().getPlayer2();
					if(errorReturned.equals(player1.getName())) {
						mancalaView.getMancalaGame().getTimerLabel().setText(player1.getName() + " Wins!");
						mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
						disableBoardComponents();
					} else if(errorReturned.equals(player2.getName())){
						mancalaView.getMancalaGame().getTimerLabel().setText(player2.getName() + " Wins!");
						mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
						disableBoardComponents();
					} else if(errorReturned.equals("TIE")) {
						mancalaView.getMancalaGame().getTimerLabel().setText("TIE!");
						mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
						disableBoardComponents();
					} else {
						gameView.setErrorMessage(errorReturned);
						gameView.getErrorMessage().setVisible(true);
					}
				} else {
					gameView.getErrorMessage().setVisible(false);
				}
			} else {
				gameView.setErrorMessage("");
				if(errorReturned != "GOAGAIN") {
					mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
				}
			}
			
			updateSeedCountLabels();
			if(mancalaModel.getMancalaBoard().getPlayer2().isAI() && !mancalaModel.isGameOver() && mancalaModel.getMancalaBoard().getCurrentPlayer() == 1) {
				Thread aiThread = new AIThread();
				aiThread.start();
			}
		}
		
	}
	
	static class MultiAIThread extends Thread{
		
		@Override
		public void run() {
			AIThread aiThread = new AIThread();
			aiThread.start();
			while(!mancalaModel.isGameOver()) {
				if(!aiThread.isAlive()) {
					aiThread = new AIThread();
					aiThread.start();
				}
			}
		}
		
	}
	
	static class AIThreadOnline extends Thread{
		
		@Override
		public void run() {
			MancalaGameView gameView = mancalaView.getMancalaGame();
			int callingPlayer = mancalaModel.getMancalaBoard().getCurrentPlayer();
			while((mancalaModel.getMancalaBoard().getCurrentPlayer() == callingPlayer) && !mancalaModel.isGameOver()) {
				GameNode root_node = new GameNode(mancalaModel.getMancalaBoard(), null);
				GameTree game_tree = new GameTree();
				game_tree.build_tree(root_node, callingPlayer);
				game_tree.greedyChoice();
				AlphaBeta alphabeta = new AlphaBeta(game_tree);
				int best_move = alphabeta.alpha_beta_search(game_tree.root);
				System.out.println("MiniMax: Best move: = " + best_move);
				String errorReturned = mancalaModel.basicOnlineMove(mancalaModel.getMancalaBoard().getCurrentPlayer(), best_move, mancalaModel.getPlayerNumber());
				if (mancalaModel.getMancalaBoard().getCurrentPlayer() == mancalaModel.getPlayerNumber()) {
					gameView.setCurrentPlayerLabel("Your Turn");
				} else {
					gameView.setCurrentPlayerLabel("Opponent's Turn");
				}
				if(errorReturned != "" && errorReturned != "GOAGAIN") {
					gameView.setErrorMessage(errorReturned);
					gameView.getErrorMessage().setVisible(true);
				} else {
					gameView.getErrorMessage().setVisible(false);
				}
				
				updateSeedCountLabels();
			}
		}
		
	}
	
	static class AIThread extends Thread{
		
		@Override
		public void run() {
			MancalaGameView gameView = mancalaView.getMancalaGame();
			int callingPlayer = mancalaModel.getMancalaBoard().getCurrentPlayer();
			while((mancalaModel.getMancalaBoard().getCurrentPlayer() == callingPlayer) && !mancalaModel.isGameOver()) {
				GameNode root_node = new GameNode(mancalaModel.getMancalaBoard(), null);
				GameTree game_tree = new GameTree();
				game_tree.build_tree(root_node, callingPlayer);
				game_tree.greedyChoice();
				AlphaBeta alphabeta = new AlphaBeta(game_tree);
				int best_move = alphabeta.alpha_beta_search(game_tree.root);
				System.out.println("MiniMax: Best move: = " + best_move);
				String errorReturned = mancalaModel.basicMove(mancalaModel.getMancalaBoard().getCurrentPlayer(), best_move);
				if (mancalaModel.getMancalaBoard().getCurrentPlayer() == 0) {
					gameView.setCurrentPlayerLabel("Player 1's Turn");
				} else {
					gameView.setCurrentPlayerLabel("Player 2's Turn");
				}
				if(errorReturned != "" && errorReturned != "GOAGAIN") {
					if(errorReturned != "") {
						User player1 = mancalaModel.getMancalaBoard().getPlayer1();
						User player2 = mancalaModel.getMancalaBoard().getPlayer2();
						if(errorReturned.equals(player1.getName())) {
							mancalaView.getMancalaGame().getTimerLabel().setText(player1.getName() + " Wins!");
							mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
							disableBoardComponents();
						} else if(errorReturned.equals(player2.getName())){
							mancalaView.getMancalaGame().getTimerLabel().setText(player2.getName() + " Wins!");
							mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
							disableBoardComponents();
						} else if(errorReturned.equals("TIE")) {
							mancalaView.getMancalaGame().getTimerLabel().setText("TIE!");
							mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
							disableBoardComponents();
						} else {
							gameView.setErrorMessage(errorReturned);
							gameView.getErrorMessage().setVisible(true);
						}
					} else {
						gameView.getErrorMessage().setVisible(false);
						if(errorReturned != "GOAGAIN") {
							mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
						}
					}
				} else {
					gameView.setErrorMessage("");
					if(errorReturned != "GOAGAIN") {
						mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
					}
				}
				
			}
			updateSeedCountLabels();
		}
		
	}
	
	static class MancalaPieRuleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mancalaModel.pieRule();
			updateSeedCountLabels();
			mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
			mancalaModel.setTurnNumber(mancalaModel.getTurnNumber() + 1);
			mancalaView.getMancalaGame().getPieRule().setVisible(false);
			if(mancalaModel.getMancalaServer() != null) {
				mancalaModel.getMancalaServer().getHandler().sendMessage("P");
			} else if(mancalaModel.getMancalaClient() != null) {
				mancalaModel.getMancalaClient().sendMessage("P");
			}
			mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Opponent's Turn");
			mancalaView.getMancalaGame().setErrorMessage("");
		}
		
	}
	
	static class OnlineTimerListener implements ActionListener {

		public OnlineTimerListener(int remainingTime) {
			mancalaModel.setRemainingTime(remainingTime);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int remainingTime = mancalaModel.getRemainingTime();
			MancalaGameView gameView = mancalaView.getMancalaGame();
			String format = Integer.toString(remainingTime);
			int index = 0;
			
			// only compute if time is positive
			if(remainingTime >= 0) {
				if(format.length() == 3) {
					index = 1;
					format = format.substring(0,index) + "." + format.substring(index);
				} else if(format.length() < 3){
					format = "0." + format;
				} else {
					index = format.length() - 2;
					format = format.substring(0,index) + "." + format.substring(index);
				}
				
				if(remainingTime == 0) {
					format = "0";
				}
				gameView.getTimerLabel().setText(format);
				
				mancalaModel.setRemainingTime(remainingTime - 1); 
			}
			
			if(remainingTime < 0) {
				User winner = mancalaModel.gameOver(true);
				mancalaView.getMancalaGame().getTimerLabel().setText(winner.getName() + " Wins!");
				String myName = "";
				if(mancalaModel.getPlayerNumber() == 0) {
					myName = "Player 1";
				} else {
					myName = "Player 2";
				}
				
				mancalaModel.getMancalaServer().getHandler().sendMessage("TIME");
				if(winner.getName().equals(myName)) {
					mancalaModel.getMancalaServer().getHandler().sendMessage("LOSER");
					mancalaView.getMancalaGame().getTimerLabel().setText("You Won!");
				} else {
					mancalaModel.getMancalaServer().getHandler().sendMessage("WINNER");
					mancalaView.getMancalaGame().getTimerLabel().setText("You Lost!");
				}
				disableBoardComponents();
			}
		}
		
	}
	
	static class MultiClientTimerListener implements ActionListener {

		public MultiClientTimerListener(int remainingTime) {
			mancalaModel.setRemainingTime(remainingTime);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int remainingTime = mancalaModel.getRemainingTime();
			MancalaGameView gameView = mancalaView.getMancalaGame();
			String format = Integer.toString(remainingTime);
			int index = 0;
			
			// only compute if time is positive
			if(remainingTime >= 0) {
				if(format.length() == 3) {
					index = 1;
					format = format.substring(0,index) + "." + format.substring(index);
				} else if(format.length() < 3){
					format = "0." + format;
				} else {
					index = format.length() - 2;
					format = format.substring(0,index) + "." + format.substring(index);
				}
				
				if(remainingTime == 0) {
					format = "0";
				}
				gameView.getTimerLabel().setText(format);
				
				mancalaModel.setRemainingTime(remainingTime - 1); 
			}
			
			if(remainingTime < 0) {
				User winner = mancalaModel.gameOver(true);
				mancalaView.getMancalaGame().getTimerLabel().setText(winner.getName() + " Wins!");
				
				mancalaModel.getMancalaMultiClientServer().getHandlers()[0].sendMessage("TIME");
				mancalaModel.getMancalaMultiClientServer().getHandlers()[1].sendMessage("TIME");
				if(winner.getName().equals("Player 1")) {
					mancalaModel.getMancalaMultiClientServer().getHandlers()[0].sendMessage("WINNER");
					mancalaModel.getMancalaMultiClientServer().getHandlers()[1].sendMessage("LOSER");
					mancalaView.getMancalaGame().getTimerLabel().setText("Player 1 Wins!");
				} else {
					mancalaModel.getMancalaMultiClientServer().getHandlers()[0].sendMessage("LOSER");
					mancalaModel.getMancalaMultiClientServer().getHandlers()[1].sendMessage("WINNER");
					mancalaView.getMancalaGame().getTimerLabel().setText("Player 2 Wins!");
				}
				disableBoardComponents();
			}
		}
		
	}
	
	static class LocalTimerListener implements ActionListener {

		public LocalTimerListener(int remainingTime) {
			mancalaModel.setRemainingTime(remainingTime);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int remainingTime = mancalaModel.getRemainingTime();
			MancalaGameView gameView = mancalaView.getMancalaGame();
			String format = Integer.toString(remainingTime);
			int index = 0;
			
			// only compute if time is positive
			if(remainingTime >= 0) {
				if(format.length() == 3) {
					index = 1;
					format = format.substring(0,index) + "." + format.substring(index);
				} else if(format.length() < 3){
					format = "0." + format;
				} else {
					index = format.length() - 2;
					format = format.substring(0,index) + "." + format.substring(index);
				}
				
				if(remainingTime == 0) {
					format = "0";
				}
				gameView.getTimerLabel().setText(format);
				
				mancalaModel.setRemainingTime(remainingTime - 1); 
			}
			
			if(remainingTime < 0) {
				User winner = mancalaModel.gameOver(true);
				mancalaView.getMancalaGame().getTimerLabel().setText(winner.getName() + " Wins!");
				disableBoardComponents();
			}
		}
		
	}
	
	static class ClientTimerListener implements ActionListener {

		public ClientTimerListener(int remainingTime) {
			mancalaModel.setRemainingTime(remainingTime);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int remainingTime = mancalaModel.getRemainingTime();
			MancalaGameView gameView = mancalaView.getMancalaGame();
			String format = Integer.toString(remainingTime);
			int index = 0;
			
			// only compute if time is positive
			if(remainingTime >= 0) {
				if(format.length() == 3) {
					index = 1;
					format = format.substring(0,index) + "." + format.substring(index);
				} else if(format.length() < 3){
					format = "0." + format;
				} else {
					index = format.length() - 2;
					format = format.substring(0,index) + "." + format.substring(index);
				}
				
				if(remainingTime == 0) {
					format = "0";
				}
				gameView.getTimerLabel().setText(format);
				
				mancalaModel.setRemainingTime(remainingTime - 1); 
			}
		}
		
	}

	static class ReturnToMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mancalaView.getMancalaHome().setVisible(true);
			if(mancalaModel != null) {
				mancalaModel.setTurnNumber(1);
				mancalaModel.setMancalaBoard(null);
			}
			if(mancalaView != null) {
				if(mancalaView.getMancalaHost() != null) {
					mancalaView.getMancalaHost().setVisible(false);
				}
				if(mancalaView.getMancalaJoin() != null) {
					mancalaView.getMancalaJoin().setVisible(false);
				}
				if(mancalaView.getMancalaOnline() != null) {
					mancalaView.getMancalaOnline().setVisible(false);
				}
				if(mancalaView.getMancalaLocal() != null) {
					mancalaView.getMancalaLocal().setVisible(false);
				}
				if(mancalaView.getMancalaGame() != null) {
					MancalaGameView gameView = mancalaView.getMancalaGame();
					gameView.setVisible(false);
					gameView.getTimer().stop();
					gameView.setTimer(new Timer(10, null));
				}
				mancalaView.setMancalaGame(null);
				mancalaView.setMancalaLocal(null);
				mancalaView.setMancalaOnline(null);
				mancalaView.setMancalaHost(null);
				mancalaView.setMancalaJoin(null);
			}
			if(mancalaModel.getMancalaServer() != null) {
				mancalaModel.getMancalaServer().stop();
			} else if(mancalaModel.getMancalaClient() != null) {
				mancalaModel.getMancalaClient().disconnect();
			} else if(mancalaModel.getMancalaMultiClientServer() != null) {
				mancalaModel.getMancalaMultiClientServer().stop();
			}
		}		
	}
}
