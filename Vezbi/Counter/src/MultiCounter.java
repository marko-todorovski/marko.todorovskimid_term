import java.util.Scanner;

public class MultiCounter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();
            if(input.equalsIgnoreCase("exit"))  break;


            String[] parts = input.split(" ");
            if(parts.length !=3 || !parts[0].equalsIgnoreCase("start")) {
                System.out.println("Invalid command");
                continue;
            }
    //a
            try {
                int numThreads = Integer.parseInt(parts[1]);
                int incrementsPerThread = Integer.parseInt(parts[2]);
                Counter counter = new Counter();

                Thread[] threads = new Thread[numThreads];
                for (int i = 0; i < numThreads; i++) {
                    threads[i] = new CounterThread(counter, incrementsPerThread);
                    threads[i].start();
                }

                for (Thread t : threads) {
                    t.join();
                }

                System.out.println("All threads finished. Final counter value: " + counter.getValue());
            }catch (NumberFormatException |InterruptedException e) {
                System.out.println("Error: Invalid input or thread error");

            }

            }
        scanner.close();
        System.out.println("All threads finished.");

        }
    }
