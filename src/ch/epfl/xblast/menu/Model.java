package ch.epfl.xblast.menu;

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

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.client.ClientBis;
import ch.epfl.xblast.client.KeyboardEventHandler;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.mapEditor.MapEditor;

public final class Model {
    private final View view;
    private JFrame frame;
    private MapEditor m;
    
    public Model(View view){
        this.view=view;
        this.frame=view.getFrame();
    }
    
    public void setWin(byte b){
        frame.getContentPane().removeAll();
        frame.add(view.createWinners(b));
        view.getMenu().addActionListener(e -> setView("Main"));
        frame.validate();
        frame.repaint();
    }
    
    public void setView(String s){
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
                frame.add(view.createWaitingClient(view.getIpField().getText()));
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
    
    public GameState readFileToGameState(File f, int playerNumber){
        try(InputStream in = new BufferedInputStream(new FileInputStream(f))){
            List<Byte> l = new ArrayList<>();

            int b;
            while((b = in.read()) != -1){
                int numericValueOfInteger = Character.getNumericValue(b);

                l.add((byte)numericValueOfInteger);
            }
            List<Sq<Block>> board = l.stream().map(bytes -> Block.values()[bytes]).map(block -> Sq.constant(block)).collect(Collectors.toList());
            
            return new GameState(new Board(board), Level.createPlayers(playerNumber));
        }catch(FileNotFoundException e){
            
        }catch(IOException e){
            
        }
        System.out.println("Fail load");
        return Level.DEFAULT_LEVEL.gameState();
    }
    
    public void mapEdit(){
        m = new MapEditor();
    }
    public MapEditor getMap(){
        return m;
    }
}
