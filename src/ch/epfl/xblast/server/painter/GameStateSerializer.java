package ch.epfl.xblast.server.painter;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.RunLengthEncoder;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;

/**
 * A GameState serializer
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class GameStateSerializer {
    
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

        for (Cell c : Cell.SPIRAL_ORDER)
            temp.add(b.byteForCell(g.board(),c));
        
        list.add((byte)RunLengthEncoder.encode(temp).size());
        list.addAll(RunLengthEncoder.encode(temp));
        temp.clear();
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
        list.add((byte)RunLengthEncoder.encode(temp).size());
        list.addAll(RunLengthEncoder.encode(temp));
        
        for (Player player : g.players()){
            list.add((byte) player.lives());
            list.add((byte) player.position().x());
            list.add((byte) player.position().y());
            list.add(PlayerPainter.byteForPlayer(g.ticks(), player));
        }
        list.add((byte) Math.ceil(g.remainingTime()/2));
        return list;
    }
}
