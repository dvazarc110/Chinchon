package chinchon.deck;

import java.util.*;

/**
 * Representa el montón de descartes de la partida, permitiendo añadir cartas,
 * consultar o tomar la última carta descartada y reciclar las restantes. No
 * hereda su comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class DiscardPile {

    /**
     * Almacena las cartas que forman el montón de descartes.
     */
    private Deque<Card> cards = new ArrayDeque<>();

    /**
     * Añade una carta al montón de descartes.
     * @param card carta que se va a descartar.
     */
    public void discard(Card card) {
        cards.push(card);
    }

    /**
     * Consulta la última carta descartada sin retirarla.
     * @return última carta descartada, o null si no hay cartas.
     */
    public Card seeLast() {
        return cards.isEmpty() ? null : cards.peek();
    }

    /**
     * Retira y devuelve la última carta descartada.
     * @return última carta descartada, o null si no hay cartas.
     */
    public Card takeLast() {
        return cards.isEmpty() ? null : cards.pop();
    }

    /**
     * Vacía el montón de descartes excepto la última carta.
     * @return lista con las cartas retiradas del montón.
     */
    public List<Card> emptyExceptLast() {
    	Card last;
        List<Card> remainder;

        if (cards.isEmpty()) {
        	remainder = new ArrayList<>();
        }else {
	        last = cards.pop();
	        remainder = new ArrayList<>(cards);
	        cards.clear();
	        cards.push(last);
        }

        return remainder;
    }

    /**
     * Elimina todas las cartas del montón de descartes.
     */
    public void deleteCards() {
    	cards.clear();
    }

    /**
     * Devuelve las cartas descartadas actualmente.
     * @return lista con las cartas del montón de descartes.
     */
	public List<Card> getDiscarded(){
    	List<Card> remainder = new ArrayList<>(cards);
    	return remainder;
    }
}
