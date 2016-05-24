package ch.epfl.xblast.menu;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.epfl.xblast.client.XBlastComponent;

public final class ViewMenu {
    JFrame frame = new JFrame("XBlast 2016");    
    
    public ViewMenu(ModelMenu model){
        setFrame();
    }
    
    private final void setFrame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(XBlastComponent.XB_COMPONENT_WIDTH,XBlastComponent.XB_COMPONENT_HEIGHT));
        frame.pack();
    }
    
    public final JPanel createMenuView(ModelMenu model){ 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        
        panel.add(Box.createRigidArea(new Dimension(0,40)));
        panel.add(model.title);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(model.names);
        panel.add(Box.createRigidArea(new Dimension(0,50)));
        panel.add(model.create);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(model.join);
        return panel;

    }
    
    public final JPanel createJoinMenu(ModelMenu model){
        JPanel panelSup = new JPanel();
        panelSup.setLayout(new BoxLayout(panelSup,1));
        
        JPanel ip = new JPanel();
        ip.setLayout(new BoxLayout(ip,0));
        
        ip.add(model.ipText);
        ip.add(model.ipField);
        panelSup.add(ip);
        panelSup.add(model.backJoin);
        return panelSup;
    }
}
