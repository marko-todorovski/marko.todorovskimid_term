import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path filepath = Paths.get("log.txt");
        File file = filepath.toFile();
        String content = "Hello world!";

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content + System.lineSeparator());
            System.out.println("Content written to file: " + content);
        } catch (IOException e) {
            System.out.println("Error: Unable to write to file");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println("File content:");
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        } catch (IOException e) {
            System.out.println("Error: Unable to read the file");
        }
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted");
            } else {
                System.out.println("Error: Unable to delete the file");
            }
        } else {
            System.out.println("File does not exist");
        }

        System.out.println("Program finished!");
    }
}
