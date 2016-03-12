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
        return permutationsBis(l);
    }
    
    public static <T> List<List<T>> permutationsBis(List<T> l){
        ArrayDeque<T> copy = new ArrayDeque<T>(l);
        ArrayDeque<T> el = new ArrayDeque<T>();
        ArrayDeque<T> temp;
        List<List<T>> list = new ArrayList<>();
        while (copy.size()>2){
            el.addFirst(copy.getFirst());
            copy.removeFirst();
        }System.out.println(el+" el");
        while (!el.isEmpty()){
            for (int i=0;i<copy.size();++i){
                temp = new ArrayDeque<>(copy);
                for (int j=0;j<i;++j){
                    temp.addFirst(temp.getLast());
                    temp.removeLast();
                }
                temp.addFirst(el.getFirst());
                list.add(new ArrayList<T>(temp));
                for (int j=0;j<temp.size()-1;++j){
                    temp.addFirst(temp.getLast());
                    temp.removeLast();
                    list.add(new ArrayList<T>(temp));
                }
            }
            el.removeFirst();
        }
        return list;
    }
    
    public static <T> List<List<T>> recursionLists(List<List<T>> l,T el){
        ArrayList<List<T>> copy = new ArrayList<>(l);
        List<T> temp = new ArrayList<>();
        ArrayList<List<T>> list = new ArrayList<>();
        for (int i=0;i<copy.size();++i){
            for (int j=0;(j<i)&&(j<copy.get(i).size());++j){
                temp.add(copy.get(i).get(j));
            }
            temp.add(el);
            for (int j=0;j+i<copy.get(i).size();++i){
                temp.add(copy.get(i).get(i+j));
            }
            list.add(temp);
            temp.clear();
        }
        return list;
        
    }
}
