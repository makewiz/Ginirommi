/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameObjects;

import markus.ginrummy.gameObjects.SuitComparator;
import markus.ginrummy.gameObjects.Card;
import java.util.Comparator;

/**
 *
 * @author Markus
 */
public class SuitValueComparator implements Comparator<Card>{

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
