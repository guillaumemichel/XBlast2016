package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

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
    private Set<PlayerID> hostedPlayers = new HashSet<>();
    
    /**
     * Constructs a block label with the given block
     * 
     * @param b
     *      The block
     */
    public BlockLabel(Block b){
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
        setBlock(b);
        this.addMouseListener(new MouseAdapter() {
            
            
            @Override
            public void mouseEntered(MouseEvent e) { 
                BlockChooser parentBlockChooser =((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).blockChooser();
                PlayerChooser parentPlayerChooser =((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).playerChooser();
                boolean conditionForSettingBlock = !parentPlayerChooser.playerSelection().isSelected() && 
                        (parentBlockChooser.currentBlock().block().canHostPlayer()||BlockLabel.this.hostedPlayers.isEmpty());
                boolean conditionForSettingPlayer = parentPlayerChooser.playerSelection().isSelected() && BlockLabel.this.block().canHostPlayer();
                
                if(((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) && conditionForSettingBlock){
                    setBlock(parentBlockChooser.currentBlock().block());
                }else if(((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) && conditionForSettingPlayer){
                    addPlayerListener(parentPlayerChooser);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) { 
                BlockChooser parentBlockChooser =((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).blockChooser();
                PlayerChooser parentPlayerChooser =((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).playerChooser();
                boolean conditionForSettingBlock = !parentPlayerChooser.playerSelection().isSelected() && 
                        (parentBlockChooser.currentBlock().block().canHostPlayer()||BlockLabel.this.hostedPlayers.isEmpty());
                boolean conditionForSettingPlayer = parentPlayerChooser.playerSelection().isSelected() && BlockLabel.this.block().canHostPlayer();
                
                if(conditionForSettingBlock){
                    setBlock(parentBlockChooser.currentBlock().block());
                }else if(conditionForSettingPlayer){
                   addPlayerListener(parentPlayerChooser);
                }
                    
            }
            
            private void addPlayerListener(PlayerChooser parentPlayerChooser){
                GridOfBlocks parentGrid = ((MapEditor)SwingUtilities.windowForComponent(BlockLabel.this)).grid();
                for(BlockLabel b: parentGrid.blocks()){//for every blockLabel
                    //before putting a new playerId in the blockLabel, we have to remove its position in the previous blockLabel
                    if(b.hostedPlayers.contains(parentPlayerChooser.currentPlayer().playerID())){
                        b.hostedPlayers.remove(parentPlayerChooser.currentPlayer().playerID());
                       
                        //then if there is no more player on the current blockLabel, we remove the border
                        if(b.hostedPlayers.isEmpty())
                            b.setBorder(BorderFactory.createEmptyBorder());
                        //else we set the border to the color of a player that is hosted in the current blockLabel
                        else 
                            b.addHostedPlayer(b.hostedPlayers.iterator().next());
                    }
                }
                //Finally we add the player to this blockLabel
                BlockLabel.this.addHostedPlayer(parentPlayerChooser.currentPlayer().playerID());
            }
        });
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
     * Returns the hosted players of this block label if there is one or more
     * 
     * @return
     *      The hosted players of this block label
     */
    public Set<PlayerID> hostedPlayers(){
        return hostedPlayers;
    }
    
    /**
     * Add a player to the hosted players set
     * 
     * @param p
     *      The player to add
     */
    public void addHostedPlayer(PlayerID p){
        hostedPlayers.add(p);
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
