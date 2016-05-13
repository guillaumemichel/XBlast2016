package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A cell
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class Cell {
    public final static int COLUMNS=15;
    public final static int ROWS=13;
    public final static int COUNT=COLUMNS*ROWS;
    
    public final static List<Cell> ROW_MAJOR_ORDER=Collections.unmodifiableList(rowMajorOrder());
    public final static List<Cell> SPIRAL_ORDER=Collections.unmodifiableList(spiralOrder());
    
    private final int x, y;
    
    /**
     * Constructs a cell with coordinates x and y
     * 
     * @param x
     *      The x coordinate of the cell
     * @param y
     *      The y coordinate of the cell
     */
    public Cell(int x, int y){
        this.x=Math.floorMod(x, COLUMNS);
        this.y=Math.floorMod(y, ROWS);
    }
    
    /**
     * Returns the x coordinate of this cell
     * 
     * @return
     *      The x coordinate of this cell
     */
    public int x(){
        return x;
    }
    
    /**
     * Returns the y coordinate of this cell
     * 
     * @return
     *      The y coordinate of this cell
     */
    public int y(){
        return y;
    }
    
    /**
     * Create and returns an array with all the cells in order by lines from right to left, top to bottom.
     * 
     * @return
     * 		An array with all the cells in Row Major Order
     */ 
    private static ArrayList<Cell> rowMajorOrder(){
        ArrayList<Cell> rowMajorOrder= new ArrayList<Cell>();
        for(int i=0;i<COUNT;++i){
            rowMajorOrder.add(new Cell(i%COLUMNS, i/COLUMNS));
        }
        return rowMajorOrder;
    }
    /**
     * Create and returns an array with all the cells in a clockwise spiral order beginning on corner top-left.
     * 
     * @return
     * 		An array with all the cells in a Spiral Order
     */
    private static ArrayList<Cell> spiralOrder(){//from the given algorithm
        ArrayList<Integer> ix = new ArrayList<Integer>();
        ArrayList<Integer> iy = new ArrayList<Integer>();
        for (int i=0;i<COLUMNS;++i){
            ix.add(i);
        }
        for (int i=0;i<ROWS;++i){
            iy.add(i);
        }
        boolean horizontal = true;
        ArrayList<Cell> spiral = new ArrayList<Cell>();
        ArrayList<Integer> i1;
        ArrayList<Integer> i2;
        int c1, c2;
        
        while(!ix.isEmpty()&&!iy.isEmpty()){
            if(horizontal){
                i1=ix;
                i2=iy;
            }else{
                i1=iy;
                i2=ix;
            }
            c2 = i2.get(0);
            i2.remove(0);
            for(int i=0;i<i1.size();++i){
            	c1 = i1.get(i);
            	spiral.add( horizontal ? new Cell(c1,c2):new Cell(c2,c1));
            	//adding new Cell in the array according on the boolean horizontal
            }
            Collections.reverse(i1);
            horizontal = !horizontal;
        }
        return spiral;
    }
    
    /**
     * Returns the index of this cell in the row major order
     * @return
     *      The index of this cell in the row major order
     */
    public int rowMajorIndex(){
        return COLUMNS*y+x;
    }
    
    /**
     * Returns the adjacent cell of this cell, according to the direction that is given
     * @param d
     *      The direction in which we look for the adjacent cell
     *      
     * @return
     *      The adjacent cell according to the direction that is given
     */
    public Cell neighbor(Direction dir){
        switch(dir){
            case E:
                    return new Cell(x+1, y);
            case W:
                    return new Cell(x-1, y);
            case N:
                    return new Cell(x, y-1);
            case S:
                    return new Cell(x, y+1);
            default:
                return null; 
        }
    }
    
    @Override
    public boolean equals(Object that){
        if(that==null){
            return false;
        }else if(that.getClass() != this.getClass()){
            return false;
        }else{
            return x==((Cell)that).x() && y==((Cell)that).y();
        }
    }
    
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
    
    @Override
    public int hashCode(){
        return rowMajorIndex();
    }

}