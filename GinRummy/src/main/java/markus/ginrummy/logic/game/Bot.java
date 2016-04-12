/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logic.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import markus.ginrummy.gameObjects.Card;
import markus.ginrummy.gameObjects.OpenDeck;
import markus.ginrummy.gameObjects.Suit;
import markus.ginrummy.logic.net.Client;
import markus.ginrummy.userInterface.graphics.PlayingCard;

/**
 *
 * @author Markus
 */
public class Bot extends Thread{
    private Client client;
    private ArrayList<Card> hand;
    private OpenDeck open;
    private HandTools tool;

    public Bot(int port) throws IOException {
        String host = "localhost";
        client = new Client(host, port);
        hand = new ArrayList<>();
        tool = new HandTools();
    }
    
    

    @Override
    public void run() {
        while (true) {
            try {
                String fromServer = client.read();
                if (fromServer.equals("Korttisi:")) {
                    hand.clear();
                    while (true) {
                        fromServer = client.read();
                        if (fromServer.equals("Loppu")) {
                            break;
                        } else {

                            String[] split = fromServer.split(":");
                            String suit = split[0];
                            String value = split[1];
                            Suit s = importSuit(suit);
                            int v = Integer.parseInt(value);
                            Card c = new Card(s, v);
                            hand.add(c);
                        }
                    }
                }                
                if (fromServer.equals("Pöytäkortti: &")) {
                    String[] messageSplit = fromServer.split("&");
                    String[] cardSplit = messageSplit[1].split(":");
                    String suit = cardSplit[0];
                    String value = cardSplit[1];
                    int v = Integer.parseInt(value);
                    Suit s = importSuit(suit);
                    Card c = new Card(s, v);
                    open.addCard(c);
                }
                if (fromServer.equals("Nostatko kortin. Vastaa kyllä, tai ei.")) {
                    int minus = tool.calculateMinus(hand);
                    ArrayList<Card> clonedHand = (ArrayList<Card>) hand.clone();
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Suit importSuit(String suit) {
        Suit s;
        if (suit.equals("PATA")) {
            s = Suit.PATA;
        } else if (suit.equals("HERTTA")) {
            s = Suit.HERTTA;
        } else if (suit.equals("RUUTU")) {
            s = Suit.RUUTU;
        } else {
            s = Suit.RISTI;
        }
        return s;
    }    
    
}
