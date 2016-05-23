package ch.epfl.xblast.menu;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Join {
    public final static void joinMenu(JFrame frame){
        JFrame frame1 = new JFrame();
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setPreferredSize(frame.getSize());
        frame1.pack();
        frame1.setVisible(true);
    }
}
