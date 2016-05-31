package ch.epfl.xblast.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Timer;

import ch.epfl.xblast.client.Client;
import ch.epfl.xblast.client.PlaySound;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.Server;

/**
 * A controller, controller of the MVC pattern
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class Controller {
    private Timer game;
    private Timer join;
    private boolean bool;
    
    /**
     * Contructs a Controller from a view and a model, set all actions listeners on the view
     * 
     * @param view
     *      The view associated to the controller
     *      
     * @param model
     *      The model associated to the controller
     */
    public Controller(View view,Model model){
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

            Server.init((int) view.getTime().getValue());
            while (Server.connect()!=(int)view.getNPlayers().getValue() && bool){}
            if (bool) Server.game(g);
            else {
                Server.closeChannel();
                join.stop();
                model.setView("Server");
            }
        };
        game = new Timer(delay, new ActionListener(){
            byte i;
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((i=Client.play())!=0x10){
                    ((Timer)e.getSource()).stop();
                    PlaySound.stop();
                    model.setWin(i);
                }
            }  
        });
        join = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Client.connect()!=null){
                    ((Timer)e.getSource()).stop();
                    Client.start(view.getComponent());
                    model.setView("Game");
                    game.start();
                }
            }  
        });

        view.getBackJoin().addActionListener(e -> model.setView("Main"));
        view.getIpJoin().addActionListener(e -> {
            model.setView("Wait");
            startClient(view.getIpField().getText());
        });
        view.getStartServer().addActionListener(e->{
            model.setView("WaitS");
            new Thread(start).start();
            startClient("localhost");
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
    
    private void startClient(String s){
        Client.initialize(s);
        join.start();
    }
}
