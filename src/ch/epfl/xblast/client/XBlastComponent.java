package ch.epfl.xblast.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.JComponent;

public final class XBlastComponent extends JComponent{
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(960, 688);
    }
    
    @Override
    public void paintComponent(Graphics g){
        
    }
    
    private boolean drawImage(Image img, int x, int y, ImageObserver observer){
        
    }
}
