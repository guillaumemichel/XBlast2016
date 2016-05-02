package ch.epfl.xblast.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import ch.epfl.xblast.PlayerAction;

/**
 * A keyboard event handler
 * 
 * @author Guilaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public class KeyboardEventHandler extends KeyAdapter implements KeyListener{
    
    Map<Integer,PlayerAction> keymap;
    Consumer c;
    
    public KeyboardEventHandler(Map<Integer,PlayerAction> map, Consumer<PlayerAction> cons){
        keymap = Collections.unmodifiableMap(map);
        c=cons;
    }
    
    @Override
    public void keyPressed(){
        
    }
}
