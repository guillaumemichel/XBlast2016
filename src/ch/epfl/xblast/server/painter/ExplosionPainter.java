package ch.epfl.xblast.server.painter;

import ch.epfl.xblast.server.Bomb;

/**
 * An ExplosionPainter
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */

public final class ExplosionPainter {
    
    public static final byte BYTE_FOR_EMPTY = 16;
    
    private ExplosionPainter(){}
    
    public static byte byteForBomb(Bomb b){
        return (byte)(((b.fuseLength()&(b.fuseLength()-1))==0) ? 21 : 20);
    }
    
    public static byte byteForBlast(boolean N, boolean E, boolean S, boolean W){
        byte n=0;
        if (N) n&=0b1000;
        if (E) n&=0b100;
        if (S) n&=0b10;
        if (W) n&=0b1;
        return n;
    }
    
    

}
