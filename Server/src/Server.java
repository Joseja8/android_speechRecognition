import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
/**
 *
 * @author Joaquín Ramírez Guerra
 */
public class Server {

    private static ServerSocket server;
    private static Socket s;
    private static InetAddress clientAddress;
    private static boolean clientConnected;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

        server = new ServerSocket(4444);
        clientConnected = false;

        while(true){
            connect();
        }
    }

    // Wait for client connection
    public static void connect() throws IOException{
        while (true) {
            System.out.println("Waiting for clients to connect...");
            s = server.accept();
            clientAddress = s.getInetAddress();
            System.out.println("Incoming connection from: " + clientAddress.getHostName() + "[" + clientAddress.getHostAddress() + "]");
            clientConnected = true;
            readClientMessage();
        }
    }

    // Receive message from client
    public static void readClientMessage() throws IOException{
        // Receive message from client
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String data="";
        while ((data = br.readLine()) != null){
            System.out.println("Message from client:---"+data+"---");
            String[] cmd = {
                    "python",
                    "Connector/main.py",
                    data
            };
            Process p = Runtime.getRuntime().exec(cmd);
            if (data.equalsIgnoreCase("Sayonara")){
                break;
            }
        }
        System.out.println("Connection from: " + clientAddress.getHostName() + "[" + clientAddress.getHostAddress() + "]" + " closed ");
        clientConnected = false;
        s.close();
    }

}
