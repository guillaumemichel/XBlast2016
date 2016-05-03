package ch.epfl.xblast;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JFrame;

import ch.epfl.xblast.client.GameStateDeserializer;
import ch.epfl.xblast.client.KeyboardEventHandler;
import ch.epfl.xblast.client.XBlastComponent;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;
import ch.epfl.xblast.server.debug.RandomEventGenerator;
import ch.epfl.xblast.server.painter.GameStateSerializer;
import ch.epfl.xblast.server.painter.Level;

public class GUI {

    public static void main(String[] args) {
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
        frame.setSize(960, 688);
        frame.setVisible(true);
        
        Map<Integer, PlayerAction> kb = new HashMap<>();
        kb.put(KeyEvent.VK_UP, PlayerAction.MOVE_N);
        kb.put(KeyEvent.VK_DOWN, PlayerAction.MOVE_S);
        kb.put(KeyEvent.VK_LEFT, PlayerAction.MOVE_W);
        kb.put(KeyEvent.VK_RIGHT, PlayerAction.MOVE_E);
        kb.put(KeyEvent.VK_SPACE, PlayerAction.DROP_BOMB);
        kb.put(KeyEvent.VK_SHIFT, PlayerAction.STOP);
        Consumer<PlayerAction> c = System.out::println;
        component.addKeyListener(new KeyboardEventHandler(kb, c));
        component.requestFocusInWindow();
        
        while(! g.isGameOver()){
            g=g.next(randomEvents.randomSpeedChangeEvents(), randomEvents.randomBombDropEvents());
            
            component.setGameState(GameStateDeserializer.deserializeGameState(GameStateSerializer.serialize(new Level(Level.DEFAULT_LEVEL.boardPainter(), g))), PlayerID.PLAYER_1);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
