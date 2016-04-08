package ch.epfl.xblast;

/**
 * ArgumentChecker
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class ArgumentChecker {

    private ArgumentChecker(){}
    
    /**
     * Returns the given value if it is positive, or equal to zero
     * 
     * @param value
     *      The given value
     *      
     * @return
     *      The given value if it is valid
     *      
     * @throws IllegalArgumentException
     *      If the given value is less than zero
     */
    public static int requireNonNegative(int value) throws IllegalArgumentException{
        if(value>=0){
            return value;
        }else{
            throw new IllegalArgumentException("The value must be positive or equal to zero !");
        }
    }
}
