package ch.epfl.xblast.client;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class GameState {
    private final List<Player> players;
    private final List<Image> imageBoard, imageExplosion, imageScore, imageTime;
    
    public GameState(List<Player> players, List<Image> imageBoard, List<Image> imageExplosion, List<Image> imageScore, List<Image> imageTime){
        this.players=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(players)));
        this.imageBoard=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(imageBoard)));
        this.imageExplosion=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(imageExplosion)));
        this.imageScore=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(imageScore)));
        this.imageTime=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(imageTime)));
    }
    
    public final static class Player{
        
    }
}
