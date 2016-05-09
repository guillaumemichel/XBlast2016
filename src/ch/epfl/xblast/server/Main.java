package ch.epfl.xblast.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;

import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;

public final class Main {

    public static void main(String[] args) {
        int numberOfPlayers = GameState.PLAYER_NUMBER;
        int currentPlayerID = 0;
        Map<SocketAddress, PlayerID> players = new HashMap<>();
                    
        if(args.length==1)
            numberOfPlayers = Integer.parseInt(args[0]);
        
        try{
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            channel.bind(new InetSocketAddress(2016));
            
            ByteBuffer buffer = ByteBuffer.allocate(1);
            SocketAddress senderAddress;
            
            while(players.size()!= numberOfPlayers){
                senderAddress = channel.receive(buffer);
                if(buffer.get(0)== PlayerAction.JOIN_GAME.ordinal()){
                    players.put(senderAddress, PlayerID.values()[currentPlayerID]);
                    ++currentPlayerID;
                }
            }
                
            

        }catch(IOException e){
            
        }
    }

}
