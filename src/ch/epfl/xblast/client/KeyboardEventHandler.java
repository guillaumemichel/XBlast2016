package ch.epfl.xblast.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    Consumer<PlayerAction> c;
    
    /**
     * Build a keyboard event handler from a map and a consumer
     * 
     * @param map
     * 			The map that contains the meaning of each key/command on the keyboard
     * @param cons
     * 			The consumer
     */
    public KeyboardEventHandler(Map<Integer,PlayerAction> map, Consumer<PlayerAction> cons){
        keymap = Collections.unmodifiableMap(map);
        c=cons;
    }
    
    @Override
    public void keyPressed(KeyEvent k){
        if (keymap.containsKey(k.getKeyCode())){
        	c.accept(keymap.get(k.getKeyCode()));
        }
    }
}
