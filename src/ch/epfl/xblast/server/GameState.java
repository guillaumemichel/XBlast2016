package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    
    
    private final static List<List<PlayerID>> PERMUTATIONS=Collections.unmodifiableList(Lists.permutations(Arrays.asList(PlayerID.values())));
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
     *      <b>True</b> if this game state corresponds to a game that is over, <b>false</b> otherwise
     */
    public boolean isGameOver(){
        return (ticks>Ticks.TOTAL_TICKS)||(alivePlayers().size()<2);
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
        List<Bomb> newBombs = new ArrayList<>();
        List<PlayerID> pid = new ArrayList<>(PERMUTATIONS.get(ticks%PERMUTATIONS.size()));
        List<Player> playersOrder = new ArrayList<>();
        
        List<Sq<Cell>> blasts1=nextBlasts(blasts, board, explosions);
        
        Set<Cell> blastedCells1=new HashSet<>();
        Set<Cell> consumedBonuses=new HashSet<>();
        Map<PlayerID,Bonus> bonusMap = new HashMap<>();
        for (Player p : players){
            for (PlayerID id : pid){
                if (p.id().equals(id)){
                    playersOrder.add(p);
                }
            }
        }
        for (Sq<Cell> sq : blasts1) {
            blastedCells1.add(sq.head());
        }
        for (Player p : playersOrder){
            if (board.blockAt(p.position().containingCell()).isBonus() && !consumedBonuses.contains(p.position().containingCell())){
                consumedBonuses.add(p.position().containingCell());
                bonusMap.put(p.id(), board.blockAt(p.position().containingCell()).associatedBonus());
            }
        }
        
        Board board1=nextBoard(board, consumedBonuses, blastedCells1);
        List<Sq<Sq<Cell>>> explosions1=nextExplosions(explosions);
        
        bombs.addAll(newlyDroppedBombs(playersOrder,bombDropEvents,bombs));
        for (Bomb b : bombs){
            if (b.fuseLengths().isEmpty()){//gérer les explosions par contact de particule
                explosions1.addAll(b.explosion());
            }else {
                newBombs.add(new Bomb(b.ownerId(),b.position(),b.fuseLengths().tail(),b.range()));
            }
        }
        
        List<Player> players1=nextPlayers();
        
        //traitement des explosions, retirer les bombes explosées du tableau, appeler la méthode newlyDroppedBomb()
        //la liste de players en paramètre est PERMUTATIONS.get(ticks%PERMUTATIONS.size())
        return new GameState(ticks+1,board1,players1,newBombs,explosions1,blasts1);
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
    
    private static Board nextBoard(Board board0, Set<Cell> consumedBonuses, Set<Cell> blastedCells1){
        List<Sq<Block>> blocks=new ArrayList<>();
        boolean alreadyDisappearing;
        
        for (Cell c : Cell.ROW_MAJOR_ORDER) {
            if(consumedBonuses.contains(c)){
                blocks.add(Sq.constant(Block.FREE));
            }else if(board0.blockAt(c).isBonus() && blastedCells1.contains(c)){
                alreadyDisappearing=false;
                Sq<Block> boardBlocks=board0.blocksAt(c);
                
                //We check if the bonus block is already disappearing (we look in the sequence if it will become a free block)
                for (int i = 0; i < Ticks.BONUS_DISAPPEARING_TICKS; ++i) {
                    if(boardBlocks.head()==Block.FREE){
                        alreadyDisappearing=true;
                        break;
                    }
                    boardBlocks=boardBlocks.tail();
                }
                
                //Only if the bonus block is not already disappearing, we add the bonus_disappearing time before it becomes a free block
                if(!alreadyDisappearing){
                    Sq<Block> bonusDisappearing=Sq.repeat(Ticks.BONUS_DISAPPEARING_TICKS, board0.blockAt(c));
                    bonusDisappearing.concat(Sq.constant(Block.FREE));
                    blocks.add(bonusDisappearing);
                }
            }else if(board0.blockAt(c)==Block.DESTRUCTIBLE_WALL && blastedCells1.contains(c)){
                Sq<Block> destructibleWall=Sq.repeat(Ticks.WALL_CRUMBLING_TICKS, Block.CRUMBLING_WALL);
                int randomBlock=RANDOM.nextInt(3);
                Block newBlock;
                
                switch (randomBlock){
                    case 0:
                        newBlock=Block.BONUS_BOMB;
                        break;
                    case 1:
                        newBlock=Block.BONUS_RANGE;
                        break;
                    default:
                        newBlock=Block.FREE;
                        break;
                }
                destructibleWall.concat(Sq.constant(newBlock));
            }else{
                blocks.add(board0.blocksAt(c));
            }
        }
        return new Board(blocks);
    }
    
    private static List<Sq<Sq<Cell>>> nextExplosions(List<Sq<Sq<Cell>>> explosions0){
        List<Sq<Sq<Cell>>> explosions1=new ArrayList<>();
        Sq<Cell> particle;
        int count;
        
        for (Sq<Sq<Cell>> explosionArm : explosions0) {
            count=0;
            particle=explosionArm.head().tail();
            
            while(!explosionArm.isEmpty()){
                explosionArm=explosionArm.tail();
                count++;
            }
            
            explosions1.add(Sq.constant(particle).limit(count));
        }
        return explosions1;
    }

    private static List<Bomb> newlyDroppedBombs(List<Player> players0, Set<PlayerID> bombDropEvents, List<Bomb> bombs0){
        
        if (bombDropEvents.isEmpty())
            return bombs0;
        
        boolean canBomb;
        Player p;
        int count;
        List<Bomb> bombs1 = new ArrayList<>(bombs0);
        for (int i=0;i<players0.size();++i){
            p=players0.get(i);
            if (bombDropEvents.contains(p.id()) && p.isAlive()){
                canBomb=true;
                count=0;
                for (Bomb b : bombs1){
                    if (b.ownerId().equals(p.id()))
                        ++count;
                    if (b.position().equals(p.position().containingCell()))
                        canBomb=false;
                }
                if (count>=p.maxBombs())
                    canBomb=false;
                if (canBomb)
                    bombs1.add(p.newBomb());
            }
        }
        bombs1.removeAll(bombs0);
        return bombs1;
    }
    
    private static List<Player> nextPlayers(List<Player> players0, Map<PlayerID, Bonus> playerBonuses, Set<Cell> bombedCells1, Board board1, Set<Cell> blastedCells1, Map<PlayerID, Optional<Direction>> speedChangeEvents){
        return null;
    }
}
