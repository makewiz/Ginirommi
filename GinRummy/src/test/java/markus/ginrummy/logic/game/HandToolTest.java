/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logic.game;

import java.util.ArrayList;
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
public class HandToolTest {
    
    private HandTools tool;
    
    public HandToolTest() {
      
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tool = new HandTools();  
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
    public void firstMinus() {
        
        Card a = new Card(Suit.HERTTA, 1);
        Card b = new Card(Suit.HERTTA, 2);
        Card c = new Card(Suit.HERTTA, 3);
        Card d = new Card(Suit.HERTTA, 4);
        Card e = new Card(Suit.RISTI, 4);
        Card f = new Card(Suit.PATA, 4);
        Card g = new Card(Suit.RUUTU, 4);
        Card h = new Card(Suit.RUUTU, 5);
        Card i = new Card(Suit.RUUTU, 6);
        Card j = new Card(Suit.HERTTA, 10);
        
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(a);
        hand.add(b);
        hand.add(c);
        hand.add(d);
        hand.add(e);
        hand.add(f);
        hand.add(g);
        hand.add(h);
        hand.add(i);
        hand.add(j);
        int minus = tool.calculateMinus(hand);
        assertEquals(10, minus);
    }
    
    @Test
    public void setCheckWorks() {
        Card d = new Card(Suit.HERTTA, 4);
        Card e = new Card(Suit.RISTI, 4);
        Card f = new Card(Suit.PATA, 4);

        ArrayList<Card> hand = new ArrayList<>();

        hand.add(d);
        hand.add(e);
        hand.add(f);
        assertTrue(tool.setCheck(hand));
    }
    
    @Test
    public void setCheck2() {
        Card d = new Card(Suit.HERTTA, 4);
        Card e = new Card(Suit.RISTI, 4);
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(d);
        hand.add(e);
        assertFalse(tool.setCheck(hand));
    }
    
    @Test
    public void StraightCheck1() {
        Card a = new Card(Suit.HERTTA, 1);
        Card b = new Card(Suit.HERTTA, 2);
        Card c = new Card(Suit.HERTTA, 3);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(a);
        hand.add(b);
        hand.add(c);
        assertTrue(tool.straightCheck(hand));
        
    }
    
    @Test
    public void StraightCheck2() {
        Card a = new Card(Suit.HERTTA, 1);
        Card b = new Card(Suit.HERTTA, 2);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(a);
        hand.add(b);
        assertFalse(tool.straightCheck(hand));
        
    }
    
    @Test
    public void multipleChoiceTest2() {
        Card c = new Card(Suit.HERTTA, 1);
        Card d = new Card(Suit.HERTTA, 2);
        Card e = new Card(Suit.HERTTA, 3);
        Card f = new Card(Suit.HERTTA, 4);
        Card g = new Card(Suit.PATA, 4);
        Card h = new Card(Suit.RUUTU, 4);
        Card i = new Card(Suit.RUUTU, 5);
        Card k = new Card(Suit.RUUTU, 6);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(e);
        hand.add(d);
        hand.add(c);
        hand.add(g);
        hand.add(h);
        hand.add(i);
        hand.add(k);
        HandTools tool = new HandTools();
        assertEquals(4, tool.calculateMinus(hand));
    }
    
    @Test
    public void simpleTest() {
        Card a = new Card(Suit.PATA, 1);
        Card b = new Card(Suit.PATA, 2);
        Card c = new Card(Suit.PATA, 3);
        Card d = new Card(Suit.HERTTA, 4);
        Card e = new Card(Suit.RISTI, 6);
        Card f = new Card(Suit.PATA, 6);
        Card g = new Card(Suit.RUUTU, 4);
        Card h = new Card(Suit.RUUTU, 5);
        Card i = new Card(Suit.RUUTU, 7);
        Card j = new Card(Suit.HERTTA, 10);
        
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(a);
        hand.add(b);
        hand.add(c);
        hand.add(d);
        hand.add(e);
        hand.add(f);
        hand.add(g);
        hand.add(h);
        hand.add(i);
        hand.add(j);
        int minus = tool.calculateMinus(hand);
        assertEquals(42, minus);
    }
    
    @Test
    public void multipleOptionTest() {
        Card a = new Card(Suit.PATA, 1);
        Card b = new Card(Suit.PATA, 2);
        Card c = new Card(Suit.PATA, 3);
        Card d = new Card(Suit.HERTTA, 3);
        Card e = new Card(Suit.RUUTU, 1);
        Card f = new Card(Suit.RUUTU, 2);
        Card g = new Card(Suit.RUUTU, 3);
        Card h = new Card(Suit.RUUTU, 6);
        Card i = new Card(Suit.RISTI, 2);
        Card j = new Card(Suit.RISTI, 3);
        
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(a);
        hand.add(b);
        hand.add(c);
        hand.add(d);
        hand.add(e);
        hand.add(f);
        hand.add(g);
        hand.add(h);
        hand.add(i);
        hand.add(j);
        int minus = tool.calculateMinus(hand);
        assertEquals(8, minus);        
    }
    
    @Test
    public void testMaxCombo() {
        Card a = new Card(Suit.PATA, 1);
        Card b = new Card(Suit.PATA, 2);
        Card c = new Card(Suit.PATA, 3);
        Card d = new Card(Suit.HERTTA, 3);
        Card e = new Card(Suit.HERTTA, 1);
        Card f = new Card(Suit.HERTTA, 2);
        Card g = new Card(Suit.RUUTU, 3);
        Card h = new Card(Suit.RUUTU, 1);
        Card i = new Card(Suit.RUUTU, 2);
        Card j = new Card(Suit.RUUTU, 4);
        
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(a);
        hand.add(b);
        hand.add(c);
        hand.add(d);
        hand.add(e);
        hand.add(f);
        hand.add(g);
        hand.add(h);
        hand.add(i);
        hand.add(j);
        int minus = tool.calculateMinus(hand);
        assertEquals(0, minus);              
    }
    
    @Test
    public void permutationTest() {
        ArrayList<ArrayList<Card>>  sets= new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sets.add(new ArrayList<>());
        }
        ArrayList<ArrayList<ArrayList<Card>>> permutations = tool.listPermutations(sets);
        assertEquals(6, permutations.size());
        assertEquals(3,permutations.get(0).size());
        
    }
    
    @Test
    public void straightCheckRemovesCard() {
        Card a = new Card(Suit.PATA, 1);
        Card b = new Card(Suit.PATA, 2);
        Card c = new Card(Suit.PATA, 3);
        Card d = new Card(Suit.PATA, 4);
        Card e = new Card(Suit.PATA, 5);
        Card f = new Card(Suit.PATA, 10);
        Card g = new Card(Suit.RUUTU, 4);
        Card h = new Card(Suit.HERTTA, 4);
        Card i = new Card(Suit.RISTI, 5);
        Card j = new Card(Suit.HERTTA, 10);
        
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(a);
        hand.add(b);
        hand.add(c);
        hand.add(d);
        hand.add(e);
        hand.add(f);
        hand.add(g);
        hand.add(h);
        hand.add(i);
        hand.add(j);
        int minus = tool.calculateMinus(hand);
        assertEquals(30, minus);        
    }
    
    @Test
    public void sortHandKeepsSameCards() {
        Card a = new Card(Suit.PATA, 1);
        Card b = new Card(Suit.PATA, 2);
        Card c = new Card(Suit.PATA, 3);
        Card d = new Card(Suit.PATA, 4);
        Card e = new Card(Suit.PATA, 5);
        Card f = new Card(Suit.PATA, 10);
        Card g = new Card(Suit.RUUTU, 4);
        Card h = new Card(Suit.HERTTA, 4);
        Card i = new Card(Suit.RISTI, 5);
        Card j = new Card(Suit.HERTTA, 10);
        
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(a);
        hand.add(b);
        hand.add(c);
        hand.add(d);
        hand.add(e);
        hand.add(f);
        hand.add(g);
        hand.add(h);
        hand.add(i);
        hand.add(j);
        tool.sortHand(hand);
        assertEquals(10, hand.size());        
    }
    
    @Test
    public void sortSetsWorks() {
        Card d = new Card(Suit.HERTTA, 4);
        Card e = new Card(Suit.RISTI, 4);
        Card f = new Card(Suit.PATA, 4);

        ArrayList<Card> hand = new ArrayList<>();

        hand.add(d);
        hand.add(e);
        hand.add(f);
        ArrayList<ArrayList<Card>> set = tool.sortSets(hand, 3);
        assertEquals(1, set.size());
    }
}
