package ch.epfl.xblast.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.client.GameState.Player;
/**
 * A XBlast component
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
@SuppressWarnings("serial")
public final class XBlastComponent extends JComponent{
    private final static int XB_WIDTH = 960;
    private final static int XB_HEIGHT = 688;
    private final static int DISPLAY_LIVES_Y = 659;
    
    private GameState gameState = null;
    private PlayerID id = null;
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(XB_WIDTH, XB_HEIGHT);
    }
    
    @Override
    public void paintComponent(Graphics g0){
        Graphics2D g = (Graphics2D)g0;
        
        List<Image> imagesBoard = gameState.imagesBoard();
        List<Image> imagesExplosion = gameState.imagesExplosion();
        List<Player> players = new ArrayList<>(gameState.players());
        List<Image> imagesScore = gameState.imagesScore();
        List<Image> imagesTime = gameState.imagesTime();
        
        //Board, bombs, and explosions display
        int widthOfBlock = gameState.imagesBoard().get(0).getWidth(null);
        int heightOfBlock = gameState.imagesBoard().get(0).getHeight(null);
        
        int blockX = 0;
        int blockY = 0;

        for(int i = 0; i < gameState.imagesBoard().size(); ++i){
            blockX=(i%Cell.COLUMNS)*widthOfBlock;
            blockY=(i/Cell.COLUMNS)*heightOfBlock;
            
            g.drawImage(imagesBoard.get(i), blockX, blockY, null);
            g.drawImage(imagesExplosion.get(i), blockX, blockY, null);          
        }
        
        //Player display
        
        //we first create the two comparators, so that we can sort the player in the right way before displaying them
        Comparator<Player> yCoordinatesComparator = (p1, p2) -> Integer.compare(p1.position().y(), p2.position().y());
        Comparator<Player> playerIDComparator = (p1, p2) -> {
            int valueP1 = Math.floorMod(p1.id().ordinal()-this.id.ordinal()-1, ch.epfl.xblast.server.GameState.PLAYER_NUMBER);
            int valueP2 = Math.floorMod(p2.id().ordinal()-this.id.ordinal()-1, ch.epfl.xblast.server.GameState.PLAYER_NUMBER);
            return Integer.compare(valueP1, valueP2);
        };
        //We sort the players, using the comparators
        Collections.sort(players, yCoordinatesComparator.thenComparing(playerIDComparator));

        for (Player player : players) {
           g.drawImage(player.image(), 4*player.position().x()-24, 3*player.position().y()-52, null);
        }
        
        //Score display
        int widthOfScore = imagesScore.get(0).getWidth(null);
        for (int i=0; i< imagesScore.size(); ++i) {
            g.drawImage(imagesScore.get(i), i*widthOfScore, blockY+heightOfBlock, null);
        }
        
        //Display of number of lives
        Font font = new Font("Arial", Font.BOLD, 25);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(String.valueOf(gameState.players().get(0).lives()), 96, DISPLAY_LIVES_Y);
        g.drawString(String.valueOf(gameState.players().get(1).lives()), 240, DISPLAY_LIVES_Y);
        g.drawString(String.valueOf(gameState.players().get(2).lives()), 768, DISPLAY_LIVES_Y);
        g.drawString(String.valueOf(gameState.players().get(3).lives()), 912, DISPLAY_LIVES_Y);
        
        //Time display
        int widthOfTime = imagesTime.get(0).getWidth(null);
        for (int i=0; i< imagesTime.size(); ++i) {
            g.drawImage(imagesTime.get(i), i*widthOfTime, blockY+heightOfBlock+widthOfScore, null);
        }
    }
    
    /**
     * Set the game state of this XBlast component with the given game state and also the player ID indicating for which player the component is displayed 
     * 
     * @param gameState
     *      The game state
     *      
     * @param id
     *      The ID of the player for which the component is displayed
     */
    public void setGameState(GameState gameState, PlayerID id){
        this.gameState = gameState;
        this.id = id;
        repaint();
    }
    
}
