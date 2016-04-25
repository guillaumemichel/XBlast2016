package ch.epfl.xblast.client;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.xblast.RunLengthEncoder;

public final class GameStateDeserializer {
    
    private GameStateDeserializer(){};
    
    public static GameState deserializeGameState(List<Byte> l){
        List<Byte> fullBoard;
        List<Byte> fullExplosion;
        List<Image> boardImage=new ArrayList<>();
        List<Image> explosionImage=new ArrayList<>();
        //deserialize board
        int bs = l.get(0); //board size
        int es = l.get(bs); //explosion size
        
        ImageCollection c1 = new ImageCollection("board"); // à définir
        fullBoard = RunLengthEncoder.decode(l.subList(0, bs-1));
        for (Byte b : fullBoard){
            boardImage.add(c1.image(b));
        }
        
        ImageCollection c2 = new ImageCollection("explosion");
        for (Byte b : fullExplosion){
            explosionImage.add(c2.image(b));
        }
        
        
        fullExplosion = RunLengthEncoder.decode(l.subList(bs, bs+es-1));
        
        return null;
    }
}
