package ch.epfl.xblast.server.debug;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.Bomb;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;

public final class GameStatePrinter {
    private GameStatePrinter() {}

    public static void printGameState(GameState s) {
        List<Player> players = s.alivePlayers();
        Set<Cell> blastedCells=s.blastedCells();
        Map<Cell, Bomb> bombedCells=s.bombedCells();
        Board board = s.board();

        for (int y = 0; y < Cell.ROWS; ++y) {
            xLoop: for (int x = 0; x < Cell.COLUMNS; ++x) {
                Cell c = new Cell(x, y);
                //Players display
                for (Player p: players) {
                    if (p.position().containingCell().equals(c)) {
                        System.out.print(stringForPlayer(p));
                        continue xLoop;
                    }
                }
                
                //Blasts display
                if(blastedCells.contains(c)){
                    if(board.blockAt(c)==Block.FREE){
                        System.out.print("\u001b[43m"+"\u001b[30m"+"**"+"\u001b[m");
                        continue xLoop;
                    }
                }
                
                //Bombs display
                if(bombedCells.containsKey(c)){
                    System.out.print(stringForBombs(bombedCells.get(c)));
                    continue xLoop;
                }
                
                Block b = board.blockAt(c);
                System.out.print(stringForBlock(b));
            }
            System.out.println();
        }
        for (Player player : players) {
            System.out.println("J"+(player.id().ordinal()+1)+": "+player.lives()+" vies "+"("+player.lifeState().state()+")\tportée "+player.bombRange()+" bombs "+player.maxBombs());
            System.out.println("Position: "+player.position()+"\n");
        }
        
        System.out.println("Temps restant :"+s.remainingTime()+" secondes"+"\n");
    }

    private static String stringForPlayer(Player p) {
        StringBuilder b = new StringBuilder();
        b.append("\u001b[46m"+"\u001b[30m"+(p.id().ordinal() + 1));
        switch (p.direction()) {
        case N: b.append('^'+"\u001b[m"); break;
        case E: b.append('>'+"\u001b[m"); break;
        case S: b.append('v'+"\u001b[m"); break;
        case W: b.append('<'+"\u001b[m"); break;
        }
        return b.toString();
    }

    private static String stringForBlock(Block b) {
        switch (b) {
        case FREE: return "\u001b[47;107m"+"  "+"\u001b[m";
        case INDESTRUCTIBLE_WALL: return "\u001b[40m"+"\u001b[30m"+"##"+"\u001b[m";
        case DESTRUCTIBLE_WALL: return "\u001b[40m"+"\u001b[37m"+"??"+"\u001b[m";
        case CRUMBLING_WALL: return "\u001b[37m"+"¿¿"+"\u001b[m";
        case BONUS_BOMB: return "\u001b[41m"+"\u001b[37m"+"+ò"+"\u001b[m";
        case BONUS_RANGE: return "\u001b[41m"+"\u001b[37m"+"+*"+"\u001b[m";
        default: throw new Error();
        }
    }
    
    private static String stringForBombs(Bomb b){
        if(b.fuseLength()<31){
            return "\u001b[44;6m"+"\u001b[37m"+"òò"+"\u001b[m";
        }else{
            return "\u001b[44;5m"+"\u001b[37m"+"òò"+"\u001b[m";
        }
    }
}
