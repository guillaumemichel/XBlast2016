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
import ch.epfl.xblast.client.Client;
import ch.epfl.xblast.client.KeyboardEventHandler;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.mapEditor.MapEditor;

/**
 * A model, model of the MVC pattern
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vanderbroucque (258715)
 *
 */
public final class Model {
    private final View view;
    private JFrame frame;
    private MapEditor m;
    
    /**
     * Construct a model from a view
     * 
     * @param view
     *      The view associated with the model
     */
    public Model(View view){
        this.view=view;
        this.frame=view.getFrame();
    }
    
    /**
     * Set the win pannel (at the end of the game)
     * 
     * @param b
     *      The encoded byte containing the winners
     */
    public void setWin(byte b){
        frame.getContentPane().removeAll();
        frame.add(view.createWinners(b));
        view.getMenu().addActionListener(e -> setView("Main"));
        frame.validate();
        frame.repaint();
    }
    
    /**
     * Set the display of a different menu or game according to its title
     * 
     * @param s
     *      The title of the screen that will be displayed
     */
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
                view.getComponent().addKeyListener(new KeyboardEventHandler(Client.getMap(), Client.getConsumer()));
                view.getComponent().requestFocusInWindow();
                break;
            case "Server":
                frame.add(view.createCreateMenu());
                break;
        }
        frame.validate();
        frame.repaint();
    }
    
    /**
     * Read a gamestate from a .txt file
     * 
     * @param f
     *      The name of the selected file
     *      
     * @param playerNumber
     *      The number of players alive for this game
     *      
     * @return
     *      A gamestate according to the board stocked in the .txt and the list of players
     */
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
        frame.setEnabled(false);
        m = new MapEditor();
    }
    public MapEditor getMap(){
        return m;
    }
}
