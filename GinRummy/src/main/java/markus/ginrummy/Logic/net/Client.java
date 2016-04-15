/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logic.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import markus.ginrummy.gameobjects.Player;
import markus.ginrummy.logic.game.GameThread;

/**
 *
 * @author Markus
 */
public class Client {

    private String hostName;
    private int portNumber;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private boolean local;
    private String fromGame;
    private String fromClient;

    public Client(String hostName, int port) throws IOException {
        this.hostName = hostName;
        this.portNumber = port;
        socket = new Socket(hostName, portNumber);
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        local = false;
    }
    
    public Client() {
        local = true;
    }

    public String read() throws IOException {
        if (local) {
            String read = fromGame;
            return read;
        } else {
            String read = in.readLine();
            return read;
        }
    }

    public void print(String s) {
        if (local) {
            fromClient = s;
        } else {
            out.println(s);            
        }

    }

    public void setFromGame(String fromGame) {
        this.fromGame = fromGame;
    }

    public void setFromClient(String fromClient) {
        this.fromClient = fromClient;
    }

    public String getFromClient() {
        return fromClient;
    }
    
    


    
    

}
