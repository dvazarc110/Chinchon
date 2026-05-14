package chinchon.table;

import java.util.ArrayList;
import java.util.List;

import chinchon.deck.Card;
import chinchon.deck.Deck;
import chinchon.deck.DiscardPile;
import chinchon.deck.DrawPile;
import chinchon.player.IPlayer;

/**
 * Gestiona el desarrollo de una ronda, incluyendo reparto inicial, robo,
 * descartes, reciclaje del montón de descartes y limpieza final. No hereda
 * su comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class AgentRound {

    /**
     * Almacena el mazo de robo usado durante la ronda.
     */
    private DrawPile drawPile = new DrawPile();

    /**
     * Almacena el montón de cartas descartadas durante la ronda.
     */
    private DiscardPile discardPile = new DiscardPile();

    /**
     * Almacena el contador de repeticiones relacionadas con el descarte.
     */
    private int repeat; 

    public void startRound(List<IPlayer> players) {

        Deck deck = new Deck();
        List<Card> all = new ArrayList<>();
        deck.shuffleDeck();

        while (!deck.isEmpty()) {
            all.addAll(deck.serve(1));
        }

        drawPile.load(all);

        for (IPlayer p : players) {
            for (int i = 0; i < 7; i++) {
                p.getCard(drawPile.draw());
            }
        }
        
        discardPile.discard(drawPile.draw());
    }

    /**
     * Finaliza la ronda eliminando las cartas de los jugadores y limpiando los mazos.
     * @param players jugadores cuyas manos se van a limpiar.
     */
    public void endRound(List<IPlayer> players) {

        for (IPlayer p : players) {
            for (int i = 0; i < 7; i++) {
                p.deleteCard((p.getHand()).get(0));
            }
        }
        drawPile.deleteCards();
        discardPile.deleteCards();
    }


    /**
     * Toma una carta del mazo de robo, reciclando descartes si es necesario.
     * @return carta tomada del mazo de robo.
     */
    public Card takeCard() {

    	Card c;

        if (drawPile.isEmpty()) {
            recicleDiscards();
        }

        c = drawPile.draw();

        // 🔴 CONTROL CRÍTICO
        if (c == null) {
            throw new IllegalStateException("No hay cartas disponibles para robar");
        }

        return c;
    }

    /**
     * Consulta la última carta del montón de descartes sin retirarla.
     * @return última carta descartada, o null si no existe.
     */
    public Card seeDiscards() {
        return discardPile.seeLast();
    }

    /**
     * Toma la última carta del montón de descartes.
     * @return última carta descartada, o null si no existe.
     */
    public Card takeDiscard() {
        return discardPile.takeLast();
    }

    /**
     * Envía una carta al montón de descartes.
     * @param c carta que se va a descartar.
     */
    public void toDiscard(Card c) {
        discardPile.discard(c);
    }

    /**
     * Recicla las cartas descartadas, dejando la última carta visible en el descarte.
     */
    private void recicleDiscards() {

        List<Card> recicled = discardPile.emptyExceptLast();

        if (recicled.isEmpty()) {
            return;
        }

        drawPile.insertCards(recicled);
    }

    /**
     * Devuelve las cartas actuales del montón de descartes.
     * @return lista de cartas descartadas.
     */
    public List<Card> getDiscards(){
    	return discardPile.getDiscarded();
    }

    /**
     * Incrementa el contador de repeticiones.
     */
    public void addRepeat() {
    	repeat++;
    }

    /**
     * Reinicia el contador de repeticiones a uno.
     */
    public void resetRepeat() {
    	repeat = 1;
    }

    /**
     * Devuelve el contador actual de repeticiones.
     * @return número actual de repeticiones.
     */
    public int getRepeat() {
    	return repeat;
    }

}

