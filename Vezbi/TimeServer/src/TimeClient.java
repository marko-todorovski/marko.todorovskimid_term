import java.io.*;
import java.net.*;

public class TimeClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 9090;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to server");


            String serverTime = in.readLine();
            System.out.println("Server time: " + serverTime);

        } catch (IOException e) {
            System.err.println("Error: Server unavailable");
        }
    }
}

//a
