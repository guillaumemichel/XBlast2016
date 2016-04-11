package ch.epfl.xblast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CellTest1 {
    @Test
    public void rowMajorIndexCorrespondsToOrder() {
        int i = 0;
        for (Cell c: Cell.ROW_MAJOR_ORDER)
            assertEquals(i++, c.rowMajorIndex());
        assertEquals(Cell.COUNT, i);
    }

    @Test
    public void spiralOrderContainsAllCells() {
        assertEquals(Cell.COUNT, Cell.SPIRAL_ORDER.size());

        boolean[] cellSeen = new boolean[Cell.COUNT];
        for (Cell c: Cell.SPIRAL_ORDER) {
            assertFalse(cellSeen[c.rowMajorIndex()]);
            cellSeen[c.rowMajorIndex()] = true;
        }
    }

    @Test
    public void spiralOrderNeighborsAreSpatialNeighbors() {
        Cell pred = Cell.SPIRAL_ORDER.get(0);
        for (Cell c: Cell.SPIRAL_ORDER.subList(1, Cell.SPIRAL_ORDER.size())) {
            int areNeighborsCount = 0;
            for (Direction d: Direction.values()) {
                if (pred.equals(c.neighbor(d)))
                    areNeighborsCount += 1;
            }
            assertEquals(1, areNeighborsCount);
            pred = c;
        }
    }

    @Test
    public void constructorCorrectlyNormalizesCoordinates() {
        for (int i = -2; i <= 2; ++i) {
            Cell c = new Cell(14 + 15 * i, 12 + 13 * i);
            assertEquals(14, c.x());
            assertEquals(12, c.y());
        }
    }

    @Test
    public void neighborsOfOriginAreCorrect() {
        Cell c = new Cell(0, 0);
        assertEquals(new Cell( 0, 12), c.neighbor(Direction.N));
        assertEquals(new Cell( 1,  0), c.neighbor(Direction.E));
        assertEquals(new Cell( 0,  1), c.neighbor(Direction.S));
        assertEquals(new Cell(14,  0), c.neighbor(Direction.W));
    }

    @Test
    public void oppositeNeighborOfNeighborIsThis() {
        for (Cell c: Cell.ROW_MAJOR_ORDER) {
            for (Direction d: Direction.values()) {
                assertEquals(c, c.neighbor(d).neighbor(d.opposite()));
            }
        }
    }
}
