/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameobjects;

/**
 * Pelin korttiolio.
 *
 * @author Markus
 */
public class Card {

    private final Suit suit;
    private final int value;

    /**
     * Luo uuden määritellyn kortin. Heittää IllegalArgumentExceptionin, jos
     * kortin arvoa ei tueta.
     *
     * @param suit Kortin maa.
     * @param value Kortin arvo.
     */
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
        return suit + ":" + value;
    }

    /**
     * Palauttaa kortin arvon merkkijonomuodossa. Tunnistaa A:n, J:n, jne.
     *
     * @return kortin arvo merkkijonomuodossa.
     */
    public String valueString() {
        if (value == 1) {
            return "A";
        } else if (value == 11) {
            return "J";
        } else if (value == 12) {
            return "Q";
        } else if (value == 13) {
            return "K";
        } else {
            return "" + value;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

}
