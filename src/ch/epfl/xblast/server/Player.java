package ch.epfl.xblast.server;

import java.util.Objects;
import ch.epfl.cs108.Sq;
import ch.epfl.xblast.*;

public final class Player {
    private final PlayerID id;
    private final Sq<LifeState> lifeStates;
    private final Sq<DirectedPosition> directedPos;
    private final int maxBombs;
    private final int bombRange;
    
    public Player(PlayerID id, Sq<LifeState> lifeStates, Sq<DirectedPosition> directedPos, int maxBombs, int bombRange) throws IllegalArgumentException, NullPointerException{
        this.id=Objects.requireNonNull(id);
        this.lifeStates=Objects.requireNonNull(lifeStates);
        this.directedPos=Objects.requireNonNull(directedPos);
        this.maxBombs=ArgumentChecker.requireNonNegative(maxBombs);
        this.bombRange=ArgumentChecker.requireNonNegative(bombRange);
    }
    
    public Player(PlayerID id, int lives, Cell position, int maxBombs, int bombRange){
        this();
    }
    
    public final static class LifeState{
        private final int lives;
        private final State state;
        
        public LifeState(int lives, State state) throws IllegalArgumentException, NullPointerException{
            this.lives=ArgumentChecker.requireNonNegative(lives);
            this.state=Objects.requireNonNull(state);
        }
        
        public int lives(){
            return lives;
        }
        
        public State state(){
            return state;
        }
        
        public boolean canMove(){
            return (state==State.INVULNERABLE || state==State.VULNERABLE);
        }
        
        public enum State{
            INVULNERABLE, VULNERABLE, DYING, DEAD;
        }
    }
    
    public final static class DirectedPosition{
        private final SubCell position;
        private final Direction direction;
        
        public DirectedPosition(SubCell position, Direction direction){
            this.position=Objects.requireNonNull(position);
            this.direction=Objects.requireNonNull(direction);
        }
        
        public SubCell position(){
            return position;
        }
        
        public DirectedPosition withPosition(SubCell newPosition){
            return new DirectedPosition(newPosition, direction);
        }
        
        public Direction direction(){
            return direction;
        }
        
        public DirectedPosition withDirection(Direction newDirection){
            return new DirectedPosition(position, newDirection);
        }
        
        public static Sq<DirectedPosition> stopped(DirectedPosition p){
            return Sq.constant(p);
        }
        
        public static Sq<DirectedPosition> moving(DirectedPosition p){
            return Sq.iterate(p, d -> d.withPosition(d.position.neighbor(d.direction)));
        }
    }
}
