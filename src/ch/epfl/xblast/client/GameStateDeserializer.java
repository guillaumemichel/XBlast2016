package ch.epfl.xblast.client;

import java.awt.Image;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.RunLengthEncoder;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.client.GameState.Player;

public final class GameStateDeserializer {
    
    private final static ImageCollection c1 = new ImageCollection("block");
    private final static ImageCollection c2 = new ImageCollection("explosion");
    private final static ImageCollection c3 = new ImageCollection("score");
    private final static ImageCollection c4 = new ImageCollection("player");
    
    private GameStateDeserializer(){};
    
    public static GameState deserializeGameState(List<Byte> l) throws NoSuchElementException, URISyntaxException, IOException{
        int bs = l.get(0); //board size
        int es = l.get(bs+1); //explosion size
        
        if (l.size()!=bs+es+2+16+1) throw new IllegalArgumentException();
        
        return new GameState(getPlayers(l.subList(bs+es+2, bs+es+18)),boardImage(RunLengthEncoder.decode(l.subList(1, bs+1))),explosionImage(RunLengthEncoder.decode(l.subList(bs+2, bs+es+2))),scoreboardImage(l.subList(bs+es+2, bs+es+18)),timeImage(l.get(l.size()-1)));
    }
    
    private static List<Player> getPlayers(List<Byte> l) throws NoSuchElementException, URISyntaxException, IOException{
        List<Player> p=new ArrayList<>();
        p.add(new GameState.Player(PlayerID.PLAYER_1, l.get(0), new SubCell(Byte.toUnsignedInt(l.get(1)),Byte.toUnsignedInt(l.get(2))), c4.image(l.get(3))));
        p.add(new GameState.Player(PlayerID.PLAYER_2, l.get(4), new SubCell(Byte.toUnsignedInt(l.get(5)),Byte.toUnsignedInt(l.get(6))), c4.image(l.get(7))));
        p.add(new GameState.Player(PlayerID.PLAYER_3, l.get(8), new SubCell(Byte.toUnsignedInt(l.get(9)),Byte.toUnsignedInt(l.get(10))), c4.image(l.get(11))));
        p.add(new GameState.Player(PlayerID.PLAYER_4, l.get(12), new SubCell(Byte.toUnsignedInt(l.get(13)),Byte.toUnsignedInt(l.get(14))), c4.image(l.get(15))));
        return p;
    }
    private static List<Image> boardImage(List<Byte> l) throws NoSuchElementException, URISyntaxException, IOException{
        List<Image> boardImage = new ArrayList<>();
        Map<Integer,Image> map = new TreeMap<>();
        for (Byte b : l)
            map.put(Cell.SPIRAL_ORDER.get(b).hashCode(), c1.image(b));
        for (Map.Entry<Integer, Image> b : map.entrySet())
            boardImage.add(b.getValue());
        return boardImage;
    }
    
    private static List<Image> explosionImage(List<Byte> l) throws NoSuchElementException, URISyntaxException, IOException{
        List<Image> explosionImage=new ArrayList<>();
        for (Byte b : l){
            explosionImage.add(c2.image(b));
        }
        return explosionImage;
    }
    
    private static List<Image> scoreboardImage(List<Byte> l) throws NoSuchElementException, URISyntaxException, IOException{
        List<Image> scoreboard=new ArrayList<>();
        scoreboard.addAll(buildScoreboardForPlayer(1,l.get(0)>0));
        scoreboard.addAll(buildScoreboardForPlayer(2,l.get(4)>0));
        scoreboard.addAll(Collections.nCopies(8, c3.image(12)));
        scoreboard.addAll(buildScoreboardForPlayer(3,l.get(8)>0));
        scoreboard.addAll(buildScoreboardForPlayer(4,l.get(12)>0));
        return scoreboard;
    }
    
    private static List<Image> buildScoreboardForPlayer(int n, boolean alive) throws NoSuchElementException, URISyntaxException, IOException{
        List<Image> l=new ArrayList<>();
        l.add(c3.image(2*n-2+(alive ? 0:1)));
        l.add(c3.image(10));
        l.add(c3.image(11));
        return l;
    }
    
    private static List<Image> timeImage(int n) throws NoSuchElementException, URISyntaxException, IOException{
        List<Image> l=new ArrayList<>();
        l.addAll(Collections.nCopies(n, c3.image(21)));
        l.addAll(Collections.nCopies(60-n, c3.image(20)));
        return l;
    }
}
