package ch.epfl.xblast.server.painter;

import ch.epfl.xblast.server.Player;

/**
 * A player painter
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class PlayerPainter {
    
    private final static byte BYTE_FOR_EMPTY_PLAYER=15;
    private final static int PLAYER_POSITIONS_NUMBER=4;//player position according to x or y

    private PlayerPainter() {}
    
    /**
     * Method that determine what image to use for a player at a given time depending on his state, his id and his position
     * 
     * @param tick
     *      The time of the game to make the INVULNERABLE blink
     *      
     * @param p
     *      Player whose image will be returned
     *      
     * @return
     *      The byte code for the current image of the player
     */
    public static byte byteForPlayer(int tick, Player p){
        byte n;
        switch (p.id()){
            case PLAYER_1:
                n=0;
                break;
            case PLAYER_2:
                n=20;
                break;
            case PLAYER_3:
                n=40;
                break;
            case PLAYER_4:
                n=60;
                break;
            default:
                return BYTE_FOR_EMPTY_PLAYER;
        }
        
        switch (p.lifeState().state()){
            case INVULNERABLE:
                if (tick%2==1)
                    n=80;
            case VULNERABLE:
                switch (p.direction()){
                    case W:
                        n+=3;
                    case S:
                        n+=3;
                    case E:
                        n+=3;
                    case N:
                }
                if (p.direction().isHorizontal()){
                    if (p.position().x()%PLAYER_POSITIONS_NUMBER==1)
                        n+=1;
                    if (p.position().x()%PLAYER_POSITIONS_NUMBER==3)
                        n+=2;
                } else{
                    if (p.position().y()%PLAYER_POSITIONS_NUMBER==1)
                        n+=1;
                    if (p.position().y()%PLAYER_POSITIONS_NUMBER==3)
                        n+=2;
                }
                break;
            case DYING:
                if (p.lives()>1)
                    n+=12;
                else 
                    n+=13;
                    break;
            default:
                n=BYTE_FOR_EMPTY_PLAYER;
        }
        return n;
    }
}
