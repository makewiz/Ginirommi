/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameobjects;

import markus.ginrummy.gameobjects.Card;
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
import markus.ginrummy.logic.game.Bot;
import markus.ginrummy.logic.net.Client;
import markus.ginrummy.useritfce.frames.GameScreen;
import markus.ginrummy.useritfce.logic.FrameController;

/**
 *
 * @author Markus
 */
public class Player implements Serializable {

    private List<Card> hand;
    private String name;
    private int points;
    private int state;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Client client;
    private boolean local;
    private FrameController controller;
    private GameScreen screen;
    private Bot bot;
    private boolean isBot;

    public Player(String name, Socket socket) {
        this.name = name;
        hand = new ArrayList<>();
        this.points = 0;
        state = 0;
        this.socket = socket;
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "UTF-8"));
        } catch (Exception e) {
        }
        local = false;

    }
    
    public Player(String name, FrameController controller, Client client, GameScreen screen) {
        this.name = name;
        hand = new ArrayList<>();
        this.points = 0;
        state = 0;
        this.controller = controller;
        this.client = client;
        local = true;
        this.screen = screen;
    }
    
    public Player(String name, Client client, Bot bot) {
        this.name = name;
        hand = new ArrayList<>();
        this.points = 0;
        state = 0;
        this.client = client;
        this.bot = bot;
        local = true;
        isBot = true;
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

    public Card removeCard(int index) {
        Card token = hand.get(index);
        hand.remove(token);
        return token;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public String status() {
        if (state > 0) {
            return "PelissÃ¤";
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
        if (local) {
            client.setFromGame(string);
            synchronized(this) {
                try {
                    if (isBot) {
                        bot.wait();
                    } else {
                        controller.wait();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            client.setFromGame(null);
        } else {
            out.println(string);
        }
    }

    public String readString() {
        if (local) {
            synchronized(this) {
                try {
                    if (isBot) {
                        bot.wait();
                    } else {
                        screen.wait();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String read = client.getFromClient();
            return read;
        } else {
            String read = null;
            try {
                read = in.readLine();
            } catch (IOException ex) {
            }
            return read;
        }
    }

    public void printCards() {
        if (local) {
            printString("Korttisi:");
            for (Card c : hand) {
                printString(c.toString());
            }
            printString("Loppu");            
        } else {
            out.println("Korttisi:");
            for (Card c : hand) {
                out.println(c.toString());
            }
            out.println("Loppu");
        }
    }

    public void emptyHand() {
        hand.clear();
    }

    public void resetPoints() {
        points = 0;
    }

    public Client getClient() {
        return client;
    }
    
    

}
