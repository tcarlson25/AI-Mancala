package csce.programstudio.mancala;

import java.util.ArrayList;
import java.lang.*;

public class AlphaBeta {
	GameTree game_tree = new GameTree();
	GameNode root = new GameNode();
	GameNode currentNode = new GameNode();
	ArrayList<GameNode> successors = new ArrayList<GameNode>();
	ArrayList<House> newBoardComponents = new ArrayList<House>();
	
	AlphaBeta(GameTree tree){
		game_tree = new GameTree(tree);
		this.root = new GameNode(tree.root);
	}
	
	public int alpha_beta_search(GameNode node) {
		int infinity = Integer.MAX_VALUE;
		int best_val = -(infinity);
		int beta = infinity;
		
		ArrayList<GameNode>successors = getSuccessors(node);
		int best_move = -1;
		int value;
		for(int i = 0; i < successors.size(); i++) { // Need to propagate values up tree for this to work
	    		value = min_value(successors.get(i), best_val, beta);
	    		if(value > best_val) {
	    			best_val = value;
	    			best_move = successors.get(i).getMoveIndex();
	    		}
	    }
	    System.out.println("AlphaBeta:  Utility Value of Root Node: =" + best_val);
		System.out.println("AlphaBeta:  Best Index is:" + best_move);
		return best_move;
	}
	
	public int max_value(GameNode node, int alpha, int beta) {
		System.out.println("AlphaBeta-->MAX: Visited Node Index" + node.getMoveIndex());
		if(isTerminal(node)) {
			return getUtility(node);
		}
		int infinity = Integer.MAX_VALUE;
		int value = -(infinity);
		
		ArrayList<GameNode>successors = getSuccessors(node);
		
		for(int i = 0; i < successors.size(); i++) { // Need to propagate values up tree for this to work
    			value = Math.max(value, min_value(successors.get(i), alpha, beta));
	    		if(value >= beta) {
	    			return value;
	    		}
	    		alpha = Math.max(alpha, value);
	    }
		return value;
		
	}
	
	public int min_value(GameNode node, int alpha, int beta) {
		System.out.println("AlphaBeta-->MIN: Visited Node Index" + node.getMoveIndex());
		if(isTerminal(node)) {
			return getUtility(node);
		}
		int infinity = Integer.MAX_VALUE;
		int value = (infinity);
		
		ArrayList<GameNode>successors = getSuccessors(node);
		
		for(int i = 0; i < successors.size(); i++) { // Need to propagate values up tree for this to work
    			value = Math.min(value, max_value(successors.get(i), alpha, beta));
	    		if(value <= beta) {
	    			return value;
	    		}
	    		beta = Math.min(beta, value);
	    }
		return value;
	}
	
	//
	// UTILITY METHODS
	//
	public ArrayList<GameNode> getSuccessors(GameNode node){
		return node.getChildren();
	}
	
	public boolean isTerminal(GameNode node) {
		if(node.getChildren().size() == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getUtility(GameNode node) {
		return node.getValue();
	}
}
