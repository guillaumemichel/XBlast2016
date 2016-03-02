package ch.epfl.xblast.server;

/**
 * A block
 * 
 * @author Guillaume Michel
 * @author Adrien Vandenbroucque
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
    
    public boolean canHostPlayer(){
        return this.isFree();
    }
    
    public boolean castsShadow(){
        return (this==INDESTRUCTIBLE_WALL || this==DESTRUCTIBLE_WALL || this==CRUMBLING_WALL);
    }
}
