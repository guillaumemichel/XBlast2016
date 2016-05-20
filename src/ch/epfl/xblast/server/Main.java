package ch.epfl.xblast.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.Time;
import ch.epfl.xblast.server.painter.BoardPainter;

public final class Main {
    
    public static void main(String[] args) {
        //GameState g = new GameState(board, Level.DEFAULT_LEVEL.gameState().players());
        
        int numberOfPlayers = GameState.PLAYER_NUMBER;
                    
        if(args.length==1)
            numberOfPlayers = Integer.parseInt(args[0]);
        
        try {
            ByteBuffer receivingBuffer = ByteBuffer.allocate(1);
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            channel.bind(new InetSocketAddress(ch.epfl.xblast.client.Main.DEFAULT_PORT));
            
            System.out.println("Finding peers ...");
            Map<SocketAddress, PlayerID> players = joiningGame(numberOfPlayers, receivingBuffer, channel);
            System.out.println("Launching game ...");
            
            long startTime;
            long remainingTime;
            GameState g = Level.DEFAULT_LEVEL.gameState();
            BoardPainter b = Level.DEFAULT_LEVEL.boardPainter();
            Map<PlayerID, Optional<Direction>> speedChangeEvents = new HashMap<>();
            Set<PlayerID> bombDropEvents = new HashSet<>();
            
            channel.configureBlocking(false);
            
            SocketAddress senderAdress;
            ByteBuffer playerBuffer;
            ByteBuffer sendingBuffer;
            List<Byte> serializedGameState;
            
            boolean bool = true;
            
            while(bool){
                startTime = System.nanoTime();
                
                serializedGameState = GameStateSerializer.serialize(b, g);
                sendingBuffer = ByteBuffer.allocate(serializedGameState.size()+1);
                
                sendingBuffer.position(1);
                for(Byte bytes : serializedGameState)
                    sendingBuffer.put(bytes);
                                  
                for(Map.Entry<SocketAddress, PlayerID> player : players.entrySet()){
                    playerBuffer = sendingBuffer.duplicate();
                    playerBuffer.put(0, (byte) player.getValue().ordinal());
                    playerBuffer.flip();
                    channel.send(playerBuffer, player.getKey());
                    playerBuffer.clear();
                }
                
                speedChangeEvents.clear();
                bombDropEvents.clear();
                while((senderAdress = channel.receive(receivingBuffer)) != null){
                    if(receivingBuffer.get(0)==PlayerAction.DROP_BOMB.ordinal()){
                        bombDropEvents.add(players.get(senderAdress));
                    }else{
                        if(receivingBuffer.get(0)==PlayerAction.STOP.ordinal())
                            speedChangeEvents.put(players.get(senderAdress), Optional.empty());
                        else
                            if(receivingBuffer.get(0) != 0)
                            speedChangeEvents.put(players.get(senderAdress), Optional.of(Direction.values()[receivingBuffer.get(0)-1]));
                    }
                    receivingBuffer.clear();
                }
                
                remainingTime = Ticks.TICK_NANOSECOND_DURATION-(System.nanoTime()-startTime);
                if (remainingTime>0)
                    Thread.sleep((long) (remainingTime*Time.MS_PER_S/Time.NS_PER_S), (int) (remainingTime%(Time.NS_PER_S/Time.MS_PER_S)));
                if (g.isGameOver()) bool = false;
                g = g.next(speedChangeEvents, bombDropEvents);
            }
            
            if(g.winner().isPresent())
                System.out.println(g.winner().get());
            else
                System.out.println("No winner !");
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    
    private static Map<SocketAddress, PlayerID> joiningGame(int numberOfplayers, ByteBuffer receivingBuffer, DatagramChannel channel) throws IOException {
        Map<SocketAddress, PlayerID> players = new HashMap<>();
        int currentPlayerID = 0;
        
        SocketAddress senderAddress;
        
        while(players.size()!= numberOfplayers){
            senderAddress = channel.receive(receivingBuffer);
            if(!players.containsKey(senderAddress) && receivingBuffer.get(0)== PlayerAction.JOIN_GAME.ordinal()){
                players.put(senderAddress, PlayerID.values()[currentPlayerID]);
                receivingBuffer.clear();
                ++currentPlayerID;
            }
        }
        return players;
    }

}
