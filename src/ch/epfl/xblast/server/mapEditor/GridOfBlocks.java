package ch.epfl.xblast.server.mapEditor;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;

@SuppressWarnings("serial")
public final class GridOfBlocks extends JPanel{
    private List<BlockButton> blocks = new ArrayList<>();

    public GridOfBlocks(){
        this.setLayout(new GridLayout(Cell.ROWS, Cell.COLUMNS));
        
        for (int i = 0; i < Cell.COUNT; i++) {
            blocks.add(new BlockButton());
            this.add(blocks.get(blocks.size()-1));
        }
    }
    
    public List<Integer> toListOfIntegers(){
        return blocks.stream().map(BlockButton::block).map(Block::ordinal).collect(Collectors.toList());
    }
    
    public Board toBoard(){
        List<Sq<Block>> blocks = this.blocks.stream().map(BlockButton::block).map(Sq::constant).collect(Collectors.toList());;
        return new Board(blocks);
    }
}
