/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markus.ginrummy.logiikka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Markus
 */
public class HandTools {
    
    public int calculateMinus(ArrayList<Card> hand) {
        ArrayList<ArrayList<Card>> sets = sortSets(hand);
        ArrayList<ArrayList<Card>> straights = sortStraights(hand);
        ArrayList<ArrayList<Card>> allSets = new ArrayList<>();
        allSets.addAll(sets);
        allSets.addAll(straights);
        if (allSets.size() == 1) {
            return setValue(hand) - fullValue(allSets);
        } else if (allSets.size() == 2) {
            int first = 0;
            int second = 0;
            for (int i = 0; i < 2; i++) {
                int b;
                if (i == 0) {
                    b = 1;
                } else {
                    b = 0;
                }
                ArrayList<Card> tempSet = (ArrayList<Card>) allSets.get(i).clone();
                removeDuplicates(allSets.get(b), tempSet);
                if (setCheck(tempSet) || straightCheck(tempSet)) {
                    first = setValue(allSets.get(b)) + setValue(tempSet);
                } else {
                    first = setValue(allSets.get(b));
                }
                if (second < first) {
                    second = first;
                }
            }
            return setValue(hand) - second;
        } else if (allSets.size() == 3) {
            int totalValue = 0;
            for (int i = 0; i < 3; i++) {
                ArrayList<ArrayList<Card>> tempSets = (ArrayList<ArrayList<Card>>) allSets.clone();
                ArrayList<Card> firstSet = (ArrayList<Card>) tempSets.get(i).clone();
                tempSets.remove(i);
                for (int j = 0; j < 2; j++) {
                    ArrayList<Card> secondSet = (ArrayList<Card>) tempSets.get(j).clone();
                    int k;
                    if (j == 0) {
                        k = 1;
                    } else {
                        k = 0;
                    }
                    ArrayList<Card> thirdSet = (ArrayList<Card>) tempSets.get(k).clone();
                    removeDuplicates(firstSet, secondSet);
                    removeDuplicates(firstSet, thirdSet);

                    int value = 0;
                    value+= setValue(firstSet); 
                    if (setCheck(secondSet) || straightCheck(secondSet)) {
                        value+= setValue(secondSet);
                        removeDuplicates(secondSet, thirdSet);                        
                    }
                    if (setCheck(thirdSet) || straightCheck(thirdSet)) {
                        value+= setValue(thirdSet);
                    }
                    if (value > totalValue) {
                        totalValue = value;
                    }
                }
            }
            return setValue(hand) - totalValue;
        } else if (allSets.size() == 4) {
            int totalValue = 0;
            for (int i = 0; i < 4; i++) {
                ArrayList<ArrayList<Card>> tempSets = (ArrayList<ArrayList<Card>>) allSets.clone();
                ArrayList<Card> firstSet = (ArrayList<Card>) tempSets.get(i).clone();
                tempSets.remove(i);
                for (int j = 0; j < 3; j++) {
                    ArrayList<ArrayList<Card>> secondTemp = (ArrayList<ArrayList<Card>>) tempSets.clone();
                    ArrayList<Card> secondSet = (ArrayList<Card>) secondTemp.get(j).clone();
                    secondTemp.remove(j);

                    for (int l = 0; l < 2; l++) {
                        ArrayList<Card> thirdSet = (ArrayList<Card>) secondTemp.get(l).clone();
                    
                        int k;
                        if (j == 0) {
                            k = 1;
                        } else {
                            k = 0;
                        }
                        ArrayList<Card> fourthSet = (ArrayList<Card>) tempSets.get(k).clone();
                        removeDuplicates(firstSet, secondSet);
                        removeDuplicates(firstSet, thirdSet);
                        removeDuplicates(firstSet, fourthSet);

                        int value = 0;
                        value+= setValue(firstSet); 
                        
                        if (setCheck(secondSet) || straightCheck(secondSet)) {
                            value+= setValue(secondSet);
                            removeDuplicates(secondSet, thirdSet);                        
                        }
                        if (setCheck(thirdSet) || straightCheck(thirdSet)) {
                            value+= setValue(thirdSet);
                        }
                        if (value > totalValue) {
                            totalValue = value;
                        }
                    }
                }
            }
            return setValue(hand) - totalValue;            
        } else if (allSets.size() == 5) {
            int totalValue = 0;
            for (int i = 0; i < 5; i++) {
                ArrayList<ArrayList<Card>> tempSets = (ArrayList<ArrayList<Card>>) allSets.clone();
                ArrayList<Card> firstSet = (ArrayList<Card>) tempSets.get(i).clone();
                tempSets.remove(i);
                for (int j = 0; j < 4; j++) {
                    ArrayList<ArrayList<Card>> secondTemp = (ArrayList<ArrayList<Card>>) tempSets.clone();
                    ArrayList<Card> secondSet = (ArrayList<Card>) secondTemp.get(j).clone();
                    secondTemp.remove(j);

                    for (int l = 0; l < 3; l++) {
                        ArrayList<ArrayList<Card>> thirdTemp = (ArrayList<ArrayList<Card>>) secondTemp.clone();
                        ArrayList<Card> thirdSet = (ArrayList<Card>) thirdTemp.get(l).clone();
                        thirdTemp.remove(l);
                        
                        for (int g = 0; g < 2; g++) {
                            
                        
                            ArrayList<Card> fourthSet = (ArrayList<Card>) thirdTemp.get(g).clone();

                            int k;
                            if (j == 0) {
                                k = 1;
                            } else {
                                k = 0;
                            }
                            ArrayList<Card> fifthSet = (ArrayList<Card>) tempSets.get(k).clone();
                            removeDuplicates(firstSet, secondSet);
                            removeDuplicates(firstSet, thirdSet);
                            removeDuplicates(firstSet, fourthSet);
                            removeDuplicates(firstSet, fifthSet);

                            int value = 0;
                            value+= setValue(firstSet); 

                            if (setCheck(secondSet) || straightCheck(secondSet)) {
                                value+= setValue(secondSet);
                                removeDuplicates(secondSet, thirdSet);                        
                            }
                            if (setCheck(thirdSet) || straightCheck(thirdSet)) {
                                value+= setValue(thirdSet);
                            }

                            if (value > totalValue) {
                                totalValue = value;
                            }
                        }
                    }
                }
            }
            return setValue(hand) - totalValue;                   
        } else if (allSets.size() == 6) {
            int totalValue = 0;
            for (int i = 0; i < 6; i++) {
                ArrayList<ArrayList<Card>> tempSets = (ArrayList<ArrayList<Card>>) allSets.clone();
                ArrayList<Card> firstSet = (ArrayList<Card>) tempSets.get(i).clone();
                tempSets.remove(i);
                for (int j = 0; j < 5; j++) {
                    ArrayList<ArrayList<Card>> secondTemp = (ArrayList<ArrayList<Card>>) tempSets.clone();
                    ArrayList<Card> secondSet = (ArrayList<Card>) secondTemp.get(j).clone();
                    secondTemp.remove(j);

                    for (int l = 0; l < 4; l++) {
                        ArrayList<ArrayList<Card>> thirdTemp = (ArrayList<ArrayList<Card>>) secondTemp.clone();
                        ArrayList<Card> thirdSet = (ArrayList<Card>) thirdTemp.get(l).clone();
                        thirdTemp.remove(l);
                        
                        for (int g = 0; g < 3; g++) {
                            ArrayList<ArrayList<Card>> fourthTemp = (ArrayList<ArrayList<Card>>) thirdTemp.clone();
                            ArrayList<Card> fourthSet = (ArrayList<Card>) fourthTemp.get(g).clone();
                            fourthTemp.remove(g);                            
                            for (int h = 0; h < 2; h++) {
                                
                            
                                ArrayList<Card> fifthSet = (ArrayList<Card>) fourthTemp.get(h).clone();

                                int k;
                                if (j == 0) {
                                    k = 1;
                                } else {
                                    k = 0;
                                }
                                ArrayList<Card> sixthSet = (ArrayList<Card>) tempSets.get(k).clone();
                                removeDuplicates(firstSet, secondSet);
                                removeDuplicates(firstSet, thirdSet);
                                removeDuplicates(firstSet, fourthSet);
                                removeDuplicates(firstSet, fifthSet);
                                removeDuplicates(firstSet, sixthSet);

                                int value = 0;
                                value+= setValue(firstSet); 

                                if (setCheck(secondSet) || straightCheck(secondSet)) {
                                    value+= setValue(secondSet);
                                    removeDuplicates(secondSet, thirdSet);                        
                                }
                                if (setCheck(thirdSet) || straightCheck(thirdSet)) {
                                    value+= setValue(thirdSet);
                                }
                                if (value > totalValue) {
                                    totalValue = value;
                                }
                            }
                        }
                    }
                }
            }
            return setValue(hand) - totalValue; 
        } else {
            return setValue(hand);
        }
    }
    
    public void sortHand(ArrayList<Card> hand) {
        ArrayList<ArrayList<Card>> sets = sortSets(hand);
        ArrayList<ArrayList<Card>> straights = sortSets(hand);
        ArrayList<ArrayList<Card>> allSets = new ArrayList<>();
        allSets.addAll(sets);
        allSets.addAll(straights);
        if (allSets.size() == 1) {
            ArrayList<Card> tempHand = (ArrayList<Card>) hand.clone();
            ArrayList<Card> sortedHand = new ArrayList<>();
            tempHand.removeAll(allSets.get(0));
            sortedHand.addAll(allSets.get(0));
            sortedHand.addAll(tempHand);
            hand.clear();
            hand.addAll(sortedHand);
        } else if (allSets.size() == 2) {
      
            int first = 0;
            int second = 0;
            ArrayList<Card> sortedFinal = new ArrayList<>();            
            for (int i = 0; i < 2; i++) {
                ArrayList<Card> tempHand = (ArrayList<Card>) hand.clone();
                ArrayList<Card> sortedHand = new ArrayList<>();                      
                int b;
                if (i == 0) {
                    b = 1;
                } else {
                    b = 0;
                }
                ArrayList<Card> tempSet = (ArrayList<Card>) allSets.get(i).clone();
                removeDuplicates(allSets.get(b), tempSet);
                if (setCheck(tempSet) || straightCheck(tempSet)) {
                    first = setValue(allSets.get(b)) + setValue(tempSet);
                    tempHand.removeAll(allSets.get(b));
                    tempHand.removeAll(tempSet);
                    sortedHand.addAll(allSets.get(b));
                    sortedHand.addAll(tempSet);
                    sortedHand.addAll(tempHand);
                } else {
                    first = setValue(allSets.get(b));
                    tempHand.removeAll(allSets.get(b));
                    sortedHand.addAll(allSets.get(b));
                    sortedHand.addAll(tempHand);
                }
                if (second < first) {
                    second = first;
                    sortedFinal = sortedHand;
                }
            }
            hand.clear();
            hand.addAll(sortedFinal);
        } else if (allSets.size() == 3) {       
            int totalValue = 0;
            ArrayList<Card> sortedFinal = new ArrayList<>();        
            for (int i = 0; i < 3; i++) {
              
                ArrayList<ArrayList<Card>> tempSets = (ArrayList<ArrayList<Card>>) allSets.clone();
                ArrayList<Card> firstSet = (ArrayList<Card>) tempSets.get(i).clone();
                tempSets.remove(i);
                for (int j = 0; j < 2; j++) {
                    ArrayList<Card> tempHand = (ArrayList<Card>) hand.clone();
                    ArrayList<Card> sortedHand = new ArrayList<>();                          
                    ArrayList<Card> secondSet = (ArrayList<Card>) tempSets.get(j).clone();
                    int k;
                    if (j == 0) {
                        k = 1;
                    } else {
                        k = 0;
                    }
                    ArrayList<Card> thirdSet = (ArrayList<Card>) tempSets.get(k).clone();
                    removeDuplicates(firstSet, secondSet);
                    removeDuplicates(firstSet, thirdSet);

                    int value = 0;
                    value+= setValue(firstSet);
                    tempHand.removeAll(firstSet);
                    sortedHand.addAll(firstSet);
                    
                    if (setCheck(secondSet) || straightCheck(secondSet)) {
                        value+= setValue(secondSet);
                    removeDuplicates(secondSet, thirdSet);                        
                        tempHand.removeAll(secondSet);
                        sortedHand.addAll(secondSet);
                    }
                    if (setCheck(thirdSet) || straightCheck(thirdSet)) {
                        value+= setValue(thirdSet);
                        tempHand.removeAll(thirdSet);
                        sortedHand.addAll(thirdSet);
                    }
                    sortedHand.addAll(tempHand);
                    if (value > totalValue) {
                        totalValue = value;
                        sortedFinal = sortedHand;
                    }
                }
            }
            hand.clear();
            hand.addAll(sortedFinal);

        } else {
            SuitValueComparator comp = new SuitValueComparator();
            hand.sort(comp);
        }        
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
            