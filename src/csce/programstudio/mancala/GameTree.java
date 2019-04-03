package csce.programstudio.mancala;

import java.util.ArrayList;
import java.util.List;

public class GameTree {
	private int DEPTH = 5;
	private int greedy_choice_index = -1;
	public GameNode root = new GameNode();
	public GameTree() {}
	
	// copy constructor
	public GameTree(GameTree gt) {
		this.DEPTH = gt.DEPTH;
		this.greedy_choice_index = gt.greedy_choice_index;
		this.root = new GameNode(gt.root);
	}
	
	// initializes tree
	public void build_tree(GameNode r, int currentPlayer){
		root = r;
		ArrayList<Integer> moves = getPossibleMoves(r.board, currentPlayer);
		
		System.out.println("");
		System.out.println("MAX TREE DEPTH: " + DEPTH);
		System.out.println("");
		
		for(int i=0;i<moves.size();i++) {
			build_subtree(moves.get(i), r, currentPlayer, DEPTH);
		}
	}
	
	// builds tree
	public void build_subtree(int move_index, GameNode parent_node, int currentPlayer, int depth) {
		int newCurrentPlayer = currentPlayer;
		printCurrentPlayer(newCurrentPlayer);
		
		// limit tree depth
		int currentDepth = depth - 1;
		System.out.println("Current Tree Depth: " + currentDepth);	
		
		// create new board model
		Board board = new Board(parent_node.board);
		MancalaModel newMancalaModel = new MancalaModel();
		newMancalaModel.setMancalaBoard(board);
		newMancalaModel.setTurnNumber(1);
		int board_size  = newMancalaModel.getMancalaBoard().getBoardComponents().size();
		
		// make move 
		System.out.println("Move index: " + move_index);
		//String moveReturn = newMancalaModel.basicMove(newCurrentPlayer, move_index);
		String moveReturn = newMancalaModel.basicMove(newMancalaModel.getMancalaBoard().getCurrentPlayer(), move_index);
		System.out.println("Player 1 Store Count: " + newMancalaModel.getMancalaBoard().getPlayer1().getStore().getNumSeeds());
		System.out.println("Player 2 Store Count: " + newMancalaModel.getMancalaBoard().getPlayer2().getStore().getNumSeeds());
		
		
		
		// build node
		int move_score = 0;
		if (newCurrentPlayer == 0) {
			move_score = newMancalaModel.getMancalaBoard().getBoardComponents().get((board_size/2)-1).getNumSeeds();
		}
		else if (newCurrentPlayer == 1) {
			move_score = newMancalaModel.getMancalaBoard().getBoardComponents().get(board_size-1).getNumSeeds();
		}
		
		System.out.println("Move Score: " + move_score);
		GameNode node = new GameNode(newMancalaModel.getMancalaBoard(), parent_node, move_score, move_index);
		printBoardComponents(node.board);
		
		if(!(moveReturn.equals("GOAGAIN"))) {
			newCurrentPlayer = 1 - newCurrentPlayer;
		}
		
		// base case
		if ((currentDepth == 0) || (isGameOver(node.board) == true)) {
			parent_node.addChild(node);
		}
		// recursive case
		else{
			ArrayList<Integer> moves = getPossibleMoves(node.board, newCurrentPlayer);
			for(int i=0;i<moves.size();i++) {
				build_subtree(moves.get(i), node, newCurrentPlayer, currentDepth);
			}
			parent_node.addChild(node);
		}
		
		System.out.println("----------------------------");
	}
	
	// returns a list of indices of possible moves
	public ArrayList<Integer> getPossibleMoves(Board board, int currentPlayer){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		// Player 1 is the AI
		if(currentPlayer == 0) {
			int start_index_AI_houses = 0;
			int end_index_AI_houses = (board.getBoardComponents().size()/2) - 2;
			for(int i = start_index_AI_houses; i <= end_index_AI_houses; i++) {
				if(board.getBoardComponents().get(i).getNumSeeds() > 0) {
					moves.add(i);
				}
			}
			
		// Player 2 is the AI
		} else {
			int start_index_AI_houses = board.getBoardComponents().size() / 2;
			int end_index_AI_houses = board.getBoardComponents().size() - 2;
			for(int i = start_index_AI_houses; i <= end_index_AI_houses; i++) {
				if(board.getBoardComponents().get(i).getNumSeeds() > 0) {
					moves.add(i);
				}
			}
		}
		return moves;
	}
	
	// check if game is over
	public boolean isGameOver(Board board) {
		boolean check = false;
		ArrayList<House> board_list = board.getBoardComponents();
		List<House> player1Houses = board_list.subList(0, (board_list.size()/2)-1);
		List<House> player2Houses = board_list.subList(board_list.size()/2, board_list.size()-1);
		
		// checks if player 2 houses empty
		int numSeedsPlayer2 = 0;
		for(int i = player2Houses.size()-1; i >= 0; --i) {
			numSeedsPlayer2 = numSeedsPlayer2 + player2Houses.get(i).getNumSeeds();
		}
		if (numSeedsPlayer2 == 0) {
			check = true;
		}
		
		// checks if player 1 houses empty
		int numSeedsPlayer1 = 0;
		for(int i = 0; i < player1Houses.size(); ++i) {
			numSeedsPlayer1 = numSeedsPlayer1 + player1Houses.get(i).getNumSeeds();
		}
		if (numSeedsPlayer2 == 0) {
			check = true;
		}
		return check;
	}
	
	
	
	
	
	
	// print board function
	public void printBoardComponents(Board board) {
		ArrayList<House> board_list = board.getBoardComponents();
		List<House> player1Houses = board_list.subList(0, (board_list.size()/2)-1);
		List<House> player2Houses = board_list.subList(board_list.size()/2, board_list.size()-1);
		
		System.out.print("\t");
		for(int i = player2Houses.size()-1; i >= 0; --i) {
			System.out.print(player2Houses.get(i).getNumSeeds() + " ");
		}
		
		System.out.print("\n     " + board_list.get(board_list.size()-1).getNumSeeds());
		for(int i = 0; i < player2Houses.size(); ++i) {
			System.out.print("  ");
		}
		System.out.print("   " + board_list.get((board_list.size()/2)-1).getNumSeeds() + "\n");
		
		System.out.print("\t");
		for(int i = 0; i < player1Houses.size(); ++i) {
			System.out.print(player1Houses.get(i).getNumSeeds() + " ");
		}
		System.out.print("\n");
	} 
	
	// greedy choice function
	public void greedyChoice(){
		for(int i = 0; i < root.children.size()-1; i++) {
			int currValue = root.children.get(i).getValue();
			if (currValue > 0) {
				greedy_choice_index = root.children.get(i).getMoveIndex();
				break;
			}
		}
		// temp fix for if seeds = 1
		if(greedy_choice_index == -1) {
			greedy_choice_index = root.board.getBoardComponents().size()-2;
		}
		System.out.println("Greedy Choice Index is: " + getGreedyIndex());
		System.out.println("======================================");
	}
	
	// greedy choice index
	public int getGreedyIndex() {
		return greedy_choice_index;
	}
	
	// helper function for printing
		public void printCurrentPlayer(int c) {
			if (c == 1){
				System.out.println("Player: " + 2 + " move!");
			}
			else if (c==0) {
				System.out.println("Player: " + 1 + " move!");
			}	
		}

}
