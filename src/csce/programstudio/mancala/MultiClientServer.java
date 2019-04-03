package csce.programstudio.mancala;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiClientServer implements Runnable{
	private boolean started;
    private boolean running;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private static int port;
    private MancalaModel mancalaModel;
    private MancalaView mancalaView;
    private int playerNumber;
    private ClientHandler[] handlers;
    private boolean error;
    private int playerToSend;
    
	public MultiClientServer(int port, MancalaModel m, MancalaView v) {
        started = false;
        error = false;
        serverSocket = null;
        MultiClientServer.port = port;
        mancalaModel = m;
        mancalaView = v;
        handlers = new ClientHandler[2];
        playerToSend = 1; // server starts off always sending first action to player 2
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
            } catch(Exception e) {
            		System.exit(0);
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
        		int numConnections = 0;
            	while(running) {
            		try {
            			Socket client = serverSocket.accept();
            			System.out.println("Client Accepted!");
            			numConnections++;
            			
            			handlers[numConnections - 1] = new ClientHandler(client);
            			
            			// if client is waiting for another client, send him a welcome message so he can wait
            			if(numConnections == 1) {
            				handlers[numConnections - 1].sendMessage("WELCOME");
            			}
            			
            			// both clients connected, start game
            			if(numConnections == 2) {
                			handlers[0].sendMessage("INFO " + mancalaModel.getGameDetails(true));
                			handlers[1].sendMessage("INFO " + mancalaModel.getGameDetails(false));
                			running = false;
                			serverSocket.close();
                			mancalaView.getMancalaHost().getErrorMessage().setText("Player's connected!");
            			}
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
		MultiClientServer.port = port;
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

	public ClientHandler[] getHandlers() {
		return handlers;
	}

	public void setHandlers(ClientHandler[] handlers) {
		this.handlers = handlers;
	}
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public int getPlayerToSend() {
		return playerToSend;
	}

	public void setPlayerToSend(int playerToSend) {
		this.playerToSend = playerToSend;
	}

	public void parse(String cmd) {
	    String[] tokens = cmd.split(" ");
	    if(tokens.length < 1) {
	      throw new IllegalArgumentException();
	    } else if (tokens[0].equals("OK")) {
	    		MancalaController.multiClientServerCheckForWinner();
	    		mancalaModel.setRemainingTime(mancalaModel.getInputedTime());
	    		handlers[playerToSend].sendMessage("OK");
	    		playerToSend = 1 - playerToSend;
	    } else if (tokens[0].equals("READY")) {
	    		mancalaView.getMancalaGame().getTimer().start();
	    } else if (tokens[0].equals("P")) {
	    		mancalaModel.pieRule();
		    	handlers[playerToSend].sendMessage("P");
	    } else {
		    	for(int i = 0; i < tokens.length; ++i) {
	    			mancalaModel.forceBasicMove(mancalaModel.getMancalaBoard().getCurrentPlayer(), Integer.parseInt(tokens[i]));
	    		}
	    		MancalaController.multiClientServerCheckForWinner();
		    	String moveToSend = "";
        		for(int i = 0; i < tokens.length; ++i) {
        			moveToSend += tokens[i] + " ";
        		}
		    	handlers[playerToSend].sendMessage(moveToSend.trim());
	    }   
	    playerToSend = 1 - playerToSend;	// switch playerToSend
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
