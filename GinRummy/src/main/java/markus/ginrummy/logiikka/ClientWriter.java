/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logiikka;

import java.awt.Container;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Markus
 */
public class ClientWriter extends Thread{
    
    private Client client;
    private JScrollPane container;
    private JFrame frame;

    public ClientWriter(Client client, JScrollPane j, JFrame frame) {
        this.client = client;
        container = j;
        this.frame = frame;
    }

    @Override
    public void run() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        container.setViewportView(panel);
        
        while (true) {
            try {
                String fromServer = client.read();
                if (fromServer != null) {
                    if (fromServer.equals("NameRegistered")) {
                        break;
                    }
                    JLabel label = new JLabel(fromServer);
                    label.setFont(label.getFont().deriveFont(24.0f));

                    panel.add(label);
                    frame.validate();
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        frame.setVisible(false);
    }
    
    
}
