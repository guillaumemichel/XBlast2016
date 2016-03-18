package ch.epfl.xblast.namecheck;

import java.util.List;
import java.util.Optional;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Lists;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.Time;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.Bomb;
import ch.epfl.xblast.server.Bonus;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;
import ch.epfl.xblast.server.Ticks;

/**
 * Classe abstraite utilisant tous les éléments de l'étape 4, pour essayer de
 * garantir que ceux-ci ont le bon nom et les bons types. Attention, ceci n'est
 * pas un test unitaire, et n'a pas pour but d'être exécuté!
 */

abstract class NameCheck04 {
    void checkLists() {
        List<Integer> l1 = null;
        List<List<Integer>> p1 = Lists.permutations(l1);
        List<List<List<Integer>>> p2 = Lists.permutations(p1);
        System.out.println(p2);
    }

    void checkBonus(boolean x) {
        Bonus b = x ? Bonus.INC_BOMB : Bonus.INC_RANGE;
        Player p = null;
        p = b.applyTo(p);
    }

    void checkBlock(Block b) {
        b = b.isBonus() ? Block.BONUS_BOMB : Block.BONUS_RANGE;
        Bonus s = b.associatedBonus();
        System.out.println(s);
    }

    void checkTime() {
        Optional<Integer> o;
        o = Optional.of(Time.S_PER_MIN);
        o = Optional.of(Time.MS_PER_S);
        o = Optional.of(Time.US_PER_S);
        o = Optional.of(Time.NS_PER_S);
        System.out.println(o);
    }

    void checkTicks() {
        Optional<Integer> o;
        o = Optional.of(Ticks.TICKS_PER_SECOND);
        o = Optional.of(Ticks.TICK_NANOSECOND_DURATION);
        o = Optional.of(Ticks.TOTAL_TICKS);
        System.out.println(o);
    }

    void checkGameState() {
        int ts = 0;
        Board b = null;
        List<Player> ps = null;
        List<Bomb> bs = null;
        List<Sq<Sq<Cell>>> es = null;
        List<Sq<Cell>> xs = null;
        GameState s = new GameState(ts, b, ps, bs, es, xs);
        s = new GameState(b, ps);
        ts = s.ticks();
        if (s.isGameOver()) {
            Optional<Double> t = Optional.of(s.remainingTime());
            System.out.println(t.get());
        }
        Optional<PlayerID> w = s.winner();
        b = s.board();
        ps = s.isGameOver() ? s.alivePlayers() : s.players();
        System.out.println(w);
    }
}
