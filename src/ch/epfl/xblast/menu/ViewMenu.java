package ch.epfl.xblast.menu;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ch.epfl.xblast.client.ClientBis;
import ch.epfl.xblast.client.KeyboardEventHandler;
import ch.epfl.xblast.client.XBlastComponent;

public final class ViewMenu {
    private JFrame frame = new JFrame("XBlast 2016");
    private ModelMenu model;
    private XBlastComponent component;
    
    public ViewMenu(ModelMenu model){
        this.model=model;
        component=new XBlastComponent();
        setFrame();
    }
    
    private final void setFrame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(component.getPreferredSize());
        frame.pack();
    }
    
    public final JFrame getFrame(){ return frame;}
    
    public final JPanel createMenuView(){ 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        
        panel.add(Box.createRigidArea(new Dimension(0,40)));
        panel.add(model.getTitle());
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(model.getNames());
        panel.add(Box.createRigidArea(new Dimension(0,50)));
        panel.add(model.getCreate());
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(model.getJoin());
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(model.getQuit());
        return panel;

    }
    
    public final JPanel createJoinMenu(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));
        
        JPanel ip = new JPanel();
        ip.setLayout(new BoxLayout(ip,0));
        
        ip.add(model.getIpText());
        ip.add(model.getIpField());
        ip.add(model.getIpJoin());
        
        panel.add(Box.createRigidArea(new Dimension(0,60)));
        panel.add(model.getJoinTitle());
        panel.add(Box.createRigidArea(new Dimension(0,160)));
        panel.add(ip);
        panel.add(Box.createRigidArea(new Dimension(0,200)));
        panel.add(model.getBackJoin());
        return panel;
    }
    
    public final JPanel createWaitingClient(){
        JPanel panel = new JPanel();
        
        panel.add(model.getWaiting(3));
        return panel;
    }
    
    public final XBlastComponent getComponent(){ return component;}
}
