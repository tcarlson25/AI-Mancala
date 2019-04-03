import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.LongStream;

class FactorServer {
    ServerSocket serverSocket;

    public FactorServer() {
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Thread clientThread = new Thread(new ClientHandler(serverSocket.accept()));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("closing server socket");
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        FactorServer es = new FactorServer();
        int port = 12321;
        if (args.length >= 1) {
            port = Integer.valueOf(args[0]);
        }
        es.start(port);
    }

    private static class ClientHandler implements Runnable {
        private static int numConnections;
        private int connectionId = 0;
        Socket clientSocket;

        public ClientHandler(Socket s) {
            connectionId = numConnections++;
            System.out.println("handling connection, #" + connectionId);
            clientSocket = s;
        }

        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                long a = Long.valueOf(in.readLine());
                long n = Long.valueOf(in.readLine());

                LongStream.range(0, Long.MAX_VALUE)
                        .map(i -> a + i * n)
                        .forEachOrdered(out::println);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                out.close();
                try {
                    in.close();
                    clientSocket.close();
                    System.out.println("closing connection, #" + connectionId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
