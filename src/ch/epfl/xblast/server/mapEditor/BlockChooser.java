package ch.epfl.xblast.server.mapEditor;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Level;

@SuppressWarnings("serial")
public final class BlockChooser extends JPanel{
    private BlockButton currentBlock = new BlockButton(BlockButton.currentBlock, true);
    private final GridOfBlocks associatedGrid;

    public BlockChooser(GridOfBlocks associatedGrid){
        this.associatedGrid = associatedGrid;
        this.setLayout(new FlowLayout());
        addBlockSelectors();
        addCurrentBlock();
        this.add(new JLabel(new String(new char[20]).replace("\0", " ")));
        addClearButton();
    }
    
    private void addClearButton(){
        JButton clear = new JButton("Clear board");
        clear.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                BlockChooser.this.associatedGrid.loadGridfromListOfBytes(Collections.nCopies(Cell.COUNT, (byte)0));
            }
        });
        this.add(clear);
    }
    
    private void addBlockSelectors(){
        for (int i = 0; i < Block.values().length; ++i) {
            if(i != Block.CRUMBLING_WALL.ordinal()){
                BlockButton block = new BlockButton(Block.values()[i], true);
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
    }
    
    private void addCurrentBlock(){
        this.add(new JLabel("Current block: "));
        this.currentBlock.setBorder(new LineBorder(Color.BLACK, 3));
        this.add(this.currentBlock);
    }
}
