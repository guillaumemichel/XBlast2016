package ch.epfl.xblast;
/**
 * A direction
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public enum Direction {
    N, E, S, W;
    
    /**
     * Returns the opposite of this direction
     * 
     * @return
     *      The opposite of this direction
     */
    public Direction opposite() {
        switch(this){
        case N:
            return S;

        case S:
            return N;

        case E:
            return W;

        case W:
            return E;

        default:
            return null;
        }
    }
    
    /**
     * Determines if this direction is horizontal (East or West) and returns the appropriate boolean
     * 
     * @return
     *      <b>True</b> if this direction is horizontal (East or West), <b>false</b> otherwise
     */
    public boolean isHorizontal(){
        return (this==E || this==W);
    }
    
    /**
     * Determines if this direction is parallel to another direction
     * 
     * @param that
     *      The direction we test the parallelism with
     * 
     * @return
     *      <b>True</b> if the directions are parallel, <b>false</b> otherwise
     */
    public boolean isParallelTo(Direction that){
       return (this==that || this==that.opposite());
    }
}
