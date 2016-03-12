package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {
        ArrayList<String> s = new ArrayList<>();
        ArrayList<String> s1 = new ArrayList<>();
        ArrayList<String> s2 = new ArrayList<>();
        ArrayList<String> s3 = new ArrayList<>();
        List<List<String>> a = new ArrayList<>();
        List<List<Integer>> a1 = new ArrayList<>();
        List<Integer> s4 = new ArrayList<>();
        List<Integer> s5 = new ArrayList<>();
        s4.add(2);
        s4.add(3);
        s5.add(3);
        s5.add(2);
        a1.add(s4);
        a1.add(s5);
        //System.out.println(Lists.recursionLists(a1, (Integer)1));
        s.add("yolo");
        s2.add("1");
        s2.add("2");
        s3.addAll(s2);
        s3.add("3");
        s3.add("4");
        //s3.add("5");
        a=Lists.permutations(s);
        //System.out.println(a);
        a=Lists.permutations(s1);
        //System.out.println(a);
        a=Lists.permutations(s2);
        //System.out.println(a);
        a=Lists.permutations(s3);
        System.out.println(s3);
        System.out.println(a.size()+" "+a);
        //s2.clear();
        //s2.addAll(Arrays.asList("1","2","3"));
        a=Lists.permutations(s2);
        //System.out.println(a);
        //Lists.recursivePermutations(s);
        //System.out.println(s+"\n"+s1+"\n"+s2);
    }

}
