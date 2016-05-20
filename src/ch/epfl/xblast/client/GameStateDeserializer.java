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
import ch.epfl.xblast.server.GameStateSerializer;

/**
 * A GameState deserializer
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 */

public final class GameStateDeserializer {
    private final static int BYTE_PER_PLAYER=4;
    private final static int SIZES=2;//the number of bytes taken by the size of the board and the explosion
    private final static int IMAGE_PER_PLAYER_SCOREBOARD=2;//the number of images per player on the scoreboard
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
                
        if (l.size()!=bs+es+SIZES+ch.epfl.xblast.server.GameState.PLAYER_NUMBER*BYTE_PER_PLAYER+1)
            throw new IllegalArgumentException();
        //not magic numbers /!\ please
        return new GameState(getPlayers(l.subList(bs+es+SIZES, bs+es+SIZES+BYTE_PER_PLAYER*ch.epfl.xblast.server.GameState.PLAYER_NUMBER)),
                boardImage(RunLengthEncoder.decode(l.subList(1, bs+1))),
                explosionImage(RunLengthEncoder.decode(l.subList(bs+SIZES, bs+es+SIZES))),
                scoreboardImage(l.subList(bs+es+SIZES, bs+es+SIZES+ch.epfl.xblast.server.GameState.PLAYER_NUMBER*BYTE_PER_PLAYER)),
                timeImage(Byte.toUnsignedInt(l.get(l.size()-1))));
    }
    
    private static List<Player> getPlayers(List<Byte> l){
        List<Player> p=new ArrayList<>();
        for (int i =0;i<ch.epfl.xblast.server.GameState.PLAYER_NUMBER;++i)
            p.add(new GameState.Player(
                    PlayerID.values()[i],
                    l.get(BYTE_PER_PLAYER*i),
                    new SubCell(Byte.toUnsignedInt(l.get(BYTE_PER_PLAYER*i+1)), Byte.toUnsignedInt(l.get(BYTE_PER_PLAYER*i+SIZES))),
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(l.get(BYTE_PER_PLAYER*i+3))));
        return p;
    }
    private static List<Image> boardImage(List<Byte> l){
        List<Image> boardImage = new ArrayList<>();
        Map<Cell,Image> map = new HashMap<>();
        for (int i=0;i<l.size();++i)
            map.put(Cell.SPIRAL_ORDER.get(i), ImageCollection.IMAGE_COLLECTION_BLOCK.image(l.get(i)));
        for (Map.Entry<Cell, Image> b : map.entrySet())
            boardImage.add(b.getValue());
        return boardImage;
    }
    
    private static List<Image> explosionImage(List<Byte> l){
        List<Image> explosionImage=new ArrayList<>();
        for (Byte b : l){
                explosionImage.add(ImageCollection.IMAGE_COLLECTION_EXPLOSION.imageOrNull(b));
        }
        return explosionImage;
    }
    
    private static List<Image> scoreboardImage(List<Byte> l){
        List<Image> scoreboard=new ArrayList<>();
        int i=0;
        for (int j=0;j<2;++j)
            scoreboard.addAll(buildScoreboardForPlayer(++i,l.get((i-1)*BYTE_PER_PLAYER)>0));
        scoreboard.addAll(Collections.nCopies(8, ImageCollection.IMAGE_COLLECTION_SCORE.image(12)));
        for (int j=0;j<2;++j)
            scoreboard.addAll(buildScoreboardForPlayer(++i,l.get((i-1)*BYTE_PER_PLAYER)>0));
        return scoreboard;
    }
    
    private static List<Image> buildScoreboardForPlayer(int n, boolean alive){
        List<Image> l=new ArrayList<>();
        l.add(ImageCollection.IMAGE_COLLECTION_SCORE.image(IMAGE_PER_PLAYER_SCOREBOARD*n-2+(alive ? 0:1)));
        l.add(ImageCollection.IMAGE_COLLECTION_SCORE.image(10));
        l.add(ImageCollection.IMAGE_COLLECTION_SCORE.image(11));
        //the "magic numbers" are the numbers of the images please don't hurt
        return l;
    }
    
    private static List<Image> timeImage(int n){
        List<Image> l=new ArrayList<>();
        l.addAll(Collections.nCopies(n, ImageCollection.IMAGE_COLLECTION_SCORE.image(21)));
        l.addAll(Collections.nCopies(GameStateSerializer.TIME_UNIT_NUMBER-n, ImageCollection.IMAGE_COLLECTION_SCORE.image(20)));
        return l;
    }
}
