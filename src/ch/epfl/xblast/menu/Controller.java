package ch.epfl.xblast.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Timer;

import ch.epfl.xblast.client.ClientBis;
import ch.epfl.xblast.client.PlaySound;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.ServerBis;

public final class Controller {
    private final View view;
    private Timer game;
    private Timer join;
    private boolean bool;
    
    public Controller(View view,Model model){
        this.view=view;
        view.getJoin().addActionListener(e -> model.setView("Join"));
        view.getCreate().addActionListener(e -> model.setView("Server"));
        view.getQuit().addActionListener(e -> System.exit(0));
        model.setView("Main");
        view.getFrame().setVisible(true);
        
        int delay = 5; //milliseconds
        
        Runnable start = () -> {
            bool = true;
            GameState g;
            int playerNumber = (int) view.getNPlayers().getValue()==1 ? 4 :(int) view.getNPlayers().getValue();
            switch (view.mapSelected()){
                case 0 :
                    g = model.readFileToGameState(new File("sample_maps/sample_map_1.txt"), playerNumber);
                    break;
                case 1 :
                    g = model.readFileToGameState(new File("sample_maps/sample_map_2.txt"), playerNumber);
                    break;
                case 2 :
                    g = model.readFileToGameState(new File("sample_maps/sample_map_3.txt"), playerNumber);
                    break;
                case 3 :
                    g = model.getMap().grid().toGameState(playerNumber);
                    break;
                default:
                    g = Level.DEFAULT_LEVEL.gameState();
            }

            ServerBis.init((int) view.getTime().getValue());
            while (ServerBis.connect()!=(int)view.getNPlayers().getValue() && bool){}
            if (bool) ServerBis.game(g);
            else {
                ServerBis.closeChannel();
                join.stop();
                model.setView("Server");
            }
        };
        game = new Timer(delay, new ActionListener(){
            byte i;
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((i=ClientBis.play())!=0x10){
                    ((Timer)e.getSource()).stop();
                    PlaySound.stop();
                    model.setWin(i);
                }
            }  
        });
        join = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ClientBis.connect()!=null){
                    ((Timer)e.getSource()).stop();
                    ClientBis.start(view.getComponent());
                    model.setView("Game");
                    game.start();
                }
            }  
        });

        view.getBackJoin().addActionListener(e -> model.setView("Main"));
        view.getIpJoin().addActionListener(e -> {
            model.setView("Wait");
            startClient();
        });
        view.getStartServer().addActionListener(e->{
            model.setView("WaitS");
            new Thread(start).start();
            startClient();
        });
        view.getBackConnect().addActionListener(e -> {
            join.stop();
            model.setView("Join");
        });
        view.getBackServer().addActionListener(e -> bool = false);
        view.getB4().addActionListener(e -> model.mapEdit());
        view.getI4().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                model.mapEdit();
            }
        });
    }
    
    private void startClient(){
        ClientBis.initialize(view.getIpField().getText());
        join.start();
    }
}
