package ch.epfl.xblast.server;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;

/**
 * A board
 * 
 * @author Guillaume Michel
 * @author Adrien Vandenbroucque
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
        if(blocks.size()!=Cell.COUNT){
            throw new IllegalArgumentException();
        }else{
            this.blocks=blocks; //Attention voir références!!!
        }
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
        if(matrix.size()!=rows){
            throw new IllegalArgumentException();
        }
        for (List<Block> list : matrix) {
            if(list.size()!=columns){
                throw new IllegalArgumentException();
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
        checkBlockMatrix(rows, 13, 15);
        List<Sq<Block>> sequence= new ArrayList<>();
        
        for (int i = 0; i < rows.size(); ++i) {
            for (int j = 0; j < rows.get(i).size(); ++j) {
                sequence.set(i+rows.size()*j, Sq.constant(rows.get(i).get(j)));
            }
        }
        
        return new Board(sequence);
    }
    
    /**
     * Constructand returns a walled board with the given inner blocks
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
        checkBlockMatrix(innerBlocks, 11, 13);
        List<Sq<Block>> sequence= new ArrayList<>();
        
        for (int i = 0; i < 13; ++i) {
            for (int j = 0; j < 15; ++j) {
                if(i==0 || i==12 || j==0 || j==14){
                    sequence.set(i+13*j, Sq.constant(Block.INDESTRUCTIBLE_WALL));
                }else{
                    sequence.set(i+13*j, Sq.constant(innerBlocks.get(i).get(j)));
                }
            }
        }
        return new Board(sequence);
    }
    
    public Sq<Block> blocksAt(Cell c){
        //A faire
        return null;
    }
}
