package chinchon.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinchon.deck.Card;
import chinchon.deck.Suit;

/**
 * Analiza una mano para detectar posibles combinaciones futuras, como parejas
 * o escaleras incompletas, que pueden ayudar a la estrategia del jugador. No
 * hereda su comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class PosibHandCombiner {

    /**
     * Analiza una mano para localizar posibles combinaciones futuras.
     * @param hand mano de cartas que se va a analizar.
     * @param player jugador propietario de la mano.
     * @return resultado con las posibles combinaciones y cartas sobrantes.
     */
    public PosibCombinationResult analizar(List<Card> hand, IPlayer player) {

    	List<Card> lefts = new ArrayList<>(hand);
    	List<List<Card>> groups = new ArrayList<>();
    	PosibCombinationResult resultfin;

    	extractTwo(lefts, groups, hand);
    	extractAlmostStraight(lefts, groups, hand);

    	return new PosibCombinationResult(groups, lefts);
    }

    /**
     * Extrae parejas de cartas con el mismo valor como posibles combinaciones.
     * @param lefts lista de cartas sobrantes que se irá actualizando.
     * @param groups lista de posibles combinaciones encontradas.
     * @param hand mano de cartas que se va a revisar.
     */
    private void extractTwo(List<Card> lefts, List<List<Card>> groups, List<Card> hand) {

        Map<Integer, List<Card>> map = new HashMap<>();

        for (Card c : hand) {

            map.computeIfAbsent(c.getValue(), k -> new ArrayList<>()).add(c);
        }

        for (List<Card> group : map.values()) {
            if (group.size() == 2) {
                groups.add(new ArrayList<>(group));
                lefts.removeAll(group);
            }
        }
    }

    /**
     * Extrae posibles escaleras incompletas de dos cartas del mismo palo.
     * @param lefts lista de cartas sobrantes que se irá actualizando.
     * @param groups lista de posibles combinaciones encontradas.
     * @param hand mano de cartas que se va a revisar.
     */
    private void extractAlmostStraight(List<Card> lefts, List<List<Card>> groups, List<Card> hand) {

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

                    if ((list.get(i).getNum().ordinal() == prev.getNum().ordinal() + 1) || (list.get(i).getNum().ordinal() == prev.getNum().ordinal() + 2)) {
                        straight.add(list.get(i));
                    } else {
                        if (straight.size() == 2) {
                            groups.add(new ArrayList<>(straight));
                            lefts.removeAll(straight);
                        }
                        straight.clear();
                        straight.add(list.get(i));
                    }
                }
            }

            if (straight.size() == 2) {
                groups.add(new ArrayList<>(straight));
                lefts.removeAll(straight);
            }
        }
    }
}
