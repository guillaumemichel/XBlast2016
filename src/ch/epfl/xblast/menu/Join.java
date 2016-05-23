package ch.epfl.xblast.menu;

import java.awt.Dimension;

import javax.swing.JFrame;

public final class Join {
    public final static void joinMenu(JFrame frame){
        frame.setVisible(false);
        JFrame frame1 = new JFrame();
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setPreferredSize(frame.getSize());
        frame1.pack();
        frame1.setVisible(true);
    }
}
