/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Palvelinyhteyden lukija ja kirjoittaja.
 *
 * @author Markus
 */
public class ReaderWriter {

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    /**
     *
     * @param socket
     * @throws IOException
     */
    public ReaderWriter(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
    }

    /**
     *
     * @return @throws IOException
     */
    public String read() throws IOException {
        return in.readLine();
    }

    /**
     *
     * @param s
     */
    public void print(String s) {
        out.println(s);
    }

    /**
     *
     * @return
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     *
     * @return
     */
    public PrintWriter getOut() {
        return out;
    }

}
