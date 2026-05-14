package chinchon.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import chinchon.deck.Card;

/**
 * Representa la base común de cualquier jugador, almacenando su nombre y mano,
 * y definiendo operaciones compartidas para gestionar cartas. Implementa la
 * interfaz IPlayer y delega la elección de descarte a sus subclases.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public abstract class Player implements IPlayer {

    /**
     * Almacena el nombre del jugador.
     */
    protected String name;

    /**
     * Almacena las cartas que forman la mano del jugador.
     */
    protected List<Card> hand = new ArrayList<>();

    /**
     * Construye un jugador con un nombre determinado.
     * @param name nombre del jugador.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Añade una carta a la mano del jugador.
     * @param card carta que se va a añadir.
     */
    @Override
    public void getCard(Card card) {

        if (card == null) {
            throw new IllegalArgumentException("No se puede añadir una carta null");
        }

        hand.add(card);
    }

    /**
     * Devuelve la mano del jugador ordenada por valor.
     * @return lista de cartas de la mano del jugador.
     */
    @Override
    public List<Card> getHand() {
    	hand.sort(Comparator.comparing(Card::getValue));
        return hand;
    }

    /**
     * Elimina una carta de la mano del jugador.
     * @param card carta que se va a eliminar.
     */
    @Override
    public void deleteCard(Card card) {
        hand.remove(card);
    }

    /**
     * Devuelve el nombre del jugador.
     * @return nombre del jugador.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Elige la carta que el jugador desea descartar.
     * @param sel posición seleccionada para descartar.
     * @param discard cartas disponibles en el montón de descartes.
     * @return carta elegida para descartar.
     */
    @Override
    public abstract Card choiceDiscard(int sel, List<Card> discard);
}
