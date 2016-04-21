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
     * Serializes the GameState from a BoardPainter and sends the list of bytes to the clients
     * 
     * @param p
     *      The Boardpainter that will be used to serialize the game
     *      
     * @param g
     *      The GameState that will be serialized
     * 
     * @return
     *      The serialized list of bytes that will be sent to the clients
     */
    
    public static List<Byte> serialize(BoardPainter p, GameState g){
        List<Byte> list = new ArrayList<>();
        List<Byte> temp = new ArrayList<>();
        for (Cell c : Cell.SPIRAL_ORDER){
            temp.add(p.byteForCell(g.board(),c));
        }
        list.addAll(RunLengthEncoder.encode(temp));
        temp = new ArrayList<>();
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
        list.addAll(RunLengthEncoder.encode(temp));
        for (Player player : g.players()){
            list.add((byte) player.lives());
            list.add((byte) player.position().x());
            list.add((byte) player.position().y());
            list.add(PlayerPainter.byteForPlayer(g.ticks(), player));
        }
        list.add((byte) (2*Math.ceil(g.remainingTime()/4)));
        return list;
    }
}
