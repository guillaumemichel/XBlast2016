package ch.epfl.xblast.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.client.Client;
import ch.epfl.xblast.server.Server;

public final class Main {

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(() -> createUI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*ActionListener l = (e->{
           Client.createUI(channel, address, frame0);
           Client.main(); 
        });
        SwingUtilities.invokeLater(() ->{
            ModelMenu model = new ModelMenu();
            ViewMenu view = new ViewMenu();
            ControllerMenu controller = new ControllerMenu(model,view);
            controller.control(l, 0);
        });*/

    }
    
    private static void createUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("XBlast 2016");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200,800));
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450,100));
        panel.setLayout(new BoxLayout(panel, 1));
        panel.setBackground(Color.lightGray);
        //panel.setAlignmentY(SwingConstants.CENTER);
        
        JLabel label = new JLabel("XBlast");//il faudra mettre une image
        label.setFont(new Font("GB18030 Bitmap",Font.BOLD,86));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        //label.setSize(new Dimension(200,100));
        
        JLabel names = new JLabel("By Vandenbroucque & Michel",SwingConstants.CENTER);
        names.setFont(new Font("Arial",Font.ITALIC,28));
        names.setMaximumSize(new Dimension(450,40));
        names.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnc = new JButton("Create Game");
        btnc.setBackground(Color.RED);
        btnc.setFont(new Font("Arial",Font.PLAIN,36));
        btnc.setMaximumSize(new Dimension(450,100));
        btnc.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnc.addActionListener(e->{
            Client.main();
            removeUI(frame);
        });
        /*btn.addActionListener(e -> {
            Client.main(new String[0]);
            String[] str = {"1"};
            Server.main(new String[0]);
        });*/
        
        JButton btnj = new JButton("Join Game");
        btnj.setBackground(Color.RED);
        btnj.setFont(new Font("Arial",Font.PLAIN,36));
        btnj.setMaximumSize(new Dimension(450,100));
        btnj.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnj.addActionListener(e->{
            Join.joinMenu(frame);
            removeUI(frame);
        });
        
        JCheckBox sound = new JCheckBox("Sound");
        sound.setBounds(400, 400, 50, 50);
        
        panel.add(Box.createRigidArea(new Dimension(0,40)));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(names);
        panel.add(Box.createRigidArea(new Dimension(0,50)));
        panel.add(btnc);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(btnj);
        panel.add(sound);
        
        //panel.setBackground(new Color(0x00FF00));//il faudra choisir un background
        frame.getContentPane().add(panel);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void removeUI(JFrame frame){
        frame.setVisible(false);
    }

}
