/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.useritfce.logic;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.DefaultCaret;
import markus.ginrummy.gameobjects.Card;
import markus.ginrummy.logic.net.Client;
import markus.ginrummy.gameobjects.Suit;
import markus.ginrummy.useritfce.frames.GameScreen;
import markus.ginrummy.useritfce.frames.LobbyScreen;
import markus.ginrummy.useritfce.graphics.PlayingCard;

/**
 *
 * @author Markus
 */
public class FrameController extends Thread {

    private Client client;
    private JScrollPane container;
    private JFrame frame;
    private boolean multiPlayer;

    public FrameController(Client client, JScrollPane j, JFrame frame) {
        this.client = client;
        container = j;
        this.frame = frame;
        multiPlayer = true;
    }

    public FrameController(Client client) {
        multiPlayer = false;
    }

    @Override
    public void run() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        if (multiPlayer) {

            container.setViewportView(panel);

            while (true) {
                try {
                    String fromServer = client.read();
                    if (fromServer != null) {

                        JLabel label = new JLabel(fromServer);
                        label.setFont(label.getFont().deriveFont(24.0f));

                        panel.add(label);
                        frame.validate();
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

            frame.setVisible(false);
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
                        }
                        JLabel label = new JLabel(fromServer);
                        label.setFont(label.getFont().deriveFont(24.0f));

                        panel.add(label);
                        lobby.validate();
                        if (fromServer.equals("Tervetuloa peliin.")) {
                            break;
                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(FrameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            lobby.setVisible(false);
        }
        GameScreen gameScreen = new GameScreen(client);
        container = gameScreen.getMessageChain();
        container.setViewportView(panel);
        gameScreen.setVisible(true);
        JPanel handPanel = gameScreen.getHandPanel();
        handPanel.setLayout(new BoxLayout(handPanel, BoxLayout.X_AXIS));
        JPanel openDeckPanel = gameScreen.getOpenDeckPanel();
        openDeckPanel.setLayout(new FlowLayout());
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
                    } else if (fromServer.startsWith("Pöytäkortti: &")) {
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
                    } else if (fromServer.equals("xxx")) {
                        client.print("/xxx");
                    } else {
                        JLabel label = new JLabel(fromServer);
                        label.setFont(label.getFont().deriveFont(24.0f));
                        panel.add(label);
                        gameScreen.validate();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(FrameController.class.getName()).log(Level.SEVERE, null, ex);
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
