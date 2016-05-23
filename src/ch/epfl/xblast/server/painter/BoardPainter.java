package ch.epfl.xblast.server.painter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;

/**
 * A board painter
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class BoardPainter {
    private final Map<Block, BlockImage> palette;
    private final BlockImage imageForFreeBlock;
    
    /**
     * Constructs a board painter with the given parameters
     * 
     * @param palette
     *      The palette used for this board painter (in the form of a map)
     *      
     * @param imageForFreeBlock
     *      The image to use for the shadowed free blocks
     */
    public BoardPainter(Map<Block, BlockImage> palette, BlockImage imageForFreeBlock) throws NullPointerException{
        this.palette = Collections.unmodifiableMap(new HashMap<>(Objects.requireNonNull(palette)));
        this.imageForFreeBlock = Objects.requireNonNull(imageForFreeBlock);
    }
    
    /**
     * Returns the byte that identifies the image to use, for the given cell and board
     * 
     * @param board
     *      The board that is used
     *      
     * @param cell
     *      The cell where we want to know which image of block to use
     *      
     * @return
     *      The byte that identifies the image to use, for the given cell
     */
    public byte byteForCell(Board board, Cell cell){
        Block block = board.blockAt(cell);
        
        if(block==Block.FREE && board.blockAt(cell.neighbor(Direction.W)).castsShadow())
            return (byte) imageForFreeBlock.ordinal();
        
        return (byte) palette.get(block).ordinal();
    }
    
    /**
     * Returns the corresponding block image's ordinal of a given block
     * 
     * @param b
     *      The block 
     *      
     * @return
     *      The corresponding block image's ordinal of the given block
     * 
     */
    public byte correspondingBlockImageOf(Block b){
        return (byte) palette.get(b).ordinal();
    }
}
