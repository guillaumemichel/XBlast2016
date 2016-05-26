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
import ch.epfl.xblast.menu.ModelMenu;

public class ClientBis {
    private DatagramChannel channel;
    private SocketAddress chaussette;
    private ByteBuffer firstState;
    private ByteBuffer join;
    ByteBuffer currentState = ByteBuffer.allocate(MAX_BUFFER_SIZE);
    private ModelMenu model;
    private Consumer<PlayerAction> c;
    PlayerID id;
    List<Byte> list=new ArrayList<>();
    XBlastComponent component;

    public final static int MAX_BUFFER_SIZE = 410;
    public final static int DEFAULT_PORT = 2016;
    
    public ClientBis(ModelMenu model){
        this.model=model;
        try {
            channel = DatagramChannel.open(StandardProtocolFamily.INET);
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public final void start(XBlastComponent component) {
        this.component = component;
        id = PlayerID.values()[firstState.get()];
        while(firstState.hasRemaining())//transfer the buffer to a list
            list.add(firstState.get());
        component.setGameState(GameStateDeserializer.deserializeGameState(list), id);
        PlaySound.play();
        list.clear();
        try {
            channel.configureBlocking(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public final void play(){
        try {
            channel.receive(currentState);
            currentState.flip();//receive the gamestate
            while (currentState.hasRemaining())
                list.add(currentState.get());//transfert into a list
            component.setGameState(GameStateDeserializer.deserializeGameState(list.subList(1, list.size())), id);
            //display the deserialized gamestate to screen
            currentState.clear();
            list.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public final void connect(){
        connect("localhost");
    }
    
    public final void connect(String s){
        chaussette = new InetSocketAddress(s, DEFAULT_PORT);
        model.getWaiting(2);
        join = ByteBuffer.allocate(1);
        firstState = ByteBuffer.allocate(MAX_BUFFER_SIZE);
        join.put((byte)PlayerAction.JOIN_GAME.ordinal()).flip();
        System.out.println("Connecting the server ...");
        try {
            do {//send the request to join the game until the server send a buffer in return
                channel.send(join, chaussette);
                Thread.sleep(1000);
            }while(channel.receive(firstState)==null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Connected");
        firstState.flip();
    }
    
    public final Map<Integer, PlayerAction> getMap(){
        Map<Integer, PlayerAction> kb = new HashMap<>();
        kb.put(KeyEvent.VK_UP, PlayerAction.MOVE_N);
        kb.put(KeyEvent.VK_DOWN, PlayerAction.MOVE_S);
        kb.put(KeyEvent.VK_LEFT, PlayerAction.MOVE_W);
        kb.put(KeyEvent.VK_RIGHT, PlayerAction.MOVE_E);
        kb.put(KeyEvent.VK_SPACE, PlayerAction.DROP_BOMB);
        kb.put(KeyEvent.VK_SHIFT, PlayerAction.STOP);
        return kb;
    }
    
    public final Consumer<PlayerAction> getConsumer(){
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
