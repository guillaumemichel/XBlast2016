package ch.epfl.xblast.server;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Lists;

/**
 * A board
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class Board {
    private final List<Sq<Block>> blocks;
    
    /**
     * Constructs a board from a given sequence of all the blocks of the board (in the form of a list)
     * 
     * @param blocks
     *      The list that contains the sequence of all the blocks of the board
     *      
     * @throws IllegalArgumentException
     *      If not all blocks are given
     */
    public Board(List<Sq<Block>> blocks) throws IllegalArgumentException{
        if(blocks.size()!=Cell.COUNT)
            throw new IllegalArgumentException();
        
        this.blocks=new ArrayList<>(blocks);
    }
    
    /**
     * Check if the given matrix (in the form of a list of a list) has the desired dimensions (rows*columns)
     * 
     * @param matrix
     *      The matrix we want to check the dimensions
     *      
     * @param rows
     *      The number of rows that we expect
     *      
     * @param columns
     *      The number of columns that we expect
     *      
     * @throws IllegalArgumentException
     *      If the dimensions are not as desired 
     */
    private static void checkBlockMatrix(List<List<Block>> matrix, int rows, int columns) throws IllegalArgumentException{
        if (matrix==null||matrix.isEmpty()){
            throw new IllegalArgumentException();
        }
        if(matrix.size()!=rows){
            throw new IllegalArgumentException();
        }
        for (List<Block> list : matrix) {
            if(list.size()!=columns){
                throw new IllegalArgumentException("Dimensions are incorrect !");
            }  
        }
    }
    
    /**
     * Constructs and returns a constant board with the given matrix of blocks
     * 
     * @param rows
     *      The matrix from which we create the board
     * @return
     *      A constant board, created from the matrix 
     *      
     * @throws IllegalArgumentException
     *      If the matrix has not the dimensions 13*15
     */
    public static Board ofRows(List<List<Block>> rows) throws IllegalArgumentException{
        checkBlockMatrix(rows, Cell.ROWS, Cell.COLUMNS);
        
        List<Sq<Block>> sequence= new ArrayList<Sq<Block>>();
        
        for (int i = 0; i < rows.size(); ++i) {
            for (int j = 0; j < rows.get(i).size(); ++j) {
                sequence.add(Sq.constant(rows.get(i).get(j)));
            }
        }
        
        return new Board(sequence);
    }
    
    /**
     * Constructs and returns a walled board with the given inner blocks
     * 
     * @param innerBlocks
     *      The matrix that contains the inner blocks
     *      
     * @return
     *      A walled board, with the given inner blocks
     *      
     * @throws IllegalArgumentException
     *      If the matrix of inner blocks has not the dimensions 11*13
     */
    public static Board ofInnerBlocksWalled(List<List<Block>> innerBlocks) throws IllegalArgumentException{
        checkBlockMatrix(innerBlocks, Cell.ROWS-2, Cell.COLUMNS-2);
        
        List<Sq<Block>> sequence= new ArrayList<Sq<Block>>();
        
        sequence.addAll(Collections.nCopies(Cell.COLUMNS, Sq.constant(Block.INDESTRUCTIBLE_WALL)));
        for(int i=0;i<innerBlocks.size();++i){
            sequence.add(Sq.constant(Block.INDESTRUCTIBLE_WALL));
            for(int j=0;j<innerBlocks.get(i).size();++j){
                sequence.add(Sq.constant(innerBlocks.get(i).get(j)));
            }
            sequence.add(Sq.constant(Block.INDESTRUCTIBLE_WALL));
        }
        sequence.addAll(Collections.nCopies(Cell.COLUMNS, Sq.constant(Block.INDESTRUCTIBLE_WALL)));

        return new Board(sequence);
    }
    
    /**
     * Constructs and returns a symmetrical walled board with the given blocks of the north-west quadrant
     * 
     * @param innerBlocks
     *      The matrix that contains blocks of the north-west quadrant
     *      
     * @return
     *      A symmetrical walled board, with the given blocks of the north-west quadrant
     *      
     * @throws IllegalArgumentException
     *      If the given matrix of blocks has not the dimensions 6*7
     */
    public static Board ofQuadrantNWBlocksWalled(List<List<Block>> quadrantNWBlocks) throws IllegalArgumentException{
        checkBlockMatrix(quadrantNWBlocks, (Cell.ROWS-1)/2, (Cell.COLUMNS-1)/2);
        
        List<List<Block>> innerBoard=new ArrayList<List<Block>>();
        
        for (int i = 0; i < quadrantNWBlocks.size(); ++i) {
            quadrantNWBlocks.set(i, Lists.mirrored(quadrantNWBlocks.get(i)));
            innerBoard.add(quadrantNWBlocks.get(i));
        }
        innerBoard = Lists.mirrored(quadrantNWBlocks);
        
        return ofInnerBlocksWalled(innerBoard);
        
    }
    
    /**
     * Returns the sequence of blocks for the given cell
     * 
     * @param c
     *      The cell from which we retrieve the sequence of blocks
     *      
     * @return
     *      The sequence of blocks for the given cell
     */
    public Sq<Block> blocksAt(Cell c){
        return blocks.get(c.rowMajorIndex());
    }
    
    /**
     * Returns the block for the given cell (the head of the sequence of blocks)
     * 
     * @param c
     *      The cell from which we retrieve the block
     *      
     * @return
     *      The block for the given cell
     */
    public Block blockAt(Cell c){
        return blocksAt(c).head();
    }
}
