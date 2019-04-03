package csce.programstudio.mancala;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MancalaView extends JFrame{

	private MancalaHomeView mancalaHome;
	private MancalaGameView mancalaGame;
	private MancalaLocalView mancalaLocal;
	private MancalaOnlineView mancalaOnline;
	private MancalaHostView mancalaHost;
	private MancalaJoinView mancalaJoin;
	
	MancalaView() {
		super("Mancala");
		setSize(1000,600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mancalaHome = new MancalaHomeView();
		
		getContentPane().add(mancalaHome);
		
		setVisible(true);
	}

	public MancalaHomeView getMancalaHome() {
		return mancalaHome;
	}

	public void setMancalaHome(MancalaHomeView mancalaHome) {
		this.mancalaHome = mancalaHome;
	}

	public MancalaGameView getMancalaGame() {
		return mancalaGame;
	}

	public void setMancalaGame(MancalaGameView mancalaGame) {
		this.mancalaGame = mancalaGame;
	}

	public MancalaLocalView getMancalaLocal() {
		return mancalaLocal;
	}

	public void setMancalaLocal(MancalaLocalView mancalaLocal) {
		this.mancalaLocal = mancalaLocal;
	}

	public MancalaOnlineView getMancalaOnline() {
		return mancalaOnline;
	}

	public void setMancalaOnline(MancalaOnlineView mancalaOnline) {
		this.mancalaOnline = mancalaOnline;
	}
	
	public MancalaHostView getMancalaHost() {
		return mancalaHost;
	}

	public void setMancalaHost(MancalaHostView mancalaHost) {
		this.mancalaHost = mancalaHost;
	}
	
	public MancalaJoinView getMancalaJoin() {
		return mancalaJoin;
	}

	public void setMancalaJoin(MancalaJoinView mancalaJoin) {
		this.mancalaJoin = mancalaJoin;
	}
	
}
