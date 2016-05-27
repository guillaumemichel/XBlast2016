package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Level;

@SuppressWarnings("serial")
public final class BlockLabel extends JLabel{
    private final static int BLOCK_IMAGE_WIDTH = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(0).getWidth(null);
    private final static int BLOCK_IMAGE_HEIGHT = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(0).getHeight(null);
    
    private Block block;
    private PlayerID hostedPlayer = null;
    
    /**
     * Constructs a block label with the given block
     * 
     * @param b
     *      The block
     */
    public BlockLabel(Block b){
        setBlock(b);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                BlockChooser parentBlockChooser =((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).blockChooser();
                PlayerChooser parentPlayerChooser =((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).playerChooser();
                
                if(((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) && !parentPlayerChooser.playerSelection().isSelected() && (parentBlockChooser.currentBlock().block().canHostPlayer()||BlockLabel.this.hostedPlayer==null)){
                    setBlock(parentBlockChooser.currentBlock().block());
                }else if(((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) && parentPlayerChooser.playerSelection().isSelected() && BlockLabel.this.block().canHostPlayer()){
                    GridOfBlocks parentGrid = ((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).grid();
                    for(BlockLabel b: parentGrid.blocks())
                        if(b.hostedPlayer == parentPlayerChooser.currentPlayer().playerID()){
                            b.hostedPlayer=null;
                            b.setBorder(BorderFactory.createEmptyBorder());
                        }
                    BlockLabel.this.setHostedPlayer(parentPlayerChooser.currentPlayer().playerID());
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                BlockChooser parentBlockChooser =((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).blockChooser();
                PlayerChooser parentPlayerChooser =((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).playerChooser();
                
                if(!parentPlayerChooser.playerSelection().isSelected() && (parentBlockChooser.currentBlock().block().canHostPlayer()||BlockLabel.this.hostedPlayer==null)){
                    setBlock(parentBlockChooser.currentBlock().block());
                }else if(parentPlayerChooser.playerSelection().isSelected() && BlockLabel.this.block().canHostPlayer()){
                    GridOfBlocks parentGrid = ((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).grid();
                    for(BlockLabel b: parentGrid.blocks())
                        if(b.hostedPlayer == parentPlayerChooser.currentPlayer().playerID()){
                            b.hostedPlayer=null;
                            b.setBorder(BorderFactory.createEmptyBorder());
                        }
                    BlockLabel.this.setHostedPlayer(parentPlayerChooser.currentPlayer().playerID());
                }
                    
            }
        });
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
    }
    
    /**
     * Returns the block of this block label
     * 
     * @return
     *      The block of this block label
     */
    public Block block(){
        return block;
    }
    
    /**
     * Returns the hosted player of this block label if there is one
     * 
     * @return
     *      The hosted player of this block label
     */
    public PlayerID hostedPlayer(){
        return hostedPlayer;
    }
    
    public void setHostedPlayer(PlayerID p){
        hostedPlayer = p;
        this.setBorder(new LineBorder(new PlayerButton(p.ordinal()+1).color(), 3));
    }
    /**
     * Sets the block of this block label and update its image
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
