package ch.epfl.xblast.menu;

import javax.swing.JFrame;

public final class ControllerMenu {
    private ViewMenu view;
    private ModelMenu model;
    private JFrame frame;
    
    public ControllerMenu(ViewMenu view,ModelMenu model){
        this.view=view;
        this.model=model;
        frame = view.getFrame();
        setView("Main");
        view.getFrame().setVisible(true);
    }
    
    public void setView(String s){
        frame.getContentPane().removeAll();
        switch (s){
            case "Main":
                frame.add(view.createMenuView());
                break;
            case "Join":
                frame.add(view.createJoinMenu());
                break;
            case "WaitC":
                frame.add(view.createWaitingClient());
                break;
            case "Game":
                frame.add(view.getComponent());
                break;
            default : frame.add(view.createMenuView());
        }
        frame.validate();
        frame.repaint();
    }
}
