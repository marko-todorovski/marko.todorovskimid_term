import java.util.Scanner;

public class ParallelReader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program...");
                break;
            }

            String[] parts = command.split("\\s+");
            if (parts.length < 2 || !parts[0].equalsIgnoreCase("count")) {
                System.out.println("Invalid command. Use: count <file1> <file2> ...");
                continue;
            }


            Thread[] threads = new Thread[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                threads[i - 1] = new WordCounterTask(parts[i]);
                threads[i - 1].start();
            }


            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.err.println("Thread interrupted: " + e.getMessage());
                }
            }

            System.out.println("Word count complete");
        }
        scanner.close();
    }
}
//a