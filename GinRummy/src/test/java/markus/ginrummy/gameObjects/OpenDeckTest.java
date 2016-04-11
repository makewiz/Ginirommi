/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameObjects;

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
public class OpenDeckTest {
    
    private OpenDeck deck;
    
    public OpenDeckTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        deck = new OpenDeck();
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
    public void addingCardWorks() {
        Card c = new Card(Suit.HERTTA, 5);
        deck.addCard(c);
        assertEquals(c, deck.getCards().get(0));
        
    }
    
    @Test
    public void removingCardWorks() {
        Card c = new Card(Suit.HERTTA, 5);
        deck.addCard(c);
        assertEquals(c, deck.getCards().get(0));
        Card d = deck.takeCard();
        assertEquals(d, c);
        assertEquals(0, deck.getCards().size());
    }
    
    @Test
    public void topCardWorks() {
        Card c = new Card(Suit.HERTTA, 5);
        deck.addCard(c);
        assertEquals(c, deck.topCard());        
    }
}
