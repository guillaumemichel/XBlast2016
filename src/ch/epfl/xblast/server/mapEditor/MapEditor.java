package ch.epfl.xblast.server.mapEditor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.server.Board;

@SuppressWarnings("serial")
public final class MapEditor extends JFrame{
    private GridOfBlocks grid = new GridOfBlocks();
    private BlockChooser blockChooser = new BlockChooser();
    private Options options = new Options(grid);
    private Board board;
    
    public MapEditor(){
        super("Map Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.add(blockChooser, BorderLayout.PAGE_START);
        this.add(grid, BorderLayout.CENTER);
        this.add(options, BorderLayout.PAGE_END);
        this.pack();
        this.setVisible(true);
    }
    
    public void setBoard(Board board){
        this.board = board;
    }
    public Board board(){
        return board;
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new MapEditor());
    }
}
