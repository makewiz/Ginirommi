/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameobjects;

import java.util.Comparator;

/**
 * Maan ja arvon perusteella kortteja vertaileva vertailija.
 *
 * @author Markus
 */
public class SuitValueComparator implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        SuitComparator suits = new SuitComparator();
        ValueComparator values = new ValueComparator();
        if (suits.compare(o1, o2) == 0) {
            return values.compare(o1, o2);
        } else {
            return suits.compare(o1, o2);
        }
    }

}
