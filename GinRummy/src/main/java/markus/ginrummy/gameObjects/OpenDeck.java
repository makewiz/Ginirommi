/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Pelin avopakka olio.
 *
 * @author Markus
 */
public class OpenDeck {

    private List<Card> cards;

    /**
     * Luo uuden avopakan.
     */
    public OpenDeck() {
        cards = new ArrayList<>();
    }

    /**
     * Metodi lisää pakan päälle uuden kortin.
     *
     * @param c Parametrina annettava pakkaan lisättävä kortti.
     */
    public void addCard(Card c) {
        cards.add(0, c);
    }

    /**
     * Palautta pakan päälimmäisen koprtin.
     *
     * @return
     */
    public Card topCard() {
        if (cards.isEmpty()) {
            return null;
        } else {
            return cards.get(0);
        }
    }

    /**
     * Palauttaa pakan päälimmäisen kortin poistaen sen samalla.
     *
     * @return
     */
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
