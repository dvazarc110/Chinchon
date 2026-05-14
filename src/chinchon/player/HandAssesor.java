package chinchon.player;

import java.util.ArrayList;
import java.util.List;

import chinchon.deck.Card;
import chinchon.deck.Deck;

/**
 * Evalúa la utilidad de una mano de jugador, calculando su puntuación y contando
 * posibles cartas útiles para mejorarla. No hereda su comportamiento de ninguna
 * otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class HandAssesor {

    /**
     * Almacena el combinador usado para analizar manos.
     */
    private final HandCombiner combiner = new HandCombiner();

    /**
     * Calcula la puntuación de una mano.
     * @param hand mano de cartas que se va a evaluar.
     * @param player jugador propietario de la mano.
     * @return puntuación calculada de la mano.
     */
    public int countPunt(List<Card> hand, IPlayer player) {

        CombinationResult r = combiner.analise(hand, player);

        int points = r.sumScore();

        return points;
    }

    /**
     * Cuenta cuántas cartas podrían ser útiles para mejorar la mano.
     * @param hand mano actual del jugador.
     * @param discards cartas descartadas durante la partida.
     * @param player jugador propietario de la mano.
     * @return cantidad de cartas útiles detectadas.
     */
    public int usefullCards(List<Card> hand, List<Card> discards, IPlayer player) {

    	Deck aux = new Deck();
    	List<Card> auxdeck = new ArrayList<>();
    	List<Card> simulation;
    	int count = 0;
    	CombinationResult r;
    	aux.initialize();
    	auxdeck = aux.getDeck();
    	if(!(discards.isEmpty())) {
    		auxdeck.removeAll(discards);
    	}
    	auxdeck.removeAll(hand);

    	for(Card c : auxdeck) {
    		simulation = new ArrayList<>(hand);
    		simulation.add(c);
    		r = combiner.analise(hand, player);
    		if(r.getCombinations().size() >=1) {
    			count++;
    		}
    	}

        return count;
    }   
}
