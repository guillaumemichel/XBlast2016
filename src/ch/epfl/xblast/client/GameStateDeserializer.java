package ch.epfl.xblast.client;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.RunLengthEncoder;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.client.GameState.Player;

/**
 * A GameState deserializer
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 */

public final class GameStateDeserializer {
    
    private final static ImageCollection c1 = new ImageCollection("block");
    private final static ImageCollection c2 = new ImageCollection("explosion");
    private final static ImageCollection c3 = new ImageCollection("score");
    private final static ImageCollection c4 = new ImageCollection("player");
    
    private GameStateDeserializer(){};
    
    /**
     * Given a list of bytes from the server return a GameState with all the properties
     * 
     * @param l
     *          The list of Bytes that will be deserialized
     * @return
     *          A GameState with the properties given from the server
     */
    
    public static GameState deserializeGameState(List<Byte> l){
        int bs = l.get(0); //board size
        int es = l.get(bs+1); //explosion size
        
        if (l.size()!=bs+es+2+ch.epfl.xblast.server.GameState.PLAYER_NUMBER*4+1) throw new IllegalArgumentException();
        
        return new GameState(getPlayers(l.subList(bs+es+2, bs+es+2+4*ch.epfl.xblast.server.GameState.PLAYER_NUMBER)),
                boardImage(RunLengthEncoder.decode(l.subList(1, bs+1))),
                explosionImage(RunLengthEncoder.decode(l.subList(bs+2, bs+es+2))),
                scoreboardImage(l.subList(bs+es+2, bs+es+2+ch.epfl.xblast.server.GameState.PLAYER_NUMBER*4)),
                timeImage(l.get(l.size()-1)));
    }
    
    private static List<Player> getPlayers(List<Byte> l){
        List<Player> p=new ArrayList<>();
        for (int i =0;i<ch.epfl.xblast.server.GameState.PLAYER_NUMBER;++i)
            p.add(new GameState.Player(
                    PlayerID.values()[i],
                    l.get(4*i),
                    new SubCell(Byte.toUnsignedInt(l.get(4*i+1)), Byte.toUnsignedInt(l.get(4*i+2))),
                    c4.imageOrNull(l.get(4*i+3))));
        return p;
    }
    private static List<Image> boardImage(List<Byte> l){
        List<Image> boardImage = new ArrayList<>();
        Map<Cell,Image> map = new HashMap<>();
        for (int i=0;i<l.size();++i)
            map.put(Cell.SPIRAL_ORDER.get(i), c1.image(l.get(i)));
        for (Map.Entry<Cell, Image> b : map.entrySet())
            boardImage.add(b.getValue());
        return boardImage;
    }
    
    private static List<Image> explosionImage(List<Byte> l){
        List<Image> explosionImage=new ArrayList<>();
        for (Byte b : l){
                explosionImage.add(c2.imageOrNull(b));
        }
        return explosionImage;
    }
    
    private static List<Image> scoreboardImage(List<Byte> l){
        List<Image> scoreboard=new ArrayList<>();
        scoreboard.addAll(buildScoreboardForPlayer(1,l.get(0)>0));
        scoreboard.addAll(buildScoreboardForPlayer(2,l.get(4)>0));
        scoreboard.addAll(Collections.nCopies(8, c3.image(12)));
        scoreboard.addAll(buildScoreboardForPlayer(3,l.get(8)>0));
        scoreboard.addAll(buildScoreboardForPlayer(4,l.get(12)>0));
        return scoreboard;
    }
    
    private static List<Image> buildScoreboardForPlayer(int n, boolean alive){
        List<Image> l=new ArrayList<>();
        l.add(c3.image(2*n-2+(alive ? 0:1)));
        l.add(c3.image(10));
        l.add(c3.image(11));
        return l;
    }
    
    private static List<Image> timeImage(int n){
        List<Image> l=new ArrayList<>();
        l.addAll(Collections.nCopies(n, c3.image(21)));
        l.addAll(Collections.nCopies(60-n, c3.image(20)));
        return l;
    }
}
