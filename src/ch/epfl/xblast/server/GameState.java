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
        this(0,board,players,Arrays.asList(new Bomb(PlayerID.PLAYER_1, new Cell(9, 7), 15, 4)),new ArrayList<Sq<Sq<Cell>>>(),new ArrayList<Sq<Cell>>());
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
        List<Bomb> newBombs = new ArrayList<>();
        List<PlayerID> pid = new ArrayList<>(PERMUTATIONS.get(ticks%PERMUTATIONS.size()));
        List<Player> playersOrder = new ArrayList<>();
        
        //The next blasts
        List<Sq<Cell>> blasts1=nextBlasts(blasts, board, explosions);
        
        //The blasted cells (depending on the new blasts)
        Set<Cell> blastedCells1=new HashSet<>();
        for (Sq<Cell> sq : blasts1) {
                blastedCells1.add(sq.head());
        }
        
        //We arrange the list of player so that it has the same order as the current permutation
        for (Player p : players){
            for (PlayerID id : pid){
                if (p.id().equals(id)){
                    playersOrder.add(p);
                }
            }
        } 
        
        //Map that will contain the bonuses taken by the players, and associate the id of the player with the bonus that he has taken
        Map<PlayerID, Bonus> bonusMap = new HashMap<>();
        //Set that will contain the bonuses consumed by the players
        Set<Cell> consumedBonuses=new HashSet<>();
        
        //For each player, we determine if he is on a bonus. If so, then the bonus is consumed. We also fill the map of <PlayerID, Bonus> at the same time.
        for (Player p : playersOrder){
            if (board.blockAt(p.position().containingCell()).isBonus() && !consumedBonuses.contains(p.position().containingCell())){
                consumedBonuses.add(p.position().containingCell());
                bonusMap.put(p.id(), board.blockAt(p.position().containingCell()).associatedBonus());
            }
        }
        
        //We create the next board
        Board board1=nextBoard(board, consumedBonuses, blastedCells1);
        //We create the next explosions
        List<Sq<Sq<Cell>>> explosions1=nextExplosions(explosions);
        
        newBombs.addAll(newlyDroppedBombs(playersOrder,bombDropEvents,bombs));
        for (Bomb b : bombs){
            if (b.fuseLength()==1 || blastedCells1.contains(b.position())){//gérer les explosions par contact de particule
                explosions1.addAll(b.explosion());
            }else {
                newBombs.add(new Bomb(b.ownerId(),b.position(),b.fuseLengths().tail(),b.range()));
            }
        }
        
        //We can create a set containing the new bombed cells
        Set<Cell> bombedCells1=new HashSet<>();
        for (Bomb bomb : newBombs) {
            bombedCells1.add(bomb.position());
        }
        
        //We can now get the "next" players
        List<Player> players1=nextPlayers(players, bonusMap, bombedCells1, board1, blastedCells1, speedChangeEvents);
        
        return new GameState(ticks+1,board1,players1,newBombs,explosions1,blasts1);
    }
    
    private static List<Sq<Cell>> nextBlasts(List<Sq<Cell>> blasts0, Board board0, List<Sq<Sq<Cell>>> explosions0){
        List<Sq<Cell>> blasts1=new ArrayList<>();
        for (Sq<Cell> b : blasts0){
            if (!b.tail().isEmpty() && board0.blockAt(b.head()).isFree()){
                blasts1.add(b.tail());
            }
        }
        
        for (Sq<Sq<Cell>> sq : explosions0) {
            if(!sq.head().isEmpty())
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
                }else{
                    blocks.add(board0.blocksAt(c));
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
                blocks.add(destructibleWall);
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
            if(!explosionArm.head().tail().isEmpty()){
                particle=explosionArm.head().tail();
                
                while(!explosionArm.isEmpty()){
                    explosionArm=explosionArm.tail();
                    count++;
                }
                
                explosions1.add(Sq.repeat(count, particle));
            }
        }
        return explosions1;
    }

    private static List<Bomb> newlyDroppedBombs(List<Player> players0, Set<PlayerID> bombDropEvents, List<Bomb> bombs0){
        
        if (bombDropEvents.isEmpty())
            return new ArrayList<>();
        
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
    
    private static List<Player> nextPlayers1(List<Player> players0, Map<PlayerID, Bonus> playerBonuses,Set<Cell> bombedCells1, Board board1, Set<Cell> blastedCells1, Map<PlayerID, Optional<Direction>> speedChangeEvents){
        List<Player> players1 = new ArrayList<>();
        Sq<DirectedPosition> nextDirectedPos;
        Sq<LifeState> nextLifeState;
        SubCell central;
        int b = 0; //boolean bomb bonus
        int r = 0; //boolean range bonus
        
        
        for (Player p : players0){
            if (speedChangeEvents.containsKey(p.id()) && !speedChangeEvents.get(p.id()).equals(p.direction())){
                if (speedChangeEvents.get(p.id()).get().isParallelTo(p.direction())){
                    
                }
            }else {
                nextDirectedPos = p.directedPositions();
            }
            //players1.add(new Player(p.id(),nextLifeState,nextDirectedPos,p.maxBombs()+b,p.bombRange()+r));
        }
        
        
        /*for (Player p : players0){
            if (bombedCells1.contains(p.position().containingCell())){
                
            }
        }*/
        return players1;
    }
    
    private static List<Player> nextPlayers(List<Player> players0, Map<PlayerID, Bonus> playerBonuses, Set<Cell> bombedCells1, Board board1, Set<Cell> blastedCells1, Map<PlayerID, Optional<Direction>> speedChangeEvents){
        List<Player> players1=new ArrayList<>();
        Sq<DirectedPosition> nextDirectedPos;
        Sq<LifeState> nextLifeState;
        Player player1;
        Optional<Direction> chosenDir;
        DirectedPosition newDirectedPos;
        
        for (Player player : players0) {
            //First of all we compute the new sequence of directedPosition depending on the chosen direction:
            
            if(speedChangeEvents.containsKey(player.id())){
                SubCell nextCentral;
                if(player.position().isCentral()){
                    nextCentral=player.position();
                }else{
                    nextCentral=(player.directedPositions().findFirst(d -> d.position().isCentral())).position();
                }
                chosenDir=speedChangeEvents.get(player.id());
                if(chosenDir.isPresent()){//the player has chosen a direction
                    if(chosenDir.get().isParallelTo(player.direction())){//if the direction chosen is parallel to the one he is already going, he can immediately move backwards or forwards 
                        nextDirectedPos=DirectedPosition.moving(new DirectedPosition(player.position(), chosenDir.get()));
                    }else{//otherwise he first need to reach the first central subCell in his path, to finally turn where he wants to
                        if(!player.position().isCentral()){
                            nextDirectedPos=player.directedPositions().takeWhile(s -> !s.position().equals(nextCentral));
                            nextDirectedPos.concat(DirectedPosition.moving(new DirectedPosition(nextCentral, chosenDir.get())));
                        }else{
                            nextDirectedPos=DirectedPosition.moving(new DirectedPosition(nextCentral, chosenDir.get()));
                        }
                    }
                }else{//the player has chosen to stop (he first needs to reach the first central subCell in his path)
                    if(!player.position().isCentral()){
                        nextDirectedPos=player.directedPositions().takeWhile(s -> !s.position().equals(nextCentral));
                        nextDirectedPos.concat(DirectedPosition.stopped(player.directedPositions().findFirst(s -> s.position().isCentral())));
                    }else{
                        nextDirectedPos=DirectedPosition.stopped(player.directedPositions().findFirst(s -> s.position().isCentral()));
                    }
                }
                
            }else{//the player hasn't chosen anything, so he keeps going where he is going
                nextDirectedPos=player.directedPositions();
                //nextDirectedPos=DirectedPosition.moving(new DirectedPosition(player.position(), player.direction()));
            }
            
            newDirectedPos=nextDirectedPos.head();
            System.out.print(player.position().toString()+"-->");
            System.out.println(newDirectedPos.position().toString());
            
            //Here, we determine if the player can move. If so, the sequence of directedPosition is consumed
            boolean canMove=true;   
            if((!player.lifeState().canMove()) || 
                    (!board1.blockAt(newDirectedPos.position().containingCell().neighbor(newDirectedPos.direction())).canHostPlayer() && newDirectedPos.position().isCentral()) || 
                    (bombedCells1.contains(newDirectedPos.position().containingCell()) && (newDirectedPos.position().neighbor(newDirectedPos.direction())).distanceToCentral()==5)){
                canMove=false;
            }

            if(canMove){
                nextDirectedPos=nextDirectedPos.tail();
            }

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
