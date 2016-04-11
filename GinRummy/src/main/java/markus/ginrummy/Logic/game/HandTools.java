/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logic.game;

import markus.ginrummy.gameObjects.ValueComparator;
import markus.ginrummy.gameObjects.SuitValueComparator;
import markus.ginrummy.gameObjects.Suit;
import markus.ginrummy.gameObjects.Card;
import java.util.ArrayList;
import markus.ginrummy.gameObjects.Card;
import markus.ginrummy.gameObjects.Suit;
import markus.ginrummy.gameObjects.SuitValueComparator;
import markus.ginrummy.gameObjects.ValueComparator;

/**
 *
 * @author Markus
 */
public class HandTools {
    
    public int calculateMinus(ArrayList<Card> hand) {
        ArrayList<ArrayList<Card>> chosenSets = chooseSets(hand);
        return setValue(hand) - fullValue(chosenSets);
    }
    
    public void loopingMethod(ArrayList<ArrayList<Card>> list, ArrayList<ArrayList<ArrayList<Card>>> permutations, ArrayList<ArrayList<Card>> permutation) {

        int size = list.size();
        if (size == 0 || permutation.size() == 3) {
            
            permutations.add((ArrayList<ArrayList<Card>>) permutation.clone());
            permutation.clear();
        } else {
            for (int i = 0; i < size; i++) {
                ArrayList<ArrayList<Card>> tempList = (ArrayList<ArrayList<Card>>) list.clone();
                permutation.add(tempList.get(i));
                tempList.remove(i);
                loopingMethod(tempList, permutations, permutation);   
            }
        }

    }
    
    public ArrayList<ArrayList<Card>> chooseSets(ArrayList<Card> hand) {
                ArrayList<ArrayList<Card>> sets = sortSets(hand);
        ArrayList<ArrayList<Card>> straights = sortStraights(hand);
        ArrayList<ArrayList<Card>> allSets = new ArrayList<>();
        allSets.addAll(sets);
        allSets.addAll(straights);
        ArrayList<ArrayList<ArrayList<Card>>> permutations = listPermutations(allSets);
        ArrayList<ArrayList<Card>> chosenSets = new ArrayList<>();
        for (ArrayList<ArrayList<Card>> perm : permutations) {
            ArrayList<ArrayList<Card>> permutation = new ArrayList<>();
            for (ArrayList<Card> set : perm) {
                permutation.add((ArrayList<Card>) set.clone());
            }
            ArrayList<ArrayList<Card>> tempSets = new ArrayList<>();
            int size = permutation.size();
            if (size > 3) {
                size = 3;
            }
            for (int i = 0; i < size; i++) {
                ArrayList<Card> set = (ArrayList<Card>) permutation.get(i);
                if (setCheck(set) || straightCheck(set)) {
                    tempSets.add(set);
                    for (int a = i + 1; a < size; a++) {
                        removeDuplicates(set, permutation.get(a));
                    }

                }
            }
            if (fullValue(tempSets) > fullValue(chosenSets)) {
                chosenSets = tempSets;
            }
        }
        return chosenSets;
    }
    
    public ArrayList<ArrayList<ArrayList<Card>>> listPermutations(ArrayList<ArrayList<Card>> list) {
        ArrayList<ArrayList<ArrayList<Card>>> permutations = new ArrayList<>();
        ArrayList<ArrayList<Card>> permutation = new ArrayList<>();
        loopingMethod(list, permutations, permutation);
        return permutations;
    }
    
    public void sortHand(ArrayList<Card> hand) {
        ArrayList<ArrayList<Card>> chosenSets = chooseSets(hand);
        ArrayList<Card> newHand = new ArrayList<>();
        for (ArrayList<Card> set : chosenSets) {
            newHand.addAll(set);
            hand.removeAll(set);
        }
        SuitValueComparator comp = new SuitValueComparator();
        hand.sort(comp);
        newHand.addAll(hand);
        hand.clear();
        hand.addAll(newHand);
    }
    
    public int setValue(ArrayList<Card> set) {
        int value = 0;
        for (Card c : set) {
            value+= c.getValue();
        }
        return value;
    }
    
    public int fullValue(ArrayList<ArrayList<Card>> sets) {
        int value = 0;
        for (ArrayList<Card> set : sets) {
            value+= setValue(set);
        }
        return value;
    }
    
    public boolean straightCheck(ArrayList<Card> set) {
        ArrayList<ArrayList<Card>> straights = sortStraights(set);
        if (straights.isEmpty()) {
            return false;
        } else {
            ArrayList<Card> newSet = new ArrayList<>();
            for (ArrayList<Card> straight : straights) {
                newSet.addAll(straight);
            }
            set.clear();
            set.addAll(newSet);
            return true;
        }
    }
    
    public boolean setCheck(ArrayList<Card> set) {
        int value = 0;
        int counter = 0;
        for (Card c : set) {
            if (counter == 0) {
                value = c.getValue();
            } else {
                if (value != c.getValue()) {
                    return false;
                }
            }
            counter++;
        }
        if (counter < 3) {
            return false;
        }
        return true;
    }
    
    public ArrayList<ArrayList<Card>> sortSets(ArrayList<Card> hand) {
        ArrayList<Card> tempHand = (ArrayList<Card>) hand.clone();
        ArrayList<ArrayList<Card>> sets = new ArrayList<>();
        ValueComparator comp = new ValueComparator();
        tempHand.sort(comp);
        ArrayList<Card> toAdd = new ArrayList<>();
        for (int i = 0; i < tempHand.size() - 1; i++) {
            Card c = tempHand.get(i);
            if (!toAdd.contains(c)) {
                toAdd.add(c);
            }
            Card s = tempHand.get(i + 1);
            if (s.getValue() == c.getValue()) {
                toAdd.add(s);
            } else {
                if (toAdd.size() > 2) {
                    sets.add(toAdd);
                }
                toAdd = new ArrayList<>();
            }
        }
        if (toAdd.size() > 2) {
            sets.add(toAdd);
        }
        return sets;
    }
    
    public ArrayList<ArrayList<Card>> sortStraights(ArrayList<Card> hand) {
        ArrayList<Card> tempHand = (ArrayList<Card>) hand.clone();
        SuitValueComparator comp = new SuitValueComparator();
        tempHand.sort(comp);
        ArrayList<ArrayList<Card>> straights = new ArrayList<>();
        ArrayList<Card> toAdd = new ArrayList<>();
        int lastCard = 0;
        Suit suit = Suit.RISTI;
        int counter = 0;
        for (Card c : tempHand) {
            if (counter == 0) {
                lastCard = c.getValue();
                suit = c.getSuit();
                toAdd.add(c);
            } else {
                int dist = Math.abs(lastCard - c.getValue());
                if (dist == 1 && suit.equals(c.getSuit())) {
                    lastCard = c.getValue();
                    toAdd.add(c);
                } else {
                    if (counter > 2) {
                        counter = 0;
                        straights.add(toAdd);
                        toAdd = new ArrayList<>();
                        lastCard = c.getValue();
                        suit = c.getSuit();
                        toAdd.add(c);
                    } else {
                        counter = 0;
                        toAdd = new ArrayList<>();
                        lastCard = c.getValue();
                        suit = c.getSuit();
                        toAdd.add(c);
                    }
                }
            }
            counter++;
        }
        if (counter > 2) {
            straights.add(toAdd);
        }
        return straights;
    }
    
    public boolean removeDuplicates(ArrayList<Card> keep, ArrayList<Card> removeFrom) {
        boolean found = false;
        for (Card c : keep) {
            if (removeFrom.contains(c)) {
                removeFrom.remove(c);
                found = true;
            }
        }
        return found;
    }
}
            