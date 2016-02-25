package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Cell {
    public final static int COLUMNS=15;
    public final static int ROWS=13;
    public final static int COUNT=COLUMNS*ROWS;
    
    public static final List<Cell> ROW_MAJOR_ORDER = Collections.unmodifiableList(rowMajorOrder());
    public static final List<Cell> SPIRAL_ORDER = Collections.unmodifiableList(spiralOrder());
    
    private final int x,y;
    
    public Cell(int x,int y){
        
    }
    
    public int x(){
        return x;
    }
    
    public int y(){
        return y;
    }
    
    
    public int rowMajorIndex(){
        return COLUMNS*y+x;
    }
    
    private static ArrayList<Cell> rowMajorOrder(){
        ArrayList<Cell> rowMajorOrder = new ArrayList<Cell>();
        for (int i=0;i<COUNT;++i){
            rowMajorOrder.add(new Cell(i%COLUMNS,i/COLUMNS));
        }
        return rowMajorOrder;
    }
    
    private static ArrayList<Cell> spiralOrder(){
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
        int c1;
        int c2;
        
        while (!ix.isEmpty()&&!iy.isEmpty()){
            if (horizontal){
                i1=ix;
                i2=iy;
            }
            else {
                i1=iy;
                i2=ix;
            }
            
            c2 = i2.get(0);
            i2.remove(0);
        }
        return spiral;
    }
    
    public Cell neighbor(Direction dir){
        int place=ROW_MAJOR_ORDER.indexOf(this);
        switch (dir){
        
            case E:
                if (x==COLUMNS-1){
                    return ROW_MAJOR_ORDER.get(place-(COLUMNS-1));
                }
                else{
                    return ROW_MAJOR_ORDER.get(place+1);
                }
                
            case W:
                if (x==0){
                    return ROW_MAJOR_ORDER.get(place+(COLUMNS-1));
                }
                else{
                    return ROW_MAJOR_ORDER.get(place-1);
                }
                
            case S:
                if (y==ROWS-1){
                    return ROW_MAJOR_ORDER.get(place-(ROWS-1)*COLUMNS);
                }
                else{
                    return ROW_MAJOR_ORDER.get(place+COLUMNS);
                }
                
            case N:
                if (y==0){
                    return ROW_MAJOR_ORDER.get(place+(ROWS-1)*COLUMNS);
                }
                else{
                    return ROW_MAJOR_ORDER.get(place-COLUMNS);
                }
            default:
                return null;
         
        }
            
    }
    
    @Override
    public boolean equals(Object that){
        if (that==null){
            return false;
        }else if (getClass()!=that.getClass()){
            return false;
        }return x==((Cell)that).x() && y==((Cell)that).y();
    }
    
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}
