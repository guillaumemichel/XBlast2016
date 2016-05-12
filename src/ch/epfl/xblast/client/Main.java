package ch.epfl.xblast.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;

public final class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeAndWait(() -> createUI());
        XBlastComponent component = new XBlastComponent();
        
        try {            
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            SocketAddress chaussette = new InetSocketAddress(args.length==0 ? "localhost": args[0],2016);
            channel.bind(chaussette).configureBlocking(false);
            List<Byte> firstState = new ArrayList<>();
            
            ByteBuffer bjoin = joinGame(channel, chaussette);
            PlayerID id = PlayerID.values()[bjoin.get()];
            
            while(bjoin.hasRemaining())
                firstState.add(bjoin.get());
            
            component.setGameState(GameStateDeserializer.deserializeGameState(firstState), id);
            

            
            channel.configureBlocking(true);
            while (true){
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private static void createUI(){
        JFrame frame = new JFrame("XBlast 2016");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(component);
        frame.pack();
        frame.setVisible(true);
    }
    
    private static ByteBuffer joinGame(DatagramChannel channel,SocketAddress chaussette) {
        ByteBuffer join = ByteBuffer.allocate(1);
        ByteBuffer firstState = ByteBuffer.allocate(410);
        SocketAddress serverAddress=null;
        int i=0;
        join.put((byte)PlayerAction.JOIN_GAME.ordinal()).flip();
        try {
            do {
                channel.send(join, chaussette);
                serverAddress = channel.receive(firstState);
                ++i;
            } while (serverAddress==null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(i);
        return firstState;
    }

}
