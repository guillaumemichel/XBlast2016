package ch.epfl.xblast.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

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
            inputStream = AudioSystem.getAudioInputStream(new File("sounds/noam.wav"));
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
