package ch.epfl.xblast.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;

public final class Main {

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            SocketAddress chaussette = new InetSocketAddress(args.length==0 ? "localhost": args[0],2016);
            channel.bind(chaussette).configureBlocking(false);
            
            ByteBuffer b = joinGame(channel, chaussette);
            PlayerID id = PlayerID.values()[b.get(0)];
            List<Byte> firstState = new ArrayList<>();
            
            while (b.hasRemaining()){
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private static ByteBuffer joinGame(DatagramChannel channel,SocketAddress chaussette) {
        ByteBuffer join = ByteBuffer.allocate(1);
        ByteBuffer firstState = ByteBuffer.allocate(410);
        
        join.put((byte)PlayerAction.JOIN_GAME.ordinal()).flip();
        try {
            do {
                channel.send(join, chaussette);
                channel.receive(firstState);
            } while (!firstState.hasRemaining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstState;
    }

}
