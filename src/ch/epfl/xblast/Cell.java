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
        this.x=Math.floorMod(x, COLUMNS);
        this.y=Math.floorMod(y, ROWS);
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
        ArrayList<Integer> temp = new ArrayList<Integer>();       
        ArrayList<Integer> i1;
        ArrayList<Integer> i2;
        int c1;
        int c2;
        
        while (!ix.isEmpty()&&!iy.isEmpty()){
            if (horizontal){
                i1=ix;
                i2=iy;
                System.out.println("horizontal");
            }
            else {
                i1=iy;
                i2=ix;
                System.out.println("vertical");
            }
            temp = (ArrayList<Integer>)i1.clone();
            i1=(ArrayList<Integer>)i2.clone();
            i2=(ArrayList<Integer>)temp.clone();
            temp.clear();
            System.out.println(i1);
            System.out.println(i2);
            c2 = i2.get(0);
            i2.remove(c2);
            for (int i=0;i<i1.size();++i){
                c1=i1.get(i);
                spiral.add(new Cell(c1,c2));
            }
            System.out.println("Spirale "+c2+i1);
            for (int i=i1.size()-1;i>=0;--i){
                temp.add(i1.get(i));
            }
            i1=(ArrayList<Integer>)temp.clone();
            temp.clear();
            horizontal = !horizontal;
        }
        return spiral;
    }
    
    public Cell neighbor(Direction dir){
        switch (dir){
        
            case E:
                if (x==COLUMNS-1){
                    return new Cell(x-(COLUMNS-1),y);
                }
                else{
                    return new Cell(x+1,y);
                }
                
            case W:
                if (x==0){
                    return new Cell(x+(COLUMNS-1),y);
                }
                else{
                    return new Cell(x-1,y);
                }
                
            case S:
                if (y==ROWS-1){
                    return new Cell(x,y-(ROWS-1));
                }
                else{
                    return new Cell(x,y+1);
                }
                
            case N:
                if (y==0){
                    return new Cell(x,y+(ROWS-1));
                }
                else{
                    return new Cell(x,y-1);
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
