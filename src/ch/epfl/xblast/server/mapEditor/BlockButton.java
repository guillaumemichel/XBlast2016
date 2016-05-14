package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Level;

@SuppressWarnings("serial")
public final class BlockButton extends JButton{    
    private final static int BLOCK_IMAGE_WIDTH = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(2).getWidth(null);
    private final static int BLOCK_IMAGE_HEIGHT = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(2).getHeight(null);
    public static Block currentBlock = Block.FREE;
    
    private Block block;
    
    public BlockButton(){
        block=Block.FREE;
        this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(0)));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0)
                    setBlock(currentBlock);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                setBlock(currentBlock);
            }
        });
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
    }
    
    public BlockButton(Block b){
        block = b;
        this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(Level.DEFAULT_LEVEL.boardPainter().correspondingBlockImageOf(block))));
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
    }
    
    public void setBlock(Block b){
        block = b;
        this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(Level.DEFAULT_LEVEL.boardPainter().correspondingBlockImageOf(block))));
    }
    
    public Block block(){
        return block;
    }
}
