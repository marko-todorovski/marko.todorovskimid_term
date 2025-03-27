import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BoardServer {
    private static final int PORT = 8080;
    private static Map<String, List<String>> messageBoard = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    handleClient(clientSocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] command = inputLine.split(" ", 3);

                if (command[0].equalsIgnoreCase("post") && command.length == 3) {
                    postMessage(command[1], command[2], out);
                } else if (command[0].equalsIgnoreCase("read") && command.length == 2) {
                    readMessages(command[1], out);
                } else if (command[0].equalsIgnoreCase("bye")) {
                    out.println("Goodbye!");
                    break;
                } else {
                    out.println("Error: Invalid command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void postMessage(String hashtag, String message, PrintWriter out) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String formattedMessage = "[" + timestamp + "] " + message;

        messageBoard.putIfAbsent(hashtag, new ArrayList<>());
        messageBoard.get(hashtag).add(formattedMessage);

        out.println("Message posted");
    }

    private static void readMessages(String hashtag, PrintWriter out) {
        List<String> messages = messageBoard.getOrDefault(hashtag, new ArrayList<>());

        if (messages.isEmpty()) {
            out.println("No messages found");
        } else {
            for (String message : messages) {
                out.println(message);
            }
        }
    }
}
