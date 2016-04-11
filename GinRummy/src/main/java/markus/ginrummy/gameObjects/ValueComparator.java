/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.gameObjects;

import markus.ginrummy.gameObjects.Card;
import java.util.Comparator;

/**
 *
 * @author Markus
 */
public class ValueComparator implements Comparator<Card>{

    @Override
    public int compare(Card o1, Card o2) {
        return o1.getValue() - o2.getValue();
    }
    
}
