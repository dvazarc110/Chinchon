package chinchon.player;

import java.util.List;

import chinchon.deck.Card;

/**
 * Representa a un jugador humano, permitiendo seleccionar manualmente la carta
 * que desea descartar y mostrar su mano. Hereda su comportamiento base de la
 * clase Player.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class PlayerHuman extends Player {

    /**
     * Construye un jugador humano con un nombre determinado.
     * @param name nombre del jugador humano.
     */
    public PlayerHuman(String name) {
        super(name);
    }

    /**
     * Elige una carta de la mano según la posición indicada.
     * @param sel posición de la carta seleccionada.
     * @param discard cartas disponibles en el montón de descartes.
     * @return carta elegida para descartar.
     */
    @Override
    public Card choiceDiscard(int sel, List<Card> discard) {
        return hand.get(sel);
    }

    /**
     * Muestra por consola la mano actual del jugador.
     */
    public void showHand() {
    	System.out.println(getHand());
    }
}
