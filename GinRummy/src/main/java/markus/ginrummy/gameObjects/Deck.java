/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameobjects;

import markus.ginrummy.gameobjects.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Markus
 */
public class Deck {

    private List<Card> cards;
    Random random = new Random();

    public Deck() {
        cards = new ArrayList<>();
        for (Suit s : Suit.values()) {
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(s, i);
                cards.add(card);
            }
        }
    }

    public void shuffle(int times) {
        for (int i = 0; i < times; i++) {
            ArrayList<Card> shuffled = new ArrayList<>();
            while (!cards.isEmpty()) {
                Card chosen = cards.get(random.nextInt(cards.size()));
                cards.remove(chosen);
                shuffled.add(chosen);
            }
            cards = shuffled;
        }
    }

    public Card takeCard() {
        if (cards.isEmpty()) {
            return null;
        } else {
            Card token = cards.get(0);
            cards.remove(token);
            return token;
        }
    }

    public List<Card> getCards() {
        return cards;
    }

}
