package csce.programstudio.mancala;

import java.util.ArrayList;
import java.lang.*;

public class MiniMax {
	GameTree game_tree = new GameTree();
	GameNode root = new GameNode();
	GameNode currentNode = new GameNode();
	ArrayList<GameNode> successors = new ArrayList<GameNode>();
	ArrayList<House> newBoardComponents = new ArrayList<House>();
	
	MiniMax(GameTree tree){
		game_tree = new GameTree(tree);
		this.root = new GameNode(tree.root);
	}
	
	public int minimax(GameNode node) {
		// first, find the max value
		int best_val = maxValue(node);
	
		// second, find the node which HAS that max value
	    //  --> means we need to propagate the values back up the
	    // tree as part of the minimax algorithm
		successors = getSuccessors(node);
		
		// print possible successor values
		System.out.print("Root Node Successor Values:");
		for(int i = 0; i < successors.size(); i++) { //  # ---> Need to propagate values up tree for this to work
			System.out.print(successors.get(i).getValue() + " ");
		}
		
		 
		System.out.println("MiniMax:  Utility Value of Root Node: = " + best_val);
		
		// find the node with our best move
        int best_move = -1;
        for(int i = 0; i < successors.size(); i++) { //  # ---> Need to propagate values up tree for this to work
        		if(successors.get(i).getValue() == best_val) {
        			best_move = successors.get(i).getMoveIndex();
        			break;
        		}
        }
        // return that best value that we've found
		//return best_val;
        // return the best move that we've found
        return best_move;
	}
	
	public int maxValue(GameNode node) {
		System.out.println("MiniMax-->MAX: Visited Node Index :: " + node.getMoveIndex());
		if(isTerminal(node)) {
			return getUtility(node);
		}
		
        int infinity = Integer.MAX_VALUE;
        int max_value = -infinity;

        ArrayList<GameNode>successors_states = getSuccessors(node);
        
        for(int i = 0; i < successors_states.size(); i++) { // Need to propagate values up tree for this to work
        		max_value = Math.max(max_value, minValue(successors_states.get(i)));
        }
        
        node.setValue(max_value);
        return max_value;
	}
	
	public int minValue(GameNode node) {
		System.out.println("MiniMax-->MIN: Visited Node Index:: " + node.getMoveIndex());
		if(isTerminal(node)) {
			return getUtility(node);
		}
		
        int infinity = Integer.MAX_VALUE;
        int min_value = infinity;

        ArrayList<GameNode>successors_states = getSuccessors(node);
        
        for(int i = 0; i < successors_states.size(); i++) { //  # ---> Need to propagate values up tree for this to work
        		min_value = Math.min(min_value, maxValue(successors_states.get(i)));
        }  
        node.setValue(min_value);
        return min_value;
        
        
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
