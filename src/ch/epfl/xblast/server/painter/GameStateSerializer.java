package ch.epfl.xblast.server.painter;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;

public final class GameStateSerializer {
    
    private GameStateSerializer(){};
    
    public static List<Byte> serialize(BoardPainter p, GameState g){
        List<Byte> list = new ArrayList<>();
        for (Cell c : Cell.SPIRAL_ORDER){
            list.add(p.byteForCell(g.board(),c));
        }
        //compress
        //sérialisation des bombes et explosions
        for (Player player : g.players()){
            list.add((byte) player.lives());
            list.add((byte) player.position().x());
            list.add((byte) player.position().y());
            list.add(PlayerPainter.byteForPlayer(g.ticks(), player));
        }
        list.add((byte) (2*Math.ceil(g.remainingTime()/4)));
        return null;
    }
}
