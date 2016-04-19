package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public final class RunLengthEncoder {
    
    private RunLengthEncoder(){}
    
    public static List<Byte> encode(List<Byte> sequence) throws IllegalArgumentException{
        List<Byte> encodedSequence=new ArrayList<>();
        int count=1;
        byte b, nextByte;
        
        for (int i=0;i<sequence.size();++i) {
            b=sequence.get(i);
            
            if(i<sequence.size()-1)
                nextByte=sequence.get(i+1);
            else
                nextByte=-1;
          
            
            if(b<0)
                throw new IllegalArgumentException();
            
            if(b==nextByte){
                ++count;
            }else{
                if(count==1 || count==2){
                    encodedSequence.addAll(Collections.nCopies(count, b));
                    count=1;
                }else{
                    encodedSequence.add((byte)(-count+2));
                    encodedSequence.add(b);
                    count=1;
                }
                    
            }   
        }
        return encodedSequence;
    }
    
    public static List<Byte> decode(List<Byte> sequence){
        List<Byte> decodedSequence=new ArrayList<>();
        int count;
        for (Byte b : sequence) {
            
            if(b<0)
                count=b;
            else
                count=1;
            
            decodedSequence.addAll(Collections.nCopies(count, b));
                
        }
        return null;
    }
}
