package ch.epfl.xblast.server.mapEditor;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Level;

@SuppressWarnings("serial")
public final class BlockChooser extends JPanel{
    private BlockButton currentBlock = new BlockButton(BlockButton.currentBlock);

    public BlockChooser(){
        this.setLayout(new FlowLayout());
        for (int i = 0; i < Block.values().length; ++i) {
            if(i != Block.CRUMBLING_WALL.ordinal()){
                BlockButton block = new BlockButton(Block.values()[i]);
                block.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                        BlockButton.currentBlock = block.block();
                        currentBlock.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(Level.DEFAULT_LEVEL.boardPainter().correspondingBlockImageOf(BlockButton.currentBlock))));
                    }
                });
                this.add(block);
            }
        }
        this.add(new JLabel("Current block: "));
        currentBlock.setBorder(new LineBorder(Color.BLACK, 3));
        this.add(this.currentBlock);
    }
}
