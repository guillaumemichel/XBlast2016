package ch.epfl.xblast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SubCellTest1 {
    @Test
    public void centralSubCellOfKnowCellIsCorrect() {
        SubCell c = SubCell.centralSubCellOf(new Cell(2, 1));
        assertEquals(40, c.x());
        assertEquals(24, c.y());
    }

    @Test
    public void centralSubCellIsCentral() {
        for (Cell c: Cell.ROW_MAJOR_ORDER)
            assertTrue(SubCell.centralSubCellOf(c).isCentral());
    }

    @Test
    public void distanceToCentralOfCentralIsZero() {
        for (Cell c: Cell.ROW_MAJOR_ORDER)
            assertEquals(0, SubCell.centralSubCellOf(c).distanceToCentral());
    }

    @Test
    public void constructorCorrectlyNormalizesCoordinates() {
        for (int i = -2; i <= 2; ++i) {
            SubCell c = new SubCell(239 + 240 * i, 207 + 208 * i);
            assertEquals(239, c.x());
            assertEquals(207, c.y());
        }
    }

    @Test
    public void distanceToCentralOfOriginIsCorrect() {
        SubCell s = new SubCell(0, 0);
        assertEquals(16, s.distanceToCentral());
    }

    @Test
    public void neighborsOfOriginAreCorrect() {
        SubCell c = new SubCell(0, 0);
        assertEquals(new SubCell(  0, 207), c.neighbor(Direction.N));
        assertEquals(new SubCell(  1,   0), c.neighbor(Direction.E));
        assertEquals(new SubCell(  0,   1), c.neighbor(Direction.S));
        assertEquals(new SubCell(239,   0), c.neighbor(Direction.W));
    }

    @Test
    public void containingCellOfCentralsNeighborIsCorrect() {
        for (Cell c: Cell.ROW_MAJOR_ORDER) {
            SubCell s = SubCell.centralSubCellOf(c);
            for (Direction d: Direction.values())
                assertEquals(c, s.neighbor(d).containingCell());
        }
    }
}
