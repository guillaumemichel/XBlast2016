package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;

public final class GameState {
    private final int ticks;
    private final Board board;
    private final List<Player> players;
    private final List<Bomb> bombs;
    private final List<Sq<Sq<Cell>>> explosions;
    private final List<Sq<Cell>> blasts;
    
    
    public GameState(int ticks, Board board, List<Player> players, List<Bomb> bombs, List<Sq<Sq<Cell>>> explosions, List<Sq<Cell>> blasts){
        this.ticks = ArgumentChecker.requireNonNegative(ticks);
        if(players.size()!=4) 
            throw new IllegalArgumentException();
        this.players = new ArrayList<>(Objects.requireNonNull(players));
        this.board = Objects.requireNonNull(board);
        this.bombs = new ArrayList<>(Objects.requireNonNull(bombs));
        this.explosions = new ArrayList<>(Objects.requireNonNull(explosions));
        this.blasts = new ArrayList<>(Objects.requireNonNull(blasts));
    }
    
    public GameState(Board board, List<Player> players){
        this(0,board,players,new ArrayList<Bomb>(),new ArrayList<Sq<Sq<Cell>>>(),new ArrayList<Sq<Cell>>());
    }
    
    public int ticks(){
        return ticks;
    }
    
    public boolean isGameOver(){
        return (ticks>=Ticks.TOTAL_TICKS)||(alivePlayers().size()<2);
    }
    
    public double remainingTime(){
        return (Ticks.TOTAL_TICKS-ticks)/Ticks.TICKS_PER_SECOND;
    }
    
    public Optional<PlayerID> winner(){
        if (alivePlayers().size()==1 && isGameOver()){//useless but beautiful (isGameOver)
            return Optional.of(alivePlayers().get(0).id());
        }
        //if the last players die at the same time there is no winner
        return Optional.empty();
    }
    
    public Board board(){
        return board;
    }
    
    public List<Player> players(){
        return players;
    }
    
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
}
