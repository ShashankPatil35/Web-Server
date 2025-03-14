
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket>getConsumer(){
        return (ClientSocket)->{
            try {
                PrintWriter toClient = new PrintWriter(ClientSocket.getOutputStream(),true);
                toClient.println("Hello from the server");
                toClient.close();
                ClientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }; 
    }
    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
    
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on this port "+port);
            
            while(true){
                Socket acceptedSocket = serverSocket.accept();
                acceptedSocket.setSoTimeout(10000);
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
