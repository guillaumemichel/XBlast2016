package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.ArgumentChecker;

public final class Bomb {
    private final PlayerID ownerId;
    private final Cell position;;
    private final Sq<Integer> fuseLengths;
    private final int range;
    
    public Bomb(PlayerID ownerId, Cell position, Sq<Integer> fuseLengths, int range) throws IllegalArgumentException, NullPointerException{
        this.ownerId=Objects.requireNonNull(ownerId);
        this.position=Objects.requireNonNull(position);
        this.fuseLengths=Objects.requireNonNull(fuseLengths);
        this.range=ArgumentChecker.requireNonNegative(range);
        
        if(fuseLengths.isEmpty())
            throw new IllegalArgumentException("The sequence of fuse lengths is empty !");
    }
    
    public Bomb(PlayerID ownerId, Cell position, int fuseLength, int range){
        this(ownerId, position, Sq.iterate(fuseLength, i -> i-1).limit(fuseLength), range);
    }
    
    public PlayerID ownerId(){
        return ownerId;
    }
    
    public Cell position(){
        return position;
    }
    
    public Sq<Integer> fuseLengths(){
        return fuseLengths;
    }
    
    public int fuseLength(){
        return fuseLengths().head();
    }
    
    public int range(){
        return range;
    }
    
    public List<Sq<Sq<Cell>>> explosion(){
        List<Sq<Sq<Cell>>> explosion=new ArrayList<>();
        //WTF?
        return null;
    }
    
    private Sq<Sq<Cell>> explosionArmTowards(Direction dir){
        Sq<Cell> particle = Sq.iterate(position, c -> c.neighbor(dir));
        particle.limit(3);
        Sq<Sq<Cell>> explosionArm = Sq.constant(particle);
        explosionArm.limit(3);
        return explosionArm;
    }
}
