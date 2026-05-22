import java.io.*;

public class FileManager {
    private static FileManager instance;
    private int highScore = 0;

    private File file = new File("HIGHSCORE.txt");
    private File logFile = new File("SCORE MANAGEMENT.txt");
    private BufferedWriter writer;

    private FileManager() {
        loadHighScore();
        try {
            writer = new BufferedWriter(new FileWriter(logFile,true));
            writer.write("=== NEW SESSION ===\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    public void loadHighScore() {
        try {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                if (line != null && !line.isBlank()) {
                    highScore = Integer.parseInt(line.trim());
                } else {
                    highScore = 0;
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            try {
                BufferedWriter hsWriter = new BufferedWriter(new FileWriter("HIGHSCORE.txt"));
                hsWriter.write(String.valueOf(score));
                hsWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void log(int sequenceNo, int cakeNo, int score, int sequenceScore, int sequenceStartScore) {
        try {
            writer.write("Sequence Number: " + sequenceNo + "\n");
            writer.write("Cake Number: " + cakeNo + "\n");
            writer.write("Score: " + score + "\n");
            writer.write("Sequence Score: " + sequenceScore + "\n");
            writer.write("Sequence Start Score: " + sequenceStartScore + "\n");
            writer.write("----------\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        return highScore;
    }
}