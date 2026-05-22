import javax.sound.sampled.*;
import java.net.URL;

public class SoundManager {
    private Clip clip;

    public void playMusic(String path) {
        try {
            URL url = getClass().getResource(path);
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void playOnce(String path, Runnable onFinish) {
        try {
            URL url = getClass().getResource(path);
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            c.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    c.close();
                    if (onFinish != null) onFinish.run();
                }
            });
            c.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}