package ch.epfl.xblast.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

import ch.epfl.xblast.PlayerID;

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

        for(int i = 0; i < gameState.imagesBoard().size(); ++i){
            
        }
        
    }
    
    private void setGameState(GameState gameState, PlayerID id){
        this.gameState = gameState;
        this.id = id;
        repaint();
    }
    
}
