package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;

public final class GameState {
    private final int ticks;
    private final Board board;
    private final List<Player> players;
    private final List<Bomb> bombs;
    private final List<Sq<Sq<Cell>>> explosions;
    private final List<Sq<Cell>> blasts;
    
    
    GameState(int ticks, Board board, List<Player> players, List<Bomb> bombs, List<Sq<Sq<Cell>>> explosions, List<Sq<Cell>> blasts){
        this.ticks = ArgumentChecker.requireNonNegative(ticks);
        if (players.size()!=4) throw new IllegalArgumentException();
        this.players = new ArrayList<>(Objects.requireNonNull(players));
        this.board = Objects.requireNonNull(board);
        this.bombs = new ArrayList<>(Objects.requireNonNull(bombs));
        this.explosions = new ArrayList<>(Objects.requireNonNull(explosions));
        this.blasts = new ArrayList<>(Objects.requireNonNull(blasts));
        
    }
    
    GameState(Board board, List<Player> players){
        this(0,board,players,new ArrayList<Bomb>(),new ArrayList<Sq<Sq<Cell>>>(),new ArrayList<Sq<Cell>>());
    }
    
    public int ticks(){
        return ticks;
    }
    
    public boolean isGameOver(){
        int count=0;
        for (Player p:players){
            if (p.isAlive())
                ++count;
        }
        return (ticks>=Ticks.TOTAL_TICKS)||(count<2);
    }
    
    public double remainingTime(){
        return (Ticks.TOTAL_TICKS-ticks)/Ticks.TICKS_PER_SECOND;
    }
}
