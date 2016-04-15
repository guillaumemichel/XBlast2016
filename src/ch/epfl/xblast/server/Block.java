package ch.epfl.xblast.server;

import java.util.NoSuchElementException;

/**
 * A block
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public enum Block {
    FREE, INDESTRUCTIBLE_WALL, DESTRUCTIBLE_WALL, CRUMBLING_WALL,BONUS_BOMB(Bonus.INC_BOMB),BONUS_RANGE(Bonus.INC_RANGE);
    
    private Bonus maybeAssociatedBonus;
    
    private Block(){
        this.maybeAssociatedBonus=null;
    }

    private Block(Bonus maybeAssociatedBonus){
        this.maybeAssociatedBonus = maybeAssociatedBonus;
    }
    
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
        return (this.isFree()||this.isBonus());
    }
    
    /**
     * Determines if this block casts shadow on the board, and returns the appropriate boolean
     * 
     * @return
     *      <b>True</b> if this block casts shadow on the board, <b>false</b> otherwise
     */
    public boolean castsShadow(){
        return (this==INDESTRUCTIBLE_WALL || this==DESTRUCTIBLE_WALL || this==CRUMBLING_WALL);
    }
    
    /**
     * Determines if this block is a bonus, and returns the appropriate boolean
     * 
     * @return
     *      <b>True</b> if this block is a bonus, <b>false</b> otherwise
     */
    public boolean isBonus(){
        return (this==BONUS_BOMB || this==BONUS_RANGE);
    }
    
    /**
     * Returns the bonus associated to this block, if this block is a bonus
     * 
     * @return
     *      The bonus associated to this block
     *      
     * @throws NoSuchElementException
     *      If this block is not a bonus
     */
    public Bonus associatedBonus() throws NoSuchElementException{
        if (!isBonus()){
            throw new NoSuchElementException();
        }
        return maybeAssociatedBonus;
    }
}
