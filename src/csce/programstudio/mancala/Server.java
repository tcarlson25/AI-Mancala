package csce.programstudio.mancala;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import csce.programstudio.mancala.MancalaController.AIThread;
import csce.programstudio.mancala.MancalaController.AIThreadOnline;
import csce.programstudio.mancala.MancalaController.MancalaHomeListener;
import csce.programstudio.mancala.MancalaController.MancalaHouseOnlineListener;

public class Server implements Runnable{
	
	private boolean started;
    private boolean running;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private static int port;
    private MancalaModel mancalaModel;
    private MancalaView mancalaView;
    private int playerNumber;
    private ClientHandler handler;
    private boolean error;
    
	public Server(int port, MancalaModel m, MancalaView v) {
        started = false;
        error = false;
        serverSocket = null;
        Server.port = port;
        mancalaModel = m;
        mancalaView = v;
	}

    public void start() {
        if(!started) {
            started = true;
            try {
        			serverSocket = new ServerSocket(port);
            		running = true;

            		serverThread = new Thread(this);
            		serverThread.start();

            		System.out.println("Server started!\n");
            		mancalaView.getMancalaHost().getErrorMessage().setText("Server started... waiting for another player");
            } catch(Exception e) {
            		MancalaController.getMancalaView().setVisible(false);
            		JOptionPane.showConfirmDialog(null, "Cannot Host Game on this Port", "Mancala", JOptionPane.PLAIN_MESSAGE);
        			MancalaController.setMancalaView(new MancalaView());
        			MancalaController.setMancalaModel(new MancalaModel());
        			MancalaController.getMancalaView().getMancalaHome().addMancalaHomeListeners(new MancalaHomeListener());
            }
        }
    }

    public void stop() {
        running = false;
        started = false;

        if(serverThread != null)
            serverThread.interrupt();
        serverThread = null;
        try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void run() {
        try {
            	while(running) {
            		try {
            			Socket client = serverSocket.accept();
            			System.out.println("Client Accepted!");

            			handler = new ClientHandler(client);

            			handler.sendMessage("WELCOME");
            			mancalaView.add(mancalaView.getMancalaGame());
            			mancalaView.getMancalaGame().setVisible(true);
            			mancalaView.getMancalaHost().setVisible(false);
            			handler.sendMessage("INFO " + mancalaModel.getGameDetails(false));
            			running = false;
            			serverSocket.close();
            		} catch(Exception e){
            			e.printStackTrace();
                	}
            	}
        	}catch(Exception e){
        		e.printStackTrace();
        	}
    	}

    public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
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
		Server.port = port;
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

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public ClientHandler getHandler() {
		return handler;
	}

	public void setHandler(ClientHandler handler) {
		this.handler = handler;
	}
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void parse(String cmd) {
	    String[] tokens = cmd.split(" ");
	    if(tokens.length < 1) {
	      throw new IllegalArgumentException();
	    } else if (tokens[0].equals("OK")) {
	    		MancalaController.serverCheckForWinner();
	    		mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
	    } else if (tokens[0].equals("READY")) {
	    		if(!mancalaModel.getMancalaBoard().getPlayer1().isAI()) {
	    			mancalaView.getMancalaGame().addMancalaHouseListeners(new MancalaHouseOnlineListener());
	    		} else {
	    			AIThreadOnline aiThreadOnline = new MancalaController.AIThreadOnline();
        			aiThreadOnline.start();
	    		}
	    		mancalaView.getMancalaGame().getTimer().start();
	    } else if (tokens[0].equals("P")) {
	    		mancalaModel.pieRule();
	    		MancalaController.updateSeedCountLabels();
		    	mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Your Turn");
		    	mancalaView.getMancalaGame().setErrorMessage("");
		    	handler.sendMessage("OK");
		    	mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
		    	if(mancalaModel.getMancalaBoard().getPlayer1().isAI()) {
		    		AIThreadOnline aiThreadOnline = new MancalaController.AIThreadOnline();
        			aiThreadOnline.start();
		    	}
	    } else {
	    		for(int i = 0; i < tokens.length; ++i) {
	    			mancalaModel.forceBasicMove(mancalaModel.getMancalaBoard().getCurrentPlayer(), Integer.parseInt(tokens[i]));
	    		}
		    	MancalaController.updateSeedCountLabels();
		    	MancalaController.checkForPieRule();
		    	mancalaView.getMancalaGame().getCurrentPlayerLabel().setText("Your Turn");
		    	mancalaView.getMancalaGame().setErrorMessage("");
		    	MancalaController.serverCheckForWinner();
		    	handler.sendMessage("OK");
		    	mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
		    	if(mancalaModel.getMancalaBoard().getPlayer1().isAI()) {
		    		AIThreadOnline aiThreadOnline = new MancalaController.AIThreadOnline();
        			aiThreadOnline.start();
		    	}
	    }   
	}

	public class ClientHandler implements Runnable {
    	
    			private Socket socket;
	        private PrintWriter writer;
	        private BufferedReader reader;
	
	        private Thread runningThread;
	        private boolean running;
	        
	        
	        public ClientHandler(Socket socket) {
            this.socket = socket;

            try {
                writer = new PrintWriter(socket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                running = true;

                runningThread = new Thread(this);
                runningThread.start();
            } catch(Exception e){
            		e.printStackTrace(); disconnect();
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
            } catch(Exception e){e.printStackTrace(); disconnect();}
        }

    }
}
