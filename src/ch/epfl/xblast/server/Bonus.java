package ch.epfl.xblast.server;

/**
 * A bonus
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */

public enum Bonus {
    INC_BOMB {
      @Override
      public Player applyTo(Player player) {
          return player.maxBombs()<9 ? player.withMaxBombs(player.maxBombs()+1):player;
      }
    },

    INC_RANGE {
      @Override
      public Player applyTo(Player player) { 
          return player.bombRange()<9 ? player.withBombRange(player.bombRange()+1):player;
      }
    };

    abstract public Player applyTo(Player player);
  }
