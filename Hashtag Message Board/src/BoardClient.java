import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BoardClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server");

            String userInput;
            while (true) {
                System.out.print("Enter command: ");
                userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("bye")) {
                    out.println("bye");
                    System.out.println("Disconnected from server");
                    break;
                } else {
                    out.println(userInput);
                    String response = in.readLine();
                    System.out.println("Server response: " + response);

                    // If read command is used, keep reading the server's response until no more messages
                    if (userInput.startsWith("read") && !response.equals("No messages found")) {
                        while (response != null && !response.isEmpty()) {
                            System.out.println(response);
                            response = in.readLine();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Server unavailable");
        }
    }
}
