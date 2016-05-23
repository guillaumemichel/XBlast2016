package ch.epfl.xblast.menu;

import java.awt.event.ActionListener;

public final class ControllerMenu {
    private ModelMenu model;
    private ViewMenu view;
    
    public ControllerMenu(ModelMenu model, ViewMenu view){
        this.model=model;
        this.view=view;
    }
    
    public void control(ActionListener l, int id){
        if (id<view.buttonsNumber())
            view.getButton(id).addActionListener(l);
    }
}
