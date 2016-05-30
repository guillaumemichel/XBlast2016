package ch.epfl.xblast.menu;

import javax.swing.SwingUtilities;

public final class Main {
    public static void main(String[] args){
        View v = new View();
        Model m = new Model(v);
        SwingUtilities.invokeLater(() -> new Controller(v,m));
    }
}
