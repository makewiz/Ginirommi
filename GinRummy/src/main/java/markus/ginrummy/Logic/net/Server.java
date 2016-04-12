/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logic.net;

import markus.ginrummy.logic.net.MultiServerThread;
import markus.ginrummy.gameobjects.Player;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Markus
 */
public class Server extends Thread {

    private int portNumber;

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        boolean listening = true;

        ArrayList<MultiServerThread> threads = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
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

}
