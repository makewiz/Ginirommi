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
import markus.ginrummy.net.ReaderWriter;

/**
 * Pelin pelaaja olio, joka koostuu Palvelinta lukevasta ja kirjoittavasta oliosta.
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

    /**
     * Luo pelaajan halutulla nimnellä ja socket yhteydellä palvelimeen.
     * @param name Pelaajan nimi.
     * @param socket Palvelinyhteys.
     */
    public Player(String name, Socket socket) {
        this.name = name;
        hand = new ArrayList<>();
        this.points = 0;
        state = 0;
        this.socket = socket;
        try {
            ReaderWriter r = new ReaderWriter(socket);
            out = r.getOut();
            in = r.getIn();
        } catch (Exception e) {
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Lisää pelaajan käteen annetun kortin.
     * @param c Käteen lisättävä kortti.
     */
    public void addCard(Card c) {
        hand.add(c);
    }

    public List<Card> getHand() {
        return hand;
    }

    /**
     * Lisää pelaajan pistetilille halutun määrän pisteitä.
     * @param x Lisättävien pisteiden määrä kokonaislukuna.
     */
    public void addPoints(int x) {
        points = points + x;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Poistaa pelaajan kädestä halutusta kohdasta kortin.
     * @param index Poistettavan kortin indeksi.
     * @return Palauttaa poistetun kortin.
     */
    public Card removeCard(int index) {
        Card token = hand.get(index);
        hand.remove(token);
        return token;
    }

    /**
     * Asettaa pelaajan tilan kokonaislukuarvona.
     * @param state pelaajan tilaa kuvaava kokonaislukuarvo.
     */
    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    /**
     * Palauttaa pelaajan tilan merkkijonona.
     * @return pelaajan tila merkkijonona.
     */
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

    /**
     * Asettaa pelaajan nimen.
     * @param name pelaajan nimi.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Palauttaa Palvelinkirjoittimen.
     * @return palvelinkirjoitin PrintWriter muodossa.
     */
    public PrintWriter getOut() {
        return out;
    }

    /**
     * Palauttaa palvelinlukijan.
     * @return Lukjija BufferedReader muodossa.
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     * Kirjoittaa clientille halutun merkkijonon.
     * @param string Kirjoitettava merkkijono.
     */
    public void printString(String string) {
        out.println(string);
    }

    /**
     * Lukee clientiltä merkkijonon.
     * @return Palauttaa luetun merkkijonon.
     */
    public String readString() {
        String read = null;
        try {
            read = in.readLine();
        } catch (IOException ex) {
        }
        return read;
    }

    /**
     * Tulostaa clientille pelaajan kortit.
     */
    public void printCards() {
        out.println("Korttisi:");
        for (Card c : hand) {
            out.println(c.toString());
        }
        out.println("Loppu");
    }

    /**
     * Tyhjentää pelaajan käden.
     */
    public void emptyHand() {
        hand.clear();
    }

    /**
     * Nollaa pelaajan pistetilin.
     */
    public void resetPoints() {
        points = 0;
    }
}
