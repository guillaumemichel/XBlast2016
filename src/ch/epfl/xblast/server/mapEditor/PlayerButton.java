package ch.epfl.xblast.server.mapEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.client.ImageCollection;

/**
 * A custom JButton representing a player in the form of a button
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
@SuppressWarnings("serial")
public final class PlayerButton extends JButton{
    private final static int PLAYER_IMAGE_WIDTH = ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(0).getWidth(null);
    private final static int PLAYER_IMAGE_HEIGHT = ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(0).getHeight(null);
    
    private final static Image IMAGE_PLAYER_1 = ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(6);
    private final static Image IMAGE_PLAYER_2 = ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(26);
    private final static Image IMAGE_PLAYER_3 = ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(46);
    private final static Image IMAGE_PLAYER_4 = ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(66);

    private final static int LINEBORDER_THICKNESS = 3;
    
    private PlayerID player;
    private Color color;
    
    /**
     * Constructs a player button with the given id
     * 
     * @param id
     *      The id of the player
     */
    public PlayerButton(int id){
        setPlayer(id);
        this.setPreferredSize(new Dimension(PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT));
        this.setContentAreaFilled(false);
    }
    
    /**
     * Returns the id of the player of this player button
     * 
     * @return
     *      The id of the player of this player button
     */
    public PlayerID playerID(){
        return player;
    }
    
    /**
     * Returns the color associated to this player button
     * 
     * @return
     *      The color associated to this player button
     */
    public Color color(){
        return color;
    }
    
    /**
     * Sets the player of this player button and update its image
     * 
     * @param b
     *      The new value of the field "player"
     */
    public void setPlayer(int id){
        this.player = PlayerID.values()[id-1];
        this.setButtonImage(this.player);
    }
    
    private void setButtonImage(PlayerID p){
        switch(p){
            case PLAYER_1:
                this.setIcon(new ImageIcon(IMAGE_PLAYER_1));
                this.color = Color.BLUE;
                this.setBorder(new LineBorder(color, LINEBORDER_THICKNESS));
                break;
            case PLAYER_2:
                this.setIcon(new ImageIcon(IMAGE_PLAYER_2));
                this.color = Color.RED;
                this.setBorder(new LineBorder(color, LINEBORDER_THICKNESS));
                break;
            case PLAYER_3:
                this.setIcon(new ImageIcon(IMAGE_PLAYER_3));
                this.color = Color.GREEN;
                this.setBorder(new LineBorder(color, LINEBORDER_THICKNESS));
                break;
            case PLAYER_4:
                this.setIcon(new ImageIcon(IMAGE_PLAYER_4));
                this.color = Color.YELLOW;
                this.setBorder(new LineBorder(color, LINEBORDER_THICKNESS));
                break;
            default:
        }
    }
}
