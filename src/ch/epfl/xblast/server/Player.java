package ch.epfl.xblast.server;

import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.server.Player.LifeState.State;

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
        
    }
    
    public PlayerID id(){
        return id;
    }
    
    public Sq<LifeState> lifeStates(){
        return lifeStates;
    }
    
    public LifeState lifeState(){
        return lifeStates.head();
    }
    
    public Sq<LifeState> statesForNextLife(){
       return Sq.repeat(Ticks.PLAYER_DYING_TICKS, new LifeState(lives(), State.DYING)).concat(createLifeStateSequence(lives()-1));
    }
    
    private static Sq<LifeState> createLifeStateSequence(int lives){
        if(lives==0){
            return Sq.constant(new LifeState(0, State.DEAD));
        }else{
            return (Sq.repeat(Ticks.PLAYER_INVULNERABLE_TICKS, new LifeState(lives, State.INVULNERABLE))).concat(Sq.constant(new LifeState(lives, State.VULNERABLE)));
        }
    }
    
    public int lives(){
        return lifeState().lives();
    }
    
    public boolean isAlive(){
        return lives()>0;
    }
    
    public Sq<DirectedPosition> directedPositions(){
        return directedPos;
    }
    
    public SubCell position(){
        return directedPositions().head().position();
    }
    
    public Direction direction(){
        return directedPositions().head().direction();
    }
    
    public int maxBombs(){
        return maxBombs;
    }
    
    public Player withMaxBombs(int newMaxBombs){
        return new Player(id, lifeStates, directedPos, newMaxBombs, bombRange);
    }
    
    public int bombRange(){
        return bombRange;
    }
    
    public Player witBombRange(int newBombRange){
        return new Player(id, lifeStates, directedPos, maxBombs, newBombRange);
    }
    
    public Bomb newBomb(){
        return new Bomb(id, position().containingCell(), Ticks.BOMB_FUSE_TICKS, bombRange);
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
