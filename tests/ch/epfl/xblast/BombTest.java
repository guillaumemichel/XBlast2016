package ch.epfl.xblast;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.server.Bomb;
import ch.epfl.xblast.server.Ticks;

public class BombTest {

    /*@Test
    public void explosionMethodWorks() {
        
        Bomb b = new Bomb(PlayerID.PLAYER_1, new Cell(2,3), 10, 3);
        Sq<Sq<Cell>> sqFinal=b.explosionArmTowards(Direction.S);
        for (int i = 0; i < Ticks.EXPLOSION_TICKS; i++) {
            Sq<Cell> p = sqFinal.head();
            for(int j=b.range(); j<b.range()+Ticks.EXPLOSION_TICKS; j++){
                System.out.print(p.head()+ ", ");
                p = p.tail();
            }
            System.out.println();
            sqFinal=sqFinal.tail();
        }
    }*/

}
