/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.kayttoliittyma;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import markus.ginrummy.logiikka.Client;

/**
 *
 * @author Markus
 */
public class FrameController implements Runnable{
    
    private Client client;

    public FrameController(Client client) {
        this.client = client;
    }
    

    @Override
    public void run() {
        LoginScreen screen = new LoginScreen(client);
        JScrollPane c = screen.getTheContainer();
        ClientWriter writer = new ClientWriter(client, c, screen);
        writer.start();
        screen.setVisible(true);
        synchronized (writer) {
        try {
            writer.wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(FrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        screen.setVisible(false);
            
    }
    
}
