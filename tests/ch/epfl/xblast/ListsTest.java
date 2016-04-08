package ch.epfl.xblast;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListsTest {

    @Test
    public void mirroredWorksCorrectly() {

        List<String> testOne= new ArrayList<>();
        testOne.add("k");
        testOne.add("a");
        testOne.add("y");
        List<String> trueOne = new ArrayList<String>();
        trueOne.add("k");
        trueOne.add("a");
        trueOne.add("y");
        trueOne.add("a");
        trueOne.add("k");
        testOne = Lists.mirrored(testOne);
        
        for (int i=0;i<testOne.size();++i)
            assertEquals(testOne.get(i), trueOne.get(i));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void mirroredThrowsExceptionIfEmpty() {
        List<String> trueOne= Lists.mirrored(Arrays.asList());
    }

}
