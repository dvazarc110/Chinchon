package chinchon.deck;

import java.util.*;

/**
 * Representa el mazo de robo durante la ronda, permitiendo cargar cartas,
 * robarlas, insertar cartas recicladas y comprobar si está vacío. No hereda
 * su comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class DrawPile {

    /**
     * Almacena las cartas disponibles para robar.
     */
    private Deque<Card> cards = new ArrayDeque<>();

    /**
     * Carga una lista de cartas en el mazo de robo después de mezclarla.
     * @param list lista de cartas que se van a cargar.
     */
    public void load(List<Card> list) {
        Collections.shuffle(list);
        cards.addAll(list);
    }

    /**
     * Roba una carta del mazo de robo.
     * @return carta robada, o null si el mazo está vacío.
     */
    public Card draw() {
        return cards.isEmpty() ? null : cards.poll();
    }

    /**
     * Comprueba si el mazo de robo está vacío.
     * @return true si no hay cartas, false en caso contrario.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Inserta una lista de cartas en el mazo de robo después de mezclarla.
     * @param list lista de cartas que se van a insertar.
     */
    public void insertCards(List<Card> list) {
        Collections.shuffle(list);
        cards.addAll(list);
    }

    /**
     * Elimina todas las cartas del mazo de robo.
     */
    public void deleteCards() {
    	cards.clear();
    }
}
