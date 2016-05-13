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
    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            SocketAddress chaussette = new InetSocketAddress(args.length==0 ? "localhost": args[0],2016);
            XBlastComponent component = new XBlastComponent();
            
            channel.configureBlocking(false);
            
            ByteBuffer bjoin = joinGame(channel, chaussette);
            PlayerID id = PlayerID.values()[bjoin.get()];
            
            //System.out.println(id);
            
            List<Byte> firstState = new ArrayList<>();
            while(bjoin.hasRemaining())
                firstState.add(bjoin.get());
            component.setGameState(GameStateDeserializer.deserializeGameState(firstState), id);
            
            SwingUtilities.invokeAndWait(() -> createUI(channel, chaussette,component));
            
            ByteBuffer currentState = ByteBuffer.allocate(410);
            List<Byte> list = new ArrayList<>();
            channel.configureBlocking(true);
            while (true){
                channel.receive(currentState);
                currentState.flip();
                while (currentState.hasRemaining())
                    list.add(currentState.get());
                component.setGameState(GameStateDeserializer.deserializeGameState(list.subList(1, list.size())), id);
                currentState.clear();
                list.clear();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
    }
    
    private static void createUI(DatagramChannel channel, SocketAddress chaussette,XBlastComponent component){
        System.out.println("Create UI");
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
        Consumer<PlayerAction> c = x -> {    
            try {
                ByteBuffer senderBuffer = ByteBuffer.allocate(1);
                senderBuffer.put((byte)x.ordinal());
                senderBuffer.flip();
                channel.send(senderBuffer, chaussette);
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
        System.out.println("Connecting the server ...");
        try {
            do {
                channel.send(join, chaussette);
            }while(channel.receive(firstState)==null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connected");
        firstState.flip();
        return firstState;
    }

}
