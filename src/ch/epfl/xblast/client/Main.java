package ch.epfl.xblast.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.channels.DatagramChannel;

public final class Main {

    public static void main(String[] args) {
        try {
            DatagramChannel channel =
                    DatagramChannel.open(StandardProtocolFamily.INET);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SocketAddress chaussette = new InetSocketAddress(args.length==0 ? args[0]:"localhost",2016);
    }

}
