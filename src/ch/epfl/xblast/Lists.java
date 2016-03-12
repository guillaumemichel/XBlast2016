package ch.epfl.xblast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lists
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class Lists {
    
    private Lists(){}
    
    /**
     * Returns a symmetric version of a given list
     * 
     * @param l
     *      The list from which we compute the symmetric version
     *      
     * @return
     *      The symmetric version of the given list
     * 
     * @throws IllegalArgumentException
     *      If the given list is empty
     */
    public static <T> List<T> mirrored(List<T> l) throws IllegalArgumentException{
        if(l.isEmpty()){
            throw new IllegalArgumentException();
        }else{
            List<T> subList=new ArrayList<T>(l.subList(0, l.size()-1));
            Collections.reverse(subList);
            l.addAll(subList);
            return l;
        }
    }
    
    public static <T> List<List<T>> permutations(List<T> l){
        List<List<T>> l1 = new ArrayList<List<T>>();
        if (l.size()<2) {
            l1.add(l);
            return l1;
        }
        if (l.size()==2){
            l1.add(l);
            List<T> l2 = new ArrayList<>();
            l2.add(l.get(1));
            l2.add(l.get(0));
            l1.add(l2);
            return l1;
        }
        return recursivePermutations(l);
    }
    
    public static <T> List<List<T>> recursivePermutations(List<T> l){
        ArrayDeque<T> copy = new ArrayDeque<T>(l);
        ArrayDeque<T> el = new ArrayDeque<T>();
        ArrayDeque<T> temp;
        System.out.println(l);
        ArrayList<ArrayList<T>> list = new ArrayList<>();
        while (copy.size()>2){
            el.addFirst(copy.getFirst());
            copy.removeFirst();
        }System.out.println(el);
        while (!el.isEmpty()){
            temp = new ArrayDeque<>(copy);
            for (int i=0;i<copy.size();++i){
                for (int j=0;j<i;++j){
                    temp.addFirst(temp.getLast());
                    temp.removeLast();
                }
                temp.addFirst(el.getFirst());
                el.removeFirst();
                for(int j=0;j<copy.size()+1-i;++j){
                    temp.addFirst(temp.getLast());
                    temp.removeLast();
                }
                list.add(new ArrayList<T>(temp));
            }
        }
        return null;
    }
}
