/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameObjects;

import markus.ginrummy.gameobjects.Card;
import markus.ginrummy.gameobjects.Suit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import markus.ginrummy.gameobjects.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Markus
 */
public class PlayerTest {
    
    private Player player;
    private BufferedReader clientRead;
    private BufferedWriter clientWrite;
    private Socket playerSocket;
    
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            ServerSocket sSocket = new ServerSocket(0);
            Socket socket = new Socket("localhost", sSocket.getLocalPort());
            playerSocket =sSocket.accept();
            clientWrite = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            player = new Player("name", playerSocket);
                    } catch (IOException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void getNameWorks() {
        player.getName().equals("name");
    }
    
    @Test
    public void printingWorks() {
        String print = "test";
        player.getOut().println(print);
        try {
            assertEquals(print, clientRead.readLine());
        } catch (IOException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void handWorks() {
        Card c = new Card(Suit.HERTTA, 5);
        Card d = new Card(Suit.HERTTA, 6);
        player.addCard(c);
        player.addCard(d);
        assertEquals(c, player.getHand().get(0));
        assertEquals(d, player.getHand().get(1));
        assertEquals(2, player.getHand().size());
        player.removeCard(0);
        assertEquals(d, player.getHand().get(0));
        assertEquals(1, player.getHand().size());
        player.emptyHand();
        assertEquals(0, player.getHand().size());
    }
    
    @Test
    public void printCardsWorks() {
        Card c = new Card(Suit.HERTTA, 5);
        player.printCards();
        try {
            assertEquals("Korttisi:", clientRead.readLine());
            assertEquals("Loppu", clientRead.readLine());
        } catch (IOException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void getSocketWorks() {
        assertEquals(playerSocket, player.getSocket());
    }
    
    @Test
    public void statesWork() {
        
        assertEquals(0, player.getState());
        player.setState(1);
        assertEquals(1, player.getState());
    }
    
    
    
}
