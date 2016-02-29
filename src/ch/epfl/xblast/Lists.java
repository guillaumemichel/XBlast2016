package ch.epfl.xblast;

import java.util.Collections;
import java.util.List;

public final class Lists {
    
    private Lists(){}
    
    public static <T> List<T> mirrored(List<T> l) throws IllegalArgumentException{
        if(l.isEmpty()){
            throw new IllegalArgumentException();
        }else{
            List<T> subList=l.subList(0, l.size());
            Collections.reverse(subList);
            l.addAll(subList);
            return l;
        }
    }
}
