package chinchon.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona la baraja completa de cartas, permitiendo inicializarla, mezclarla,
 * repartir cartas y comprobar si está vacía. No hereda su comportamiento de
 * ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class Deck {

    /**
     * Almacena las cartas que forman la baraja.
     */
    private List<Card> cards = new ArrayList<>();

    /**
     * Inicializa la baraja añadiendo todas las cartas posibles.
     */
    public void initialize() {
        for (Suit p : Suit.values()) {
            for (NumCard t : NumCard.values()) {
                cards.add(new Card(p, t));
            }
        }
    }

    /**
     * Inicializa y mezcla las cartas de la baraja.
     */
    public void shuffleDeck() {
    	initialize();
        Collections.shuffle(cards);
    }

    /**
     * Reparte un número determinado de cartas desde la baraja.
     * @param n número de cartas que se van a repartir.
     * @return lista de cartas repartidas.
     */
    public List<Card> serve(int n) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            hand.add(cards.remove(0));
        }
        return hand;
    }

    /**
     * Comprueba si la baraja no contiene cartas.
     * @return true si la baraja está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
    	if(cards.isEmpty()) {
    		return true;
    	}else {
    		return false;
    	}
    }

    /**
     * Devuelve la lista de cartas de la baraja.
     * @return cartas actuales de la baraja.
     */
    public List<Card> getDeck(){
    	return cards;
    }
}
