/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameobjects;

import java.util.Comparator;

/**
 * Maan perusteella kortteja vertaava vertailija.
 *
 * @author Markus
 */
public class SuitComparator implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return o1.getSuit().compareTo(o2.getSuit());
    }

}
