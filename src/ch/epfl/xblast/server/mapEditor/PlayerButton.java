package ch.epfl.xblast.server.mapEditor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.client.ImageCollection;

@SuppressWarnings("serial")
public final class PlayerButton extends JButton{
    private final static int PLAYER_IMAGE_WIDTH = ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(0).getWidth(null);
    private final static int PLAYER_IMAGE_HEIGHT = ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(0).getHeight(null);
    
    private PlayerID player;
    private Color color;
    
    public PlayerButton(int id){
        setPlayer(id);
        this.setPreferredSize(new Dimension(PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT));
        this.setContentAreaFilled(false);
    }
    
    public PlayerID playerID(){
        return player;
    }
    
    public Color color(){
        return color;
    }
    
    /**
     * Sets the block of this block button and update the image of this block button
     * 
     * @param b
     *      The new value of the field "block"
     */
    public void setPlayer(int id){
        this.player = PlayerID.values()[id-1];
        this.setButtonImage(this.player);
    }
    
    private void setButtonImage(PlayerID p){
        switch(p){
            case PLAYER_1:
                this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(6)));
                this.color = Color.BLUE;
                this.setBorder(new LineBorder(color, 3));
                break;
            case PLAYER_2:
                this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(26)));
                this.color = Color.RED;
                this.setBorder(new LineBorder(color, 3));
                break;
            case PLAYER_3:
                this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(46)));
                this.color = Color.GREEN;
                this.setBorder(new LineBorder(color, 3));
                break;
            case PLAYER_4:
                this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(66)));
                this.color = Color.YELLOW;
                this.setBorder(new LineBorder(color, 3));
                break;
            default:
        }
    }
}
