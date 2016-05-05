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
    
    private final static int SUBCELL_DIMENSION = 16;
    /**
     * Constructs a subcell with coordinates x and y
     * 
     * @param x
     *      The x coordinate of the subcell
     * @param y
     *      The y coordinate of the subcell
     */
    public SubCell(int x, int y){
        this.x=Math.floorMod(x, Cell.COLUMNS*SUBCELL_DIMENSION);
        this.y=Math.floorMod(y, Cell.ROWS*SUBCELL_DIMENSION);
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
     * Returns the central subcell of the given cell
     * 
     * @param cell
     *      The cell where we look for the central subcell
     *      
     * @return
     *      The central subcell of the cell
     */
    public static SubCell centralSubCellOf(Cell cell){
        return new SubCell(cell.x()*SUBCELL_DIMENSION+SUBCELL_DIMENSION/2, cell.y()*SUBCELL_DIMENSION+SUBCELL_DIMENSION/2);
    }
    
    
    /**
     * Returns the Manhattan distance between this subcell and the nearest central subcell
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
        return distanceToCentral()==0;
    }
    
    /**
     * Returns the adjacent subcell of this subcell, according to the direction that is given
     * 
     * @param d
     *      The direction in which we look for the adjacent subcell
     *      
     * @return
     *      The adjacent subcell according to the direction that is given
     */
    public SubCell neighbor(Direction d){
        switch(d){
            case E:
                    return new SubCell(x+1, y);
            case W:
                    return new SubCell(x-1, y);
            case N:
                    return new SubCell(x, y-1);
            case S:
                    return new SubCell(x, y+1);
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
        return new Cell(x/SUBCELL_DIMENSION, y/SUBCELL_DIMENSION);
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
    public String toString(){
        return "("+x+","+y+")";
    }
    
    @Override
    public int hashCode(){
        return SUBCELL_DIMENSION*Cell.COLUMNS*y+x;
    }
}
