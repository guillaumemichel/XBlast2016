package ch.epfl.xblast.menu;

import javax.swing.SwingUtilities;

public final class Main {
    public static void main(String[] args) {
        ModelMenu model = new ModelMenu();
        ViewMenu view = new ViewMenu(model);
        SwingUtilities.invokeLater(()->{
            ControllerMenu c = new ControllerMenu(view,model);
        });
    }
}
