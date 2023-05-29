import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player1 {
    public static void main(String[] args) {
        final String SERVER_IP = "127.0.0.1";
        final int SERVER_PORT = 5000;

        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String serverMessage = in.readLine();
            System.out.println(serverMessage);

            while (true) {
                String guess = reader.readLine();
                out.println(guess);

                serverMessage = in.readLine();
                System.out.println(serverMessage);

                if (serverMessage.startsWith("Game over")) {
                    break;
                }
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
