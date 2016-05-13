package ch.epfl.xblast;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

        int t=0;
        
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
        players.add(new Player(PlayerID.PLAYER_1,3, new Cell(13, 1), 2, 3));
        players.add(new Player(PlayerID.PLAYER_2,Sq.constant(new Player.LifeState(3, Player.LifeState.State.INVULNERABLE)),Player.DirectedPosition.moving(new DirectedPosition(new SubCell(13*16+8,24),Direction.S)),5,5));
        players.add(new Player(PlayerID.PLAYER_3,Sq.constant(new Player.LifeState(3, Player.LifeState.State.DYING)),Player.DirectedPosition.moving(new DirectedPosition(new SubCell(24,24),Direction.S)),5,5));
        players.add(new Player(PlayerID.PLAYER_4,Sq.constant(new Player.LifeState(1, Player.LifeState.State.DYING)),Player.DirectedPosition.moving(new DirectedPosition(new SubCell(24,24),Direction.S)),5,5));

        Set<PlayerID> set=new HashSet<>();
        set.add(PlayerID.PLAYER_4);
        
        Map<PlayerID, Optional<Direction>> map=new HashMap<>();
        map.put(PlayerID.PLAYER_1, Optional.of(Direction.W));

        GameState g = new GameState(board, players);

        while(t<32){
            g=g.next(map,set);
	        ++t;
	        System.out.println(t);
	        
	        if (t%4==1){
	        	assertEquals(7+4,PlayerPainter.byteForPlayer(t, g.players().get(0))%20);
	        	assertEquals(7,PlayerPainter.byteForPlayer(t, g.players().get(1))%20);
	        }
	        else if (t%4==3){
	        	assertEquals(5+5,PlayerPainter.byteForPlayer(t, g.players().get(0))%20);
	        	assertEquals(8,PlayerPainter.byteForPlayer(t, g.players().get(1))%20);
	        }
	        else{
	        	assertEquals(6+3,PlayerPainter.byteForPlayer(t, g.players().get(0))%20);
	        	assertEquals(6,PlayerPainter.byteForPlayer(t, g.players().get(1))%20);
	        }
	        
	        if (t%2==1) assertEquals(85, PlayerPainter.byteForPlayer(t, g.players().get(1)),5);
	        else assertEquals(25, PlayerPainter.byteForPlayer(t, g.players().get(1)),5);
	        
	        assertEquals(52, PlayerPainter.byteForPlayer(t, g.players().get(2)));
	        assertEquals(73, PlayerPainter.byteForPlayer(t, g.players().get(3)));

        }
    }
        
}
