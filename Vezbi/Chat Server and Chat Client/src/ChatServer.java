import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    private static List<PrintWriter> clients = new CopyOnWriteArrayList<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("Chat server started on port 9090");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
            new ClientHandler(clientSocket).start();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                clients.add(out);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("bye")) {
                        break;
                    }
                    String timestamp = sdf.format(new Date());
                    broadcast("[" + timestamp + "] " + message);
                }
            } catch (IOException e) {
                System.out.println("Connection lost: " + socket.getInetAddress().getHostAddress());
            } finally {
                try {
                    clients.remove(out);
                    socket.close();
                    System.out.println("User disconnected: " + socket.getInetAddress().getHostAddress());
                } catch (IOException ignored) {}
            }
        }

        private void broadcast(String message) {
            for (PrintWriter client : clients) {
                client.println(message);
            }
        }
    }
}
