package markus.ginrummy.gameObjects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import markus.ginrummy.gameobjects.Card;
import markus.ginrummy.gameobjects.Suit;
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
                Card c = new Card(s,i);
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
                Card c = new Card(Suit.RUUTU, i);
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
        Card c = new Card(Suit.HERTTA, 1);
        assertEquals(c.toString(), "HERTTA:1");
    }
    
    @Test
    public void valueStringPresentation() {
        Card a = new Card(Suit.HERTTA, 1);
        Card k = new Card(Suit.HERTTA, 13);
        Card q = new Card(Suit.HERTTA, 12);
        Card j = new Card(Suit.HERTTA, 11);
        Card c = new Card(Suit.HERTTA, 10);
        assertEquals("A", a.valueString());
        assertEquals("K", k.valueString());
        assertEquals("Q", q.valueString());
        assertEquals("J", j.valueString());
        assertEquals("10", c.valueString());
    }
}
