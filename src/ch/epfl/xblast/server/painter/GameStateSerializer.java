package ch.epfl.xblast.server.painter;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.RunLengthEncoder;
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
     * Serializes a level (formed by a GameState and a BoardPainter) and sends the list of bytes to the clients
     * 
     * @param l
     *      The Level that will be serialized (formed with a GameState and a BoardPainter)
     * 
     * @return
     *      The serialized list of bytes that will be sent to the clients
     */
    public static List<Byte> serialize(Level l){
        List<Byte> list = new ArrayList<>();
        List<Byte> temp = new ArrayList<>();
        for (Cell c : Cell.SPIRAL_ORDER)
            temp.add(l.boardPainter().byteForCell(l.gameState().board(),c));
        
        list.addAll(RunLengthEncoder.encode(temp));
        temp.clear();
        for (Cell c : Cell.ROW_MAJOR_ORDER){
            if (l.gameState().board().blockAt(c).isFree()){
                if (l.gameState().blastedCells().contains(c))
                    temp.add(ExplosionPainter.byteForBlast(l.gameState().blastedCells().contains(c.neighbor(Direction.N)), l.gameState().blastedCells().contains(c.neighbor(Direction.E)), l.gameState().blastedCells().contains(c.neighbor(Direction.S)), l.gameState().blastedCells().contains(c.neighbor(Direction.W))));
                
                else if (l.gameState().bombedCells().containsKey(c))
                    temp.add(ExplosionPainter.byteForBomb(l.gameState().bombedCells().get(c)));
                
                else temp.add(ExplosionPainter.BYTE_FOR_EMPTY);
            }
            else temp.add(ExplosionPainter.BYTE_FOR_EMPTY);
        }
        list.addAll(RunLengthEncoder.encode(temp));
        for (Player player : l.gameState().players()){
            list.add((byte) player.lives());
            list.add((byte) player.position().x());
            list.add((byte) player.position().y());
            list.add(PlayerPainter.byteForPlayer(l.gameState().ticks(), player));
        }
        list.add((byte) (2*Math.ceil(l.gameState().remainingTime()/4)));
        return list;
    }
}
