package ch.epfl.xblast.client;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;

public final class Main {
    private static XBlastComponent component = new XBlastComponent();

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            SocketAddress chaussette = new InetSocketAddress(args.length==0 ? "localhost": args[0],2016);
            SwingUtilities.invokeAndWait(() -> createUI(channel, chaussette));

            channel.configureBlocking(false);
            
            ByteBuffer bjoin = joinGame(channel, chaussette);
            PlayerID id = PlayerID.values()[bjoin.get()];
            
            List<Byte> firstState = new ArrayList<>();
            while(bjoin.hasRemaining())
                firstState.add(bjoin.get());
            
            System.out.println(firstState.toString());
            component.setGameState(GameStateDeserializer.deserializeGameState(firstState), id);
            

            ByteBuffer currentState = ByteBuffer.allocate(409);
            List<Byte> list = new ArrayList<>();
            channel.configureBlocking(true);
            while (true){
                channel.receive(currentState);
                while (currentState.hasRemaining())
                    list.add(currentState.get());
                component.setGameState(GameStateDeserializer.deserializeGameState(list), id);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
    }
    
    private static void createUI(DatagramChannel channel, SocketAddress chaussette){
        JFrame frame = new JFrame("XBlast 2016");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(component);
        frame.pack();
        
        Map<Integer, PlayerAction> kb = new HashMap<>();
        kb.put(KeyEvent.VK_UP, PlayerAction.MOVE_N);
        kb.put(KeyEvent.VK_DOWN, PlayerAction.MOVE_S);
        kb.put(KeyEvent.VK_LEFT, PlayerAction.MOVE_W);
        kb.put(KeyEvent.VK_RIGHT, PlayerAction.MOVE_E);
        kb.put(KeyEvent.VK_SPACE, PlayerAction.DROP_BOMB);
        kb.put(KeyEvent.VK_SHIFT, PlayerAction.STOP);
        ByteBuffer send = ByteBuffer.allocate(1);
        Consumer<PlayerAction> c = x -> {    
            try {
                channel.send(send.put((byte)x.ordinal()), chaussette);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        component.addKeyListener(new KeyboardEventHandler(kb, c));
        component.requestFocusInWindow();
        
        frame.setVisible(true);
    }
    
    private static ByteBuffer joinGame(DatagramChannel channel,SocketAddress chaussette) {
        ByteBuffer join = ByteBuffer.allocate(1);
        ByteBuffer firstState = ByteBuffer.allocate(410);
        join.put((byte)PlayerAction.JOIN_GAME.ordinal()).flip();
        try {
            do
                channel.send(join, chaussette);
            while(channel.receive(firstState)==null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstState;
    }

}
