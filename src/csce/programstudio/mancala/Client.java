package csce.programstudio.mancala;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import csce.programstudio.mancala.MancalaController.AIThreadOnline;

public class Client implements Runnable{
	
	private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private Thread runningThread;
    private boolean running;
    private static int port;
    private MancalaModel mancalaModel;
    private MancalaView mancalaView;
    private boolean isAI;
    
	public Client(String host, int port, MancalaModel m, MancalaView v, boolean ai) {
        try {
        		Client.port = port;
        		try {
        			socket = new Socket(host, port);
        			writer = new PrintWriter(socket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                running = true;
                mancalaModel = m;
                mancalaView = v;
                isAI = ai;
                runningThread = new Thread(this);
                runningThread.start();
        		} catch(Exception e) {
        			MancalaController.getMancalaView().getMancalaJoin().getErrorMessage().setText("Could not connect given these inputs");
        			disconnect();
        		}
        } catch(Exception e){
        		e.printStackTrace(); 
        		disconnect();
        	}
    }

    public void disconnect() {
        running = false;
        if(runningThread != null)
            runningThread.interrupt();
        runningThread = null;

        reader = null;
        writer = null;
        
        try {
            socket.close();
        }catch(Exception e){}
        socket = null;
    }

    public void sendMessage(String message) {
        if(running) {
            writer.println(message);
            writer.flush();
        }
    }

    public void run() {
        try {
            String message = "";
            while((message = reader.readLine()) != null && running) {
                System.out.println("Message Recieved: " + message);
                parse(message);
            }
        } catch(Exception e){
        		e.printStackTrace(); disconnect();
        	}
    }
    
    public void parse(String cmd) {
        String[] tokens = cmd.split(" ");
        if(tokens.length < 1) {
          throw new IllegalArgumentException();
        }
        else if (tokens[0].equals("WELCOME")) {
        		mancalaView.getMancalaJoin().getErrorMessage().setText("Connected to server... Waiting for another player");
        		JButton playerButton = mancalaView.getMancalaJoin().getPlayAsAIButton();
        		JButton aiButton = mancalaView.getMancalaJoin().getPlayAsPlayerButton();
        		JButton menuButton = mancalaView.getMancalaJoin().getReturnToMenuButton();
        		for(ActionListener al : playerButton.getActionListeners()) {
        			playerButton.removeActionListener(al);
    			}
        		for(ActionListener al : aiButton.getActionListeners()) {
        			aiButton.removeActionListener(al);
    			}
        		for(ActionListener al : menuButton.getActionListeners()) {
        			menuButton.removeActionListener(al);
    			}
        }
        else if (tokens[0].equals("OK")) {
        		mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
        }
        else if (tokens[0].equals("ILLEGAL")) {
        		// doesn't ever get illegal move
        		mancalaModel.gameOver(true);
        }
        else if (tokens[0].equals("TIME")) {
        		mancalaView.getMancalaGame().getTimer().stop();
        }
        else if (tokens[0].equals("LOSER")) {
        		mancalaView.getMancalaGame().getTimerLabel().setText("You Lost!");
        		mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
        		MancalaController.disableBoardComponents();
        }
        else if (tokens[0].equals("WINNER")) {
        		mancalaView.getMancalaGame().getTimerLabel().setText("You Won!");
        		mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
        		MancalaController.disableBoardComponents();
        }
        else if (tokens[0].equals("TIE")) {
        		mancalaView.getMancalaGame().getTimerLabel().setText("TIE!");
    			mancalaView.getMancalaGame().getTimerLabel().setVisible(true);
    			MancalaController.disableBoardComponents();
        }
        else if (tokens[0].equals("INFO")) {
        		ArrayList<Integer> randomSeeds = new ArrayList<Integer>();
        		int numHouses = Integer.parseInt(tokens[1]);
        		int numSeeds = Integer.parseInt(tokens[2]);
        		int timeInMS = Integer.parseInt(tokens[3]);
        		boolean goFirst = (tokens[4].equals("F"));
        		boolean random = (tokens[5].equals("R"));
        		if(random) {
        			for(int i = 6; i < tokens.length; ++i) {
        				randomSeeds.add(Integer.parseInt(tokens[i]));
        			}
        		}
        		if(goFirst) {
        			mancalaModel.setPlayerNumber(0);
        		} else {
        			mancalaModel.setPlayerNumber(1);
        		}
        		MancalaController.initializeClientBoard(numHouses, numSeeds, random, timeInMS, randomSeeds);
        		if(mancalaModel.getMancalaBoard().getCurrentPlayer() == mancalaModel.getPlayerNumber()) {
        			mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Your Turn");
			} else {
				mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Opponent's Turn");
			}
        		sendMessage("READY");
        		mancalaView.getMancalaGame().getTimer().start();
        		if(isAI) {
        			if(goFirst) {
        				AIThreadOnline aiThreadOnline = new AIThreadOnline();
        				aiThreadOnline.start();
        			}
        		}
        }
        else if (tokens[0].equals("P")) {
        		mancalaModel.pieRule();
        		MancalaController.updateSeedCountLabels();
		    	mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Your Turn");
		    	mancalaView.getMancalaGame().setErrorMessage("");
		    	sendMessage("OK");
		    	mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
		    	if(isAI) {
		    		AIThreadOnline aiThreadOnline = new MancalaController.AIThreadOnline();
        			aiThreadOnline.start();
		    	}
        } else {
        		mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
        		for(int i = 0; i < tokens.length; ++i) {
	    			mancalaModel.forceBasicMove(mancalaModel.getMancalaBoard().getCurrentPlayer(), Integer.parseInt(tokens[i]));
	    		}
		    	MancalaController.updateSeedCountLabels();
		    	MancalaController.checkForPieRule();
		    	mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Your Turn");
		    	mancalaView.getMancalaGame().setErrorMessage("");
		    	sendMessage("OK");
		    	mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
		    	if(isAI) {
		    		mancalaView.getMancalaGame().getPieRule().setVisible(false);
    				AIThreadOnline aiThreadOnline = new AIThreadOnline();
    				aiThreadOnline.start();
        		}
        }    
    }
    
	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		Client.port = port;
	}

	public MancalaModel getMancalaModel() {
		return mancalaModel;
	}

	public void setMancalaModel(MancalaModel mancalaModel) {
		this.mancalaModel = mancalaModel;
	}

	public MancalaView getMancalaView() {
		return mancalaView;
	}

	public void setMancalaView(MancalaView mancalaView) {
		this.mancalaView = mancalaView;
	}

	public boolean isAI() {
		return isAI;
	}

	public void setAI(boolean isAI) {
		this.isAI = isAI;
	}

}
