package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.Lists;
import ch.epfl.xblast.PlayerID;

/**
 * A game state
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class GameState {
    private final int ticks;
    private final Board board;
    private final List<Player> players;
    private final List<Bomb> bombs;
    private final List<Sq<Sq<Cell>>> explosions;
    private final List<Sq<Cell>> blasts;
    
    
    private final static List<List<PlayerID>> PERMUTATIONS=Lists.permutations(Arrays.asList(PlayerID.values()));
    private final static Random RANDOM=new Random(2016);
    
    /**
     * Constructs a game state with the given attributes
     * 
     * @param ticks
     *      The number of ticks
     *      
     * @param board
     *      The board
     * 
     * @param players
     *      The players 
     * 
     * @param bombs
     *      The bombs
     * 
     * @param explosions
     *      The explosions
     * 
     * @param blasts
     *      The blasts
     * 
     * @throws IllegalArgumentException
     *      If the tick is strictly negative
     *      
     * @throws NullPointerException
     *      If board, players, bombs, explosions or blasts is null
     */
    public GameState(int ticks, Board board, List<Player> players, List<Bomb> bombs, List<Sq<Sq<Cell>>> explosions, List<Sq<Cell>> blasts) throws IllegalArgumentException, NullPointerException{
        this.ticks = ArgumentChecker.requireNonNegative(ticks);
        if(players.size()!=4) 
            throw new IllegalArgumentException();
        this.players = new ArrayList<>(Objects.requireNonNull(players));
        this.board = Objects.requireNonNull(board);
        this.bombs = new ArrayList<>(Objects.requireNonNull(bombs));
        this.explosions = new ArrayList<>(Objects.requireNonNull(explosions));
        this.blasts = new ArrayList<>(Objects.requireNonNull(blasts));
    }
    
    
    /**
     * Constructs a game state at tick 0 with no explosions, bombs or blasts, and with the given board and players
     * 
     * @param board
     *      The board
     *      
     * @param players
     *      The players
     */
    public GameState(Board board, List<Player> players){
        this(0,board,players,new ArrayList<Bomb>(),new ArrayList<Sq<Sq<Cell>>>(),new ArrayList<Sq<Cell>>());
    }
    
    /**
     * Returns the tick corresponding to this game state
     * 
     * @return
     *      The tick corresponding to this game state
     */
    public int ticks(){
        return ticks;
    }
    
    /**
     * Determines if this game state is corresponding to a game that is over, and returns the appropriate boolean
     * 
     * @return
     *      <b>True</b> if thhis game state corresponds to a game that is over, <b>false</b> otherwise
     */
    public boolean isGameOver(){
        return (ticks>=Ticks.TOTAL_TICKS)||(alivePlayers().size()<2);
    }
    
    /**
     * Returns the remaining time before the end of this game (in seconds)
     * 
     * @return
     *      The remaining time before the end of this game (in seconds)
     */
    public double remainingTime(){
        return (Ticks.TOTAL_TICKS-ticks)/Ticks.TICKS_PER_SECOND;
    }
    
    /**
     * Returns the identity of the winner of this game if there is one
     * 
     * @return
     *      The identity of the winner of this game if there is one, or an empty optional value otherwise
     */
    public Optional<PlayerID> winner(){
        if (alivePlayers().size()==1 && isGameOver()){//useless but beautiful (isGameOver)
            return Optional.of(alivePlayers().get(0).id());
        }
        //if the last players die at the same time there is no winner
        return Optional.empty();
    }
    
    /**
     * Returns the board of this game
     * 
     * @return
     *      The board of this game
     */
    public Board board(){
        return board;
    }
    
    /**
     * Returns a list of the 4 players of this game
     * 
     * @return
     *      A list of the 4 players of this game
     */
    public List<Player> players(){
        return players;
    }
    
    /**
     * Returns a list of the players of this game that are alive, those who have at least one life
     * 
     * @return
     *      A list of the players of this game that are alive 
     */
    public List<Player> alivePlayers(){
        List<Player> alivePlayers = new ArrayList<>();
        for (Player p:players){
            if (p.isAlive())
                alivePlayers.add(p); 
        }
        return alivePlayers;
    }
    
    private static List<Sq<Cell>> nextBlasts(List<Sq<Cell>> blasts0, Board board0, List<Sq<Sq<Cell>>> explosions0){
        List<Sq<Cell>> blasts1=new ArrayList<>();
        for (Sq<Cell> c : blasts0){
            if (!c.isEmpty() && board0.blockAt(c.head()).isFree()){
                blasts1.add(c.tail());
            }
        }
        for (Sq<Sq<Cell>> sq : explosions0) {
            blasts1.add(sq.head());
        }
        return blasts1;
    }
    
    public Map<Cell, Bomb> bombedCells(){
        Map<Cell, Bomb> bombedCells= new HashMap<>();
        for (Bomb b : bombs) {
            bombedCells.put(b.position(), b);
        }
        return bombedCells;
    }
    
    public Set<Cell> blastedCells(){
        Set<Cell> blastedCells= new HashSet<>();
        for (Sq<Cell> cell : blasts) {
            blastedCells.add(cell.head());
        }
        return blastedCells;
    }
    
    public GameState next(Map<PlayerID, Optional<Direction>> speedChangeEvents, Set<PlayerID> bombDropEvents){
        return null;
    }
    
}
