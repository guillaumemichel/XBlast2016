package ch.epfl.xblast.server;

import ch.epfl.xblast.Time;

/**
 * Interface Ticks
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public interface Ticks {
    /**
     * The number of ticks during which a player dies
     */
    public final static int PLAYER_DYING_TICKS=8;
    
    /**
     * The number of ticks during which a player is invulnerable
     */
    public final static int PLAYER_INVULNERABLE_TICKS=64;
    
    /**
     * The number of ticks during which a bomb fuses
     */
    public final static int BOMB_FUSE_TICKS=100;
    
    /**
     * The number of ticks during which an explosion takes place
     */
    public final static int EXPLOSION_TICKS=30;
    
    /**
     * The number of ticks during which a destructible wall crumbles
     */
    public final static int WALL_CRUMBLING_TICKS=30;
    
    /**
     * The number of ticks during which a bomb disappears
     */
    public final static int BONUS_DISAPPEARING_TICKS=30;
    
    /**
     * The number of ticks per second
     */
    public final static int TICKS_PER_SECOND = 20;
    
    /**
     * The duration of a tick in nanoseconds
     */
    public final static int TICK_NANOSECOND_DURATION = Time.NS_PER_S/TICKS_PER_SECOND;
    
    /**
     * The total number of ticks of a game, which lasts 2 minutes
     */
    public final static int TOTAL_TICKS = 2 * Time.S_PER_MIN * TICKS_PER_SECOND; 
}
