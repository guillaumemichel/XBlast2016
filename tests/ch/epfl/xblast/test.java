package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {
        ArrayList<String> s = new ArrayList<>();
        ArrayList<String> s1 = new ArrayList<>(Arrays.asList("coucou"));
        ArrayList<String> s2 = new ArrayList<>(Arrays.asList("coucou","hello"));
        ArrayList<String> s3 = new ArrayList<>(Arrays.asList("coucou","hello","bite"));
        List<List<String>> a = new ArrayList<>();
        List<List<Integer>> a1 = new ArrayList<>();
        List<Integer> s4 = new ArrayList<>();
        List<Integer> s5 = new ArrayList<>();
        /*a=Lists.permutations(s);
        System.out.println(a);
        a=Lists.permutations(s1);
        System.out.println(a);
        a=Lists.permutations(s2);
        System.out.println(a);*/
        a=Lists.permutations(s3);
        System.out.println(a);
        
        long startTime = System.currentTimeMillis();
        List<List<Integer>> t = Lists.permutations(l);
        long endTime   = System.currentTimeMillis();
        System.out.println("Temps de calcul: " + (endTime-startTime) + " ms");
    }

}
