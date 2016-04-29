/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.useritfce.logic;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import markus.ginrummy.gameobjects.Card;
import markus.ginrummy.net.ReaderWriter;
import markus.ginrummy.gameobjects.Suit;
import markus.ginrummy.useritfce.frames.GameScreen;
import markus.ginrummy.useritfce.frames.LobbyScreen;
import markus.ginrummy.useritfce.frames.LoginScreen;
import markus.ginrummy.useritfce.graphics.CardBack;
import markus.ginrummy.useritfce.graphics.PlayingCard;

/**
 * Clientin ruutuja hallitseva ajettava säie.
 * @author Markus
 */
public class FrameController extends Thread {

    private ReaderWriter client;

    /**
     * Luo uuden ruudunhallintasäikeen määrätyllä palvelinyhteydellä.
     * @param client
     */
    public FrameController(ReaderWriter client) {
        this.client = client;
    }

    @Override
    public void run() {
        LoginScreen login = new LoginScreen(client);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        login.getTheContainer().setViewportView(panel);
        login.setVisible(true);

        while (true) {
            try {
                String fromServer = client.read();
                if (fromServer != null) {

                    JLabel label = new JLabel(fromServer);
                    label.setFont(label.getFont().deriveFont(24.0f));

                    panel.add(label);
                    login.validate();
                    if (fromServer.equals("NameRegistered")) {
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(FrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        LobbyScreen lobby = new LobbyScreen(client);
        panel = lobby.messagePanel();
        JPanel playerPanel = lobby.playerPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

        login.setVisible(false);
        lobby.setVisible(true);

        while (true) {
            try {
                String fromServer = client.read();
                if (fromServer != null) {
                    if (fromServer.equals("Players online:.")) {
                        playerPanel.removeAll();
                        while (true) {
                            fromServer = client.read();
                            if (fromServer.equals("Loppu")) {
                                break;
                            } else {
                                JLabel label = new JLabel(fromServer);
                                label.setFont(label.getFont().deriveFont(24.0f));
                                playerPanel.add(label);
                            }
                        }
                        lobby.validate();
                    } else {
                        if (panel.getComponentCount() >= 30) {
                            panel.remove(0);
                        }                        
                        JLabel label = new JLabel(fromServer);
                        label.setFont(label.getFont().deriveFont(24.0f));

                        panel.add(label);
                        lobby.validate();
                        panel.scrollRectToVisible(label.getBounds());
                        lobby.validate();
                    }
                    if (fromServer.equals("Tervetuloa peliin.")) {
                        break;
                    }
                    if (fromServer.equals("/stcmd")) {
                        client.print("/stcmd");
                    }
                    if (fromServer.endsWith("liittyi")) {
                        client.print("/update");
                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(FrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        GameScreen gameScreen = new GameScreen(client);
        lobby.setVisible(false);

        JScrollPane container = gameScreen.getMessageChain();
        container.setViewportView(panel);
        container.setAutoscrolls(true);
        gameScreen.setVisible(true);
        JPanel handPanel = gameScreen.getHandPanel();
        handPanel.setLayout(new BoxLayout(handPanel, BoxLayout.X_AXIS));
        JPanel openDeckPanel = gameScreen.getOpenDeckPanel();
        openDeckPanel.setLayout(new FlowLayout());
        JPanel deckPanel = gameScreen.getDeckPanel();
        deckPanel.setLayout(new FlowLayout());
        CardBack cardBack = new CardBack(client);
        deckPanel.add(cardBack);
        while (true) {
            try {
                String fromServer = client.read();
                if (fromServer != null) {
                    if (fromServer.equals("Korttisi:")) {
                        handPanel.removeAll();
                        int idx = 0;
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
                                PlayingCard card = new PlayingCard(c, client, idx);
                                handPanel.add(card);
                                idx++;
                            }

                        }
                        handPanel.validate();
                        gameScreen.validate();
                    } else if (fromServer.startsWith("Poytakortti: &")) {
                        String[] messageSplit = fromServer.split("&");
                        String[] cardSplit = messageSplit[1].split(":");
                        String suit = cardSplit[0];
                        String value = cardSplit[1];
                        int v = Integer.parseInt(value);
                        Suit s = importSuit(suit);
                        Card c = new Card(s, v);
                        PlayingCard card = new PlayingCard(c, client);
                        openDeckPanel.removeAll();
                        openDeckPanel.add(card);
                        openDeckPanel.validate();
                        gameScreen.validate();
                    } else {
                        if (panel.getComponentCount() >= 30) {
                            panel.remove(0);
                        }
                        JLabel label = new JLabel(fromServer);
                        label.setFont(label.getFont().deriveFont(24.0f));
                        panel.add(label);
                        gameScreen.validate();
                        panel.scrollRectToVisible(label.getBounds());
                        gameScreen.validate();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(FrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Muuntaa maan merkkijonosta enum muotoon.
     * @param suit maa merkkijonomuodossa.
     * @return
     */
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
