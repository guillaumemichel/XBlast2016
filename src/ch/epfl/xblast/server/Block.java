package ch.epfl.xblast.server;

/**
 * A block
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public enum Block {
    FREE, INDESTRUCTIBLE_WALL, DESTRUCTIBLE_WALL, CRUMBLING_WALL;
    
    /**
     * Determines if this block is free and returns the appropriate boolean
     * 
     * @return
     *      <b>True</b> if this block is free, <b>false</b> otherwise
     */
    public boolean isFree(){
        return this==FREE;
    }
    
    /**
     * Determines if this block can host a player and returns the appropriate boolean
     * 
     * @return
     *      <b>True</b> if this block can host a player, <b>false</b> otherwise
     */
    public boolean canHostPlayer(){
        return this.isFree();
    }
    
    /**
     * Determines if this block casts shadow on the board, and returns the appropriate boolean
     * 
     * @return
     *      <b>True</b> if this casts shadow on the board, <b>false</b> otherwise
     */
    public boolean castsShadow(){
        return (this==INDESTRUCTIBLE_WALL || this==DESTRUCTIBLE_WALL || this==CRUMBLING_WALL);
    }
}
