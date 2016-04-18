package ch.epfl.xblast;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;
import ch.epfl.xblast.server.Player.DirectedPosition;
import ch.epfl.xblast.server.painter.PlayerPainter;

public class PlayerPainterTest {
    
    @Test
    public void byteForPlayerIsOk(){
        int tick=0;
        
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
        
        Player p01 = new Player(PlayerID.PLAYER_1,Sq.constant(new Player.LifeState(3, Player.LifeState.State.INVULNERABLE)),Player.DirectedPosition.moving(new DirectedPosition(new SubCell(24,24),Direction.E)),5,5);
        ///Player p0 = new Player(PlayerID.PLAYER_1,5,new Cell(1,1),5,5);
        Player p1 = new Player(PlayerID.PLAYER_2,1,new Cell(1,2),5,5);
        Player p2 = new Player(PlayerID.PLAYER_3,2,new Cell(6,5),5,5);
        Player p3 = new Player(PlayerID.PLAYER_4,3,new Cell(1,6),5,5);
        List<Player> list = new ArrayList<>(Arrays.asList(p01,p1,p2,p3));
        
        GameState g = new GameState(board,list);
        
        assertEquals(3, PlayerPainter.byteForPlayer(tick, p01));
        assertEquals(PlayerPainter.byteForPlayer(tick, p1), 26);
        g.next(new HashMap<PlayerID, Optional<Direction>>(),new HashSet<PlayerID>());tick+=1;
        assertEquals(84, PlayerPainter.byteForPlayer(tick, p01));
        g.next(new HashMap<PlayerID, Optional<Direction>>(),new HashSet<PlayerID>());tick+=1;
        assertEquals(6, PlayerPainter.byteForPlayer(tick, p01));


    }
}
