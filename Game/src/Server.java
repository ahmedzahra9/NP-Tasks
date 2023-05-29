import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int PORT = 5000;
        int maxAttempts = 5;

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for players to connect...");

            Socket player1Socket = serverSocket.accept();
            System.out.println("Player 1 connected.");

            Socket player2Socket = serverSocket.accept();
            System.out.println("Player 2 connected.");

            // Generate a random number between 1 and 100
            int randomNumber = (int) (Math.random() * 100) + 1;
            System.out.println("The Number is: "+ randomNumber);

            // Start a new game thread
            Game game = new Game(player1Socket, player2Socket,randomNumber, maxAttempts);
            game.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
