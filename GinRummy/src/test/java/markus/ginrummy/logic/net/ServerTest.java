/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logic.net;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ServerTest {
    
    private Server server;
    private ReaderWriter firstReader;
    private ReaderWriter secondReader;
    
    public ServerTest() {
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
            server.start();
            Socket a = new Socket("localhost", server.getPort());
            Socket b = new Socket("localhost", server.getPort());
            firstReader = new ReaderWriter(a);
            secondReader = new ReaderWriter(b);
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
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
    public void loginWorks() {
        try {
            assertEquals("Tervetuloa ohjelmaan...", firstReader.read());
            assertEquals("Anna pelaajalle nimi:", firstReader.read());
            firstReader.print("ee");
            assertEquals("Nimi tulee olla vähintään 3 merkkiä pitkä.", firstReader.read());
            assertEquals("Anna pelaajalle nimi:", firstReader.read());
            firstReader.print("first");
            secondReader.read();
            secondReader.read();
            secondReader.print("second");
            assertEquals("NameRegistered", secondReader.read());
            assertEquals("Tervetuloa second", secondReader.read());
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
