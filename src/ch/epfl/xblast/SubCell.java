package ch.epfl.xblast;

/**
 * A subcell
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class SubCell {
    private final int x, y;
    
    /**
     * Constructs a subcell with coordinates x and y
     * 
     * @param x
     *      The x coordinate of the subcell
     * @param y
     *      The y coordinate of the subcell
     */
    public SubCell(int x, int y){
        this.x=Math.floorMod(x, Cell.COLUMNS*16);
        this.y=Math.floorMod(y, Cell.ROWS*16);
    }
    
    /**
     * Returns the x coordinate of this subcell
     * 
     * @return
     *      The x coordinate of this subcell
     */
    public int x(){
        return x;
    }
    
    /**
     * Returns the y coordinate of this subcell
     * 
     * @return
     *      The y coordinate of this subcell
     */
    public int y(){
        return y;
    }
    
    /**
     * Given a cell, it determines its central subcell
     * 
     * @param cell
     *      The cell where we look for the central subcell
     *      
     * @return
     *      The central subcell of the cell
     */
    public static SubCell centralSubCellOf(Cell cell){
        return new SubCell(cell.x()*16+8, cell.y()*16+8);
    }
    
    
    /**
     * Determines the Manhattan distance between this subcell and the nearest central subcell
     * 
     * @return
     *      The Manhattan distance between this subcell and the nearest central subcell
     */
    public int distanceToCentral(){
        SubCell center=centralSubCellOf(containingCell());
        return Math.abs(center.x()-x)+Math.abs(center.y()-y);
    }
 
    /**
     * Determines if this subcell is a central subcell
     * 
     * @return
     *      <b>true</b> if it is a central subcell, <b>false</b> otherwise
     */
    public boolean isCentral(){
        return (x%16==8 && y%16==8);
    }
    
    /**
     * Determines the adjacent subcell of this subcell, according to the direction that is given
     * @param d
     *      The direction in which we look for the adjacent subcell
     *      
     * @return
     *      The adjacent subcell according to the direction that is given
     */
    public SubCell neighbor(Direction d){
        switch(d){
            case E:
                if(x==Cell.COLUMNS*16-1){
                    return new SubCell(x-(Cell.COLUMNS*16-1), y);
                }else{
                    return new SubCell(x+1, y);
                }
            case W:
                if(x==0){
                    return new SubCell(x+(Cell.COLUMNS*16-1),y);
                }else{
                    return new SubCell(x-1, y);
                }
            case N:
                if(y==0){
                    return new SubCell(x, y+(Cell.ROWS*16-1));
                }else{
                    return new SubCell(x, y-1);
                }
            case S:
                if(y==Cell.ROWS*16-1){
                    return new SubCell(x, y-(Cell.ROWS*16-1));
                }else{
                    return new SubCell(x, y+1);
                }
            default:
                return null; 
        }
    }
    
    /**
     * Returns the cell in which this subcell is contained
     * 
     * @return
     *      The cell in which this subcell is contained
     */
    public Cell containingCell(){
        return new Cell(x/16, y/16);
    }
    
    @Override
    public boolean equals(Object that){
        if(that==null){
            return false;
        }else if(that.getClass() != this.getClass()){
            return false;
        }else{
            return x==((SubCell)that).x() && y==((SubCell)that).y();
        }
    }
    
    @Override
    public int hashCode(){
        return x+Cell.ROWS*y;
    }
    
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}
