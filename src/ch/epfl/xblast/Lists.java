package ch.epfl.xblast;

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
            List<T> l1 = new ArrayList<>(l);
            List<T> subList=new ArrayList<>(l1.subList(0, l1.size()-1));
            Collections.reverse(subList);
            l1.addAll(subList);
            return l1;
        }
    }
    
    /**
     * Recursive function that returns a list of all possible permutations of the elements of the given list
     * 
     * @param l
     * 		The list from which we do all the possible permutations
     * 
     * @return
     * 		The list of lists containing all possible combinations of the elements of the given list
     */
    public static <T> List<List<T>> permutations(List<T> l){
        List<T> copy = new ArrayList<>(l);//create new copy of the list l
        List<List<T>> list = new ArrayList<>();
        int s1 = copy.size();
        if (s1<2){//s1 = 0, add an empty list to the list and return it, if s1 = 1 add the list with one element (no need to permute) and return the list
            list.add(copy);
            return list;
        }
        if (s1==2){//if the size of the list = 2, add a list with the first element and then the second, and then a list with the second element and then the first
            list.add(new ArrayList<>(copy));
            Collections.reverse(copy);
            list.add(new ArrayList<>(copy));
            return list;
        }
        List<List<T>> l1 = new ArrayList<>();
        l1.addAll(permutations(copy.subList(1, s1)));//recursive list to get all possible permutations
        List<T> temp;
        T el = copy.get(0);
        for (List<T> t : l1){//add all elements to a list that will be returned
            for (int i=0;i<s1;++i){
                temp= new ArrayList<>(t);
                temp.add(i,el);
                list.add(temp);
            }
        }
        return list;
    }
}
