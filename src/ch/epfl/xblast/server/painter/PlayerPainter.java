package ch.epfl.xblast.server.painter;

import ch.epfl.xblast.server.Player;

public final class PlayerPainter {
    
    private PlayerPainter(){}
    
    public static byte byteForPlayer(int tick, Player p){
        byte n=0;
        switch (p.id()){
            case PLAYER_1:
                break;
            case PLAYER_2:
                n+=20;
                break;
            case PLAYER_3:
                n+=40;
                break;
            case PLAYER_4:
                n+=60;
                break;
            default:
                return 15;
        }
        
        switch (p.lifeState().state()){
            case VULNERABLE:
                
            case INVULNERABLE:
                
            case DYING:
                
            case DEAD:
                
            default:
                
        }
        return n;
    }
}
