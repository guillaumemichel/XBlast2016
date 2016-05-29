package ch.epfl.xblast.client;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public final class PlaySound {
    private static Clip clip;
    private static AudioInputStream inputStream;

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
    
    public static void stop(){
        clip.close();
    }
    
    public static void repeat(int n){
            try {
                clip = AudioSystem.getClip();
                inputStream = AudioSystem.getAudioInputStream(new File("sounds/Faggioni.wav"));
                clip.open(inputStream);
                clip.loop(n);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
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
