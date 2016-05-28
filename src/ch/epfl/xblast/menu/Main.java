package ch.epfl.xblast.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import ch.epfl.xblast.client.ClientBis;
import ch.epfl.xblast.client.KeyboardEventHandler;
import ch.epfl.xblast.client.PlaySound;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.ServerBis;

public final class Main {
    private static ModelMenu model = new ModelMenu();
    private static ViewMenu view = new ViewMenu(model);
    private static JFrame frame = view.getFrame();

    public static void main(String[] args) {
        int delay = 5; //milliseconds
        Runnable start = () -> {
            GameState g=Level.DEFAULT_LEVEL.gameState();

            if (model.getRB1().getButton().isSelected()) g=model.getRB1().gamestate();
            else if (model.getRB2().getButton().isSelected()) g=model.getRB2().gamestate();
            else if (model.getRB3().getButton().isSelected()) g=model.getRB3().gamestate();

            
            ServerBis.main(g, (int) model.getTime().getValue(), 1); };
        Timer timer = new Timer(delay, new ActionListener(){
            byte i;
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((i=ClientBis.play())!=0x10){
                    ((Timer)e.getSource()).stop();
                    PlaySound.stop();
                    setWin(i);
                }
            }  
        });
        
        SwingUtilities.invokeLater(()->{
            ControllerMenu c = new ControllerMenu(view,model);
            model.getJoin().addActionListener(e -> c.setView("Join"));
            model.getBackJoin().addActionListener(e -> c.setView("Main"));
            model.getIpJoin().addActionListener(e -> {
                ClientBis.connect(model.getIpField().getText());
                ClientBis.start(view.getComponent());
                c.setView("Game");
                timer.start();
            });
            model.getCreate().addActionListener(e -> c.setView("Server"));
            model.getQuit().addActionListener(e -> System.exit(0));
            model.getStartServer().addActionListener(e->{
                new Thread(start).start();
                ClientBis.connect(model.getIpField().getText());
                ClientBis.start(view.getComponent());
                c.setView("Game");
                timer.start();
            });
        });

    }
    
    public static void setWin(byte b){
        frame.getContentPane().removeAll();
        frame.add(view.createWinners(b));
        model.getMenu().addActionListener(e -> setView("Main"));
        frame.validate();
        frame.repaint();
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
                view.getComponent().addKeyListener(new KeyboardEventHandler(ClientBis.getMap(), ClientBis.getConsumer()));
                view.getComponent().requestFocusInWindow();
                break;
            default : frame.add(view.createMenuView());
        }
        frame.validate();
        frame.repaint();
    }
}
