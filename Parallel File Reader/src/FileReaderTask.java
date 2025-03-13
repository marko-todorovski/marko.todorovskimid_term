import java.io.*;
import java.util.Scanner;

// FileReaderTask class
class FileReaderTask extends Thread {
    private final String fileName;

    public FileReaderTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Error: " + fileName + " not found");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(fileName + ": " + line);
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to read " + fileName);
        }
    }
}