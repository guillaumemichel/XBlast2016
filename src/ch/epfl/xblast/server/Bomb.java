package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.ArgumentChecker;

/**
 * A bomb
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class Bomb {
    private final PlayerID ownerId;
    private final Cell position;;
    private final Sq<Integer> fuseLengths;
    private final int range;
    
    /**
     * Constructs a bomb with the given parameters
     * 
     * @param ownerId
     *      The id of the owner of the bomb
     *      
     * @param position
     *      The position of the bomb
     *      
     * @param fuseLengths
     *      The sequence of fuse lengths of the bomb
     *      
     * @param range
     *      The range of the bomb
     *      
     * @throws IllegalArgumentException
     *      If the range is strictly negative
     *      If the sequence of fuse lengths is empty
     *      
     * @throws NullPointerException
     *      If ownerId, position, or the sequence of fuse lengths are null
     */
    public Bomb(PlayerID ownerId, Cell position, Sq<Integer> fuseLengths, int range) throws IllegalArgumentException, NullPointerException{
        this.ownerId=Objects.requireNonNull(ownerId);
        this.position=Objects.requireNonNull(position);
        this.fuseLengths=Objects.requireNonNull(fuseLengths);
        this.range=ArgumentChecker.requireNonNegative(range);
        
        if(fuseLengths.isEmpty())
            throw new IllegalArgumentException("The sequence of fuse lengths is empty !");
    }
    
    /**
     * Constructs a bomb with the given parameters
     * 
     * @param ownerId
     *      The id of the owner of the bomb
     *      
     * @param position
     *      The position of the bomb
     *      
     * @param fuseLength
     *      The starting fuse length, from which we create a decreasing sequence of fuse lengths (-1 each time)
     *      
     * @param range
     *      The range of the bomb
     */
    public Bomb(PlayerID ownerId, Cell position, int fuseLength, int range){
        this(ownerId, position, Sq.iterate(fuseLength, i -> i-1).limit(fuseLength), range);
    }
    
    /**
     * Returns the identity of the owner of this bomb
     *  
     * @return
     *      The identity of the owner of this bomb
     */
    public PlayerID ownerId(){
        return ownerId;
    }
    
    /**
     * Returns the position of this bomb
     * 
     * @return
     *      The position of this bomb
     */
    public Cell position(){
        return position;
    }
    
    /**
     * Returns the sequence of fuse lengths of this bomb
     * 
     * @return
     *      The sequence of fuse lengths of this bomb
     */
    public Sq<Integer> fuseLengths(){
        return fuseLengths;
    }
    
    /**
     * Returns the current fuse length of this bomb
     * 
     * @return
     *      The current fuse length of this bomb
     */
    public int fuseLength(){
        return fuseLengths().head();
    }
    
    /**
     * Returns the range of this bomb
     * 
     * @return
     *      The range of this bomb
     */
    public int range(){
        return range;
    }
    
    /**
     * Returns the explosion corresponding to this bomb (in the form of an array of 4 elements, 
     * each one representing an arm of the explosion)
     * 
     * @return
     *      The explosion corresponding to this bomb
     */
    public List<Sq<Sq<Cell>>> explosion(){
        List<Sq<Sq<Cell>>> explosion=new ArrayList<>();
        Direction[] directions = Direction.values();
        for (int i = 0; i < 4; i++) {
            explosion.add(explosionArmTowards(directions[i]));
        }
        return explosion;
    }
    
    private Sq<Sq<Cell>> explosionArmTowards(Direction dir){
        Sq<Cell> particle = Sq.iterate(position, c -> c.neighbor(dir)).limit(range);
        Sq<Sq<Cell>> explosionArm=Sq.repeat(Ticks.EXPLOSION_TICKS, particle);
        return explosionArm;
    }
}
