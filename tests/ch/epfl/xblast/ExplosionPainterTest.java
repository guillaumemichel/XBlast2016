package ch.epfl.xblast;

import ch.epfl.xblast.server.Bomb;
import ch.epfl.xblast.server.painter.ExplosionPainter;

public final class ExplosionPainterTest {

    public static void main(String[] args) {
        Bomb b0= new Bomb(PlayerID.PLAYER_1, new Cell(1,1), 10, 5);
        Bomb b1= new Bomb(PlayerID.PLAYER_1, new Cell(7,1), 2, 5);
        Bomb b2= new Bomb(PlayerID.PLAYER_1, new Cell(1,6), 1024, 4);
        Bomb b3= new Bomb(PlayerID.PLAYER_1, new Cell(1,2), 14, 2);

        System.out.println(ExplosionPainter.byteForBomb(b0));
        System.out.println(ExplosionPainter.byteForBomb(b1));
        System.out.println(ExplosionPainter.byteForBomb(b2));
        System.out.println(ExplosionPainter.byteForBomb(b3));
        
        System.out.println(ExplosionPainter.byteForBlast(true, true, true, true));
        System.out.println(ExplosionPainter.byteForBlast(false, true, true, true));
        System.out.println(ExplosionPainter.byteForBlast(false, false, true, true));
        System.out.println(ExplosionPainter.byteForBlast(false, false, false, true));

    }

}
