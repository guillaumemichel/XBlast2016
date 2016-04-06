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
        Board board = Board.ofQuadrantNWBlocksWalled(
          Arrays.asList(
            Arrays.asList(br, br, br, br, br, br, br),
            Arrays.asList(__, XX, xx, XX, xx, XX, xx),
            Arrays.asList(__, xx, __, __, __, xx, __),
            Arrays.asList(__, XX, __, XX, XX, XX, XX),
            Arrays.asList(__, xx, __, xx, __, __, __),
            Arrays.asList(__, __, __, __, __, __, __)));
        
        List<Player> players = new ArrayList<>();
        players.add(new Player(PlayerID.PLAYER_1,3, new Cell(1, 1), 2, 1));
        players.add(new Player(PlayerID.PLAYER_2,1, new Cell(10, 10), 2, 1));
        players.add(new Player(PlayerID.PLAYER_3,0, new Cell(13, 13), 2, 1));
        players.add(new Player(PlayerID.PLAYER_4,0, new Cell(13, 12), 2, 1));

        GameState g = new GameState(board, players);
        GameStatePrinter.printGameState(g);

        int t=0;
        Map<PlayerID,Optional<Direction>> map1 = new HashMap<>();
        map1.put(PlayerID.PLAYER_1,Optional.of(Direction.E));
        while (t<1000){
            if (t==0)
                g = g.next(map1, new HashSet<>());
            else
                g = g.next(new HashMap<>(), new HashSet<>());
            GameStatePrinter.printGameState(g);
            Thread.sleep(50);
            System.out.println();
            ++t;
        }
    }

}
