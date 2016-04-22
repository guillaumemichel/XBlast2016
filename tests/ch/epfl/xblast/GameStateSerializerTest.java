package ch.epfl.xblast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;
import ch.epfl.xblast.server.debug.RandomEventGenerator;
import ch.epfl.xblast.server.painter.BlockImage;
import ch.epfl.xblast.server.painter.BoardPainter;
import ch.epfl.xblast.server.painter.GameStateSerializer;
import ch.epfl.xblast.server.painter.Level;

public class GameStateSerializerTest {
    
    public static List<Byte> getList(){
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        Board board = Board.ofQuadrantNWBlocksWalled(
          Arrays.asList(
            Arrays.asList(__, __, __, __, __, xx, __),
            Arrays.asList(__, XX, xx, XX, xx, XX, xx),
            Arrays.asList(__, xx, __, __, __, xx, __),
            Arrays.asList(xx, XX, __, XX, XX, XX, XX),
            Arrays.asList(__, xx, __, xx, __, __, __),
            Arrays.asList(xx, XX, xx, XX, xx, XX, __)));
        
        List<Player> players = new ArrayList<>();
        players.add(new Player(PlayerID.PLAYER_1,3, new Cell(1, 1), 2, 3));
        players.add(new Player(PlayerID.PLAYER_2, 3, new Cell(13, 1), 2, 3));
        players.add(new Player(PlayerID.PLAYER_3, 3, new Cell(13, 11), 2, 3));
        players.add(new Player(PlayerID.PLAYER_4, 3, new Cell(1, 11), 2, 3));

        GameState g = new GameState(board, players);
        
        Map<Block, BlockImage> palette=new HashMap<>();
        palette.put(Block.FREE, BlockImage.IRON_FLOOR);
        for(int i=1;i<Block.values().length;i++)
            palette.put(Block.values()[i], BlockImage.values()[i+1]);
        
        BoardPainter p= new BoardPainter(palette, BlockImage.IRON_FLOOR_S);
        
        Level l = new Level(p,g);
        
        return GameStateSerializer.serialize(l);
    }
    
    public static List<Integer> getExp(){
        return Arrays.asList(121, -50, 2, 1, -2, 0, 3, 1, 3, 1, -2, 0, 1, 1, 3, 1, 3,
                1, 3, 1, 1, -2, 0, 1, 3, 1, 3, -2, 0, -1, 1, 3, 1, 3, 1,
                3, 1, 1, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3,
                2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2,
                3, 1, 0, 0, 3, 1, 3, 1, 0, 0, 1, 1, 3, 1, 1, 0, 0, 1, 3,
                1, 3, 0, 0, -1, 1, 3, 1, 1, -5, 2, 3, 2, 3, -5, 2, 3, 2,
                3, 1, -2, 0, 3, -2, 0, 1, 3, 2, 1, 2,

                4, -128, 16, -63, 16,

                3, 24, 24, 6,
                3, -40, 24, 26,
                3, -40, -72, 46,
                3, 24, -72, 66,

                60);
    }
    
    @Test
    public void timeIsOk(){
        assertTrue(((int)getList().get(getList().size()-1))==(getExp().get(getExp().size()-1)));
    }

}
