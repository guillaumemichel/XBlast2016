package ch.epfl.xblast.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.client.ClientBis;
import ch.epfl.xblast.client.KeyboardEventHandler;
import ch.epfl.xblast.client.PlaySound;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.Player;
import ch.epfl.xblast.server.ServerBis;
import ch.epfl.xblast.server.mapEditor.MapEditor;

public final class Main {
    private static ModelMenu model = new ModelMenu();
    private static ViewMenu view = new ViewMenu(model);
    private static JFrame frame = view.getFrame();
    private static MapEditor m;
    private static Timer game;
    private static Timer join;
    private static boolean bool;

    public static void main(String[] args) {
        int delay = 5; //milliseconds
        Runnable start = () -> {
            bool = true;
            Board b=Level.DEFAULT_LEVEL.gameState().board();
            List<Player> l = Level.DEFAULT_LEVEL.gameState().players();
            switch (model.mapSelected()){
                case 0 :
                    b=readFileToBoard(new File("sample_maps/sample_map_1.txt"));
                    break;
                case 1 :
                    b=readFileToBoard(new File("sample_maps/sample_map_2.txt"));
                    break;
                case 2 :
                    b=readFileToBoard(new File("sample_maps/sample_map_3.txt"));
                    break;
                case 3 :
                    b=m.grid().toGameState().board();
                    l=m.grid().createPlayers(
                            (int) model.getNPlayers().getValue()==1 ? 4 :(int) model.getNPlayers().getValue());
                    break;
            }
            if (model.mapSelected()!=3)
                l=Level.createPlayers((int) model.getNPlayers().getValue()==1 ?
                        4 :(int) model.getNPlayers().getValue());
            GameState g = new GameState(b,l);
            ServerBis.init((int) model.getTime().getValue());
            while (ServerBis.connect()!=(int)model.getNPlayers().getValue() && bool){}
            if (bool) ServerBis.game(g);
            else {
                ServerBis.closeChannel();
                join.stop();
                setView("Server");
            }
            };
        game = new Timer(delay, new ActionListener(){
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
        join = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ClientBis.connect()!=null){
                    ((Timer)e.getSource()).stop();
                    ClientBis.start(view.getComponent());
                    setView("Game");
                    game.start();
                }
            }  
        });
        
        SwingUtilities.invokeLater(()->{
            setView("Main");
            frame.setVisible(true);
            model.getJoin().addActionListener(e -> setView("Join"));
            model.getBackJoin().addActionListener(e -> setView("Main"));
            model.getIpJoin().addActionListener(e -> {
                setView("Wait");
                startClient();
            });
            model.getCreate().addActionListener(e -> setView("Server"));
            model.getQuit().addActionListener(e -> System.exit(0));
            model.getStartServer().addActionListener(e->{
                setView("WaitS");
                new Thread(start).start();
                startClient();
            });
            model.getBackConnect().addActionListener(e -> {
                join.stop();
                setView("Join");
            });
            model.getBackServer().addActionListener(e -> bool = false);
        });


    }
    
    private static void startClient(){
        ClientBis.initialize(model.getIpField().getText());
        join.start();
    }
    
    public static void mapEdit(){
        m = new MapEditor();
    }
    
    private static void setWin(byte b){
        frame.getContentPane().removeAll();
        frame.add(view.createWinners(b));
        model.getMenu().addActionListener(e -> setView("Main"));
        frame.validate();
        frame.repaint();
    }
    
    private static void setView(String s){
        frame.getContentPane().removeAll();
        switch (s){
            default : frame.add(view.createMenuView());
            case "Main":
                frame.add(view.createMenuView());
                break;
            case "Join":
                frame.add(view.createJoinMenu());
                break;
            case "Wait":
                frame.add(view.createWaitingClient(model.getIpField().getText()));
                break;
            case "WaitS" :
                frame.add(view.createWaitingServer());
                break;
            case "Game":
                frame.add(view.getComponent());
                view.getComponent().addKeyListener(new KeyboardEventHandler(ClientBis.getMap(), ClientBis.getConsumer()));
                view.getComponent().requestFocusInWindow();
                break;
            case "Server":
                frame.add(view.createCreateMenu());
                break;
        }
        frame.validate();
        frame.repaint();
    }
    
    private static Board readFileToBoard(File f){
        try(InputStream in = new BufferedInputStream(new FileInputStream(f))){
            List<Byte> l = new ArrayList<>();

            int b;
            while((b = in.read()) != -1){
                int numericValueOfInteger = Character.getNumericValue(b);

                l.add((byte)numericValueOfInteger);
            }
            List<Sq<Block>> board = l.stream().map(bytes -> Block.values()[bytes]).map(block -> Sq.constant(block)).collect(Collectors.toList());
            
            return new Board(board);
        }catch(FileNotFoundException e){
            
        }catch(IOException e){
            
        }
        System.out.println("Fail load");
        return Level.DEFAULT_LEVEL.gameState().board();
    }
}
