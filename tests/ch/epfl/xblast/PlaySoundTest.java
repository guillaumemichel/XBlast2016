package ch.epfl.xblast;


import java.util.Scanner;

import ch.epfl.xblast.client.PlaySound;

public class PlaySoundTest {

    public static void main(String[] args) {
        PlaySound.loop();
        Scanner keyb = new Scanner(System.in);
        String str = keyb.nextLine();
        PlaySound.stop();
    }

}
