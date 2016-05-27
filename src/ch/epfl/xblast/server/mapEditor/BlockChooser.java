package ch.epfl.xblast.server.mapEditor;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;

/**
 * A custom JPanel representing a block chooser
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbrouque (258715)
 *
 */
@SuppressWarnings("serial")
public final class BlockChooser extends JPanel{
    private BlockButton currentBlock = new BlockButton(Block.FREE);
    
    private final static int PADDING_VALUE = 10;
    private final static int LINEBORDER_THICKNESS = 3;

    /**
     * Constructs a block chooser
     */
    public BlockChooser(){
        this.setLayout(new FlowLayout());
        addBlockSelectors();
        addCurrentBlock();
        this.add(new JLabel(new String(new char[PADDING_VALUE]).replace("\0", " ")));
        addClearButton();
        addWalledBoardButton();
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
        JButton clear = new JButton("Cleared board");
        clear.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                GridOfBlocks parentGrid = ((MapEditor) SwingUtilities.windowForComponent(BlockChooser.this)).grid();
                parentGrid.loadGridfromListOfBytes(Collections.nCopies(Cell.COUNT, (byte)0));
            }
        });
        this.add(clear);
    }
    
    private void addWalledBoardButton(){
        JButton walledBoard = new JButton("Cleared walled board");
        walledBoard.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                GridOfBlocks parentGrid = ((MapEditor) SwingUtilities.windowForComponent(BlockChooser.this)).grid();
                Board b = Board.ofInnerBlocksWalled(Collections.nCopies(Cell.ROWS-2, Collections.nCopies(Cell.COLUMNS-2, Block.FREE)));
                List<Byte> l = b.toListOfBytes();
                parentGrid.loadGridfromListOfBytes(l);
            }
        });
        this.add(walledBoard);
    }
    
    private void addBlockSelectors(){
        for (int i = 0; i < Block.values().length; ++i) {
            if(i != Block.CRUMBLING_WALL.ordinal()){
                BlockButton block = new BlockButton(Block.values()[i]);
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
        this.currentBlock.setBorder(new LineBorder(Color.BLACK, LINEBORDER_THICKNESS));
        this.add(this.currentBlock);
    }
}
