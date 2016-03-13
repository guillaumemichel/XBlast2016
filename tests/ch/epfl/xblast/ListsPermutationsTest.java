package ch.epfl.xblast;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListsPermutationsTest {

    @Test
    public void test() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
        List<List<Integer>> check = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        List<List<Integer>> t = Lists.permutations(l);
        long endTime   = System.currentTimeMillis();
        System.out.println("Temps de calcul: " + (endTime-startTime) + " ms");
        /*int factorial=1;
        List<List<Integer>> t = Lists.permutationsBis(l);
        
        for(int i=2; i<=l.size(); i++){
            factorial*=i;
        }
        
        for(List<Integer> e:t){
            if(!check.contains(e)){
                check.add(e);
            }
        }
        
        assertEquals(check.size(), factorial);*/
    }

}
