import java.io.*;

public class FileManager {
    Cake playerCake;
    Cake currentOrder;
    private static FileManager instance;

    private File file = new File("SCORE MANAGEMENT.txt");
    private BufferedWriter writer;

    private FileManager() {
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    public void log(int sequenceNo, int cakeNo, int score,
            int sequenceScore, int sequenceStartScore, int threshHold) {
        try {
            writer.write("Sequence Number: " + sequenceNo + "\n");
            writer.write("Cake Number: " + cakeNo + "\n");
            writer.write("Score: " + score + "\n");
            writer.write("Sequence Score: " + sequenceScore + "\n");
            writer.write("Sequence Threshold: " + (threshHold) + "\n");
            writer.write("----------\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getFile() {
        return file;
    }
}
