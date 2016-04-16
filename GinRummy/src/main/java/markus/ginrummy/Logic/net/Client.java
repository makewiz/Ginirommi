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

    public Client(String hostName, int port) throws IOException {
        this.hostName = hostName;
        this.portNumber = port;
        socket = new Socket(hostName, portNumber);
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
    }

    public String read() throws IOException {
        return in.readLine();
    }

    public void print(String s) {
        out.println(s);
    }

}
