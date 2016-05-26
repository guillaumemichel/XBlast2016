package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

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
    
    public BlockButton(Block b, boolean chooser){
        setBlock(b);
        if(!chooser){
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0){
                        setBlock(((MapEditor)SwingUtilities.windowForComponent(BlockButton.this)).blockChooser().currentBlock().block());
                    }
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    setBlock(((MapEditor)SwingUtilities.windowForComponent(BlockButton.this)).blockChooser().currentBlock().block());
                }
            });
            this.setBorderPainted(false);
        }
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
        this.setFocusPainted(false);
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
     * Sets the block of this block button and update the image of this block button
     * 
     * @param b
     *      The new value of the field "block"
     */
    public void setBlock(Block b){
        block = b;
        this.setButtonImage(b);
    }
    
    private void setButtonImage(Block b){
        this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(Level.DEFAULT_LEVEL.boardPainter().correspondingBlockImageOf(block))));
    }
}
