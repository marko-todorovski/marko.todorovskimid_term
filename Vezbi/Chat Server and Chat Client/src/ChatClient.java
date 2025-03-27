import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 9090);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to chat server.");

            Thread readerThread = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ignored) {}
            });
            readerThread.start();

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                out.println(message);
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            System.out.println("Disconnected from server.");

        } catch (IOException e) {
            System.out.println("Error: Unable to connect to server.");
        }
    }
}
