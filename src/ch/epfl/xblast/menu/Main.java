package ch.epfl.xblast.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
import javax.swing.Timer;

import ch.epfl.xblast.client.Client;
import ch.epfl.xblast.client.ClientBis;
import ch.epfl.xblast.client.KeyboardEventHandler;
import ch.epfl.xblast.server.Server;

public final class Main {
    private static ModelMenu model = new ModelMenu();
    private static ViewMenu view = new ViewMenu(model);
    private static JFrame frame = view.getFrame();
    private static ClientBis client = new ClientBis(model);

    public static void main(String[] args) {
        int delay = 5; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                client.play();
            }
        };
        SwingUtilities.invokeLater(()->{
            ControllerMenu c = new ControllerMenu(view,model);
        });
        model.getJoin().addActionListener(e -> setView("Join"));
        model.getBackJoin().addActionListener(e -> setView("Main"));
        model.getIpJoin().addActionListener(e -> {
            client.connect(model.getIpField().getText());
            client.start(view.getComponent());
            setView("Game");
            new Timer(delay,taskPerformer).start();
        });
        model.getQuit().addActionListener(e -> System.exit(0));
    }
    
    public static void setView(String s){
        frame.getContentPane().removeAll();
        switch (s){
            case "Main":
                frame.add(view.createMenuView());
                break;
            case "Join":
                frame.add(view.createJoinMenu());
                break;
            case "WaitC":
                frame.add(view.createWaitingClient());
                break;
            case "Game":
                frame.add(view.getComponent());
                view.getComponent().addKeyListener(new KeyboardEventHandler(client.getMap(), client.getConsumer()));
                view.getComponent().requestFocusInWindow();
                break;
            default : frame.add(view.createMenuView());
        }
        frame.validate();
        frame.repaint();
    }
}
