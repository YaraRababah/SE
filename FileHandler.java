
package javaapplication15;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final FileHandler instance = new FileHandler();
    private FileHandler() {}
    public static FileHandler getInstance() {
        return instance;
    }
    public List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    public void writeToFile(String fileName, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : data) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

