package ch.epfl.xblast.server;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;

public final class Board {
    private final List<Sq<Block>> blocks;
    
    public Board(List<Sq<Block>> blocks) throws IllegalArgumentException{
        if(blocks.size()!=Cell.COUNT){
            throw new IllegalArgumentException();
        }else{
            this.blocks=blocks; //Attention voir références!!!
        }
    }
    
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
    
    public static Board ofInnerBlocksWalled(List<List<Block>> innerBlocks){
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
