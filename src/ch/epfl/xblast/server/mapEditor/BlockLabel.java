package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.Player;

@SuppressWarnings("serial")
public final class BlockLabel extends JLabel{
    private final static int BLOCK_IMAGE_WIDTH = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(0).getWidth(null);
    private final static int BLOCK_IMAGE_HEIGHT = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(0).getHeight(null);
    
    private Block block;
    private Player hostedPlayer = null;
    
    public BlockLabel(Block b){
        setBlock(b);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0){
                    setBlock(((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).blockChooser().currentBlock().block());
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                setBlock(((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).blockChooser().currentBlock().block());
            }
        });
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
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
        this.setLabelImage(b);
    }
    
    private void setLabelImage(Block b){
        this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(Level.DEFAULT_LEVEL.boardPainter().correspondingBlockImageOf(block))));
    }
    
}
