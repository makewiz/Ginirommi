/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.net;

import markus.ginrummy.gameobjects.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import markus.ginrummy.gameLogic.GameThread;

/**
 * Palvelimen tiettyä clienttiä palveleva säie.
 * @author Markus
 */
public class MultiServerThread extends Thread {

    private Socket socket = null;
    private List<Player> players;
    private Player player = null;
    private ArrayList<MultiServerThread> threads;
    private MultiServerThread connectingThread;
    private GameThread gameThread;
    private ReaderWriter reader;

    /**
     *
     * @param socket
     * @param players
     * @param threads
     */
    public MultiServerThread(Socket socket, ArrayList<Player> players, ArrayList<MultiServerThread> threads) {
        super("MultiServerThread");
        this.socket = socket;
        this.players = players;
        this.threads = threads;
        try {
            reader = new ReaderWriter(socket);
        } catch (IOException ex) {
            Logger.getLogger(MultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public static String newline = System.getProperty("line.separator");

    public void run() {

        try {
            PrintWriter out = reader.getOut();
            BufferedReader in = reader.getIn();
            out.println("Tervetuloa ohjelmaan...");
            String name;
            while (true) {
                out.println("Anna pelaajalle nimi:");
                name = in.readLine();
                if (name.length() >= 3) {
                    boolean notUsed = true;
                    for (Player p : players) {
                        if (p.getName().equals(name)) {
                            notUsed = false;
                        }
                    }
                    if (notUsed) {
                        break;
                    } else {
                        out.println("Nimi on jo käytössä.");
                    }
                } else {
                    out.println("Nimi tulee olla vähintään 3 merkkiä pitkä.");
                }
            }
            out.println("NameRegistered");
            out.println("Tervetuloa " + name);
            out.println();

            player = new Player(name, socket);
            Player playerTwo;
            players.add(player);
            for (Player p : players) {
                if (p != this.player) {
                    sendStringTo(p.getSocket(), "Pelaaja: " + this.player.getName() + " liittyi");
                }
            }
            //printing players
            out.println("Players online:.");
            printPlayers(out);
            out.println();
            printCommands(out);
            out.println();
            while (true) {
                String command = in.readLine();
                if (connectingThread != null && player.getState() == 1) {
                    while (true) {
                        if (player.getState() == 0) {
                            break;
                        }
                        String message = in.readLine();
                        if (message != null && message.equals("/xxx")) {
                            synchronized(gameThread) {
                                gameThread.wait();
                            }
                        } else if (message != null && !message.startsWith("/")) {
                                sendStringTo(connectingThread.getSocket(), this.player.getName() + ": " + message);
                                sendStringTo(socket, this.player.getName() + ": " + message);
                        }
                    }
                    continue;
                } else if (command.startsWith("/")) {
                    if (command.startsWith("/start")) {
                        String playerName = command.substring(7);
                        MultiServerThread connectTo = null;
                        for (MultiServerThread t : threads) {
                            if (t.getPlayer() != null && t.getPlayer().getName().equals(playerName)) {
                                connectTo = t;
                            }
                        }
                        if (connectTo == null) {
                            out.println("Pelaajanimeä ei löytynyt");
                            continue;
                        }
                        if (connectTo == this) {
                            out.println("Et voi valita itseäsi.");
                            continue;
                        }
                        MultiServerThread falsePlayer = connectTo;
                        if (falsePlayer.getPlayer().getState() > 0) {
                            out.println("Pelaaja on jo pelissä.");
                            continue;
                        }
                        if (falsePlayer.getPlayer() != player) {
                            out.println("Lähetetään kutsu...");
                            playerTwo = falsePlayer.getPlayer();
                            try (
                                    Socket connecting = falsePlayer.getSocket();
                                    PrintWriter out2 = new PrintWriter(connecting.getOutputStream(), true);
                                    BufferedReader in2 = new BufferedReader(
                                            new InputStreamReader(connecting.getInputStream()));) {
                                falsePlayer.connect(this);
                                playerTwo.setState(1);
                                player.setState(1);
                                String test;
                                while (true) {
                                    out2.println("Pelaaja " + player.getName() + " tahtoo aloittaa pelin. Hyväksytaanko pyynto?");
                                    out2.println("Komennot: /1: kyllä, /2: ei:");
                                    out2.println("/stcmd");
                                    test = in2.readLine();
                                    if (test.equals("/1") || test.equals("/2")) {
                                        break;
                                    } else {
                                        out2.println("Väärä komento.");
                                    }
                                }
                                if (test.equals("/2")) {
                                    playerTwo.setState(0);
                                    player.setState(0);
                                    out.println("Ei hyväksytty.");
                                    synchronized (this) {
                                        notify();
                                    }
                                } else {
                                    synchronized (this) {
                                        notify();
                                    }
                                    gameThread = new GameThread(player, playerTwo);
                                    falsePlayer.setGameThread(gameThread);
                                    gameThread.start();
                                    while (true) {
                                        if (player.getState() == 0) {
                                            break;
                                        }
                                        String message = in.readLine();
                                        if (message != null && message.equals("/xxx")) {
                                            synchronized(gameThread) {
                                                gameThread.wait();
                                            }
                                        } else if (message != null && !message.startsWith("/")) {
                                                sendStringTo(connecting, this.player.getName() + ": " + message);
                                                sendStringTo(socket, this.player.getName() + ": " + message);
                                        }
                                                                        
                                    }
                                }

                            } catch (Exception e) {
                                out.println(e.getLocalizedMessage());
                                out.println(e.getStackTrace());
                            }
                        }
                    } else if (command.startsWith("/cmdlist")) {
                        printCommands(out);
                    } else if (command.equals("/stop")) {
                        break;
                    } else if (command.equals("/update")) {
                        out.println();
                        out.println("Players online:.");
                        //                    for (Player p : players) {
                        //                        out.println("    " + players.indexOf(p) + "    " + p.getName() + "    " + p.status());
                        //                    }
                        printPlayers(out);
                    } else if (command.contains(":")) {
                        String[] splitCommand = command.split(":");
                        String playerName = splitCommand[0].substring(1);
                        String message = splitCommand[1];
                        Socket messageSocket = null;
                        for (Player p : players) {
                            if (p.getName().equals(playerName) && p.getSocket() != this.getSocket()) {
                                messageSocket = p.getSocket();
                            }
                        }
                        if (messageSocket == null) {
                            out.println("Pelaajanimeä ei löytynyt");
                            continue;
                        }
                        String toSend = this.player.getName() + ": " + message;
                        sendStringTo(messageSocket, toSend);
                    } else {
                        out.println("Väärä komento");
                    }
                } else {
                    for (Player p : players) {
                        sendStringTo(p.getSocket(), this.player.getName() + ": " + command);                        
                    }
                }
            }

            out.println("Kiitos! Paina entteriä lopettaaksesi.");
            String lopetus = in.readLine();
            out.println("Bye.");
            players.remove(player);
            for (Player p : players) {
                if (p != this.player) {
                    sendStringTo(p.getSocket(), "Pelaaja: " + this.player.getName() + "poistui");
                }
            }
            socket.close();
        } catch (IOException e) {
            e.getLocalizedMessage();

        } catch (InterruptedException ex) {
            Logger.getLogger(MultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     *
     * @param thread
     * @throws InterruptedException
     */
    public void waitingGame(MultiServerThread thread) throws InterruptedException {
        synchronized (thread) {
            thread.wait();
        }
    }

    /**
     *
     * @param thread
     */
    public void connect(MultiServerThread thread) {
        connectingThread = thread;
    }

    /**
     *
     * @param connection
     * @param string
     */
    public void sendStringTo(Socket connection, String string) {
        try {
            ReaderWriter r = new ReaderWriter(connection);
            r.print(string);
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param out
     */
    public void printPlayers(PrintWriter out) {
        if (threads != null) {
            for (MultiServerThread t : threads) {
                if (t != null && t.getPlayer() != null) {
                    out.println("    " + threads.indexOf(t) + "    " + t.getPlayer().getName() + "    " + t.getPlayer().status());
                }
            }
            out.println("Loppu");
        }
    }

    /**
     *
     * @param out
     */
    public void printCommands(PrintWriter out) {
        out.println("Komennot aloitetaan merkillä: '/'"
                + newline + "Kirjoita ilman etuliitettä lähettääksesi viesti kaikille"
                + newline + "Yksityisviesti kirjoitetaan komennolla: '/nimi:teksti'"
                + newline + "Päivitä pelaajalista: '/update'"
                + newline + "Kutsu peliin: '/start playerName'"
                + newline + "Komentolista: '/cmdlist'"
                + newline + "Lopeta: '/stop' ");
    }

    /**
     *
     * @param thread
     */
    public void setGameThread(GameThread thread) {
        gameThread = thread;
    }

}
