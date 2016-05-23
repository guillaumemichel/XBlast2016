package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.RunLengthEncoder;
import ch.epfl.xblast.Time;
import ch.epfl.xblast.server.painter.BoardPainter;
import ch.epfl.xblast.server.painter.ExplosionPainter;
import ch.epfl.xblast.server.painter.PlayerPainter;

/**
 * A GameState serializer
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class GameStateSerializer {
    /**
     * number of rectangles of time in the scoreboard
     */
    public  final static int TIME_UNIT_NUMBER=60;
    /**
     * Number of seconds per unit of time on the scoreboard
     */
    private final static int SECOND_PER_TIME_UNIT = Time.GAME_DURATION*Time.S_PER_MIN/TIME_UNIT_NUMBER;
    
    private GameStateSerializer(){};
    
    
    /**
     * Serializes a game state (represented by a board painter b) and returns a list of bytes that represents the game state
     * 
     * @param b
     *      The board painter used with the game state
     *      
     * @param g
     *      The game state we want to serialize
     *      
     * @return
     *      A list of bytes that represent the serialized version of the game state
     */
    public static List<Byte> serialize(BoardPainter b, GameState g){
        List<Byte> list = new ArrayList<>();
        List<Byte> temp = new ArrayList<>();
        
        //first we add the board in the list
        for (Cell c : Cell.SPIRAL_ORDER)
            temp.add(b.byteForCell(g.board(),c));
        
        list.add((byte)RunLengthEncoder.encode(temp).size());//size of the board encoded
        list.addAll(RunLengthEncoder.encode(temp));
        temp.clear();
        
        //then we add the explosions
        for (Cell c : Cell.ROW_MAJOR_ORDER){
            if (g.board().blockAt(c).isFree()){
                if (g.blastedCells().contains(c))
                    temp.add(ExplosionPainter.byteForBlast(g.blastedCells().contains(c.neighbor(Direction.N)), g.blastedCells().contains(c.neighbor(Direction.E)), g.blastedCells().contains(c.neighbor(Direction.S)), g.blastedCells().contains(c.neighbor(Direction.W))));
                
                else if (g.bombedCells().containsKey(c))
                    temp.add(ExplosionPainter.byteForBomb(g.bombedCells().get(c)));
                
                else temp.add(ExplosionPainter.BYTE_FOR_EMPTY);
            }
            else temp.add(ExplosionPainter.BYTE_FOR_EMPTY);
        }
        list.add((byte)RunLengthEncoder.encode(temp).size());//size of the explosions encoded
        list.addAll(RunLengthEncoder.encode(temp));
        
        //we add the players
        for (Player player : g.players()){
            list.add((byte) player.lives());
            list.add((byte) player.position().x());
            list.add((byte) player.position().y());
            list.add(PlayerPainter.byteForPlayer(g.ticks(), player));
        }
        //we finally add the number of "time units" that will be display on the scoreboard
        list.add((byte) Math.ceil(g.remainingTime()/SECOND_PER_TIME_UNIT));
        return list;
    }
}
