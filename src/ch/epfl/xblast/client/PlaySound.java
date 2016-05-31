package ch.epfl.xblast.client;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * A PlaySound class, that can play sounds *incredible*
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 * Source of the song : http://www.newgrounds.com/audio/listen/476918
 */
public final class PlaySound {
    private static Clip clip;
    private static AudioInputStream inputStream;

    /**
     * Play the song Games of Thrones 8 bits once
     */
    public static void play() {
            try {
              clip = AudioSystem.getClip();
              inputStream = AudioSystem.getAudioInputStream(new File("sounds/GoT8bits.wav"));
              clip.open(inputStream);
              clip.start();
            } catch (Exception e) {
              System.err.println(e.getMessage());
            }
      }
    
    /**
     * stop the music
     */
    public static void stop(){
        clip.close();
    }
    
    /**
     * Play the song Games of Thrones 8 bits n times
     * 
     * @param n
     *      The number of times that the song will be repeated
     */
    public static void repeat(int n){
            try {
                clip = AudioSystem.getClip();
                inputStream = AudioSystem.getAudioInputStream(new File("sounds/GoT8bits.wav"));
                clip.open(inputStream);
                clip.loop(n);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    /**
     * Loop on the song Games of Thrones 8 bits until stop is called
     */
    public static void loop(){
        try {
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(new File("sounds/GoT8bits.wav"));
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
