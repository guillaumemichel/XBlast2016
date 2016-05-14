package ch.epfl.xblast.server.mapEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.client.ImageCollection;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Level;

@SuppressWarnings("serial")
public final class MapEditor extends JFrame{
    private JPanel blockChooser = new JPanel();
    private JPanel grid = new JPanel();
    private BlockButton[] blocks = new BlockButton[Cell.COUNT];
    private BlockButton currentBlock = new BlockButton(BlockButton.currentBlock);
    
    public MapEditor(){
        super("Map Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        prepareGrid();
        prepareBlockChooser();
        this.add(blockChooser, BorderLayout.PAGE_START);
        this.add(grid, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    
    private void prepareBlockChooser(){
        blockChooser.setLayout(new FlowLayout());
        for (int i = 0; i < Block.values().length; ++i) {
            BlockButton block = new BlockButton(Block.values()[i]);
            block.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    BlockButton.currentBlock = block.block();
                    currentBlock.setIcon(new ImageIcon(ImageCollection.IMAGE_COLLECTION_BLOCK.imageOrNull(Level.DEFAULT_LEVEL.boardPainter().correspondingBlockImageOf(BlockButton.currentBlock))));
                }
            });
            blockChooser.add(block);
        }
        blockChooser.add(new JTextField("Current block: "));
        currentBlock.setBorder(new LineBorder(Color.BLUE, 3));
        blockChooser.add(this.currentBlock);

        
    }
    
    private void prepareGrid(){
        grid.setLayout(new GridLayout(Cell.ROWS, Cell.COLUMNS));
        
        for (int i = 0; i < Cell.COUNT; i++) {
            blocks[i] = new BlockButton();
            grid.add(blocks[i]);
        }
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new MapEditor());
    }
}
