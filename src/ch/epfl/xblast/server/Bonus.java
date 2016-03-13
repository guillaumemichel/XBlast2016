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

    /**
     * Apply the bonus to the player
     * 
     * @param player
     *      player targeted by the bonus
     * @return
     *      the same player with the bonus applied
     */
    
    abstract public Player applyTo(Player player);
   
  }
