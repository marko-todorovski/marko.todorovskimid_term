import java.util.Scanner;

// ParallelReader class
public class ParallelReader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to ParallelReader! Enter commands (read <file1> <file2>, exit):");

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            if (parts[0].equalsIgnoreCase("read")) {
                if (parts.length != 3) {
                    System.out.println("Error: Invalid input. Usage: read <file1> <file2>");
                } else {
                    String file1 = parts[1];
                    String file2 = parts[2];

                    FileReaderTask task1 = new FileReaderTask(file1);
                    FileReaderTask task2 = new FileReaderTask(file2);

                    task1.start();
                    task2.start();

                    try {
                        task1.join();
                        task2.join();
                        System.out.println("Reading complete");
                    } catch (InterruptedException e) {
                        System.out.println("Error: Thread interrupted");
                    }
                }
            } else if (parts[0].equalsIgnoreCase("exit")) {
                System.out.println("Exiting ParallelReader. Goodbye!");
                break;
            } else {
                System.out.println("Error: Unknown command");
            }
        }

        scanner.close();
    }
}