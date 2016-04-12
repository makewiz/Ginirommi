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
import markus.ginrummy.gameobjects.Card;
import markus.ginrummy.gameobjects.OpenDeck;
import markus.ginrummy.gameobjects.Suit;
import markus.ginrummy.gameobjects.ValueComparator;
import markus.ginrummy.logic.net.Client;
import markus.ginrummy.useritfce.graphics.PlayingCard;

/**
 *
 * @author Markus
 */
public class Bot extends Thread {

    private Client client;
    private ArrayList<Card> hand;
    private OpenDeck open;
    private HandTools tool;

    public Bot(Client client) throws IOException {
        String host = "localhost";
        this.client = client;
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
                if (fromServer.equals("Nostatko kortin.") || fromServer.equals("Nostatko avopakasta vai umpipakasta?.")) {
                    int firstMinus = tool.calculateMinus(hand);
                    hand.add(open.topCard());
                    int secondMinus = tool.calculateMinus(hand);
                    if (firstMinus > secondMinus) {
                        client.print("/e");
                    } else {
                        client.print("/k");
                    }
                }
                if (fromServer.equals("Valitse poistettava kortti komennolla: '/numero'")) {
                    ArrayList<ArrayList<Card>> sets = tool.chooseSets(hand);
                    ArrayList<Card> clonedHand = (ArrayList<Card>) hand.clone();
                    for (ArrayList<Card> set : sets) {
                        clonedHand.removeAll(set);
                    }
                    ValueComparator comp = new ValueComparator();
                    clonedHand.sort(comp);
                    Card toRemove = clonedHand.get(clonedHand.size() - 1);
                    int idx = hand.indexOf(toRemove);
                    client.print("/" + idx);
                }
                if (fromServer.startsWith("Jos poistat kortin: ")) {
                    client.print("/k");
                }
                if (fromServer.startsWith("Kierros: ")) {
                    open = new OpenDeck();
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
