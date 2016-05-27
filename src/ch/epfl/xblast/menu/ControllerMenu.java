package ch.epfl.xblast.menu;

import javax.swing.JFrame;

import ch.epfl.xblast.client.ClientBis;
import ch.epfl.xblast.client.KeyboardEventHandler;

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
            default :
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
                view.getComponent().addKeyListener(new KeyboardEventHandler(ClientBis.getMap(), ClientBis.getConsumer()));
                view.getComponent().requestFocusInWindow();
                break;
            case "Server":
                frame.add(view.createCreateMenu());
                break;
        }
        frame.validate();
        frame.repaint();
    }
}
