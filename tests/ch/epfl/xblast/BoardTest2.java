package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;
import ch.epfl.xblast.server.debug.GameStatePrinter;

public class BoardTest2 {

    public static void main(String[] args) {
        List<String> test = Arrays.asList("Block.FREE","Block.DESTRUCTIBLE_WALL","Block.DESTRUCTIBLE_WALL");

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
        players.add(new Player(PlayerID.PLAYER_1, 5, new Cell(1, 1), 5, 5));
        players.add(new Player(PlayerID.PLAYER_2, 5, new Cell(13, 1), 5, 5));
        players.add(new Player(PlayerID.PLAYER_3, 5, new Cell(1, 11), 5, 5));
        players.add(new Player(PlayerID.PLAYER_4, 5, new Cell(13, 11), 5, 5));
        
        GameState g = new GameState(board, players);
        GameStatePrinter.printGameState(g);
    }

}
