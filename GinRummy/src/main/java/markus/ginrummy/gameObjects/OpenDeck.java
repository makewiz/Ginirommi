/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameobjects;

import markus.ginrummy.gameobjects.Card;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markus
 */
public class OpenDeck {

    private List<Card> cards;

    public OpenDeck() {
        cards = new ArrayList<>();
    }

    public void addCard(Card c) {
        cards.add(0, c);
    }

    public Card topCard() {
        if (cards.isEmpty()) {
            return null;
        } else {
            return cards.get(0);
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
