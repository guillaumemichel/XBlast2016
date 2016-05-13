package ch.epfl.xblast.server.mapEditor;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.epfl.xblast.Cell;

public final class MapEditor extends JFrame{
    JPanel p = new JPanel();
    BlockButton[] blocks = new BlockButton[Cell.COUNT]; 
    
    public MapEditor(){
        super("Map Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setLayout(new GridLayout(Cell.COLUMNS, Cell.ROWS));
        
        for (int i = 0; i < Cell.COUNT; i++) {
            p.add();
        }

        
    }

}
