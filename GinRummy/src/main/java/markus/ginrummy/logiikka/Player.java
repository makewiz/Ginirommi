/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logiikka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class Player implements Serializable{
    private List<Card> hand;
    private String name;
    private int points;
    private int state;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Player(String name, Socket socket) {
        this.name = name;
        hand = new ArrayList<>();
        this.points = 0;
        state = 0;
        this.socket = socket;
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(
            socket.getInputStream(), "UTF-8"));
            this.out = out;
            this.in = in;
        } catch (Exception e) {            
        }

    }

    public String getName() {
        return name;
    }
   
    public void addCard(Card c) {
        hand.add(c);
    }

    public List<Card> getHand() {
        return hand;
    }
    
    public void addPoints(int x) {
        points = points + x;
    }

    public int getPoints() {
        return points;
    }
    
    public Card remoweCard(int index) {
        Card token = hand.get(index);
        hand.remove(token);
        return token;
    }
    
    public void switchPosition(int from, int to) {
        Card first = hand.get(from);
        Card second = hand.get(to);
        hand.remove(to);
        hand.remove(from);
        hand.add(from, second);
        hand.add(to, first);
    }
    
    public void shiftPostition(int from, int to) {
        Card token = hand.get(from);
        hand.remove(from);
        hand.add(to, token);
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
    
    public String status() {
        if (state > 0) {
            return "Pelissä";
        } else {
            return "Aulassa";
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }
    
    public void printString(String string) {
        out.println(string);
    }
    
    public String readString() {
        String read = null;
        try {
            read = in.readLine();
        } catch (IOException ex) {
        }
        return read;
    }
    
    public void printCards() {
        out.println("Korttisi:");
        for (Card c : hand) {
            out.println(c.toString());
        }
        out.println("Loppu");
    }
    
    public void emptyHand() {
        hand.clear();
    }
    
    public void resetPoints() {
        points = 0;
    }
    
}
