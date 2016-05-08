/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.net;

import markus.ginrummy.gameobjects.Player;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Palvelin, joka hallitsee kaikkia yhteyksiä.
 *
 * @author Markus
 */
public class Server extends Thread {

    private int portNumber;
    private ServerSocket serverSocket;

    /**
     *
     * @param portNumber
     * @throws IOException
     */
    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        serverSocket = new ServerSocket(portNumber);
    }

    @Override
    public void run() {
        boolean listening = true;

        ArrayList<MultiServerThread> threads = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();

        try {
            while (listening) {
                Socket socket = serverSocket.accept();
                MultiServerThread thread = new MultiServerThread(socket, players, threads);
                threads.add(thread);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }

    /**
     * Palauttaa palvelimen paikallisen portin.
     *
     * @return
     */
    public int getPort() {
        return serverSocket.getLocalPort();
    }

}
