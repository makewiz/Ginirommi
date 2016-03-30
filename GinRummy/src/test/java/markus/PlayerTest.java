/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import markus.ginrummy.logiikka.Player;
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
    private ServerSocket sSocket;
    private BufferedReader serverRead;
    private PrintWriter serverWrite;
    
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
            Socket dSocket =sSocket.accept();
            serverWrite = new PrintWriter(new OutputStreamWriter(dSocket.getOutputStream()));
            serverRead = new BufferedReader(new InputStreamReader(dSocket.getInputStream()));
            player = new Player("name", socket);
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
        player.printString(print);
        try {
            assertEquals(print, serverRead.readLine());
        } catch (IOException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
