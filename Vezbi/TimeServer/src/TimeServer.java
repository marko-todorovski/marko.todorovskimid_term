import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeServer {
    public static void main(String[] args) {
        int port = 9090;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());


                String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(currentTime);

                System.out.println("Sent time: " + currentTime);


                clientSocket.close();
                System.out.println("Client disconnected");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
//a