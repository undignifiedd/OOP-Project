import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;

public class SoundManager {
    private Clip clip;

    public void playMusic(String path){
        AudioInputStream audio;
        try {
                audio = AudioSystem.getAudioInputStream(
                        getClass().getResourceAsStream(path));

            clip=AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void stopMusic(){
        if (clip!=null){
            clip.stop();
        }
    }
    public void playOnce(String path, Runnable onFinish) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream(path)
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    if (onFinish != null) onFinish.run();
                }
            });
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
