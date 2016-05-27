package ch.epfl.xblast;

/**
 * Interface Time
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public interface Time {
    /**
     * The number of seconds per minute
     */
    public final static int S_PER_MIN = 60;
    
    /**
     * The number of milliseconds per second
     */
    public final static int MS_PER_S = 1000;
    
    /**
     * The number of microseconds per second
     */
    public final static int US_PER_S = 1000 * MS_PER_S;
    
    /**
     * The number of nanoseconds per second
     */
    public final static int NS_PER_S = 1000 * US_PER_S;
}
