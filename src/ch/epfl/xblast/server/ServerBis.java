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
import ch.epfl.xblast.server.painter.ExplosionPainter;

public final class ServerBis {
    /**
     * Duration of the game in minutes
     */
    public static int GAME_DURATION = 2;

    public final static void main(GameState g, int time, int numberOfPlayers) {
        GAME_DURATION = time;
        try {
            ByteBuffer receivingBuffer = ByteBuffer.allocate(1);
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
            channel.bind(new InetSocketAddress(ch.epfl.xblast.client.Client.DEFAULT_PORT));

            // We fill a map of players with the players that are trying to
            // join the game
            System.out.println("Finding peers ...");
            Map<SocketAddress, PlayerID> players = joiningGame(numberOfPlayers,receivingBuffer, channel);
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
                System.out.println(g.winner().get());
            else
                System.out.println("No winner !");

            ByteBuffer end = ByteBuffer.allocate(1);
            end.put(ExplosionPainter.byteForBlast(g.players().get(0).isAlive(), 
                    g.players().get(1).isAlive(), g.players().get(2).isAlive(), g.players().get(3).isAlive()));
            end.flip();
            for (Map.Entry<SocketAddress, PlayerID> player : players.entrySet())
                channel.send(end, player.getKey());
            channel.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Map<SocketAddress, PlayerID> joiningGame(int numberOfplayers,
            ByteBuffer receivingBuffer, DatagramChannel channel)
            throws IOException {
        Map<SocketAddress, PlayerID> players = new HashMap<>();
        int currentPlayerID = 0;

        SocketAddress senderAddress;

        while (players.size() != numberOfplayers) {
            senderAddress = channel.receive(receivingBuffer);
            if (!players.containsKey(senderAddress) && receivingBuffer.get(0) == PlayerAction.JOIN_GAME.ordinal()) {
                players.put(senderAddress, PlayerID.values()[currentPlayerID]);
                receivingBuffer.clear();
                ++currentPlayerID;
            }
        }
        return players;
    }
}
