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
          return (player.maxBombs()<MAX_BOMBS) ? player.withMaxBombs(player.maxBombs()+1) : player;
      }
    },

    INC_RANGE {
      @Override
      public Player applyTo(Player player) { 
          return (player.bombRange()<MAX_RANGE) ? player.withBombRange(player.bombRange()+1) : player;
      }
    };
    
    private final static int MAX_BOMBS = 9;
    private final static int MAX_RANGE = 9;

    /**
     * Applies this bonus to the given player
     * 
     * @param player
     *      The player targeted by this bonus
     *      
     * @return
     *      The player with this bonus applied to him
     */
    abstract public Player applyTo(Player player);
   
  }
