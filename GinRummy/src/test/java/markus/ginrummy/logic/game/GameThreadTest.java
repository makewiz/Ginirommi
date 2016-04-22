/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logic.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import markus.ginrummy.gameobjects.Player;
import markus.ginrummy.logic.net.Server;
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
public class GameThreadTest {
    
    private Player firstPlayer;
    private Player secondPlayer;
    private GameThread gameThread;
    private Server server;
    
    
    public GameThreadTest() {
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
            server = new Server(0);
            Socket firstSocket = new Socket("localhost", server.getPort());
            firstPlayer = new Player("First", firstSocket);
            Socket secondSocket = new Socket("localhost", server.getPort());
            secondPlayer = new Player("Second", secondSocket);
            gameThread = new GameThread(firstPlayer, secondPlayer);
        } catch (IOException ex) {
            Logger.getLogger(GameThreadTest.class.getName()).log(Level.SEVERE, null, ex);
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
    public void changePlayerWorks() {
        Player a = gameThread.currentPlayer();
        gameThread.changePlayer();
        assertFalse(a == gameThread.currentPlayer());
        assertTrue(a == gameThread.otherPlayer());
        gameThread.changePlayer();
        assertTrue(a == gameThread.currentPlayer());
    }
}
