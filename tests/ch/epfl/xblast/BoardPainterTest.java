package ch.epfl.xblast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.painter.BlockImage;
import ch.epfl.xblast.server.painter.BoardPainter;

public class BoardPainterTest {
            
        @Test
        public void byteForCellWorksProperly(){
            Map<Block, BlockImage> palette=new HashMap<>();
            palette.put(Block.FREE, BlockImage.IRON_FLOOR);
            for(int i=1;i<Block.values().length;i++)
                palette.put(Block.values()[i], BlockImage.values()[i+1]);
            
            for(Map.Entry<Block, BlockImage> map : palette.entrySet())
                System.out.println("Key: "+ map.getKey()+"\nValue: "+map.getValue());
            
            BoardPainter bp= new BoardPainter(palette, BlockImage.IRON_FLOOR_S);

            
            Block __ = Block.FREE;
            Block XX = Block.INDESTRUCTIBLE_WALL;
            Block xx = Block.DESTRUCTIBLE_WALL;
            Board board = Board.ofQuadrantNWBlocksWalled(
              Arrays.asList(
                Arrays.asList(__, __, __, __, __, xx, __),
                Arrays.asList(__, XX, xx, XX, xx, XX, xx),
                Arrays.asList(__, xx, __, __, __, xx, __),
                Arrays.asList(xx, XX, __, XX, XX, XX, XX),
                Arrays.asList(__, xx, __, xx, __, __, __),
                Arrays.asList(xx, XX, xx, XX, xx, XX, __)));
            
            byte myByteFreeShadow=bp.byteForCell(board, new Cell(1,1));
            byte myByteFree=bp.byteForCell(board, new Cell(2, 1));
            byte myByteIndestructible=bp.byteForCell(board, new Cell(2, 2));
            byte myByteDestructible=bp.byteForCell(board, new Cell(3, 2));
            
            assertEquals(myByteFreeShadow, 1);
            assertEquals(myByteFree, 0);
            assertEquals(myByteIndestructible, 2);
            assertEquals(myByteDestructible, 3);
        }
        

}
