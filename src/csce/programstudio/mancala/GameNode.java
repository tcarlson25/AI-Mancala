package csce.programstudio.mancala;
import java.util.ArrayList;

public class GameNode {
	private int value = 0;
	private int move_index = -1;
	public Board board;
	public GameNode parent = null;
	public ArrayList<GameNode> children = new ArrayList<GameNode>();
	
	// default constructor
	public GameNode() {}
	
	// copy constructor 
	public GameNode(GameNode g) {
		this.value = g.value;
		this.move_index = g.move_index;
		this.board = new Board(g.board);
		this.parent = g.parent;
		this.children = g.children;
	}
	
	// constructor
	public GameNode(Board b, GameNode parent){
		this.board = b;
		this.parent = parent;
	}
	
	// constructor
	public GameNode(Board b, GameNode parent, int v){
		this.board = b;
		this.parent = parent;
		this.value = v;
	}

	// constructor
	public GameNode(Board b, GameNode parent, int v, int m){
		this.board = b;
		this.parent = parent;
		this.value = v;
		this.move_index = m;
	}
	
	
	// functions
	public void addChild(GameNode childNode) {
		this.children.add(childNode);
	}
	
	public ArrayList<GameNode> getChildren(){
		return children;
	}
	
	public void setValue(int val) {
		this.value = val;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setMoveIndex(int m) {
		move_index = m;
	}
	
	public int getMoveIndex() {
		return move_index;
	}
}
