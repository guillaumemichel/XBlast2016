package ch.epfl.xblast.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.HashMap;
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
    
    private final Map<Integer,PlayerAction> keyMap;
    private final Consumer<PlayerAction> c;
    
    /**
     * Constructs a keyboard event handler from a key map and a consumer
     * 
     * @param keyMap
     * 			The map that contains the meaning of each key/command on the keyboard
     * @param c
     * 			The consumer
     */
    public KeyboardEventHandler(Map<Integer,PlayerAction> keyMap, Consumer<PlayerAction> c){
        this.keyMap = Collections.unmodifiableMap(new HashMap<>(keyMap));
        this.c=c;
    }
    
    @Override
    public void keyPressed(KeyEvent k){
        if (keyMap.containsKey(k.getKeyCode()))
        	c.accept(keyMap.get(k.getKeyCode()));
    }
}
