/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logiikka;

/**
 *
 * @author Markus
 */
public class Card {
    private final Suit suit;
    private final int value;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        if (value < 1 || value > 13) {
            throw new IllegalArgumentException("Illegal playing card value");
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value == 1) {
            return suit + " A";
        } else if (value == 11) {
            return suit + " J";
        } else if (value == 12) {
            return suit + " Q";
        } else if (value == 13) {
            return suit + " K";
        } else {
            return suit + " " + value;
        }
    }
    
    
}
