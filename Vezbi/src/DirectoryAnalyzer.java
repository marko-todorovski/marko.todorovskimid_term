import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

        public class DirectoryAnalyzer {
            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                while (true) {
                    System.out.print("Enter command: ");
                    String input = scanner.nextLine().trim();
                    if (input.equalsIgnoreCase("exit")) break;

                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2 || !parts[0].equals("analyze")) {
                        System.out.println("Invalid command");
                        continue;
                    }

                    File dir = new File(parts[1]);
                    if (!dir.exists() || !dir.isDirectory()) {
                        System.out.println("Error: Directory not found");
                        continue;
                    }

                    File[] files = dir.listFiles();
                    if (files == null) {
                        System.out.println("Error: Access denied");
                        continue;
                    }

                    for (File file : files) {
                        if (file.isFile()) {
                            System.out.printf("File: %s | Size: %d bytes | Last Modified: %s%n",
                                    file.getName(), file.length(), sdf.format(file.lastModified()));
                        } else if (file.isDirectory()) {
                            int itemCount = file.list() != null ? file.list().length : 0;
                            System.out.printf("Directory: %s | Contains %d items%n", file.getName(), itemCount);
                        }
                    }
                }
                System.out.println("Exiting...");
                scanner.close();
            }
        }

//a