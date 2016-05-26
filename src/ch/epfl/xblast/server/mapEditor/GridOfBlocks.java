package ch.epfl.xblast.server.mapEditor;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.Player;

/**
 * A custom JPanel representing a grid of blocks
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
@SuppressWarnings("serial")
public final class GridOfBlocks extends JPanel{
    private List<BlockButton> blocks = new ArrayList<>();
    private List<Player> players = Level.DEFAULT_LEVEL.gameState().players();

    /**
     * Constructs a grid of blocks
     */
    public GridOfBlocks(){
        this.setLayout(new GridLayout(Cell.ROWS, Cell.COLUMNS));
        
        for (int i = 0; i < Cell.COUNT; i++) {
            blocks.add(new BlockButton(Block.FREE, false));
            this.add(blocks.get(blocks.size()-1));
        }
    }
    
    /**
     * Returns a list of bytes containing the value (ordinal) of each block of the grid in row major order
     * 
     * @return
     *      A list of bytes containing the value (ordinal) of each block of the grid in row major order
     */
    public List<Byte> toListOfBytes(){
        return blocks.stream().map(BlockButton::block).map(b -> (byte)b.ordinal()).collect(Collectors.toList());
    }
    
    /**
     * Returns the corresponding board of this grid of blocks
     * @return
     */
    public Board toBoard(){
        List<Sq<Block>> blocks = this.blocks.stream().map(BlockButton::block).map(Sq::constant).collect(Collectors.toList());;
        return new Board(blocks);
    }
    
    /**
     * Given a list of bytes that represents a grid of blocks, it sets all the blocks of this grid to the corresponding value in byte
     * 
     * @param l
     *      The list of bytes from which we sets the new blocks of this grid of blocks
     */
    public void loadGridfromListOfBytes(List<Byte> l){
        List<Block> blocks = l.stream().map(b -> Block.values()[b]).collect(Collectors.toList());
       
        Iterator<BlockButton> it = this.blocks.iterator();
        Iterator<Block> it2 = blocks.iterator();
        while(it.hasNext() && it2.hasNext()){
            BlockButton button = it.next();
            Block b = it2.next();
            button.setBlock(b);
        }      
    }
}
