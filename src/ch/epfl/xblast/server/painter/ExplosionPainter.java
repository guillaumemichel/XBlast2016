package ch.epfl.xblast.server.painter;

import ch.epfl.xblast.server.Bomb;

/**
 * An explosion painter
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class ExplosionPainter {
    /**
     * Byte that represent the empty value
     */
    public static final byte BYTE_FOR_EMPTY = 16;
    
    private ExplosionPainter(){}
    
    /**
     * Determine if the bomb is represented by the black image or the white one. The image black is always used except if the fuse length of the bomb is a power of 2
     * 
     * @param b
     *      The bomb whose image will be determined
     *      
     * @return 
     *      The byte representing the image of the bomb (20 for black bomb and 21 for white bomb)
     */
    public static byte byteForBomb(Bomb b){
        return (byte)(((b.fuseLength()&(b.fuseLength()-1))==0) ? 21 : 20);
    }
    
    /**
     * Determine which picture use for explosions
     * 
     * @param N
     *      True if the cell at north has a blast
     * 
     * @param E
     *      True if the cell at east has a blast
     * 
     * @param S
     *      True if the cell at south has a blast
     * 
     * @param W
     *      True if the cell at west has a blast
     *      
     * @return 
     *      The byte representing the image of the explosion
     */
    public static byte byteForBlast(boolean N, boolean E, boolean S, boolean W){
        byte n=0;
        if (N) n+=0b1000;
        if (E) n+=0b100;
        if (S) n+=0b10;
        if (W) n+=0b1;//it is no magic numbers it is the reference to the images
        return n;
    }
}
