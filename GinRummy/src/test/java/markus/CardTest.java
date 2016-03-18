package markus;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import markus.ginrummy.logiikka.Card;
import markus.ginrummy.logiikka.Suit;
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
public class CardTest {
    
    public CardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
    public void creatingWorks() {
        for (int i = 1; i < 14; i++) {
            for (Suit s : Suit.values()) {
                Card c = new Card(i,s);
                assertEquals(c.getValue(), i);
                assertEquals(c.getSuit(), s);
            }
        }
    }
    
    @Test
    public void illegalValue() {
        int i = 0;
        while (true) {
            boolean thrown = false;
            try {
                Card c = new Card(i, Suit.CLUBS);
            } catch(IllegalArgumentException e) {
                thrown = true;
                assertEquals(e.getMessage(), "Illegal playing card value");
            }

            assertTrue(thrown);
            if (i == 14) {
                break;
            }
            i = 14;
        }
    }
    
    @Test
    public void stringPresentation() {
        Card c = new Card(1, Suit.CLUBS);
        assertEquals(c.toString(), "1 of CLUBS");
    }
}
