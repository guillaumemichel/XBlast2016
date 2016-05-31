package ch.epfl.xblast.menu;

import javax.swing.SwingUtilities;

/**
 * The main function of the game XBlast 2016 by Michel and Vandenbroucque
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class Main {
    public static void main(String[] args){
        View v = new View();
        Model m = new Model(v);
        SwingUtilities.invokeLater(() -> new Controller(v,m));
    }
}
