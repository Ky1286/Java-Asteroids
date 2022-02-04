package asteroid.entity;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
    
    public static final String laser = "file:./src/asteroid/util/Sounds/Jackie sfx.wav";
    public static final String yab = "file:./src/asteroid/util/Sounds/YabaDaba DOOO.wav";
    public static final String music = "file:./src/asteroid/util/Sounds/Shelter.wav";
    public static final String music2 = "file:./src/asteroid/util/Sounds/Take me on.wav";
    public static final String thrust = "file:./src/asteroid/util/Sounds/thrust.au";
    
    public static void playSoundEffect(String soundToPlay) {
        URL soundLocation;
        try {
            soundLocation = new URL(soundToPlay);
            Clip clip = null;
            clip = AudioSystem.getClip();
            AudioInputStream inputStream;
            inputStream = AudioSystem.getAudioInputStream(soundLocation);
            clip.open(inputStream);
            clip.loop(0);
            clip.start();

            clip.addLineListener(new LineListener() {
                public void update(LineEvent evt) {
                    if (evt.getType() == LineEvent.Type.STOP) {
                        evt.getLine().close();
                    }
                }
            });

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}