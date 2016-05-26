package ch.epfl.xblast.server.mapEditor;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.Block;

/**
 * A custom JPanel representing a block chooser
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbrouque (258715)
 *
 */
@SuppressWarnings("serial")
public final class BlockChooser extends JPanel{
    private BlockButton currentBlock = new BlockButton(Block.FREE, true);

    /**
     * Constructs a block chooser
     */
    public BlockChooser(){
        this.setLayout(new FlowLayout());
        addBlockSelectors();
        addCurrentBlock();
        this.add(new JLabel(new String(new char[20]).replace("\0", " ")));
        addClearButton();
    }
    
    /**
     * Returns the current chosen block
     * 
     * @return
     *      The current chosen block
     */
    public BlockButton currentBlock(){
        return currentBlock;
    }
    
    private void addClearButton(){
        JButton clear = new JButton("Clear board");
        clear.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                GridOfBlocks parentGrid = ((MapEditor) SwingUtilities.windowForComponent(BlockChooser.this)).grid();
                parentGrid.loadGridfromListOfBytes(Collections.nCopies(Cell.COUNT, (byte)0));
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
                        Block blockOfparentBlockButton = ((BlockButton)e.getSource()).block();
                        currentBlock.setBlock(blockOfparentBlockButton);
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
