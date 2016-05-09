package ch.epfl.xblast.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.server.painter.BoardPainter;

public final class Main {

    public static void main(String[] args) {
        int numberOfPlayers = GameState.PLAYER_NUMBER;
                    
        if(args.length==1)
            numberOfPlayers = Integer.parseInt(args[0]);
        
        try {
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            channel.bind(new InetSocketAddress(2016));
            
            Map<SocketAddress, PlayerID> players = joiningGame(numberOfPlayers, channel);
            
            long currentTime = System.nanoTime();
            
            GameState g = Level.DEFAULT_LEVEL.gameState();
            BoardPainter b = Level.DEFAULT_LEVEL.boardPainter();
            
            while(!g.isGameOver()){
                
                List<Byte> serializedGameState = GameStateSerializer.serialize(b, g);
                ByteBuffer buffer = ByteBuffer.allocate(1);
                for(Byte bytes : serializedGameState)
                    buffer.put(bytes);
                buffer.flip();
                  
                for(SocketAddress address : players.keySet())
                    channel.send(buffer, address);
                
                currentTime+=Ticks.TICK_NANOSECOND_DURATION;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private static Map<SocketAddress, PlayerID> joiningGame(int numberOfplayers, DatagramChannel channel) throws IOException {
        Map<SocketAddress, PlayerID> players = new HashMap<>();
        int currentPlayerID = 0;
        
        ByteBuffer buffer = ByteBuffer.allocate(1);
        SocketAddress senderAddress;
        
        while(players.size()!= numberOfplayers){
            senderAddress = channel.receive(buffer);
            if(buffer.get(0)== PlayerAction.JOIN_GAME.ordinal()){
                players.put(senderAddress, PlayerID.values()[currentPlayerID]);
                ++currentPlayerID;
            }
        }
        return players;        
    }

}
