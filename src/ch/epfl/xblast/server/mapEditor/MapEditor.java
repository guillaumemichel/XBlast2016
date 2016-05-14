package ch.epfl.xblast.server.mapEditor;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.Cell;

@SuppressWarnings("serial")
public final class MapEditor extends JFrame{
    JPanel grid = new JPanel();
    BlockButton[] blocks = new BlockButton[Cell.COUNT]; 
    
    public MapEditor(){
        super("Map Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        grid.setLayout(new GridLayout(Cell.COLUMNS, Cell.ROWS));
        
        for (int i = 0; i < Cell.COUNT; i++) {
            blocks[i] = new BlockButton();
            grid.add(blocks[i]);
        }
        this.add(grid);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new MapEditor());
    }
}
