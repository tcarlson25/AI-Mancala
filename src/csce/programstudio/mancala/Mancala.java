package csce.programstudio.mancala;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Mancala extends JFrame{
	
	private static MancalaView mancalaView;
	private static MancalaModel mancalaModel;
	
	public static void main(String args[]) {
		mancalaView = new MancalaView();
		mancalaModel = new MancalaModel();
		new MancalaController(mancalaView, mancalaModel);
	}
}