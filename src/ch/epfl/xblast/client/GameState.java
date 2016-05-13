package ch.epfl.xblast.client;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;

/**
 * A game state
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class GameState {
    private final List<Player> players;
    private final List<Image> imagesBoard, imagesExplosion, imagesScore, imagesTime;
    
    /**
     * Constructs a game state with the given parameters
     * 
     * @param players
     *      The players of the game
     * 
     * @param imageBoard
     *      The images that represent the board
     *      
     * @param imageExplosion
     *      The images that represent the explosions
     * 
     * @param imageScore
     *      The images that represent the score
     *      
     * @param imageTime
     *      The images that represent the time
     */
    public GameState(List<Player> players, List<Image> imageBoard, List<Image> imageExplosion, List<Image> imageScore, List<Image> imageTime){
        this.players=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(players)));
        this.imagesBoard=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(imageBoard)));
        this.imagesExplosion=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(imageExplosion)));
        this.imagesScore=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(imageScore)));
        this.imagesTime=Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(imageTime)));
    }
    
    /**
     * Returns the list of players of this game state
     * 
     * @return
     *      The list of players of this game state
     */
    public List<Player> players(){
        return players;
    }
    
    /**
     * Returns the list of images that represent the board of this game state
     * 
     * @return
     *      The list of images that represent the board of this game state
     */
    public List<Image> imagesBoard(){
        return imagesBoard;
    }
    
    /**
     * Returns the list of images that represent the explosions of this game state
     * 
     * @return
     *      The list of images that represent the explosions of this game state
     */
    public List<Image> imagesExplosion(){
        return imagesExplosion;
    }
    
    /**
     * Returns the list of images that represent the score of this game state
     * 
     * @return
     *      The list of images that represent the score of this game state
     */
    public List<Image> imagesScore(){
        return imagesScore;
    }
    
    /**
     * Returns the list of images that represent the time for this game state
     * 
     * @return
     *      The list of images that represent the time for this game state
     */
    public List<Image> imagesTime(){
        return imagesTime;
    }
    
    /**
     * A player
     */
    public final static class Player{
        private final PlayerID id;
        private final int lives;
        private final SubCell position;
        private final Image image;
        
        /**
         * Constructs a player with the given parameters
         * 
         * @param id
         *      The id of the player
         *      
         * @param lives
         *      The number of lives of the player
         *      
         * @param position
         *      The position of the player
         *      
         * @param image
         *      The image that represent the player
         */
        public Player(PlayerID id, int lives, SubCell position, Image image){
            this.id=Objects.requireNonNull(id);//WTF ?
            this.lives=ArgumentChecker.requireNonNegative(lives);
            this.position=Objects.requireNonNull(position);
            this.image=image;
        }
        
        /**
         * Returns the id of this player
         * 
         * @return
         *      The id of this player
         */
        public PlayerID id(){
            return id;
        }
        
        /**
         * Returns the number of lives of this player
         * 
         * @return
         *      The number of lives of this player
         */
        public int lives(){
            return lives;
        }
        
        /**
         * Returns the position of this player
         * 
         * @return
         *      The position of this player
         */
        public SubCell position(){
            return position;
        }
        
        /**
         * Returns the image that represents this player
         * 
         * @return
         *      The image that represents this player
         */
        public Image image(){
            return image;
        }
    }
}
