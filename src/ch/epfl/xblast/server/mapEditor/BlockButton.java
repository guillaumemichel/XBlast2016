package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Level;

/**
 * A custom JButton representing a block in the form of a button
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
@SuppressWarnings("serial")
public final class BlockButton extends JButton{    
    private final static int BLOCK_IMAGE_WIDTH = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(0).getWidth(null);
    private final static int BLOCK_IMAGE_HEIGHT = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(0).getHeight(null);
    
    private Block block;
    
    /**
     * Constructs a block button with the given block
     * 
     * @param b
     *      The block represented by the block button
     */
    public BlockButton(Block b){
        setBlock(b);
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
        this.setContentAreaFilled(false);
    }
    
    /**
     * Returns the block of this block button
     * 
     * @return
     *      The block of this block button
     */
    public Block block(){
        return block;
    }
    
    /**
     * Sets the block of this block button and update its image
     * 
     * @param b
     *      The new value of the field "block"
     */
    public void setBlock(Block b){
        this.block = b;
        this.setButtonImage(b);
    }
    
    private void setButtonImage(Block b){
        this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(Level.DEFAULT_LEVEL.boardPainter().correspondingBlockImageOf(block))));
    }
}
