package ch.epfl.xblast.server.mapEditor;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.painter.BlockImage;

@SuppressWarnings("serial")
public final class BlockButton extends JButton{    
    private final static int BLOCK_IMAGE_WIDTH = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(2).getWidth(null);
    private final static int BLOCK_IMAGE_HEIGHT = ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(2).getHeight(null);

    
    private int currentBlock;
    
    public BlockButton(){
        currentBlock=0;
        this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(0)));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0)
                    nextBlock();
            }
        });
        this.setPreferredSize(new Dimension(BLOCK_IMAGE_WIDTH, BLOCK_IMAGE_HEIGHT));
        this.setBorderPainted(false);
    }
    
    public void nextBlock(){
        this.currentBlock++;
        this.currentBlock %= BlockImage.values().length;
        this.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(currentBlock)));
    }

}
