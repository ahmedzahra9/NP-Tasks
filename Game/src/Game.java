import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Game extends Thread {
    private Socket player1Socket;
    private Socket player2Socket;
    private int randomNumber;
    private int maxAttempts;

    public Game(Socket player1Socket, Socket player2Socket, int randomNumber, int maxAttempts) {
        this.player1Socket = player1Socket;
        this.player2Socket = player2Socket;
        this.randomNumber = randomNumber;
        this.maxAttempts = maxAttempts;
    }

    public void run() {
        try {
            PrintWriter player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
            PrintWriter player2Out = new PrintWriter(player2Socket.getOutputStream(), true);
            BufferedReader player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
            BufferedReader player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));

            player1Out.println("You are Player 1. Guess a number between 1 and 100.");
            player2Out.println("You are Player 2. Guess a number between 1 and 100.");

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                String player1Guess = player1In.readLine();
                System.out.println("Player 1 guessed: " + player1Guess);

                if (Integer.parseInt(player1Guess) == randomNumber) {
                    player1Out.println("Congratulations! You guessed the number.");
                    player2Out.println("Game over. Player 1 guessed the number.");
                    endGame();
                    return;
                } else if (attempt == maxAttempts) {
                    player1Out.println("Game over. You did not guess the number.");
                    player2Out.println("Game over. Player 1 did not guess the number.");
                    endGame();
                    return;
                } else {
                    player1Out.println("Incorrect guess. Try again.");
                }

                String player2Guess = player2In.readLine();
                System.out.println("Player 2 guessed: " + player2Guess);

                if (Integer.parseInt(player2Guess) == randomNumber) {
                    player1Out.println("Game over. Player 2 guessed the number.");
                    player2Out.println("Congratulations! You guessed the number.");
                    endGame();
                    return;
                } else if (attempt == maxAttempts) {
                    player1Out.println("Game over. Player 2 did not guess the number.");
                    player2Out.println("Game over. You did not guess the number.");
                    endGame();
                    return;
                } else {
                    player2Out.println("Incorrect guess. Try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endGame() {
        try {
            player1Socket.close();
            player2Socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
