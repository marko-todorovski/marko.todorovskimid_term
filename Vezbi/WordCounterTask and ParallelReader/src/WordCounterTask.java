import java.io.*;

public class WordCounterTask extends Thread {
    private String filename;

    public WordCounterTask(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        int wordCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordCount += line.split("\\s+").length; // Split by spaces
            }
            System.out.println(filename + ": " + wordCount + " words");
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + filename + " not found");
        } catch (IOException e) {
            System.err.println("Error reading " + filename);
        }
    }
}
//a