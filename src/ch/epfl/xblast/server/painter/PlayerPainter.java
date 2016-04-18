package ch.epfl.xblast.server.painter;

import ch.epfl.xblast.server.Player;

public final class PlayerPainter {

    private PlayerPainter() {
    }

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
                return 15;
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
            }
            if (p.direction().isHorizontal()){
                if (p.position().x()%4==1)
                    n+=1;
                if (p.position().x()%4==3)
                    n+=2;
            } else{
                if (p.position().y()%4==1)
                    n+=1;
                if (p.position().y()%4==3)
                    n+=3;
            }
            break;
            case DYING:
                if (p.lives()>1)
                    n+=12;
                else 
                    n+=13;
                    break;
            default:
                n=15;
        }
        return n;
    }
}
