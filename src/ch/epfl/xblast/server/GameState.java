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
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.server.Player.DirectedPosition;
import ch.epfl.xblast.server.Player.LifeState;
import ch.epfl.xblast.server.Player.LifeState.State;

/**
 * A game state
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class GameState {
    
    public static final int PLAYER_NUMBER = 4;
    private static final int DISTANCE_TO_BOMB = 6;
    
    private final int ticks;
    private final Board board;
    private final List<Player> players;
    private final List<Bomb> bombs;
    private final List<Sq<Sq<Cell>>> explosions;
    private final List<Sq<Cell>> blasts;
    
    
    private final static List<List<PlayerID>> PERMUTATIONS = Collections.unmodifiableList(Lists.permutations(Arrays.asList(PlayerID.values())));
    private final static Random RANDOM = new Random(2016);
    
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
        this.board = Objects.requireNonNull(board);
        if(players.size()!= PLAYER_NUMBER) 
            throw new IllegalArgumentException();
        Collections.sort(players, (p1, p2) -> Integer.compare(p1.id().ordinal(), p2.id().ordinal()));
        this.players = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(players)));
        this.bombs = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(bombs)));
        this.explosions = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(explosions)));
        this.blasts = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(blasts)));
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
        return (ticks>=Ticks.TOTAL_TICKS)||(alivePlayers().size()<2);
    }
    
    /**
     * Returns the remaining time before the end of this game (in seconds)
     * 
     * @return
     *      The remaining time before the end of this game (in seconds)
     */
    public double remainingTime(){
        return ((double)(Ticks.TOTAL_TICKS-ticks))/(double)(Ticks.TICKS_PER_SECOND);
    }
    
    /**
     * Returns the identity of the winner of this game if there is one
     * 
     * @return
     *      The identity of the winner of this game if there is one, or an empty optional value otherwise
     */
    public Optional<PlayerID> winner(){
        if (alivePlayers().size()==1 && isGameOver()){
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
    
    /**
     * Returns a map that associates the bombs of this game state with their positions
     * 
     * @return
     *      The map that associates the bombs of this game state with their positions
     */
    public Map<Cell, Bomb> bombedCells(){
        Map<Cell, Bomb> bombedCells= new HashMap<>();
        for (Bomb b : bombs) {
            bombedCells.put(b.position(), b);
        }
        return bombedCells;
    }
    
    /**
     * Returns a set of cells on which there is at least one blast
     * 
     * @return
     *      The set of cells on which there is at least one blast
     */
    public Set<Cell> blastedCells(){
        Set<Cell> blastedCells= new HashSet<>();
        for (Sq<Cell> cell : blasts) {
            blastedCells.add(cell.head());
        }
        return blastedCells;
    }
    
    /**
     * Returns the game state at the next tick, depending on the actual one and the given events
     * 
     * @param speedChangeEvents
     * 
     * @param bombDropEvents
     * 
     * @return
     *      The game state at the next tick, depending on the actual one and the given events
     */
    public GameState next(Map<PlayerID, Optional<Direction>> speedChangeEvents, Set<PlayerID> bombDropEvents){
        
        List<PlayerID> pid = new ArrayList<>(PERMUTATIONS.get(ticks%PERMUTATIONS.size()));
        List<Player> playersOrder = new ArrayList<>();
        //We arrange the list of player so that it has the same order as the current permutation        
        for (PlayerID id : pid){
            for (Player p : players){
                if (p.id().equals(id))
                    playersOrder.add(p);
            }
        }
        
        //The next blasts
        List<Sq<Cell>> blasts1=nextBlasts(blasts, board, explosions);
        
        //The blasted cells (depending on the new blasts)
        Set<Cell> blastedCells1=new HashSet<>();
        for (Sq<Cell> sq : blasts1) {
                blastedCells1.add(sq.head());
        }
        
        //Map that will contain the bonuses taken by the players, and associate the id of the player with the bonus that he has taken
        Map<PlayerID, Bonus> bonusMap = new HashMap<>();
        //Set that will contain the bonuses consumed by the players
        Set<Cell> consumedBonuses=new HashSet<>();
        
        //For each player, we determine if he is on a bonus. If so, then the bonus is consumed. We also fill the map of <PlayerID, Bonus> at the same time.
        for (Player p : playersOrder){
            if (p.position().isCentral() && board.blockAt(p.position().containingCell()).isBonus() && !consumedBonuses.contains(p.position().containingCell())){
                consumedBonuses.add(p.position().containingCell());
                bonusMap.put(p.id(), board.blockAt(p.position().containingCell()).associatedBonus());
            }
        }
        
        //We create the next board
        Board board1=nextBoard(board, consumedBonuses, blastedCells1);
        
        //We create the next explosions
        List<Sq<Sq<Cell>>> explosions1=nextExplosions(explosions);
        
        //List that will contain the new bombs
        List<Bomb> newBombs = new ArrayList<>();
        List<Bomb> newBombs1 = new ArrayList<>();
        
        //We add the newly dropped bombs, and we make the current bombs explode if their fuse length is zero
        newBombs.addAll(newlyDroppedBombs(playersOrder,bombDropEvents,bombs));
        newBombs.addAll(bombs);
        for (Bomb b :newBombs){
            if (b.fuseLengths().tail().isEmpty() || blastedCells1.contains(b.position())){
                explosions1.addAll(b.explosion());
            }else {
                newBombs1.add(new Bomb(b.ownerId(),b.position(),b.fuseLengths().tail(),b.range()));
            }
        }
        
        //We can create a set containing the new bombed cells
        Set<Cell> bombedCells1=new HashSet<>();
        for (Bomb bomb : newBombs1) {
            bombedCells1.add(bomb.position());
        }
        
        //We can now get the "next" players
        List<Player> players1=nextPlayers(players, bonusMap, bombedCells1, board1, blastedCells1, speedChangeEvents);
        
        return new GameState(ticks+1,board1,players1,newBombs1,explosions1,blasts1);
    }
    
    private static List<Sq<Cell>> nextBlasts(List<Sq<Cell>> blasts0, Board board0, List<Sq<Sq<Cell>>> explosions0){
        List<Sq<Cell>> blasts1=new ArrayList<>();
        
        //For every current blasts, we move them if the block they are at is free
        for (Sq<Cell> b : blasts0){
            if (!b.tail().isEmpty() && board0.blockAt(b.head()).isFree()){
                blasts1.add(b.tail());
            }
        }
        
        //For every current explosions, we add the blasts created by the explosions
        for (Sq<Sq<Cell>> sq : explosions0) {
            blasts1.add(sq.head());
        }
        return blasts1;
    }
    
    private static Board nextBoard(Board board0, Set<Cell> consumedBonuses, Set<Cell> blastedCells1){
        List<Sq<Block>> blocks=new ArrayList<>();
        
        //We iterate over all cells
        for (Cell c : Cell.ROW_MAJOR_ORDER) {
            
            //if there is a consumed bonus at this place, we set the new block as free block
            if(consumedBonuses.contains(c)){
                blocks.add(Sq.constant(Block.FREE));
            }else if(board0.blockAt(c).isBonus() && blastedCells1.contains(c)){/*if the current block is a bonus and there is
                also a blast at this cell, then the bonus has to disappear after a certain amount of time*/
                Sq<Block> boardBlocks=board0.blocksAt(c);
                boardBlocks=boardBlocks.tail().limit(Ticks.BONUS_DISAPPEARING_TICKS).concat(Sq.constant(Block.FREE));
                blocks.add(boardBlocks);
            }else if(board0.blockAt(c)==Block.DESTRUCTIBLE_WALL && blastedCells1.contains(c)){/*if the current block is destructible
            and there is also a blast at this cell, then it has to crumble a while, and finally it has to change into another block*/
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
                destructibleWall=destructibleWall.concat(Sq.constant(newBlock));
                blocks.add(destructibleWall);
            }else{//else we just consume the sequence of blocks to make it "evolve"
                blocks.add(board0.blocksAt(c).tail());
            }
        }
        return new Board(blocks);
    }
    
    private static List<Sq<Sq<Cell>>> nextExplosions(List<Sq<Sq<Cell>>> explosions0){
        List<Sq<Sq<Cell>>> explosions1=new ArrayList<>();
        
        //We iterate over the current explosions and make them "age", by consuming the sequence
        for (Sq<Sq<Cell>> sq : explosions0) {
            if(!sq.tail().isEmpty())
                explosions1.add(sq.tail());     
        }
        return explosions1;
    }

    private static List<Bomb> newlyDroppedBombs(List<Player> players0, Set<PlayerID> bombDropEvents, List<Bomb> bombs0){
        
        if (bombDropEvents.isEmpty()) //if there is no new bomb in bombDropEvent return an empty array.
            return new ArrayList<>();
        
        boolean canBomb;
        int count;
        List<Bomb> bombs1 = new ArrayList<>(bombs0);//we create a list that will contain all the bombs on the board
        for (Player p : players0){//we iterate on players in the "random" order
            if (bombDropEvents.contains(p.id()) && p.isAlive()){
                canBomb=true;
                count=0;
                for (Bomb b : bombs1){
                    if (b.ownerId().equals(p.id()))//we count the number of bombs of each player to determine whether it's possible to drop one
                        ++count;
                    
                    if (b.position().equals(p.position().containingCell()))//if there is already a bomb on this cell
                        canBomb=false;
                }
                if (count>=p.maxBombs())//if the max number of bombs is already reached
                    canBomb=false;
                
                if (canBomb){
                    bombs1.add(p.newBomb());
                }
            }
        }
        bombs1.removeAll(bombs0);//remove the bombs that were already present
        
        return bombs1;
    }
    
    private static List<Player> nextPlayers(List<Player> players0, Map<PlayerID, Bonus> playerBonuses,
            Set<Cell> bombedCells1, Board board1, Set<Cell> blastedCells1, Map<PlayerID, Optional<Direction>> speedChangeEvents){
        List<Player> players1=new ArrayList<>();
        Sq<DirectedPosition> nextDirectedPos;
        Sq<LifeState> nextLifeState;
        Player player1;
        SubCell nextCentral;
        Optional<Direction> chosenDir;
        DirectedPosition newDirectedPos, nextCentralDirectedPos;
        
        for (Player player : players0) {
            //First of all we compute the new sequence of directedPosition depending on the chosen direction:
            
            if(speedChangeEvents.containsKey(player.id())){//if the player has made a speed or direction change
                nextCentral=(player.directedPositions().findFirst(d -> d.position().isCentral())).position();
                chosenDir=speedChangeEvents.get(player.id());
                if(chosenDir.isPresent()){//the player has chosen a direction
                    if(chosenDir.get().isParallelTo(player.direction())){/*if the direction chosen is parallel to the one
                        he is already going, he can immediately move backwards or forwards*/
                        nextDirectedPos=DirectedPosition.moving(new DirectedPosition(player.position(), chosenDir.get()));
                    }else{//otherwise he first need to reach the first central subCell in his path, to finally turn where he wants to
                        nextDirectedPos=player.directedPositions().takeWhile(s -> !s.position().isCentral());
                        nextDirectedPos=nextDirectedPos.concat(DirectedPosition.moving(new DirectedPosition(nextCentral, chosenDir.get())));
                    }
                }else{//the player has chosen to stop (he first needs to reach the first central subCell in his path)
                    nextCentralDirectedPos=player.directedPositions().findFirst(d -> d.position().isCentral());
                    
                    nextDirectedPos=player.directedPositions().takeWhile(s -> !s.position().isCentral());
                    nextDirectedPos=nextDirectedPos.concat(DirectedPosition.stopped(
                            new DirectedPosition(nextCentralDirectedPos.position(),nextCentralDirectedPos.direction())));
                }
                
            }else{//the player hasn't chosen anything, so he keeps going where he is going
                nextDirectedPos=player.directedPositions();
            }
            
            newDirectedPos=nextDirectedPos.head();
            
            //Here, we determine if the player can move. If so, the sequence of directedPosition is consumed
            if(player.lifeState().canMove() &&
                    
                    (!player.position().isCentral() || (player.position().isCentral() && 
                            board1.blockAt(player.position().containingCell().neighbor(newDirectedPos.direction())).canHostPlayer())) &&
                    
                    ((player.position().distanceToCentral()!=DISTANCE_TO_BOMB) || !(player.position().distanceToCentral()==DISTANCE_TO_BOMB && 
                            bombedCells1.contains(player.position().containingCell()) && 
                            newDirectedPos.position().neighbor(newDirectedPos.direction()).distanceToCentral()==DISTANCE_TO_BOMB-1)))
                       
                nextDirectedPos=nextDirectedPos.tail();
                    
            //We create the new lifeState sequence for the next state
            if(blastedCells1.contains(nextDirectedPos.head().position().containingCell()) && player.lifeState().state()==State.VULNERABLE){
                nextLifeState=player.statesForNextLife();
            }else{
                nextLifeState=player.lifeStates().tail();
            }
            
            //We are now able to create the evolved player
            player1=new Player(player.id(), nextLifeState, nextDirectedPos, player.maxBombs(), player.bombRange());
            
            //We apply the bonus if the player has taken one, and we add the evolved player in the list of evolved players
            if(playerBonuses.containsKey(player.id())){
                players1.add(playerBonuses.get(player.id()).applyTo(player1));
            }else{
                players1.add(player1);
            }
        }
        return players1;
    }
}
