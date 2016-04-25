/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logic.game;

import markus.ginrummy.gameobjects.OpenDeck;
import markus.ginrummy.gameobjects.Player;
import markus.ginrummy.gameobjects.Deck;
import markus.ginrummy.gameobjects.Card;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Markus
 */
public class GameThread extends Thread {

    private Deck deck;
    private OpenDeck open;
    private ArrayList<Player> players;
    private Random random;
    private HandTools tool;
    private int playerInt;

    public GameThread(Player first, Player second) {

        deck = new Deck();
        open = new OpenDeck();
        players = new ArrayList<>();
        players.add(first);
        players.add(second);
        random = new Random();
        tool = new HandTools();
        playerInt = 0;
    }

    public static String newline = System.getProperty("line.separator");

    @Override
    public void run() {

        printBoth("Tervetuloa peliin.");
        printBoth("Voit lähettää viestin kirjoittamalla tavallisesti.");
        int starter = random.nextInt(2);
        int round = 1;
        printBoth("Aloittajaksi arvottiin: " + currentPlayer().getName());
        while (true) {
            playerInt = starter;
            deck = new Deck();
            open = new OpenDeck();
            deck.shuffle(10);

            for (Player p : players) {
                p.emptyHand();
                for (int i = 0; i < 10; i++) {
                    p.addCard(deck.takeCard());
                }
                tool.sortHand((ArrayList<Card>) p.getHand());
                p.printCards();
            }

            open.addCard(deck.takeCard());
            printBoth("Kierros: " + round);
            printBoth("Poytakortti: &" + open.topCard().toString());

            boolean second = false;
            while (true) {
                otherPlayer().printString("Odotetaan pelaajan: " + currentPlayer().getName() + " päätöstä.");
                currentPlayer().printString("Nostatko kortin.");
                String a = null;
                a = currentPlayer().readString();
                synchronized (this) {
                    notifyAll();
                }

                if (a != null && a.equals("/k")) {
                    currentPlayer().addCard(open.takeCard());
                    otherPlayer().printString(currentPlayer().getName() + " nosti pöytäkortin.");
                    break;
                } else if (a != null && a.equals("/e")) {
                    otherPlayer().printString(currentPlayer().getName() + " ei nostanut pöytäkorttia.");
                    changePlayer();
                    if (second) {
                        currentPlayer().printString("Sinun on pakko nostaa pakasta");
                        Card token = deck.takeCard();
                        currentPlayer().addCard(token);
                        currentPlayer().printString("Sait kortin: " + token.toString());
                        break;
                    }
                    second = true;
                }
            }
            tool.sortHand((ArrayList<Card>) currentPlayer().getHand());
            currentPlayer().printCards();
            currentPlayer().printString("Valitse poistettava kortti komennolla: '/numero'");
            String a = null;
            while (true) {
                a = currentPlayer().readString();
                synchronized (this) {
                    notifyAll();
                }
                if (a != null && a.matches("/[0-9]|/10")) {
                    int remowable = Integer.parseInt(a.substring(1));
                    open.addCard(currentPlayer().removeCard(remowable));
                    currentPlayer().printCards();
                    changePlayer();
                    break;
                }
            }

            while (true) {
                printBoth("Pelaajan " + currentPlayer().getName() + " vuoro");
                printBoth("Poytakortti: &" + open.topCard().toString());
                currentPlayer().printString("Nostatko avopakasta vai umpipakasta?.");
                while (true) {
                    String option = currentPlayer().readString();                 
                    synchronized (this) {
                        notifyAll();
                    }
                    if (option != null && option.equals("/k")) {
                        currentPlayer().addCard(open.takeCard());
                        break;
                    } else if (option != null && option.equals("/e")) {
                        Card token = deck.takeCard();
                        currentPlayer().addCard(token);
                        currentPlayer().printString("Sait kortin: " + token.toString());
                        break;
                    }
                }
                tool.sortHand((ArrayList<Card>) currentPlayer().getHand());
                currentPlayer().printCards();
                int minus = tool.calculateMinus((ArrayList<Card>) currentPlayer().getHand());
                currentPlayer().printString("Miinuspisteesi tällä hetkellä: " + minus);
                int minusTwo = tool.calculateMinus((ArrayList<Card>) otherPlayer().getHand());
                if (minus == 0) {

                    printBoth(currentPlayer().getName() + " Sai ison ginin! Bonus 31 pistettä ja jäännöspisteet: " + minusTwo);
                    currentPlayer().addPoints(31 + minusTwo);
                    starter = nextPlayer(starter);
                    break;
                } else {
                    ArrayList<Card> hand = (ArrayList<Card>) currentPlayer().getHand();
                    ArrayList<Card> tempHand;
                    int bestMinus = 0;
                    int idx = 0;
                    Card toRemove = null;
                    int x = 0;
                    for (Card c : hand) {
                        tempHand = (ArrayList<Card>) hand.clone();
                        tempHand.remove(c);
                        int cminus = tool.calculateMinus(tempHand);
                        if (x == 0) {
                            bestMinus = cminus;
                            toRemove = c;
                            idx = hand.indexOf(c);
                        } else {
                            if (cminus < bestMinus) {
                                bestMinus = cminus;
                                toRemove = c;
                                idx = hand.indexOf(c);
                            }
                        }
                        x++;
                    }
                    if (bestMinus <= 10) {
                        currentPlayer().printString("Jos poistat kortin: " + toRemove.toString() + " miinuspisteesi on: " + bestMinus + newline
                                + " Haluatko lopettaa?");
                        boolean stop = false;
                        while (true) {
                            String option = currentPlayer().readString();                        
                            synchronized (this) {
                                notifyAll();
                            }

                            if (option != null && option.equals("/k")) {
                                open.addCard(currentPlayer().removeCard(idx));
                                if (bestMinus == 0) {
                                    printBoth(currentPlayer().getName() + "Sai ginin! Bonus 25 pistettä ja jäännöspisteet: " + minusTwo);
                                    currentPlayer().addPoints(25 + minusTwo);
                                } else {
                                    printBoth(currentPlayer().getName() + " Koputti jäännöspisteillä: " + bestMinus + newline
                                            + otherPlayer().getName() + "n jäännöspisteet ovat: " + minusTwo);
                                    if (minusTwo <= bestMinus) {
                                        printBoth(currentPlayer().getName() + "lla oli enemmän, tai yhtäpaljon jäännöspisteitä kuin " + newline
                                                + otherPlayer().getName() + "lla, joten " + otherPlayer().getName() + " Saa altalyönnistä 25 bonuspistettä + " + (bestMinus - minusTwo) + " jäännöspistettä");
                                        otherPlayer().addPoints(25 + bestMinus - minusTwo);
                                    } else {
                                        printBoth(currentPlayer().getName() + " Sai " + (minusTwo - bestMinus) + " pistettä.");
                                        currentPlayer().addPoints(minusTwo - bestMinus);
                                    }
                                }
                                starter = nextPlayer(starter);
                                stop = true;
                                break;
                            } else if (option != null && option.equals("/e")) {
                                break;
                            }
                        }
                        if (stop) {
                            break;
                        }
                    }
                }
                currentPlayer().printString("Valitse poistettava kortti komennolla: '/numero'");
                a = null;
                while (true) {
                    a = currentPlayer().readString();              
                    synchronized (this) {
                        notifyAll();
                    }

                    if (a != null && a.matches("/[0-9]|/10")) {
                        int remowable = Integer.parseInt(a.substring(1));
                        open.addCard(currentPlayer().removeCard(remowable));
                        tool.sortHand((ArrayList<Card>) currentPlayer().getHand());
                        currentPlayer().printCards();
                        minus = tool.calculateMinus((ArrayList<Card>) currentPlayer().getHand());
                        currentPlayer().printString("Miinuspisteesi tällä hetkellä: " + minus);
                        changePlayer();
                        break;
                    }
                }
            }
            round++;
            Player winner = null;
            Player loser = null;
            for (Player p : players) {
                if (p.getPoints() >= 100) {
                    winner = p;
                    loser = players.get(nextPlayer(players.indexOf(p)));
                }
            }
            if (winner != null && loser != null) {
                printBoth(winner.getName() + " Saavutti sadan pisteen rajan " + winner.getPoints() + " pisteellä. Onnea voittajalle"
                        + newline + loser.getName() + " Saavutti " + loser.getPoints() + " pistettä.");
                for (Player p : players) {
                    p.resetPoints();
                }
                break;
            }
        }
        synchronized (this) {
            notify();
        }
        for (Player p : players) {
            p.setState(0);
        }

        System.out.println("Valmista");
    }

    public void printBoth(String string) {
        for (Player p : players) {
            p.printString(string);
        }
    }

    public int nextPlayer(int current) {
        if (current == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public Player currentPlayer() {
        return players.get(playerInt);
    }

    public Player otherPlayer() {
        return players.get(nextPlayer(playerInt));
    }

    public void changePlayer() {
        playerInt = nextPlayer(playerInt);
    }
}
