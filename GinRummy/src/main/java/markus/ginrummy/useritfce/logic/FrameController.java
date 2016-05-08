/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.useritfce.logic;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
 * Clientin ruutuja hallitseva ajettava sÃ¤ie.
 *
 * @author Markus
 */
public class FrameController extends Thread {

    private ReaderWriter client;

    /**
     * Luo uuden ruudunhallintasÃ¤ikeen mÃ¤Ã¤rÃ¤tyllÃ¤ palvelinyhteydellÃ¤.
     *
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
                        updatePlayerPanel(playerPanel, lobby);
                        lobby.validate();
                    } else if (fromServer.startsWith("/kutsu:")) {
                        String name = fromServer.substring(7);
                        JPanel invite = new JPanel();
                        invite.setLayout(new FlowLayout());
                        JLabel inviteText = new JLabel("Pelaaja " + name + " lähetti pelikutsun. Hyväksytäänkö");
                        JLabel acceptLabel = new JLabel("Kyllä");
                        JLabel denyLabel = new JLabel("Ei");
                        inviteText.setFont(inviteText.getFont().deriveFont(24.0f));
                        acceptLabel.setFont(acceptLabel.getFont().deriveFont(24.0f));
                        denyLabel.setFont(denyLabel.getFont().deriveFont(24.0f));
                        acceptLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                client.print("/1");
                                client.print("/1");
                            }

                        });
                        denyLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                client.print("/2");
                                client.print("/2");
                            }

                        });
                        acceptLabel.setForeground(Color.green);
                        denyLabel.setForeground(Color.red);
                        invite.add(acceptLabel);
                        invite.add(denyLabel);
                        panel.add(inviteText);
                        panel.add(invite);
                        lobby.validate();
                        panel.scrollRectToVisible(invite.getBounds());
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
     *
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

    private void updatePlayerPanel(JPanel playerPanel, LobbyScreen lobby) throws IOException {
        playerPanel.removeAll();
        while (true) {
            String fromServer = client.read();
            if (fromServer.equals("Loppu")) {
                break;
            } else {
                JLabel label = new JLabel(fromServer);
                label.setFont(label.getFont().deriveFont(24.0f));
                String s = fromServer;

                JPopupMenu menu = new JPopupMenu();
                JMenuItem startItem = new JMenuItem("Aloita peli pelaajan " + fromServer + " kanssa");
                startItem.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        client.print("/start " + s);
                    }
                });

                JMenuItem messageItem = new JMenuItem("LÃ¤hetÃ¤ yksityisviesti");
                messageItem.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        client.print("/" + s + ":" + lobby.messageField().getText());
                    }
                });
                menu.add(messageItem);
                menu.add(startItem);
                label.add(menu);
                label.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            doPop(e);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            doPop(e);
                        }
                    }

                    private void doPop(MouseEvent e) {
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }

                });
                playerPanel.add(label);
            }
        }
    }

}
