import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;

public class ClientStreamIterator {
    private BufferedReader serverReader;
    private long current;

    public ClientStreamIterator(long a, long n, String hostName, int portNumber) throws IOException {
        Socket socket = new Socket(hostName, portNumber);
        this.serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(a);
        out.println(n);
        this.current = Long.valueOf(serverReader.readLine());
    }

    public ClientStreamIterator(BufferedReader serverReader) throws IOException {
        this.serverReader = serverReader;
        this.current = Long.valueOf(serverReader.readLine());
    }

    public long consume() throws IOException {
        this.current = Long.valueOf(serverReader.readLine());
        return current;
    }

    public long head() {
        return current;
    }

    public static void consume(long n, Collection<ClientStreamIterator> streams) throws IOException {
        for (ClientStreamIterator stream : streams) {
            if (stream.head() == n) {
                stream.consume();
            }
        }
    }

    public static long minHead(Collection<ClientStreamIterator> streams) {
        return streams.stream()
                .mapToLong(ClientStreamIterator::head)
                .min()
                .getAsLong();
    }

    public static long maxHead(Collection<ClientStreamIterator> streams) {
        return streams.stream()
                .mapToLong(ClientStreamIterator::head)
                .max()
                .getAsLong();
    }
}