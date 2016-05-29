package ch.epfl.xblast.client;

import java.awt.event.KeyEvent;
import java.io.IOException;
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

import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;

public class ClientBis {
    public final static int MAX_BUFFER_SIZE = 410;
    public final static int DEFAULT_PORT = 2016;
    
    private static DatagramChannel channel;
    private static SocketAddress chaussette;
    private static ByteBuffer firstState;
    private static ByteBuffer join;
    private static ByteBuffer currentState = ByteBuffer.allocate(MAX_BUFFER_SIZE);
    private static PlayerID id;
    private static List<Byte> list=new ArrayList<>();
    private static XBlastComponent component;
    private static byte returnValue;
    
    public final static void start(XBlastComponent component0) {
        firstState.flip();
        component = component0;
        id = PlayerID.values()[firstState.get()];
        while(firstState.hasRemaining())//transfer the buffer to a list
            list.add(firstState.get());
        component.setGameState(GameStateDeserializer.deserializeGameState(list), id);
        PlaySound.loop();
        list.clear();
        firstState.clear();
        try {
            channel.configureBlocking(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public final static byte play(){
        try {
            channel.receive(currentState);
            currentState.flip();//receive the gamestate
            while (currentState.hasRemaining())
                list.add(currentState.get());//transfert into a list
            if (list.size()<2){
                returnValue=list.get(0);
                list.clear();
                currentState.clear();
                channel.close();
                return returnValue;
            }
            component.setGameState(GameStateDeserializer.deserializeGameState(list.subList(1, list.size())), id);
            //display the deserialized gamestate to screen
            currentState.clear();
            list.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0x10;
    }
    
    public final static void initialize(){
        initialize("localhost");
    }
    
    public final static void initialize(String s){
        try {
            channel = DatagramChannel.open(StandardProtocolFamily.INET);
            channel.configureBlocking(false);
            chaussette = new InetSocketAddress(s, DEFAULT_PORT);
            join = ByteBuffer.allocate(1);
            firstState = ByteBuffer.allocate(MAX_BUFFER_SIZE);
            join.put((byte)PlayerAction.JOIN_GAME.ordinal()).flip();
            
            System.out.println("Connecting the server ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public final static SocketAddress connect(){
        try {
            channel.send(join, chaussette);
            Thread.sleep(1000);
            return channel.receive(firstState);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public final static void connectBis(String s){
        try {
            channel = DatagramChannel.open(StandardProtocolFamily.INET);
            channel.configureBlocking(false);
            chaussette = new InetSocketAddress(s, DEFAULT_PORT);
            join = ByteBuffer.allocate(1);
            firstState = ByteBuffer.allocate(MAX_BUFFER_SIZE);
            join.put((byte)PlayerAction.JOIN_GAME.ordinal()).flip();
            
            System.out.println("Connecting the server ...");
            do {//send the request to join the game until the server send a buffer in return
                channel.send(join, chaussette);
                Thread.sleep(1000);
            } while(channel.receive(firstState)==null);
            
            System.out.println("Connected");
            firstState.flip();
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    
    public final static Map<Integer, PlayerAction> getMap(){
        Map<Integer, PlayerAction> kb = new HashMap<>();
        kb.put(KeyEvent.VK_UP, PlayerAction.MOVE_N);
        kb.put(KeyEvent.VK_DOWN, PlayerAction.MOVE_S);
        kb.put(KeyEvent.VK_LEFT, PlayerAction.MOVE_W);
        kb.put(KeyEvent.VK_RIGHT, PlayerAction.MOVE_E);
        kb.put(KeyEvent.VK_SPACE, PlayerAction.DROP_BOMB);
        kb.put(KeyEvent.VK_SHIFT, PlayerAction.STOP);
        return kb;
    }
    
    public final static Consumer<PlayerAction> getConsumer(){
        Consumer<PlayerAction> c = x -> {
            try {//if a key in the map is pressed send the key event to the server
                ByteBuffer senderBuffer = ByteBuffer.allocate(1);
                senderBuffer.put((byte)x.ordinal());
                senderBuffer.flip();
                channel.send(senderBuffer, chaussette);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        return c;
    }
}
