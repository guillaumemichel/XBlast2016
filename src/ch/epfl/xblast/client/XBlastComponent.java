package ch.epfl.xblast.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.client.GameState.Player;

public final class XBlastComponent extends JComponent{
    private GameState gameState = null;
    private PlayerID id = null;
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(960, 688);
    }
    
    @Override
    public void paintComponent(Graphics g0){
        Graphics2D g = (Graphics2D)g0;
        
        int widthOfBlock = gameState.imagesBoard().get(0).getWidth(null);
        int heightOfBlock = gameState.imagesBoard().get(0).getHeight(null);
        int blockX, blockY;

        for(int i = 0; i < gameState.imagesBoard().size(); ++i){
            blockX=(i%Cell.COLUMNS)*widthOfBlock;
            blockY=(i/Cell.COLUMNS)*heightOfBlock;
            
            g.drawImage(gameState.imagesBoard().get(i), blockX, blockY, null);
            g.drawImage(gameState.imagesExplosion().get(i), blockX, blockY, null);          
        }
        
        List<Player> players = new ArrayList<>(gameState.players());
        Comparator<Player> yCoordinatesComparator = (p1, p2) -> Integer.compare(p1.position().y(), p2.position().y());
        Comparator<Player> playerIDComparator;
        Collections.sort(players, yCoordinatesComparator);
        
        for (Player player : players) {
           g.drawImage(player.image(), 4*player.position().x()-24, 3*player.position().y()-52, null);
        }
        
        Font font = new Font("Arial", Font.BOLD, 25);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(String.valueOf(gameState.players().get(0).lives()), 96, 659);
        g.drawString(String.valueOf(gameState.players().get(1).lives()), 240, 659);
        g.drawString(String.valueOf(gameState.players().get(2).lives()), 768, 659);
        g.drawString(String.valueOf(gameState.players().get(3).lives()), 912, 659);
        
    }
    
    public void setGameState(GameState gameState, PlayerID id){
        this.gameState = gameState;
        this.id = id;
        repaint();
    }
    
}
