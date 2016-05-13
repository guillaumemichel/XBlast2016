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
    public static void playSound2(){
        try {
            File source = new File("sounds/GoT8bits.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(source);
            
            Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, sound.getFormat()));
            clip.open(sound);
            clip.addLineListener(new LineListener() {
                public void update(LineEvent event) {
                  if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                    System.exit(0);
                  }
                }
            });
            clip.start();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void play(){
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
              Main.class.getResourceAsStream("/sounds/GoT8bits.wav"));
            clip.open(inputStream);
            clip.start(); 
          } catch (Exception e) {
            System.err.println(e.getMessage());
          }
    }
    public static void playSound1() {
        new Thread(new Runnable() {
        // The wrapper thread is unnecessary, unless it blocks on the
        // Clip finishing; see comments.
          public void run() {
            try {
              Clip clip = AudioSystem.getClip();
              //File soundFile = new File("sounds/GoT8bits.wav");
              AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                      new File("sounds/GoT8bits.wav"));
              AudioInputStream pcmIn = AudioSystem.getAudioInputStream(
                      new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100f, 16, 1, 2, 44100f, true), inputStream);
                      

              clip.open(pcmIn);
              clip.start();
            } catch (Exception e) {
              System.err.println(e.getMessage());
            }
            
          }
        }).start();
      }
    private static final int BUFFER_SIZE = 128000;
    private static File soundFile;
    private static AudioInputStream audioStream;
    private static AudioFormat audioFormat;
    private static SourceDataLine sourceLine;

    /**
     * @param filename the name of the file that is going to be played
     */
    public static void playSound(){

        String strFilename = "sounds/GoT8bits.wav";

        try {
            soundFile = new File(strFilename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        //sourceLine.drain();
        //sourceLine.close();
    }
}
