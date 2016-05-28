package ch.epfl.xblast.server.mapEditor;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.Player;

/**
 * A custom JPanel representing a grid of blocks
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
@SuppressWarnings("serial")
public final class GridOfBlocks extends JPanel{
    private List<BlockLabel> blocks = new ArrayList<>();
    
    /**
     * Constructs a grid of blocks
     */
    public GridOfBlocks(){
        this.setLayout(new GridLayout(Cell.ROWS, Cell.COLUMNS));
        
        for (int i = 0; i < Cell.COUNT; i++) {
            BlockLabel b =new BlockLabel(Block.FREE);
            blocks.add(b);
            
            //Put the spawns of each player to an initial position
            if(i == Level.POSITION_PLAYER_1.rowMajorIndex())
                b.addHostedPlayer(PlayerID.PLAYER_1);
            else if(i == Level.POSITION_PLAYER_2.rowMajorIndex())
                b.addHostedPlayer(PlayerID.PLAYER_2);
            else if(i == Level.POSITION_PLAYER_3.rowMajorIndex())
                b.addHostedPlayer(PlayerID.PLAYER_3);
            else if(i == Level.POSITION_PLAYER_4.rowMajorIndex())
                b.addHostedPlayer(PlayerID.PLAYER_4);
            
            this.add(blocks.get(blocks.size()-1));
        }
    }
    
    /**
     * Returns the list of block labels representing the grid
     * 
     * @return
     *      The list of block labels representing the grid
     */
    public List<BlockLabel> blocks(){
        return blocks;
    }
    
    /**
     * Returns a list of bytes containing the value (ordinal) of each block of the grid in row major order
     * 
     * @return
     *      A list of bytes containing the value (ordinal) of each block of the grid in row major order
     */
    public List<Byte> toListOfBytes(){
        return blocks.stream().map(BlockLabel::block).map(b -> (byte)b.ordinal()).collect(Collectors.toList());
    }
    
    /**
     * Returns the corresponding board of this grid of blocks
     * 
     * @return
     *      The corresponding board of this grid of blocks
     */
    private Board toBoard(){
        List<Sq<Block>> blocks = this.blocks.stream().map(BlockLabel::block).map(Sq::constant).collect(Collectors.toList());;
        return new Board(blocks);
    }
    
    /**
     * Returns the corresponding list of players of this grid of blocks
     * 
     * @return
     *      The corresponding list of players of this grid of blocks
     */
    private List<Player> createPlayers(){
        List<Player> players = new ArrayList<>();
        
        for(BlockLabel b : blocks){
            for(PlayerID id : b.hostedPlayers()){
                players.add(new Player(id, Level.NUMBER_OF_LIVES, Cell.ROW_MAJOR_ORDER.get(blocks.indexOf(b)), Level.MAX_BOMBS, Level.BOMB_RANGE));
                System.out.println(id+"---->"+Cell.ROW_MAJOR_ORDER.get(blocks.indexOf(b)).toString());
            }
        }
        return players;
    }
    
    /**
     * Returns the corresponding game state of this grid of blocks
     * 
     * @return
     *      The corresponding game state of this grid of blocks
     */
    public GameState toGameState(){
        return new GameState(toBoard(), createPlayers());
    }
    
    /**
     * Given a list of bytes that represents a grid of blocks, it sets all the blocks of this grid to the corresponding value in byte
     * 
     * @param l
     *      The list of bytes from which we sets the new blocks of this grid of blocks
     */
    public void loadGridfromListOfBytes(List<Byte> l){
        List<Block> blocks = l.stream().map(b -> Block.values()[b]).collect(Collectors.toList());
       
        Iterator<BlockLabel> it = this.blocks.iterator();
        Iterator<Block> it2 = blocks.iterator();
        while(it.hasNext() && it2.hasNext()){
            BlockLabel blockLabel = it.next();
            Block b = it2.next();
            if(blockLabel.hostedPlayers().isEmpty() || (!blockLabel.hostedPlayers().isEmpty() && b.canHostPlayer()))
                blockLabel.setBlock(b);
        }      
    }
}
