package ch.epfl.xblast.server.mapEditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.Block;

@SuppressWarnings("serial")
public final class MapEditor extends JFrame{
    JPanel blockChooser = new JPanel();
    JPanel grid = new JPanel();
    BlockButton[] blocks = new BlockButton[Cell.COUNT]; 
    
    
    public MapEditor(){
        super("Map Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        prepareGrid();
        this.add(blockChooser, BorderLayout.PAGE_START);
        this.add(grid, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    
    private void prepareBlockChooser(){
        blockChooser.setLayout(new FlowLayout());
        for (int i = 0; i < Block.values().length; ++i) {
            JLabel block = new JLabel();
        }
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
