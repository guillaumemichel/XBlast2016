package ch.epfl.xblast.server.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.JFrame;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.client.GameStateDeserializer;
import ch.epfl.xblast.client.XBlastComponent;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;
import ch.epfl.xblast.server.painter.GameStateSerializer;
import ch.epfl.xblast.server.painter.Level;

/**
 * A random game
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public class RandomGame {

    public static void main(String[] args) throws InterruptedException{
        RandomEventGenerator randomEvents=new RandomEventGenerator(2016, 30, 100);
        //java -classpath jar/sq.jar:bin ch.epfl.xblast.server.debug.RandomGame
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
        
        Set<PlayerID> set=new HashSet<>();
        set.add(PlayerID.PLAYER_4);
        
        Map<PlayerID, Optional<Direction>> map=new HashMap<>();
        map.put(PlayerID.PLAYER_1, Optional.of(Direction.E));

        GameState g = new GameState(board, players);
        
        //Version in terminal
        
        /*GameStatePrinter.printGameState(g);

        while(! g.isGameOver()){
            g=g.next(randomEvents.randomSpeedChangeEvents(), randomEvents.randomBombDropEvents());
            GameStatePrinter.printGameState(g);
            Thread.sleep(50);
            //System.out.print("\u001b[2J");
            System.out.println();
        }*/
        
        //Version in gui
        ch.epfl.xblast.client.GameState gClient = GameStateDeserializer.deserializeGameState(GameStateSerializer.serialize(Level.DEFAULT_LEVEL));
        XBlastComponent component = new XBlastComponent();
        component.setGameState(gClient, PlayerID.PLAYER_1);
        
        JFrame frame = new JFrame("XBlast 2016");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(component);
        frame.setSize(970, 690);
        frame.setVisible(true);
        
        /*while(! g.isGameOver()){
            g=g.next(randomEvents.randomSpeedChangeEvents(), randomEvents.randomBombDropEvents());
            
            //component.setGameState(gClient, id);
            //GameStatePrinter.printGameState(g);
            Thread.sleep(50);
            //System.out.print("\u001b[2J");
            System.out.println();
        }*/
        
    }

}
