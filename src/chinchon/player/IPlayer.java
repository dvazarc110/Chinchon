package chinchon.player;

import java.util.List;

import chinchon.deck.Card;

/**
 * Define el contrato común que deben cumplir todos los tipos de jugador,
 * incluyendo operaciones para recibir cartas, consultar la mano, descartar y
 * obtener el nombre. No hereda su comportamiento de ninguna otra interfaz propia.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public interface IPlayer {

    /**
     * Añade una carta a la mano del jugador.
     * @param card carta que se va a añadir.
     */
    void getCard(Card card);

    /**
     * Devuelve la mano actual del jugador.
     * @return lista de cartas del jugador.
     */
    List<Card> getHand();

    /**
     * Elige la carta que el jugador desea descartar.
     * @param sel posición seleccionada para descartar.
     * @param discard cartas disponibles en el montón de descartes.
     * @return carta elegida para descartar.
     */
    Card choiceDiscard(int sel, List<Card> discard);

    /**
     * Elimina una carta de la mano del jugador.
     * @param carta carta que se va a eliminar.
     */
    void deleteCard(Card carta);

    /**
     * Devuelve el nombre del jugador.
     * @return nombre del jugador.
     */
    String getName();
}
