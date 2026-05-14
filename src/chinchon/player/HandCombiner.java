package chinchon.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinchon.deck.Card;
import chinchon.deck.Suit;

/**
 * Analiza una mano de cartas para extraer combinaciones válidas, como grupos de
 * cartas iguales o escaleras del mismo palo, gestionando conflictos entre ellas.
 * No hereda su comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class HandCombiner {

    /**
     * Analiza una mano y obtiene las combinaciones válidas junto con las cartas sobrantes.
     * @param hand mano de cartas que se va a analizar.
     * @param player jugador propietario de la mano analizada.
     * @return resultado del análisis con combinaciones y cartas sobrantes.
     */
    public CombinationResult analise(List<Card> hand, IPlayer player) {

    	List<Card> lefts = new ArrayList<>(hand);
    	List<List<Card>> groups = new ArrayList<>();
    	Card samecard;
    	int groupsize;

    	extractThreeOrFour(lefts, groups, hand);
    	extractStraight(lefts, groups, hand);

    	groupsize = groups.size();

    	if((groupsize == 2) && (lefts.size()>1)) {
    		samecard = checkCombinations(groups);
    		if(!(samecard == null)) {
    			if(!analiseCombinations(groups, samecard, hand)) {
    				selectCombination(groups, lefts, samecard);
    			}
    		}
    	}else if(groupsize > 2) {
    		removeExtraCard(groups, lefts);
    	}

    	return new CombinationResult(groups, lefts);
    }

    /**
     * Extrae grupos de tres o cuatro cartas con el mismo valor.
     * @param lefts lista de cartas sobrantes que se irá actualizando.
     * @param groups lista de combinaciones encontradas.
     * @param hand mano de cartas que se va a revisar.
     */
    private void extractThreeOrFour(List<Card> lefts, List<List<Card>> groups, List<Card> hand) {

        Map<Integer, List<Card>> map = new HashMap<>();

        for (Card c : hand) {

            map.computeIfAbsent(c.getValue(), k -> new ArrayList<>()).add(c);
        }

        for (List<Card> group : map.values()) {
            if (group.size() == 3 || group.size() == 4) {
                groups.add(new ArrayList<>(group));
                lefts.removeAll(group);
            }
        }
    }

    /**
     * Extrae escaleras de tres o más cartas consecutivas del mismo palo.
     * @param lefts lista de cartas sobrantes que se irá actualizando.
     * @param groups lista de combinaciones encontradas.
     * @param hand mano de cartas que se va a revisar.
     */
    private void extractStraight(List<Card> lefts, List<List<Card>> groups, List<Card> hand) {

        Map<Suit, List<Card>> suitGroups = new HashMap<>();
        List<Card> straight;
        Card prev;

        for (Card c : hand) {

            suitGroups.computeIfAbsent(c.getSuit(), k -> new ArrayList<>()).add(c);
        }


        for (List<Card> list : suitGroups.values()) {

            list.sort(Comparator.comparingInt(Card::getValue));

            straight = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {

                if (straight.isEmpty()) {
                    straight.add(list.get(i));
                } else {
                    prev = straight.get(straight.size() - 1);

                    if (list.get(i).getNum().ordinal() == prev.getNum().ordinal() + 1) {
                        straight.add(list.get(i));
                    } else {
                        if (straight.size() >= 3) {
                            groups.add(new ArrayList<>(straight));
                            lefts.removeAll(straight);
                        }
                        straight.clear();
                        straight.add(list.get(i));
                    }
                }
            }

            if (straight.size() >= 3) {
                groups.add(new ArrayList<>(straight));
                lefts.removeAll(straight);
            }
        }
    }

    /**
     * Comprueba si dos combinaciones comparten una misma carta.
     * @param groups lista de combinaciones que se van a comparar.
     * @return carta repetida entre combinaciones, o null si no hay coincidencia.
     */
    public Card checkCombinations(List<List<Card>> groups) {
    	List<Card> comb1 = groups.get(0);
    	List<Card> comb2 = groups.get(1);

    	for(Card c1 : comb1) {
    		for(Card c2 : comb2) {
        		if(c2 == c1) {
        			return c2;
        		}
        	}
    	}
    	return null;
    }

    /**
     * Elimina una combinación sobrante cuando existen más combinaciones de las permitidas.
     * @param groups lista de combinaciones detectadas.
     * @param lefts lista de cartas sobrantes que se actualizará con la carta liberada.
     */
    public void removeExtraCard(List<List<Card>> groups, List<Card> lefts) {
    	List<Card> comb1 = groups.get(0);
    	List<Card> comb2 = groups.get(1);
    	List<Card> comb3 = groups.get(2);
    	List<Card> combRemoved = new ArrayList<>();
    	List<List<Card>> extrac = new ArrayList<>(); 
    	Card same1 = null;
    	Card same2 = null;

    	for(Card c1 : comb1) {
    		for(Card c2 : comb2) {
        		if(c2 == c1) {
        			if(!(same1 == null)) {
        				same2 = c2;
        			}else {
        				same1 = c2;
        			}
        		}
        	}
    	}
    	
    	for(Card c2 : comb2) {
    		for(Card c3 : comb3) {
        		if(c2 == c3) {
        			if(!(same1 == null)) {
        				same2 = c2;
        			}else {
        				same1 = c2;
        			}
        		}
        	}
    	}
    	
    	for(Card c1 : comb1) {
    		for(Card c3 : comb3) {
        		if(c3 == c1) {
        			if(!(same1 == null)) {
        				same2 = c3;
        			}else {
        				same1 = c3;
        			}
        		}
        	}
    	}
    	
    	for(List<Card> group : groups) {
    		if(group.contains(same1) && group.contains(same2)) {
    			for(Card c : group) {
    				combRemoved.add(c);
    			}
    		}
    	}
    	
    	extrac.add(combRemoved);
    	groups.removeAll(extrac);
    	combRemoved.remove(same1);
    	combRemoved.remove(same2);
    	lefts.add(combRemoved.get(0));
    }

    /**
     * Analiza dos combinaciones que comparten una carta para decidir si ambas siguen siendo válidas.
     * @param groups lista de combinaciones que se va a modificar.
     * @param samecard carta compartida por las combinaciones.
     * @param hand mano original que sirve como referencia.
     * @return true si las combinaciones pueden mantenerse, false en caso contrario.
     */
    public boolean analiseCombinations(List<List<Card>> groups, Card samecard, List<Card> hand) {

    	List<Card> comb1 = groups.get(0);
    	List<Card> comb1_2 = new ArrayList<>();
    	List<Card> comb2 = groups.get(1);
    	List<Card> comb2_2 = new ArrayList<>();
    	List<List<Card>> group1 = new ArrayList<>();
    	List<List<Card>> group2 = new ArrayList<>();
    	int group1size;
    	int group2size;

    	for(Card c : comb1) {
    		comb1_2.add(c);
    	}
    	for(Card c : comb2) {
    		comb2_2.add(c);
    	}

    	groups.remove(comb1);
        groups.remove(comb2);

    	comb1.remove(samecard);
    	comb2.remove(samecard);

    	extractThreeOrFour(comb1_2, group1, comb1);
        extractStraight(comb1_2, group1, comb1);

        group1size = group1.size();

        extractThreeOrFour(comb2_2, group2, comb2);
        extractStraight(comb2_2, group2, comb2);

        group2size = group2.size();

        if((group1size == 1) && (group2size == 1)) {
        	comb2.add(samecard);
        	groups.add(comb1);        	
        	groups.add(comb2);
        	return true;
        }else if((group1size == 0) && (group2size == 1)) {
        	comb1.add(samecard);
        	groups.add(comb1);        	
        	groups.add(comb2);
        	return true;
        }else if((group1size == 1) && (group2size == 0)) {
        	comb2.add(samecard);        	
        	groups.add(comb1);        	
        	groups.add(comb2);
        	return true;
        }else {
        	comb1.add(samecard);
        	comb2.add(samecard);
        	groups.add(comb1);        	
        	groups.add(comb2);
        	return false;
        }
    }

    /**
     * Selecciona la mejor combinación cuando dos combinaciones comparten una carta.
     * @param groups lista de combinaciones que se va a actualizar.
     * @param lefts lista de cartas sobrantes que recibirá las cartas descartadas de la combinación no elegida.
     * @param samecard carta compartida entre las combinaciones.
     */
    public void selectCombination(List<List<Card>> groups, List<Card> lefts, Card samecard) {
    	List<Card> comb1 = groups.get(0);
    	int group1points = 0;
    	List<Card> comb2 = groups.get(1);
    	int group2points = 0;

    	for(Card c : comb1) {
    		group1points += c.getValue();
    	}

    	for(Card c : comb2) {
    		group2points += c.getValue();
    	}

    	groups.remove(comb1);
        groups.remove(comb2);

        if(group1points > group2points) {
        	comb2.remove(samecard);
        	groups.add(comb1); 
        	for(Card c : comb2) {
        		lefts.add(c);
        	}
        }else if(group1points < group2points) {
        	comb1.remove(samecard);       	
        	groups.add(comb2);
        	for(Card c : comb1) {
        		lefts.add(c);
        	}
        }else {
        	comb1.remove(samecard);       	
        	groups.add(comb2);
        	for(Card c : comb1) {
        		lefts.add(c);
        	}
        }
    }
}
