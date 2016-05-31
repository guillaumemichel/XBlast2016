package ch.epfl.xblast.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.UnknownHostException;
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
import ch.epfl.xblast.server.painter.ExplosionPainter;

/**
 * Server that will host the game
 * 
 * @author Guillaume Michel (258066)
 * @author Adrien Vandenbroucque (258715)
 *
 */
public final class Server {
    /**
     * Duration of the game in minutes
     */
    public static int GAME_DURATION = 2;
    private static DatagramChannel channel;
    private static ByteBuffer receivingBuffer;
    private static Map<SocketAddress, PlayerID> players;
    private static SocketAddress senderAddress;
    private static int currentPlayerID;
    
    /**
     * Initialize a server
     * 
     * @param time
     *      Duration of the game
     */
    public final static void init(int time){
        GAME_DURATION = time;
        receivingBuffer = ByteBuffer.allocate(1);
        try {
            channel = DatagramChannel.open(StandardProtocolFamily.INET);
            channel.bind(new InetSocketAddress(ch.epfl.xblast.client.Client.DEFAULT_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // We fill a map of players with the players that are trying to
        // join the game
        System.out.println("Finding peers ...");
        players = new HashMap<>();
        currentPlayerID = 0;
    }
    
    /**
     * Connect the server to the clients
     * 
     * @return
     *      the number of connected players
     */
    public final static int connect(){
        try {
            senderAddress = channel.receive(receivingBuffer);
            if (!players.containsKey(senderAddress) && receivingBuffer.get(0) == PlayerAction.JOIN_GAME.ordinal()) {
                players.put(senderAddress, PlayerID.values()[currentPlayerID]);
                receivingBuffer.clear();
                ++currentPlayerID;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players.size();
    }
    
    /**
     * Run the game
     * 
     * @param g
     *      The gamestate of the game
     */
    public final static void game(GameState g){
        try {
            System.out.println("Launching game ...");

            long startTime;
            long remainingTime;
            BoardPainter b = Level.DEFAULT_LEVEL.boardPainter();
            Map<PlayerID, Optional<Direction>> speedChangeEvents = new HashMap<>();
            Set<PlayerID> bombDropEvents = new HashSet<>();

            channel.configureBlocking(false);

            SocketAddress senderAdress;
            ByteBuffer playerBuffer;
            ByteBuffer sendingBuffer;
            List<Byte> serializedGameState;

            boolean bool = true;

            while (bool) {
                startTime = System.nanoTime();

                // We serialize the current game state
                serializedGameState = GameStateSerializer.serialize(b, g);
                sendingBuffer = ByteBuffer.allocate(serializedGameState.size() + 1);

                sendingBuffer.position(1);
                for (Byte bytes : serializedGameState)
                    sendingBuffer.put(bytes);

                // We send the game state to each player
                for (Map.Entry<SocketAddress, PlayerID> player : players.entrySet()) {
                    playerBuffer = sendingBuffer.duplicate();
                    playerBuffer.put(0, (byte) player.getValue().ordinal());
                    playerBuffer.flip();
                    channel.send(playerBuffer, player.getKey());
                    playerBuffer.clear();
                }

                // We receive the action made b the players (move or bomb drop)
                speedChangeEvents.clear();
                bombDropEvents.clear();
                while ((senderAdress = channel.receive(receivingBuffer)) != null) {
                    if (receivingBuffer.get(0) == PlayerAction.DROP_BOMB.ordinal()) {
                        bombDropEvents.add(players.get(senderAdress));
                    } else {
                        if (receivingBuffer.get(0) == PlayerAction.STOP.ordinal())
                            speedChangeEvents.put(players.get(senderAdress),Optional.empty());
                        else if (receivingBuffer.get(0) != PlayerAction.JOIN_GAME.ordinal())
                            speedChangeEvents.put(players.get(senderAdress),Optional.of(
                                    Direction.values()[receivingBuffer.get(0)- 1]));
                    }
                    receivingBuffer.clear();
                }

                // If there is some remaining time before the "tick nanosecond
                // duration" ends, we "sleep"
                remainingTime = Ticks.TICK_NANOSECOND_DURATION
                        - (System.nanoTime() - startTime);
                if (remainingTime > 0)
                    Thread.sleep(
                            (long) (remainingTime * Time.MS_PER_S/ Time.NS_PER_S),
                            (int) (remainingTime % (Time.NS_PER_S / Time.MS_PER_S)));

                if (g.isGameOver())
                    bool = false;
                // We compute the next game state by giving the actions of the
                // players
                g = g.next(speedChangeEvents, bombDropEvents);
            }

            if (g.winner().isPresent())
                System.out.println("The winner is "+g.winner().get());
            else
                System.out.println("No winner !");

            byte end = (ExplosionPainter.byteForBlast(g.players().get(0).isAlive(), 
                    g.players().get(1).isAlive(), g.players().get(2).isAlive(), g.players().get(3).isAlive()));
            ByteBuffer send = ByteBuffer.allocate(1);
            for (Map.Entry<SocketAddress, PlayerID> player : players.entrySet()){
                send.put(end);
                send.flip();
                channel.send(send, player.getKey());
                send.clear();
            }
            channel.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Close the server channel to get ready to start a new server
     */
    public final static void closeChannel(){ 
        try {
            channel.close();
            System.out.println("Channel closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get the ip address of the server
     * 
     * @return
     *      A string containing the ip of the server
     */
    public final static String getIp(){
        try {
            return InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}
