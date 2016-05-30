package ch.epfl.xblast.menu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.client.XBlastComponent;
import ch.epfl.xblast.server.ServerBis;

public final class ViewMenu {
    private JFrame frame = new JFrame("XBlast 2016");
    private ModelMenu model;
    private XBlastComponent component=new XBlastComponent();
    private ButtonGroup bg;
    
    public ViewMenu(ModelMenu model){
        this.model=model;
        setFrame();
    }
    
    private final void setFrame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //frame.setSize(component.getSize());
        frame.setPreferredSize(new Dimension(960,708));
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
    
    
    public final JPanel createCreateMenu(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);    
        
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options,0));
        options.setMaximumSize(new Dimension(700,60));
        options.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        options.add(model.getDuration());
        options.add(model.getTime());
        options.add(model.getMinutes());
        options.add(Box.createRigidArea(new Dimension(180,0)));       
        options.add(model.getPlayers());
        options.add(model.getNPlayers());
        
        panel.add(Box.createRigidArea(new Dimension(0,10)));       
        panel.add(model.getCreateTitle());
        panel.add(Box.createRigidArea(new Dimension(0,10)));       
        panel.add(model.getSelectBoard());
        panel.add(Box.createRigidArea(new Dimension(0,10)));       
        panel.add(model.getRadioMap());

        panel.add(options);
        panel.add(model.getIp());
        panel.add(model.getStartServer());
        panel.add(model.getBackJoin());
 
        return panel;
    }
    public final ButtonGroup getBg(){ return bg;}
    
    public final JPanel createWinners(byte n){
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new BoxLayout(panel,1));
        
        JPanel winner = new JPanel();
        winner.setLayout(new BoxLayout(winner,0));
        if (n==0) winner.add(model.getNobody());
        else {
            if ((n>>3)%2==1) winner.add(new JLabel(new ImageIcon(
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(6))));
            if ((n>>2)%2==1) winner.add(new JLabel(new ImageIcon(
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(26))));
            if ((n>>1)%2==1) winner.add(new JLabel(new ImageIcon(
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(46))));
            if (n%2==1) winner.add(new JLabel(new ImageIcon(
                    ImageCollection.IMAGE_COLLECTION_PLAYER.imageOrNull(66))));
        }
        winner.add(model.getWon());
        panel.add(Box.createRigidArea(new Dimension(0,200)));
        panel.add(winner, Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0,140)));
        panel.add(model.getMenu());
        
        winner.setAlignmentX(Component.CENTER_ALIGNMENT);
        model.getMenu().setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }
    
    public final JPanel createWaitingClient(String str){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));
        panel.add(Box.createRigidArea(new Dimension(0,260)));
        panel.add(model.getConnecting(str));
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(model.getBackConnect());
        return panel;
    }
    
    public final JPanel createWaitingServer(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));
        panel.add(Box.createRigidArea(new Dimension(0,260)));
        panel.add(model.getConnecting(ServerBis.getIp()));
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(model.getBackServer());
        return panel;
    }
    
    public final XBlastComponent getComponent(){ return component;}
}
