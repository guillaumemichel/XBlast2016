package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.Block;

@SuppressWarnings("serial")
public final class BlockButton extends JButton implements ActionListener{
    private final static ImageCollection blockImages = new ImageCollection("block");
    
    private final static int BLOCK_IMAGE_WIDTH = blockImages.imageOrNull(2).getWidth(null);
    private final static int BLOCK_IMAGE_HEIGHT = blockImages.imageOrNull(2).getHeight(null);

    
    private int currentBlock;
    
    public BlockButton(){
        currentBlock=0;
        this.setIcon(new ImageIcon(blockImages.imageOrNull(0)));
        this.addActionListener(this);
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
    }
    
    public void nextBlock(){
        this.currentBlock++;
        this.currentBlock %= Block.values().length;
        this.setIcon(new ImageIcon(blockImages.imageOrNull(currentBlock)));
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        nextBlock();
    }

}
