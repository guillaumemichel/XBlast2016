package ch.epfl.xblast.namecheck;

import java.util.List;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.server.Bomb;
import ch.epfl.xblast.server.Player;

/**
 * Classe abstraite utilisant tous les éléments de l'étape 3, pour essayer de
 * garantir que ceux-ci ont le bon nom et les bons types. Attention, ceci n'est
 * pas un test unitaire, et n'a pas pour but d'être exécuté!
 */

abstract class NameCheck03 {
    void checkArgumentChecker() {
        ArgumentChecker.requireNonNegative(10);
    }

    void checkPlayerID() {
        ArgumentChecker.requireNonNegative(PlayerID.PLAYER_1.ordinal());
        ArgumentChecker.requireNonNegative(PlayerID.PLAYER_2.ordinal());
        ArgumentChecker.requireNonNegative(PlayerID.PLAYER_3.ordinal());
        ArgumentChecker.requireNonNegative(PlayerID.PLAYER_4.ordinal());
    }

    void checkPlayerState() {
        ArgumentChecker.requireNonNegative(Player.LifeState.State.INVULNERABLE.ordinal());
        ArgumentChecker.requireNonNegative(Player.LifeState.State.VULNERABLE.ordinal());
        ArgumentChecker.requireNonNegative(Player.LifeState.State.DYING.ordinal());
        ArgumentChecker.requireNonNegative(Player.LifeState.State.DEAD.ordinal());
        Player.LifeState.State k = null;
        Player.LifeState t = new Player.LifeState(-1, k);
        k = t.state();
        int l = t.lives();
        System.out.println(t.canMove() ? l : "");
    }

    void checkPlayerDirectedPosition() {
        Player.DirectedPosition p = null;
        Sq<Player.DirectedPosition> s = Player.DirectedPosition.stopped(p);
        Sq<Player.DirectedPosition> m = Player.DirectedPosition.moving(p);
        SubCell c = m.head().position();
        Direction d = s.head().direction();
        p = new Player.DirectedPosition(c, d);
        p = s.head().withDirection(d);
        p = m.head().withPosition(c);
    }

    void checkPlayer() {
        PlayerID pid = null;
        Sq<Player.LifeState> s = null;
        Sq<Player.DirectedPosition> d = null;
        Player p = new Player(pid, s, d, -1, -1);
        Cell c = null;
        p = new Player(pid, -1, c, -1, -1);
        pid = p.id();
        s = p.lifeStates();
        Player.LifeState s1 = p.lifeState();
        s = p.statesForNextLife();
        int l = p.lives() + p.maxBombs() + p.bombRange();
        if (p.isAlive() || l > 2 || s1 != null)
            ++l;
        p = p.withBombRange(-1).withMaxBombs(-1);
        Bomb b = p.newBomb();
        d = p.directedPositions();
        Direction d2 = p.direction();
        SubCell pos = p.position();
        System.out.println(b.toString() + d2 + pos);
    }

    void checkBomb() {
        PlayerID pid = null;
        Cell c = null;
        Sq<Integer> s = null;
        Bomb b = new Bomb(pid, c, s, -1);
        b = new Bomb(pid, c, -1, -1);
        pid = b.ownerId();
        c = b.position();
        int t = b.fuseLength() + b.range();
        s = b.fuseLengths();
        List<Sq<Sq<Cell>>> x = b.explosion();
        System.out.println(String.valueOf(t) + x);
    }
}
