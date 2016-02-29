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
        //A finir 
        return null;
    }
}
