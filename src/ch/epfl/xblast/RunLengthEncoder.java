package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A run-length encoder
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class RunLengthEncoder {
    
    private RunLengthEncoder(){}
    
    /**
     * Returns a list that contains the run-length encoded version of the given list of bytes
     * 
     * @param sequence
     *      The list of bytes to encode
     *      
     * @return
     *      The run-length encoded version of the given list of bytes
     *      
     * @throws IllegalArgumentException
     *      If an element of the given list is negative
     */
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
    
    /**
     * Returns the decoded version of the given list of bytes that is run-length encoded
     * 
     * @param sequence
     *      The run-length encoded list of bytes to decode
     *      
     * @return
     *      The decoded version of the given list of bytes
     *      
     * @throws IllegalArgumentException
     *      If the last element of the given list is negative
     */
    public static List<Byte> decode(List<Byte> sequence) throws IllegalArgumentException{
        List<Byte> decodedSequence=new ArrayList<>();
        Iterator<Byte> it=sequence.iterator();
        byte b;
        
        if(sequence.get(sequence.size()-1)<0)
            throw new IllegalArgumentException();
        
        while(it.hasNext()){
            b =it.next();
            
            if(b<0)
                decodedSequence.addAll(Collections.nCopies(-b+2, it.next()));
            else
                decodedSequence.add(b);
      
        }
        return decodedSequence;
    }
}
