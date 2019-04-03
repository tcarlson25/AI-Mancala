import java.io.IOException;
import java.util.ArrayList;

public class crtClient2 {
    public static void main(String[] args) throws IOException {

        if (args.length <= 2) {
            System.err.println("Usage: java crtClient <host name> <port number> [a n]*");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

		    int arrSize = ((args.length - 2)/2);
        int[] a = new int[arrSize];
        int[] n = new int[arrSize];
        for (int i = 2; i < args.length; i++) {
        	if (i % 2 == 0) {
        		a[(i/2 - 1)] = Integer.parseInt(args[i]);
        	}
        	else {
        		n[(i/2 - 1)] = Integer.parseInt(args[i]);
        	}
        }
        ArrayList<ClientStreamIterator> streams = new ArrayList<>();
        for (int i = 0; i < n.length; i++) {
            streams.add(new ClientStreamIterator(a[i], n[i], hostName, portNumber));
        }
        while (ClientStreamIterator.maxHead(streams) != ClientStreamIterator.minHead(streams)) {
            ClientStreamIterator.consume(ClientStreamIterator.minHead(streams), streams);
        }
        System.out.println(ClientStreamIterator.minHead(streams));
    }
}
