package ch.epfl.xblast.server;

import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.server.Player.LifeState.State;

/**
 * A player
 * 
 * @author Guillaume Michel
 * @author Adrien Vandenbroucque
 *
 */
public final class Player {
    private final PlayerID id;
    private final Sq<LifeState> lifeStates;
    private final Sq<DirectedPosition> directedPos;
    private final int maxBombs;
    private final int bombRange;
    
    /**
     * Constructs a player with the given attributes
     * 
     * @param id
     *      The id of the player
     *      
     * @param lifeStates
     *      The sequence of life state of the player
     *      
     * @param directedPos
     *      The sequence of directed position of the player
     *      
     * @param maxBombs
     *      The maximum number of bombs the player can plant 
     *      
     * @param bombRange
     *      The range of the explosions of the player's bombs
     *      
     * @throws IllegalArgumentException
     *      If maxBombs or bombRange is strictly negative
     *      
     * @throws NullPointerException
     *      If id, lifeStates or directedPos is null
     */
    public Player(PlayerID id, Sq<LifeState> lifeStates, Sq<DirectedPosition> directedPos, int maxBombs, int bombRange) throws IllegalArgumentException, NullPointerException{
        this.id=Objects.requireNonNull(id);
        this.lifeStates=Objects.requireNonNull(lifeStates);
        this.directedPos=Objects.requireNonNull(directedPos);
        this.maxBombs=ArgumentChecker.requireNonNegative(maxBombs);
        this.bombRange=ArgumentChecker.requireNonNegative(bombRange);
    }
    
    /**
     * Constructs a player with the given attributes
     * 
     * @param id
     *      The id of the player
     *      
     * @param lives
     *      The number of lives of the player
     *      
     * @param position
     *      The cell where the player stands
     *      
     * @param maxBombs
     *      The maximum number of bombs the player can plant 
     *      
     * @param bombRange
     *      The range of the explosions of the player's bombs
     *      
     * @throws IllegalArgumentException
     *      If maxBombs or bombRange is strictly negative
     *      
     * @throws NullPointerException
     *      If id, lifeStates or directedPos is null
     */
    public Player(PlayerID id, int lives, Cell position, int maxBombs, int bombRange){
        this(id, createLifeStateSequence(lives),Sq.repeat(Ticks.PLAYER_DYING_TICKS,new DirectedPosition(SubCell.centralSubCellOf(position),Direction.S)),maxBombs,bombRange);
    }
    /**
     * Returns the identity of this player
     * 
     * @return
     *      The id of this player
     */
    public PlayerID id(){
        return id;
    }
    
    /**
     * Returns the sequence of life states of this player
     * 
     * @return
     *      The sequence of life states of this player
     */
    public Sq<LifeState> lifeStates(){
        return lifeStates;
    }
    
    /**
     * Returns the current life state of this player
     * 
     * @return
     *      The current life state of this player
     */
    public LifeState lifeState(){
        return lifeStates.head();
    }
    
    /**
     * Returns the sequence of life states for the player's next life
     * 
     * @return
     *      The sequence of life states for the player's next life
     */
    public Sq<LifeState> statesForNextLife(){
       return Sq.repeat(Ticks.PLAYER_DYING_TICKS, new LifeState(lives(), State.DYING)).concat(createLifeStateSequence(lives()-1));
    }
    
    private static Sq<LifeState> createLifeStateSequence(int lives){
        if(lives<=0){
            return Sq.constant(new LifeState(0, State.DEAD));
        }else{
            return (Sq.repeat(Ticks.PLAYER_INVULNERABLE_TICKS, new LifeState(lives, State.INVULNERABLE))).concat(Sq.constant(new LifeState(lives, State.VULNERABLE)));
        }
    }
    
    /**
     * Returns the current number of lives of this player
     * 
     * @return
     *      The current number of lives of this player
     */
    public int lives(){
        return lifeState().lives();
    }
    
    /**
     * Determines if this player is alive and returns the appropriate boolean
     * 
     * @return
     *      <b>True</b> if this player is alive, <b>false</b> otherwise
     */
    public boolean isAlive(){
        return lives()>0;
    }
    
    /**
     * Returns the sequence of directed positions of this player
     * 
     * @return
     *      The sequence of directed positions of this player
     */
    public Sq<DirectedPosition> directedPositions(){
        return directedPos;
    }
    
    /**
     * Returns the current position of this player
     * 
     * @return
     *      The current position of this player
     */
    public SubCell position(){
        return directedPositions().head().position();
    }
    
    /**
     * Returns the direction this player is currently looking at
     * 
     * @return
     *      The direction this player is currently looking at
     */
    public Direction direction(){
        return directedPositions().head().direction();
    }
    
    /**
     * Returns the maximum number of bombs this player can plant
     * 
     * @return
     *      The maximum number of bombs this player can plant
     */
    public int maxBombs(){
        return maxBombs;
    }
    
    /**
     * Returns a player with the given number of maximum bombs he can plant (and conserves his other attributes)
     * 
     * @param newMaxBombs
     *      The new maximum number of bombs the player can plant
     *      
     * @return
     *      The player with the new number of maxBombs (and conserves the other attributes)
     */
    public Player withMaxBombs(int newMaxBombs){
        return new Player(id, lifeStates, directedPos, newMaxBombs, bombRange);
    }
    
    /**
     * Returns the range of the explosions of this player's bombs
     * 
     * @return
     *      The range of the explosions of this player's bombs
     */
    public int bombRange(){
        return bombRange;
    }
    
    /**
     * Returns a player with the given range of explosions his bombs make (and conserves his other attributes)
     * 
     * @param newBombRange
     *      The new range of explosions the player's bombs make
     *      
     * @return
     *      The player with the new range of explosions his bombs make (and conserves his other attributes)
     */
    public Player witBombRange(int newBombRange){
        return new Player(id, lifeStates, directedPos, maxBombs, newBombRange);
    }
    
    /**
     * Returns a bomb  positioned on the cell where this player is currently
     * 
     * @return
     *      The bomb, positioned on the cell where this player is currently
     */
    public Bomb newBomb(){
        return new Bomb(id, position().containingCell(), Ticks.BOMB_FUSE_TICKS, bombRange);
    }

    /**
     * A life state (represents the couple (number of lives, state) of the player)
     */
    public final static class LifeState{
        private final int lives;
        private final State state;
        
        /**
         * Constructs the couple (number of lives, state) with the given values
         * 
         * @param lives
         *      The number of lives of the player
         *      
         * @param state
         *      The state of the player
         *      
         * @throws IllegalArgumentException
         *      If the number of lives is strictly negative
         *      
         * @throws NullPointerException
         *      If the state is null
         */
        public LifeState(int lives, State state) throws IllegalArgumentException, NullPointerException{
            this.lives=ArgumentChecker.requireNonNegative(lives);
            this.state=Objects.requireNonNull(state);
        }
        
        /**
         * Returns the number of lives of this life state
         * 
         * @return
         *      The number of lives of this couple
         */
        public int lives(){
            return lives;
        }
        
        /**
         * Returns the state of this life state
         * 
         * @return
         *      The state of the couple
         */
        public State state(){
            return state;
        }
        
        /**
         * Determines if the player can move, and returns the appropriate boolean
         * 
         * @return
         *      <b>True</b> if the player can move, <b>false</b> otherwise
         */
        public boolean canMove(){
            return (state==State.INVULNERABLE || state==State.VULNERABLE);
        }
        
        /**
         * A state
         */
        public enum State{
            INVULNERABLE, VULNERABLE, DYING, DEAD;
        }
    }
    
    /**
     * A directed position (represents the pair (position, direction))
     */
    public final static class DirectedPosition{
        private final SubCell position;
        private final Direction direction;
        
        /**
         * Constructs a directed position with the given position and the given direction
         * 
         * @param position
         *      The position
         *      
         * @param direction
         *      The direction
         *      
         * @throws NullPointerException
         *      If one argument or the other is null
         */
        public DirectedPosition(SubCell position, Direction direction) throws NullPointerException{
            this.position=Objects.requireNonNull(position);
            this.direction=Objects.requireNonNull(direction);
        }
        
        /**
         * Returns the position of this directed position
         * 
         * @return
         *      The position
         */
        public SubCell position(){
            return position;
        }
        
        /**
         * Returns a directed position with the given position (and conserves its direction)
         * 
         * @param newPosition
         *      The new position
         *      
         * @return
         *      The directed position with the given position (and conserves the same direction)
         */
        public DirectedPosition withPosition(SubCell newPosition){
            return new DirectedPosition(newPosition, direction);
        }
        
        /**
         * Returns the direction of this directed position
         * 
         * @return
         *      The direction of this directed position
         */
        public Direction direction(){
            return direction;
        }
        
        /**
         * Returns a directed position with the given direction (and conserves its position)
         * 
         * @param newDirection
         *      The new direction
         *      
         * @return
         *      The directed position with the given direction (and conserves the same position)
         */
        public DirectedPosition withDirection(Direction newDirection){
            return new DirectedPosition(position, newDirection);
        }
        
        /**
         * Returns an infinite sequence composed only by the given directed position (represents a player stopped in this position)
         * 
         * @param p
         *      The directed position the player is stopped at
         *      
         * @return
         *      The infinite sequence composed only by the given directed position
         * 
         */
        public static Sq<DirectedPosition> stopped(DirectedPosition p){
            return Sq.constant(p);
        }
        
        /**
         * Returns an infinite sequence of directed positions (to represent a player moving in a direction he looks at)
         * 
         * @param p
         *      The current directed position of the player
         *      
         * @return
         *      The infinite sequence of directed positions representing the moving player
         */
        public static Sq<DirectedPosition> moving(DirectedPosition p){
            return Sq.iterate(p, d -> d.withPosition(d.position.neighbor(d.direction)));
        }
    }
}
