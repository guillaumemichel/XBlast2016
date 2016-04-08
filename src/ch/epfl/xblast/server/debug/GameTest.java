package ch.epfl.xblast.server.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;

/**
 * A random game
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public class GameTest {

    public static void main(String[] args) throws InterruptedException{
        RandomEventGenerator randomEvents=new RandomEventGenerator(2016, 30, 100);
        //java -classpath jar/sq.jar:bin ch.epfl.xblast.server.debug.RandomGame
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        Block br = Block.BONUS_RANGE;
        Block bb = Block.BONUS_BOMB;
        Board board = Board.ofQuadrantNWBlocksWalled(
                Arrays.asList(
                        Arrays.asList(__,xx,__,__,__,xx,xx),
                        Arrays.asList(__,__,xx,__,xx,xx,xx),
                        Arrays.asList(xx,xx,__,__,__,xx,__),
                        Arrays.asList(__,__,xx,__,xx,__,__),
                        Arrays.asList(__,xx,__,xx,xx,__,xx),
                        Arrays.asList(__,__,xx,__,xx,xx,__)));
        /*Board board = Board.ofRows(
          Arrays.asList(
            Arrays.asList(XX, __, XX, XX, XX, XX, XX, __, __, bb, XX, XX, XX, XX, XX),
            Arrays.asList(XX, br, br, br, br, br, br, br, br, br, br, br, br, br, br),
            Arrays.asList(XX, __, XX, __, XX, xx, XX, __, __, __, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, __, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, __, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, __, __, __, __, __, __),
            Arrays.asList(__, __, __, __, __, __, __, __, __, __, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, __, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, __, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, __, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, __, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, bb, __, __, __, __, __),
            Arrays.asList(XX, __, xx, __, __, __, xx, __, __, bb, __, __, __, __, __)));*/

        
        List<Player> players = new ArrayList<>();
        players.add(new Player(PlayerID.PLAYER_1,3, new Cell(1, 1), 2, 3));
        players.add(new Player(PlayerID.PLAYER_2,2, new Cell(10, 6), 2, 3));
        players.add(new Player(PlayerID.PLAYER_3,2, new Cell(9, 7), 2, 3));
        players.add(new Player(PlayerID.PLAYER_4,2, new Cell(7, 2), 2, 3));

        GameState g = new GameState(board, players);
        GameStatePrinter.printGameState(g);

        int t=0;
        Map<PlayerID,Optional<Direction>> map1 = new HashMap<>();
        map1.put(PlayerID.PLAYER_1,Optional.of(Direction.E));
        map1.put(PlayerID.PLAYER_2,Optional.of(Direction.W));
        map1.put(PlayerID.PLAYER_3,Optional.of(Direction.N));
        map1.put(PlayerID.PLAYER_4, Optional.of(Direction.S));

        Map<PlayerID,Optional<Direction>> map2 = new HashMap<>();
        map2.put(PlayerID.PLAYER_1, Optional.of(Direction.S));
        map2.put(PlayerID.PLAYER_3, Optional.of(Direction.E));

        Set<PlayerID> set1 = new HashSet<>();
        set1.add(PlayerID.PLAYER_1);
        set1.add(PlayerID.PLAYER_2);
        set1.add(PlayerID.PLAYER_4);
        
        while (!g.isGameOver()){
            if (t%311==0)
                g = g.next(map1, new HashSet<>());
            else if (t%24==0)
                g = g.next(map2,set1);
            else
                g = g.next(new HashMap<>(), new HashSet<>());
            GameStatePrinter.printGameState(g);
            Thread.sleep(50);
            System.out.println();
            ++t;
        }
    }

}
