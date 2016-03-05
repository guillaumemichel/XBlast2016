package ch.epfl.xblast;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListsTest {

    @Test
    public void mirroredWorksCorrectly() {
        List<Integer> trueOne= Arrays.asList(1, 2, 3, 2, 1);
        List<Integer> testOne= Lists.mirrored(Arrays.asList(1, 2, 3));
        
        assertTrue(trueOne.size()==testOne.size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void mirroredThrowsExceptionIfEmpty() {
        List<String> trueOne= Lists.mirrored(Arrays.asList());
    }

}
