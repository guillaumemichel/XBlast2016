package ch.epfl.xblast.client;

import java.awt.Image;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ch.epfl.xblast.RunLengthEncoder;

public final class GameStateDeserializer {
    
    private GameStateDeserializer(){};
    
    public static GameState deserializeGameState(List<Byte> l) throws NoSuchElementException, URISyntaxException, IOException{
        List<Byte> fullBoard;
        List<Byte> fullExplosion;
        List<Image> boardImage=new ArrayList<>();
        List<Image> explosionImage=new ArrayList<>();
        
        int bs = l.get(0); //board size
        int es = l.get(bs+1); //explosion size
        
        ImageCollection c1 = new ImageCollection("block");
        fullBoard = RunLengthEncoder.decode(l.subList(1, bs+1));
        for (Byte b : fullBoard){
            boardImage.add(c1.image(b));
        }
        
        ImageCollection c2 = new ImageCollection("explosion");
        fullExplosion = RunLengthEncoder.decode(l.subList(bs+2, bs+es+2));
        for (Byte b : fullExplosion){
            explosionImage.add(c2.image(b));
        }
        
        
        
        return new GameState(null,boardImage,explosionImage,null,null);
    }
}
