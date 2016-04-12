/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameObjects;

import java.util.ArrayList;
import markus.ginrummy.gameobjects.Card;
import markus.ginrummy.gameobjects.Deck;
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
public class DeckTest {
    
    private Deck deck;
    
    public DeckTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        deck = new Deck();
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
    public void hasAllCards() {
        assertEquals(52, deck.getCards().size());
        for (Suit s : Suit.values()) {
            for (int i = 1; i < 14; i++) {
                Card c = new Card(s, i);
                assertTrue(deck.getCards().contains(c));
            }
        }
    }
    
    @Test
    public void shuffleDoesNotAlterDeck() {
        ArrayList<Card> seconDeck = new ArrayList<>();
        seconDeck.addAll(deck.getCards());
        deck.shuffle(100);
        for (Card c : seconDeck) {
            assertTrue(deck.getCards().contains(c));
        }
        assertEquals(seconDeck.size(), deck.getCards().size());
    }
}
